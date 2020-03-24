package com.codersteam.alwin.core.service.mapper;

import com.codersteam.aida.core.api.model.AidaCompanyDto;
import com.codersteam.aida.core.api.model.AidaInvoiceDto;
import com.codersteam.aida.core.api.model.AidaInvoiceWithCorrectionsDto;
import com.codersteam.aida.core.api.model.AidaPersonDto;
import com.codersteam.alwin.common.issue.Segment;
import com.codersteam.alwin.common.prop.AlwinProperties;
import com.codersteam.alwin.common.prop.AlwinPropertyKey;
import com.codersteam.alwin.core.api.model.activity.*;
import com.codersteam.alwin.core.api.model.customer.*;
import com.codersteam.alwin.core.api.model.demand.DemandForPaymentDto;
import com.codersteam.alwin.core.api.model.demand.DemandForPaymentTypeConfigurationDto;
import com.codersteam.alwin.core.api.model.demand.FormalDebtCollectionInvoiceDto;
import com.codersteam.alwin.core.api.model.issue.*;
import com.codersteam.alwin.core.api.model.operator.OperatorTypeDto;
import com.codersteam.alwin.core.api.model.tag.TagDto;
import com.codersteam.alwin.core.api.model.tag.TagIconDto;
import com.codersteam.alwin.core.api.model.tag.TagInputDto;
import com.codersteam.alwin.core.api.model.termination.TerminationContractDto;
import com.codersteam.alwin.core.api.model.termination.TerminationDto;
import com.codersteam.alwin.core.api.service.DateProvider;
import com.codersteam.alwin.core.api.service.termination.ContractTerminationInitialData;
import com.codersteam.alwin.core.comparator.TagComparator;
import com.codersteam.alwin.core.service.mapper.custom.InvoiceMapper;
import com.codersteam.alwin.jpa.IssueWallet;
import com.codersteam.alwin.jpa.User;
import com.codersteam.alwin.jpa.Wallet;
import com.codersteam.alwin.jpa.activity.*;
import com.codersteam.alwin.jpa.customer.*;
import com.codersteam.alwin.jpa.issue.*;
import com.codersteam.alwin.jpa.notice.DemandForPayment;
import com.codersteam.alwin.jpa.notice.DemandForPaymentTypeConfiguration;
import com.codersteam.alwin.jpa.notice.DemandForPaymentWithCompanyName;
import com.codersteam.alwin.jpa.termination.ContractTermination;
import com.codersteam.alwin.jpa.termination.FormalDebtCollectionContractOutOfService;
import com.codersteam.alwin.jpa.termination.FormalDebtCollectionCustomerOutOfService;
import com.codersteam.alwin.jpa.termination.FormalDebtCollectionInvoice;
import com.codersteam.alwin.jpa.type.ActivityTypeKey;
import com.codersteam.alwin.jpa.type.TagType;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.converter.ConverterFactory;
import ma.glasnost.orika.converter.builtin.PassThroughConverter;
import ma.glasnost.orika.impl.ConfigurableMapper;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static com.codersteam.alwin.core.comparator.ActivityTypeDetailPropertyDictValueComparatorProvider.ACTIVITY_TYPE_DETAIL_PROPERTY_DICT_VALUE_COMPARATOR;
import static com.codersteam.alwin.core.util.DateUtils.daysBetween;
import static com.codersteam.alwin.core.util.InvoiceUtils.isIssueSubjectForNewIssue;
import static java.util.Collections.emptyList;
import static org.apache.commons.collections.CollectionUtils.isEmpty;
import static org.apache.commons.collections.CollectionUtils.isNotEmpty;

/**
 * @author Tomasz Sliwinski
 */
@Stateless
public class AlwinMapper extends ConfigurableMapper {

    private DateProvider dateProvider;
    private AlwinProperties properties;

    public AlwinMapper() {
        super.init();
        // for deployment
    }

    @Inject
    public AlwinMapper(final DateProvider dateProvider, final AlwinProperties properties) {
        this.dateProvider = dateProvider;
        this.properties = properties;
        super.init();
    }

    @Override
    protected void init() {
        // delay init until date provider is set
    }

    @Override
    protected void configure(final MapperFactory factory) {
        registerConverterFactory(factory);
        personMapping(factory);
        issueMapping(factory);
        activityMapping(factory);
        activityTypeMapping(factory);
        activityDetailPropertyMapping(factory);
        aidaInvoiceMapping(factory);
        invoiceMapping(factory);
        aidaCompanyMapping(factory);
        aidaPersonMapping(factory);
        outOfServiceMapping(factory);
        issueTerminationRequestMapping(factory);
        walletMapping(factory);
        issueTypeConfigurationMapping(factory);
        tagMapping(factory);
        demandForPaymentMapping(factory);
        aidaInvoiceWithCorrectionsDtoToFormalDebtCollectionInvoiceMapping(factory);
        registerContractTerminationMapping(factory);
    }

    private void registerConverterFactory(final MapperFactory factory) {
        final ConverterFactory converterFactory = factory.getConverterFactory();
        converterFactory.registerConverter("customer-by-ref", new PassThroughConverter(Customer.class));
        converterFactory.registerConverter("contract-by-ref", new PassThroughConverter(Contract.class));
        converterFactory.registerConverter("issue-priority-dto", new IssuePriorityDtoConverter());
        converterFactory.registerConverter(new ContactTypeConverter());
        converterFactory.registerConverter(new ContactTypeDtoConverter());
        converterFactory.registerConverter(new ContactStateConverter());
        converterFactory.registerConverter(new ContactStateDtoConverter());
        converterFactory.registerConverter(new AddressTypeConverter());
        converterFactory.registerConverter(new AddressTypeDtoConverter());
        converterFactory.registerConverter(new ActivityStateConverter());
        converterFactory.registerConverter(new ActivityStateDtoConverter());
        converterFactory.registerConverter(new IssueTerminationRequestStateConverter());
        converterFactory.registerConverter(new IssueTerminationRequestStateDtoConverter());
        converterFactory.registerConverter(new SegmentConverter());
        converterFactory.registerConverter(new SchedulerTaskTypeConverter());
        converterFactory.registerConverter(new SchedulerTaskTypeDtoConverter());
        converterFactory.registerConverter(new SchedulerBatchProcessTypeConverter());
        converterFactory.registerConverter(new SchedulerBatchProcessTypeDtoConverter());
        converterFactory.registerConverter(new IssueStateDtoConverter());
    }

    private void personMapping(final MapperFactory factory) {
        factory.classMap(Person.class, PersonDto.class)
                .exclude("maritalStatus")
                .exclude("idDocType")
                .customize(new CustomMapper<Person, PersonDto>() {
                    @Override
                    public void mapAtoB(final Person person, final PersonDto personDto, final MappingContext mappingContext) {
                        personDto.setIdDocType(DocTypeDto.valueOf(person.getIdDocType()));
                        personDto.setMaritalStatus(MaritalStatusDto.valueOf(person.getMaritalStatus()));
                    }

                    @Override
                    public void mapBtoA(final PersonDto personDto, final Person person, final MappingContext context) {
                        person.setMaritalStatus(personDto.getMaritalStatus() != null ? personDto.getMaritalStatus().getKey() : null);
                        person.setIdDocType(personDto.getIdDocType() != null ? personDto.getIdDocType().getKey() : null);
                    }
                })
                .byDefault()
                .register();

        factory.classMap(Person.class, Person.class)
                .exclude("id")
                .byDefault()
                .register();
    }

    private void issueMapping(final MapperFactory factory) {
        factory.classMap(Issue.class, IssueDto.class)
                .exclude("issueState")
                .field("issueState", "issueState.key")
                .fieldMap("priority").converter("issue-priority-dto").add()
                .customize(new CustomMapper<Issue, IssueDto>() {
                    @Override
                    public void mapAtoB(final Issue issue, final IssueDto issueDto, final MappingContext mappingContext) {
                        issueDto.setIssueState(IssueStateDto.valueOf(issue.getIssueState().name()));
                        if (issue.getDpdStartDate() == null) {
                            // w przypadku gdy nie ma dokumentów zaległych w momencie tworzenia zlecenia (utworzone manualnie) data dpd start nie jest ustawiona
                            issueDto.setDpdStart(0L);
                            issueDto.setDpdEstimated(0L);
                        } else {
                            final Long dpdStart = daysBetween(issue.getDpdStartDate(), issueDto.getStartDate());
                            issueDto.setDpdStart(dpdStart);
                            final Long issueLengthInDays = daysBetween(issueDto.getStartDate(), issueDto.getExpirationDate());
                            issueDto.setDpdEstimated(dpdStart + issueLengthInDays);
                        }
                        if (issue.getTags() != null) {
                            final List<TagDto> tagList = mapperFacade.mapAsList(issue.getTags(), TagDto.class);
                            tagList.sort(new TagComparator());
                            issueDto.setTags(tagList);
                        }
                    }
                })
                .byDefault()
                .register();

        factory.classMap(Issue.class, CompanyIssueDto.class)
                .exclude("issueState")
                .field("issueState", "issueState.key")
                .customize(new CustomMapper<Issue, CompanyIssueDto>() {
                    @Override
                    public void mapAtoB(final Issue issue, final CompanyIssueDto companyIssueDto, final MappingContext mappingContext) {
                        companyIssueDto.setIssueState(IssueStateDto.valueOf(issue.getIssueState().name()));
                    }
                })
                .byDefault()
                .register();

        /*
         * Służy do tworzenia nowego zlecenia na podstawie istniejącego zlecenia
         * Wykorzystywany podczas automatycznego kończenia zlecenia i otwierania nowego
         */
        factory.classMap(Issue.class, Issue.class)
                .exclude("id")
                .exclude("assignee")
                .exclude("startDate")
                .exclude("issueType")
                .exclude("issueState")
                .exclude("parentIssue")
                .exclude("invoices")
                .exclude("expirationDate")
                .exclude("dpdStartDate")
                .fieldMap("customer").converter("customer-by-ref").add()
                .fieldMap("contract").converter("contract-by-ref").add()
                .customize(new CustomMapper<Issue, Issue>() {
                    @Override
                    public void mapAtoB(final Issue issue, final Issue copyIssue, final MappingContext mappingContext) {
                        final List<IssueInvoice> sourceIssueInvoices = issue.getIssueInvoices();
                        if (isEmpty(sourceIssueInvoices)) {
                            return;
                        }
                        final List<IssueInvoice> issueInvoices = new ArrayList<>(sourceIssueInvoices.size());
                        sourceIssueInvoices.forEach(sourceIssueInvoice -> {
                            final IssueInvoice issueInvoice = new IssueInvoice();
                            issueInvoice.setIssue(copyIssue);
                            issueInvoice.setInvoice(sourceIssueInvoice.getInvoice());
                            final Date currentDate = dateProvider.getCurrentDate();
                            issueInvoice.setAdditionDate(currentDate);
                            issueInvoice.setExcluded(sourceIssueInvoice.isExcluded());
                            final BigDecimal finalBalance = sourceIssueInvoice.getFinalBalance();
                            final boolean issueSubject = isIssueSubjectForNewIssue(finalBalance, currentDate, sourceIssueInvoice.getInvoice().getRealDueDate());
                            issueInvoice.setIssueSubject(issueSubject);
                            issueInvoice.setFinalBalance(finalBalance);
                            issueInvoice.setInclusionBalance(finalBalance);
                            issueInvoices.add(issueInvoice);
                        });
                        copyIssue.setIssueInvoices(issueInvoices);
                    }
                })
                .byDefault()
                .register();

        factory.classMap(Issue.class, UnresolvedIssueDto.class)
                .exclude("extContractsNumbers")
                .field("id", "issueId")
                .customize(new CustomMapper<Issue, UnresolvedIssueDto>() {
                    @Override
                    public void mapAtoB(final Issue issue, final UnresolvedIssueDto unresolvedIssueDto, final MappingContext mappingContext) {
                        final Set<String> contractNumbers = issue.getIssueInvoices().stream()
                                .map(IssueInvoice::getInvoice)
                                .map(Invoice::getContractNumber)
                                .collect(Collectors.toSet());
                        unresolvedIssueDto.setExtContractsNumbers(contractNumbers);
                    }
                })
                .byDefault()
                .register();

        factory.classMap(Issue.class, IssueForFieldDebtCollectorDto.class)
                .byDefault()
                .register();
    }

    private void activityMapping(final MapperFactory factory) {
        factory.classMap(Activity.class, ActivityDto.class)
                .field("issue.id", "issueId")
                .byDefault()
                .register();

        factory.classMap(DefaultIssueActivity.class, DefaultIssueActivityDto.class)
                .field("activityType.id", "activityTypeId")
                .byDefault()
                .register();
    }

    private void activityTypeMapping(final MapperFactory factory) {
        factory.classMap(ActivityType.class, ActivityTypeDto.class)
                .customize(new CustomMapper<ActivityType, ActivityTypeDto>() {
                    @Override
                    public void mapAtoB(final ActivityType activityType, final ActivityTypeDto activityTypeDto, final MappingContext context) {
                        if (activityType.getKey() != null) {
                            activityTypeDto.setKey(activityType.getKey().name());
                        }
                    }

                    @Override
                    public void mapBtoA(final ActivityTypeDto activityTypeDto, final ActivityType activityType, final MappingContext context) {
                        if (activityTypeDto.getKey() != null) {
                            activityType.setKey(ActivityTypeKey.valueOf(activityTypeDto.getKey()));
                        }
                    }
                })
                .byDefault()
                .register();

        copyActivityTypeDto(factory);
    }

    /**
     * Mapowanie ActivityDetailProperty z sortowaniem typów słownikowych
     */
    private void activityDetailPropertyMapping(final MapperFactory factory) {
        factory.classMap(ActivityDetailProperty.class, ActivityDetailPropertyDto.class)
                .exclude("dictionaryValues")
                .customize(new CustomMapper<ActivityDetailProperty, ActivityDetailPropertyDto>() {
                    @Override
                    public void mapAtoB(final ActivityDetailProperty activityDetailProperty, final ActivityDetailPropertyDto activityDetailPropertyDto,
                                        final MappingContext context) {

                        final Set<ActivityTypeDetailPropertyDictValue> dictionaryValues = activityDetailProperty.getDictionaryValues();
                        if (isNotEmpty(dictionaryValues)) {
                            final List<ActivityTypeDetailPropertyDictValue> dictionaryValuesAsList = new ArrayList<>(dictionaryValues);
                            dictionaryValuesAsList.sort(ACTIVITY_TYPE_DETAIL_PROPERTY_DICT_VALUE_COMPARATOR);
                            activityDetailPropertyDto.setDictionaryValues(mapAsList(dictionaryValuesAsList, ActivityTypeDetailPropertyDictValueDto.class));
                        } else {
                            activityDetailPropertyDto.setDictionaryValues(emptyList());
                        }
                    }

                    @Override
                    public void mapBtoA(final ActivityDetailPropertyDto activityDetailPropertyDto, final ActivityDetailProperty activityDetailProperty,
                                        final MappingContext context) {
                        final List<ActivityTypeDetailPropertyDictValueDto> dictionaryValues = activityDetailPropertyDto.getDictionaryValues();
                        if (isNotEmpty(dictionaryValues)) {
                            activityDetailProperty.setDictionaryValues(mapAsSet(dictionaryValues, ActivityTypeDetailPropertyDictValue.class));
                        }
                    }
                })
                .byDefault()
                .register();
    }

    /**
     * Używane do kopiowania obiektów bez szczegółów czynności, bo zostaną nadpisane w zależności od stanu (planowana/wykonana)
     */
    private void copyActivityTypeDto(final MapperFactory factory) {
        factory.classMap(ActivityTypeDto.class, ActivityTypeDto.class)
                .exclude("activityTypeDetailProperties")
                .byDefault()
                .register();
    }

    private void outOfServiceMapping(final MapperFactory factory) {
        factory.classMap(CustomerOutOfService.class, CustomerOutOfServiceDto.class)
                .exclude("blockingOperator")
                .customize(new CustomMapper<CustomerOutOfService, CustomerOutOfServiceDto>() {
                    @Override
                    public void mapAtoB(final CustomerOutOfService customerOutOfService, final CustomerOutOfServiceDto customerOutOfServiceDto,
                                        final MappingContext context) {
                        final User user = customerOutOfService.getBlockingOperator().getUser();
                        final String blockingOperator = String.format("%s %s", user.getFirstName(), user.getLastName());
                        customerOutOfServiceDto.setBlockingOperator(blockingOperator);
                    }
                })
                .byDefault()
                .register();

        factory.classMap(FormalDebtCollectionCustomerOutOfService.class, FormalDebtCollectionCustomerOutOfServiceDto.class)
                .exclude("blockingOperator")
                .customize(new CustomMapper<FormalDebtCollectionCustomerOutOfService, FormalDebtCollectionCustomerOutOfServiceDto>() {
                    @Override
                    public void mapAtoB(final FormalDebtCollectionCustomerOutOfService customerOutOfService,
                                        final FormalDebtCollectionCustomerOutOfServiceDto customerOutOfServiceDto,
                                        final MappingContext context) {
                        final User user = customerOutOfService.getBlockingOperator().getUser();
                        final String blockingOperator = String.format("%s %s", user.getFirstName(), user.getLastName());
                        customerOutOfServiceDto.setBlockingOperator(blockingOperator);
                    }
                })
                .field("reasonType.label", "reasonTypeLabel")
                .byDefault()
                .register();

        factory.classMap(ContractOutOfService.class, ContractOutOfServiceDto.class)
                .exclude("blockingOperator")
                .customize(new CustomMapper<ContractOutOfService, ContractOutOfServiceDto>() {
                    @Override
                    public void mapAtoB(final ContractOutOfService contractOutOfService, final ContractOutOfServiceDto contractOutOfServiceDto,
                                        final MappingContext context) {
                        final User user = contractOutOfService.getBlockingOperator().getUser();
                        final String blockingOperator = String.format("%s %s", user.getFirstName(), user.getLastName());
                        contractOutOfServiceDto.setBlockingOperator(blockingOperator);
                    }
                })
                .byDefault()
                .register();

        factory.classMap(FormalDebtCollectionContractOutOfService.class, FormalDebtCollectionContractOutOfServiceDto.class)
                .exclude("blockingOperator")
                .customize(new CustomMapper<FormalDebtCollectionContractOutOfService, FormalDebtCollectionContractOutOfServiceDto>() {
                    @Override
                    public void mapAtoB(final FormalDebtCollectionContractOutOfService contractOutOfService,
                                        final FormalDebtCollectionContractOutOfServiceDto contractOutOfServiceDto,
                                        final MappingContext context) {
                        final User user = contractOutOfService.getBlockingOperator().getUser();
                        final String blockingOperator = String.format("%s %s", user.getFirstName(), user.getLastName());
                        contractOutOfServiceDto.setBlockingOperator(blockingOperator);
                    }
                })
                .field("reasonType.label", "reasonTypeLabel")
                .byDefault()
                .register();
    }

    private void aidaInvoiceMapping(final MapperFactory factory) {
        factory.classMap(AidaInvoiceDto.class, FormalDebtCollectionInvoiceDto.class)
                .field("id", "leoId")
                .field("balanceOnDocument", "currentBalance")
                .field("number", "invoiceNumber")
                .field("contractNo", "contractNumber")
                .byDefault()
                .register();
    }

    private void invoiceMapping(final MapperFactory factory) {
        factory.classMap(Invoice.class, InvoiceDto.class)
                .customize(new InvoiceMapper(dateProvider))
                .byDefault()
                .register();
    }

    private void aidaCompanyMapping(final MapperFactory factory) {
        factory.classMap(Company.class, AidaCompanyDto.class)
                .exclude("id")
                .exclude("legalForm")
                .exclude("persons")
                .exclude("addresses")
                .exclude("contactDetails")
                .field("extCompanyId", "id")
                .byDefault()
                .register();

        factory.classMap(Company.class, CompanyDto.class)
                .field("companyPersons{person}", "persons{}")
                .field("companyPersons{contactPerson}", "persons{contactPerson}")
                .byDefault()
                .register();

        factory.classMap(CompanyPerson.class, PersonDto.class)
                .field("person", "")
                .byDefault()
                .register();
    }

    private void aidaPersonMapping(final MapperFactory factory) {
        factory.classMap(Person.class, AidaPersonDto.class)
                .field("jobFunction", "function")
                .field("country", "country.longName")
                .byDefault()
                .register();
    }

    private void issueTerminationRequestMapping(final MapperFactory factory) {
        factory.classMap(IssueTerminationRequest.class, IssueTerminationRequestDto.class)
                .field("issue.id", "issueId")
                .byDefault()
                .register();
    }

    private void walletMapping(final MapperFactory factory) {
        factory.classMap(IssueWallet.class, IssueWalletDto.class)
                .exclude("segment")
                .customize(new CustomMapper<IssueWallet, IssueWalletDto>() {
                    @Override
                    public void mapAtoB(final IssueWallet wallet, final IssueWalletDto walletDto, final MappingContext context) {
                        walletDto.setSegment(SegmentDto.valueOf(wallet.getSegment().name()));
                    }
                })
                .byDefault()
                .register();

        factory.classMap(IssueWallet.class, TagWalletDto.class)
                .byDefault()
                .register();

        factory.classMap(Wallet.class, IssueStateWalletDto.class)
                .byDefault()
                .register();
    }

    private void issueTypeConfigurationMapping(final MapperFactory factory) {
        factory.classMap(IssueTypeConfiguration.class, IssueTypeConfigurationDto.class)
                .exclude("segment")
                .customize(new CustomMapper<IssueTypeConfiguration, IssueTypeConfigurationDto>() {
                    @Override
                    public void mapAtoB(final IssueTypeConfiguration issueTypeConfiguration, final IssueTypeConfigurationDto issueTypeConfigurationDto,
                                        final MappingContext context) {
                        issueTypeConfigurationDto.setOperatorTypes(mapAsSet(issueTypeConfiguration.getIssueType().getOperatorTypes(), OperatorTypeDto.class));
                        issueTypeConfigurationDto.setSegment(SegmentDto.valueOf(issueTypeConfiguration.getSegment().name()));
                    }

                    @Override
                    public void mapBtoA(final IssueTypeConfigurationDto issueTypeConfigurationDto, final IssueTypeConfiguration issueTypeConfiguration,
                                        final MappingContext context) {
                        issueTypeConfiguration.setIssueType(null);
                        issueTypeConfiguration.setSegment(Segment.valueOf(issueTypeConfigurationDto.getSegment().getKey()));
                    }
                })
                .byDefault()
                .register();
    }

    private void tagMapping(final MapperFactory factory) {
        factory.classMap(Tag.class, TagInputDto.class)
                .customize(new CustomMapper<Tag, TagInputDto>() {
                    @Override
                    public void mapBtoA(final TagInputDto tagDto, final Tag tag, final MappingContext context) {
                        if (tagDto.getType() == null) {
                            tag.setType(TagType.CUSTOM);
                        } else {
                            tag.setType(TagType.valueOf(tagDto.getType().name()));
                        }
                    }

                    @Override
                    public void mapAtoB(final Tag tag, final TagInputDto tagInputDto, final MappingContext context) {
                        tagInputDto.setType(TagTypeDto.valueOf(tag.getType().name()));
                    }
                })
                .byDefault()
                .register();

        factory.classMap(Tag.class, TagDto.class)
                .customize(new CustomMapper<Tag, TagDto>() {
                    @Override
                    public void mapBtoA(final TagDto tagDto, final Tag tag, final MappingContext context) {
                        if (tagDto.getType() == null) {
                            tag.setType(TagType.CUSTOM);
                        } else {
                            tag.setType(TagType.valueOf(tagDto.getType().name()));
                        }
                    }

                    @Override
                    public void mapAtoB(final Tag tag, final TagDto tagDto, final MappingContext context) {
                        tagDto.setType(TagTypeDto.valueOf(tag.getType().name()));
                    }
                })
                .byDefault()
                .register();

        factory.classMap(TagIcon.class, TagIconDto.class)
                .byDefault()
                .register();
    }

    private void demandForPaymentMapping(final MapperFactory factory) {
        factory.classMap(DemandForPaymentTypeConfiguration.class, DemandForPaymentTypeConfigurationDto.class)
                .exclude("segment")
                .customize(new CustomMapper<DemandForPaymentTypeConfiguration, DemandForPaymentTypeConfigurationDto>() {
                    @Override
                    public void mapAtoB(final DemandForPaymentTypeConfiguration type, final DemandForPaymentTypeConfigurationDto typeDto, final MappingContext context) {
                        typeDto.setSegment(SegmentDto.valueOf(type.getSegment().name()));
                    }
                })
                .byDefault()
                .register();

        factory.classMap(DemandForPayment.class, DemandForPaymentDto.class)
                .customize(new CustomMapper<DemandForPayment, DemandForPaymentDto>() {
                    @Override
                    public void mapAtoB(final DemandForPayment demandForPayment, final DemandForPaymentDto demandForPaymentDto, final MappingContext context) {
                        demandForPaymentDto.setAttachmentRef(buildDocumentUrl(demandForPayment.getAttachmentRef()));
                    }
                })
                .field("reasonType.label", "reasonTypeLabel")
                .byDefault()
                .register();

        factory.classMap(DemandForPaymentWithCompanyName.class, DemandForPaymentDto.class)
                .customize(new CustomMapper<DemandForPaymentWithCompanyName, DemandForPaymentDto>() {
                    @Override
                    public void mapAtoB(final DemandForPaymentWithCompanyName demandForPayment, final DemandForPaymentDto demandForPaymentDto, final MappingContext context) {
                        demandForPaymentDto.setAttachmentRef(buildDocumentUrl(demandForPayment.getAttachmentRef()));
                    }
                })
                .byDefault()
                .register();

    }

    private void aidaInvoiceWithCorrectionsDtoToFormalDebtCollectionInvoiceMapping(final MapperFactory factory) {
        factory.classMap(AidaInvoiceWithCorrectionsDto.class, FormalDebtCollectionInvoice.class)
                .exclude("id")
                .field("number", "invoiceNumber")
                .field("balanceOnDocument", "currentBalance")
                .field("contractNo", "contractNumber")
                .field("id", "leoId")
                .byDefault()
                .register();
    }

    private void registerContractTerminationMapping(final MapperFactory factory) {
        factory.classMap(FormalDebtCollectionInvoice.class, FormalDebtCollectionInvoiceDto.class)
                .byDefault()
                .register();

        factory.classMap(ContractTermination.class, TerminationDto.class)
                .customize(new CustomMapper<ContractTermination, TerminationDto>() {
                    @Override
                    public void mapAtoB(final ContractTermination contractTermination, final TerminationDto terminationDto, final MappingContext context) {
                        if (contractTermination.getContractTerminationAttachments() != null) {
                            final Set<String> attachmentRefs = new HashSet<>();
                            contractTermination.getContractTerminationAttachments().forEach(attachment -> attachmentRefs.add(buildDocumentUrl(attachment.getReference())));
                            terminationDto.setAttachmentRefs(attachmentRefs);
                        }
                    }
                })
                .byDefault()
                .register();

        factory.classMap(ContractTermination.class, TerminationContractDto.class)
                .field("id", "contractTerminationId")
                .byDefault()
                .register();

        factory.classMap(ContractTermination.class, ContractTerminationInitialData.class)
                .field("invoiceNumberInitiatingDemandForPayment", "initialInvoiceNo")
                .field("id", "precedingContractTerminationId")
                .field("formalDebtCollectionInvoices", "invoices")
                .byDefault()
                .register();

        factory.classMap(DemandForPayment.class, ContractTerminationInitialData.class)
                .field("id", "precedingDemandForPaymentId")
                .byDefault()
                .register();
    }

    private String buildDocumentUrl(String reference) {
        return String.format("%s%s", properties.getProperty(AlwinPropertyKey.DMS_DOCUMENT_URL), reference);
    }

}
