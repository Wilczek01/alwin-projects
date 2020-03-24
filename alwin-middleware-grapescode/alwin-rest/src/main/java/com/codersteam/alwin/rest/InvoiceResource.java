package com.codersteam.alwin.rest;

import com.codersteam.aida.core.api.model.InvoiceEntryDto;
import com.codersteam.aida.core.api.model.InvoiceTypeDto;
import com.codersteam.alwin.auth.annotation.Secured;
import com.codersteam.alwin.auth.model.InvoiceResponse;
import com.codersteam.alwin.common.search.criteria.InvoiceSearchCriteria;
import com.codersteam.alwin.common.sort.InvoiceSortField;
import com.codersteam.alwin.common.sort.SortOrder;
import com.codersteam.alwin.core.api.model.invoice.InvoicesWithTotalBalanceDto;
import com.codersteam.alwin.core.api.model.issue.InvoiceDto;
import com.codersteam.alwin.core.api.service.DateProvider;
import com.codersteam.alwin.core.api.service.issue.InvoiceService;
import com.codersteam.alwin.parameters.DateParam;
import com.codersteam.alwin.util.CSVUtils;
import com.codersteam.alwin.validator.InvoiceValidator;
import io.swagger.annotations.*;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import static com.codersteam.alwin.model.search.InvoiceSearchCriteriaBuilder.aSearchCriteria;
import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/invoices")
@Api("/invoices")
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
public class InvoiceResource {

    private static final int CSV_DATA_ROWS_NUMBER_PART = 1000;
    private InvoiceService invoiceService;
    private InvoiceValidator invoiceValidator;
    private DateProvider dateProvider;

    @Inject
    public InvoiceResource(InvoiceService invoiceService, InvoiceValidator invoiceValidator, DateProvider dateProvider) {
        this.invoiceService = invoiceService;
        this.invoiceValidator = invoiceValidator;
        this.dateProvider = dateProvider;
    }

    public InvoiceResource() {
    }

    @GET
    @Path("{issueId}")
    @Secured(all = true)
    @ApiOperation("Pobieranie listy faktur po identyfikatorze zlecenia z dodatkowym filtrowaniem")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public InvoiceResponse findInvoiceForIssueId(@ApiParam(name = "issueId", value = "Identyfikator zlecenia") @PathParam("issueId") final long issueId,
                                                 @ApiParam(name = "firstResult", value = "Pierwszy element", defaultValue = "0")
                                                 @QueryParam("firstResult") final int firstResult,
                                                 @ApiParam(name = "maxResults", value = "Maksymalna ilość per strona", defaultValue = "5")
                                                 @QueryParam("maxResults") final int maxResults,
                                                 @ApiParam(name = "contractNumber", value = "Numer umowy")
                                                 @QueryParam("contractNumber") final String contractNumber,
                                                 @ApiParam(name = "typeId", value = "Identyfikator typu faktury")
                                                 @QueryParam("typeId") final Long typeId,
                                                 @ApiParam(name = "startDueDate", value = "Początek okresu terminu wymagalności")
                                                 @QueryParam("startDueDate") final DateParam startDueDate,
                                                 @ApiParam(name = "endDueDate", value = "Koniec okresu terminu wymagalności")
                                                 @QueryParam("endDueDate") final DateParam endDueDate,
                                                 @ApiParam(name = "notPaidOnly", value = "Czy pobierać tyko faktury nieopłacone")
                                                 @QueryParam("notPaidOnly") final boolean notPaidOnly,
                                                 @ApiParam(name = "overdueOnly", value = "Czy pobierać tyko faktury których termin płatności minął")
                                                 @QueryParam("overdueOnly") final boolean overdueOnly,
                                                 @ApiParam(name = "issueSubjectOnly", value = "Czy pobierać tyko faktury będące przedmiotem zlecenia")
                                                 @QueryParam("issueSubjectOnly") final boolean issueSubjectOnly,
                                                 @QueryParam("sortField") InvoiceSortField sort,
                                                 @QueryParam("sortOrder") @DefaultValue("ASC") SortOrder sortOrder) {

        InvoiceSearchCriteria searchCriteria = aSearchCriteria()
                .withFirstResult(firstResult)
                .withMaxResults(maxResults)
                .withContractNumber(contractNumber)
                .withTypeId(typeId)
                .withStartDueDate(startDueDate)
                .withEndDueDate(endDueDate)
                .withOverdueOnly(overdueOnly, dateProvider.getCurrentDate())
                .withNotPaidOnly(notPaidOnly)
                .withIssueSubjectOnly(issueSubjectOnly)
                .build();

        Map<InvoiceSortField, SortOrder> sortCriteria = sort != null ? Collections.singletonMap(sort, sortOrder) : Collections.emptyMap();

        InvoicesWithTotalBalanceDto invoicesWithTotalBalanceDto = invoiceService.findInvoicesWithTotalBalance(issueId, searchCriteria, sortCriteria);
        return new InvoiceResponse(invoicesWithTotalBalanceDto.getInvoices(), invoicesWithTotalBalanceDto.getBalance());
    }

    @GET
    @Path("types")
    @Secured(all = true)
    @ApiOperation("Pobieranie wszystkich typów faktur")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public List<InvoiceTypeDto> findAllInvoiceTypes() {
        return invoiceService.findAllInvoiceTypes();
    }

    @GET
    @Path("entries")
    @Secured(all = true)
    @ApiOperation("Pobieranie listy pozycji faktury po numerze faktury")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public List<InvoiceEntryDto> getInvoiceEntries(@ApiParam(name = "invoiceNo", value = "Numer faktury", required = true)
                                      @QueryParam("invoiceNo") final String invoiceNo) {
        invoiceValidator.validateInvoiceNo(invoiceNo);
        return invoiceService.findInvoiceEntries(invoiceNo);
    }

    @GET
    @Path("report/{issueId}")
    @Secured(all = true)
    @ApiOperation("Pobieranie wyeksportowanej listy faktur w formacie CSV po identyfikatorze zlecenia z dodatkowym filtrowaniem")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public Response findCSVInvoiceForIssueId(@ApiParam(name = "issueId", value = "Identyfikator zlecenia") @PathParam("issueId") final long issueId,
                                             @ApiParam(name = "contractNumber", value = "Numer umowy")
                                             @QueryParam("contractNumber") final String contractNumber,
                                             @ApiParam(name = "typeId", value = "Identyfikator typu faktury")
                                             @QueryParam("typeId") final Long typeId,
                                             @ApiParam(name = "startDueDate", value = "Początek okresu terminu wymagalności")
                                             @QueryParam("startDueDate") final DateParam startDueDate,
                                             @ApiParam(name = "endDueDate", value = "Koniec okresu terminu wymagalności")
                                             @QueryParam("endDueDate") final DateParam endDueDate,
                                             @ApiParam(name = "notPaidOnly", value = "Czy pobierać tyko faktury nieopłacone")
                                             @QueryParam("notPaidOnly") final boolean notPaidOnly,
                                             @ApiParam(name = "overdueOnly", value = "Czy pobierać tyko faktury których termin płatności minął")
                                             @QueryParam("overdueOnly") final boolean overdueOnly,
                                             @ApiParam(name = "issueSubjectOnly", value = "Czy pobierać tyko faktury będące przedmiotem zlecenia")
                                             @QueryParam("issueSubjectOnly") final boolean issueSubjectOnly,
                                             @ApiParam(name = "totalValues", value = "Liczba wszystkich faktur")
                                             @QueryParam("totalValues") final int totalValues,
                                             @QueryParam("sortField") InvoiceSortField sort,
                                             @QueryParam("sortOrder") @DefaultValue("ASC") SortOrder sortOrder) throws IOException {

        List<InvoiceDto> values = new ArrayList<>();

        int firstResult = 0;

        int pages = totalValues / CSV_DATA_ROWS_NUMBER_PART + 1;
        for (int i = 0; i < pages; i++) {

            InvoiceSearchCriteria searchCriteria = aSearchCriteria()
                    .withFirstResult(firstResult)
                    .withMaxResults(CSV_DATA_ROWS_NUMBER_PART)
                    .withContractNumber(contractNumber)
                    .withTypeId(typeId)
                    .withStartDueDate(startDueDate)
                    .withEndDueDate(endDueDate)
                    .withOverdueOnly(overdueOnly, dateProvider.getCurrentDate())
                    .withNotPaidOnly(notPaidOnly)
                    .withIssueSubjectOnly(issueSubjectOnly)
                    .build();

            Map<InvoiceSortField, SortOrder> sortCriteria = sort != null ? Collections.singletonMap(sort, sortOrder) : Collections.emptyMap();
            InvoicesWithTotalBalanceDto invoicesWithTotalBalanceDto = invoiceService.findInvoicesWithTotalBalance(issueId, searchCriteria, sortCriteria);

            values.addAll(invoicesWithTotalBalanceDto.getInvoices().getValues());

            firstResult += CSV_DATA_ROWS_NUMBER_PART;
        }

        File file = getFile(typeId, startDueDate, endDueDate, notPaidOnly, overdueOnly, values);
        return Response.ok(file).type("text/csv").build();
    }

    private File getFile(Long typeId, DateParam startDueDate, DateParam endDueDate, boolean notPaidOnly, boolean overdueOnly,
                         List<InvoiceDto> values) throws IOException {
        File file = File.createTempFile("CSVInvoiceForIssueIdTmp", ".csv");
        try (PrintWriter pw = new PrintWriter(file, "UTF-8")) {
            StringBuilder sb = new StringBuilder();

            CSVUtils.writeHeader(sb);

            for (InvoiceDto value : values) {
                CSVUtils.writeLine(sb, Arrays.asList(value.getCorrections().size() > 0, value.getIssueSubject(), value.getNumber(), value.getContractNumber(),
                        value.getTypeLabel(), value.getCurrency(), value.getIssueDate(), value.getRealDueDate(), value.getLastPaymentDate(), value.getGrossAmount(),
                        value.getCurrentBalance(), value.getPaid(), value.getDpd(), value.getExcluded()));
            }

            CSVUtils.writeFilterDescriptionFooter(typeId, startDueDate, endDueDate, notPaidOnly, overdueOnly, sb, getAllInvoiceTypes());

            pw.write(sb.toString());
        }
        file.deleteOnExit();
        return file;
    }

    private Map<Long, String> getAllInvoiceTypes() {
        List<InvoiceTypeDto> allInvoiceTypes = invoiceService.findAllInvoiceTypes();
        Map<Long, String> invoiceTypes = new HashMap<>();

        for (InvoiceTypeDto invoiceType : allInvoiceTypes) {
            invoiceTypes.put(invoiceType.getId(), invoiceType.getLabel());
        }
        return invoiceTypes;
    }
}
