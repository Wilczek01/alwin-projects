package com.codersteam.alwin.db.dao;

import com.codersteam.alwin.common.issue.IssueTypeName;
import com.codersteam.alwin.common.issue.Segment;
import com.codersteam.alwin.common.search.criteria.IssueForCompanySearchCriteria;
import com.codersteam.alwin.common.search.criteria.IssueSearchCriteria;
import com.codersteam.alwin.common.search.criteria.IssuesSearchCriteria;
import com.codersteam.alwin.common.sort.IssueSortField;
import com.codersteam.alwin.common.sort.SortOrder;
import com.codersteam.alwin.db.dao.criteria.CompanyIssuesCriteriaBuilder;
import com.codersteam.alwin.db.dao.criteria.IssuesCriteriaBuilder;
import com.codersteam.alwin.jpa.IssueWallet;
import com.codersteam.alwin.jpa.TagWallet;
import com.codersteam.alwin.jpa.Wallet;
import com.codersteam.alwin.jpa.activity.Activity;
import com.codersteam.alwin.jpa.activity.Activity_;
import com.codersteam.alwin.jpa.customer.Company;
import com.codersteam.alwin.jpa.issue.*;
import com.codersteam.alwin.jpa.type.ActivityState;
import com.codersteam.alwin.jpa.type.IssueState;
import com.codersteam.alwin.jpa.type.OperatorNameType;
import com.codersteam.alwin.jpa.type.TagType;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.*;
import java.util.stream.Collectors;

import static com.codersteam.alwin.common.issue.IssueTypeName.PHONE_DEBT_COLLECTION;
import static com.codersteam.alwin.jpa.type.ActivityState.*;
import static com.codersteam.alwin.jpa.type.ActivityTypeKey.FIRST_DEMAND_PAYMENT;
import static com.codersteam.alwin.jpa.type.ActivityTypeKey.LAST_DEMAND_PAYMENT;
import static com.codersteam.alwin.jpa.type.IssueState.CLOSED_ISSUE_STATES;
import static com.codersteam.alwin.jpa.type.IssueState.OPEN_ISSUE_STATES;

/**
 * Klasa dostępu do zleceń
 *
 * @author Michal Horowic
 */
@Stateless
public class IssueDao extends AbstractAuditDao<Issue> {

    private IssueTagDao issueTagDao;

    public IssueDao() {
    }

    @Inject
    public IssueDao(final IssueTagDao issueTagDao) {
        this.issueTagDao = issueTagDao;
    }

    @Override
    public Class<Issue> getType() {
        return Issue.class;
    }

    /**
     * Pobiera wszystkie zlecenia danego użytkownika, z wyłączeniem zgłoszenia o podanym identyfikatorze.
     * Sortowane po dacie utworzenia.
     *
     * @param companyId      - identyfikator firmy
     * @param issueToExclude - identyfikator zlecenia, którego nie bierzemy pod uwagę
     * @param searchCriteria - kryteria wyszukiwania
     * @return strona z listą zleceń
     */
    public List<Issue> findAllIssuesForCompanyExcludingGivenIssue(Long companyId,
                                                                  Long issueToExclude,
                                                                  IssueForCompanySearchCriteria searchCriteria) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Issue> criteriaQuery = cb.createQuery(Issue.class);

        Root<Activity> activity = criteriaQuery.from(Activity.class);
        Join<Activity, Issue> issue = activity.join(Activity_.issue);

        List<Predicate> predicates = CompanyIssuesCriteriaBuilder.fillRequiredPredicatesForCompanyExcludingGivenIssue(companyId, issueToExclude,
                searchCriteria, cb, issue, activity);

        criteriaQuery.select(issue)
                .distinct(true)
                .orderBy(cb.asc(issue.get(Issue_.startDate)))
                .where(cb.and(predicates.toArray(new Predicate[0])));

        return entityManager.createQuery(criteriaQuery)
                .setFirstResult(searchCriteria.getFirstResult())
                .setMaxResults(searchCriteria.getMaxResults())
                .getResultList();
    }

    /**
     * Pobiera ilość zleceń danego użytkownika, z wyłączeniem zgłoszenia o podanym identyfikatorze.
     *
     * @param companyId      - identyfikator firmy
     * @param issueToExclude - identyfikator zlecenia, którego nie bierzemy pod uwagę
     * @param searchCriteria - kryteria wyszukiwania
     * @return ilość zleceń
     */
    public long findAllIssuesForCompanyExcludingGivenIssueCount(final Long companyId, final Long issueToExclude, final IssueForCompanySearchCriteria searchCriteria) {
        final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);

        final Root<Activity> activity = criteriaQuery.from(Activity.class);
        final Join<Activity, Issue> issue = activity.join(Activity_.issue);

        final List<Predicate> predicates = CompanyIssuesCriteriaBuilder.fillRequiredPredicatesForCompanyExcludingGivenIssue(companyId, issueToExclude,
                searchCriteria, criteriaBuilder, issue, activity);

        criteriaQuery.select(criteriaBuilder.countDistinct(issue))
                .where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));

        return entityManager.createQuery(criteriaQuery)
                .getSingleResult();
    }

    /**
     * Pobiera stronicowane zlecenia obsługiwane przez danego operatora.
     * Pobiera zlecenia, w których operator ma choć jedną zamkniętą i przypisaną do siebie czynność windykacyjną.
     * Sortowane po identyfikatorze - z założenia im starsze tym wyższa wartość w sekwencji.
     *
     * @param assigneeId     - identyfikator operatora
     * @param searchCriteria - kryteria wyszukiwania
     * @return strona z listą zleceń
     */
    public List<Issue> findOperatorIssues(final Long assigneeId, final IssueSearchCriteria searchCriteria) {
        final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Issue> criteriaQuery = criteriaBuilder.createQuery(Issue.class);

        final Root<Issue> issue = criteriaQuery.from(Issue.class);
        final Root<Activity> activity = criteriaQuery.from(Activity.class);

        final List<Predicate> predicates = CompanyIssuesCriteriaBuilder.fillRequiredPredicates(assigneeId, searchCriteria, criteriaBuilder, issue, activity);

        criteriaQuery.select(issue)
                .distinct(true)
                .orderBy(criteriaBuilder.asc(issue.get(Issue_.id)))
                .where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));

        return entityManager.createQuery(criteriaQuery)
                .setFirstResult(searchCriteria.getFirstResult())
                .setMaxResults(searchCriteria.getMaxResults())
                .getResultList();
    }

    /**
     * Pobiera ilość wszystkich zleceń obsługiwanych przez danego operatora.
     *
     * @return ilość zleceń
     */
    public long findOperatorIssuesCount(final Long assigneeId, final IssueSearchCriteria searchCriteria) {
        final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);

        final Root<Issue> issue = criteriaQuery.from(Issue.class);
        final Root<Activity> activity = criteriaQuery.from(Activity.class);

        final List<Predicate> predicates = CompanyIssuesCriteriaBuilder.fillRequiredPredicates(assigneeId, searchCriteria, criteriaBuilder, issue, activity);

        criteriaQuery.select(criteriaBuilder.countDistinct(issue));
        criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));

        return entityManager.createQuery(criteriaQuery).getSingleResult();
    }

    /**
     * Przypisanie czynności windykacyjnej do operatora
     *
     * @param activityId - identyfikator czynności windykacyjnej
     * @param operatorId - identyfikator operatora
     * @return <code>true</code> jeśli operacja zakończona sukcesem
     */
    public boolean assignNotAssignedActivityToOperator(final Long activityId, final Long operatorId) {
        final String sql = "update Activity i set i.operator.id =:operatorId where i.id =:activityId and i.operator.id is null";
        final Query query = entityManager.createQuery(sql);
        query.setParameter("activityId", activityId);
        query.setParameter("operatorId", operatorId);
        return query.executeUpdate() > 0;
    }

    /**
     * Pobranie wszystkich aktywnych zleceń
     *
     * @return Lista aktywnych zleceń
     */
    public List<Issue> findAllActiveIssues() {
        return entityManager.createQuery("select i from Issue i where i.issueState not in (:issueStates)", Issue.class)
                .setParameter("issueStates", CLOSED_ISSUE_STATES)
                .getResultList();
    }

    public List<Long> findAllActiveIssuesIds() {
        return entityManager.createQuery("select i.id from Issue i where i.issueState not in (:issueStates)", Long.class)
                .setParameter("issueStates", CLOSED_ISSUE_STATES)
                .getResultList();
    }

    /**
     * Pobranie identyfikatorów wszystkich aktywnych zleceń po typach
     *
     * @return Lista identyfikatorów aktywnych zleceń
     */
    public List<Long> findActiveIssuesIds(final List<IssueTypeName> issueTypesNames) {
        final String sql = "select i.id from Issue i where i.issueState not in (:issueStates) and i.issueType.name in (:issueTypesNames)";
        return entityManager.createQuery(sql, Long.class)
                .setParameter("issueStates", CLOSED_ISSUE_STATES)
                .setParameter("issueTypesNames", issueTypesNames)
                .getResultList();
    }

    /**
     * Pobranie wszystkich aktywnych zleceń po typach
     *
     * @return Lista aktywnych zleceń
     */
    public List<Issue> findActiveIssues(final List<IssueTypeName> issueTypesNames) {
        final String sql = "select i from Issue i where i.issueState not in (:issueStates) and i.issueType.name in (:issueTypesNames)";
        return entityManager.createQuery(sql, Issue.class)
                .setParameter("issueStates", CLOSED_ISSUE_STATES)
                .setParameter("issueTypesNames", issueTypesNames)
                .getResultList();
    }

    /**
     * Pobranie wszystkich firm z aktywnym zleceniem windykacyjnym
     *
     * @return Lista firm z aktywnym zleceniem windykacyjnym
     */
    public List<Company> findAllCompaniesWithActiveIssue() {
        final String sql = "select distinct i.customer.company from Issue i where i.issueState not in (:issueStates) and i.customer.company is not null";
        return entityManager.createQuery(sql, Company.class)
                .setParameter("issueStates", CLOSED_ISSUE_STATES)
                .getResultList();
    }

    /**
     * Metoda zwraca najbliższą przyszłą czynność windykacyjną do pracy dla operatora
     * W przypadku zleceń utworzonych automatycznie zwracane są jedynie te, które posiadają zaległości (ujemne saldo bieżące)
     * W przypadku zleceń, które mają już wykonaną czynność w dniu inspectionDate są one pomijane (zapobiega to blokowaniu kolejki czynności do obsłużenia)
     *
     * @param userId             - identyfikator operatora
     * @param operatorIssueTypes - typy zleceń, do których ma prawa operator
     * @return lista posortowanych czynności
     */
    public Optional<Activity> findFutureActivityForUserToWorkOn(final Long userId, final List<IssueTypeName> operatorIssueTypes, final Date inspectionDate) {
        final String sql = "select a from Activity a, IssueInvoice ii where ii.pk.issue.id = a.issue.id and ii.pk.invoice.currentBalance < 0  " +
                "and ii.issueSubject = true and a.state in :openActivityStates and a.issue.issueState not in :closedIssueStates and " +
                "a.plannedDate > :inspectionDate and (a.operator.id=:userId or a.operator.id is null) " +
                "and ((a.issue.createdManually = false and (a.issue.currentBalanceEUR < 0 or a.issue.currentBalancePLN < 0)) or a.issue.createdManually = true)" +
                "and a.issue.id not in (select aa.issue.id from Activity aa where aa.state = :activityExecutedState and aa.activityDate = :inspectionDate) " +
                "and a.activityType.key not in :omittedActivityTypes and a.issue.issueType.name in :issueTypeNames " +
                " group by a, a.issue.priority, a.issue.rpbPLN " +
                "order by min(ii.pk.invoice.realDueDate) asc, a.issue.rpbPLN desc, a.id asc ";
        TypedQuery<Activity> query = entityManager.createQuery(sql, Activity.class)
                .setParameter("closedIssueStates", CLOSED_ISSUE_STATES)
                .setParameter("inspectionDate", inspectionDate)
                .setParameter("openActivityStates", OPEN_STATES)
                .setParameter("omittedActivityTypes", Arrays.asList(FIRST_DEMAND_PAYMENT, LAST_DEMAND_PAYMENT))
                .setParameter("userId", userId)
                .setParameter("activityExecutedState", EXECUTED)
                .setParameter("issueTypeNames", operatorIssueTypes)
                .setMaxResults(1);
        return checkForSingleResult(query.getResultList());
    }

    /**
     * Pobranie wszystkich aktywnych zleceń, których data wygaśnięcia jest wcześniejsza od podanej
     *
     * @param inspectionDate - data analizy
     * @return lista zleceń windykacyjnych do zamknięcia
     */
    public List<Issue> findIssuesToClose(final Date inspectionDate) {
        return entityManager.createQuery("select i from Issue i where i.issueState not in (:issueStates) " +
                "and i.expirationDate<:inspectionDate", Issue.class)
                .setParameter("issueStates", CLOSED_ISSUE_STATES)
                .setParameter("inspectionDate", inspectionDate)
                .getResultList();
    }

    /**
     * Pobieranie filtrowanych zleceń z obszaru danego operatora
     *
     * @param operatorTypeName    - typ operatora do rozpoznania obszaru
     * @param criteria            - kryteria filtrowania
     * @param excludedIssueStates - lista statusów zleceń wyłączonych z wyszukiwania
     * @param activityStates      - lista statusów czynności windykacyjnych
     * @param sortCriteria        - kryteria sortowania
     * @return lista zleceń
     */
    public List<Issue> findAllFilteredIssues(String operatorTypeName,
                                             IssuesSearchCriteria criteria,
                                             List<IssueState> excludedIssueStates,
                                             List<ActivityState> activityStates,
                                             Map<IssueSortField, SortOrder> sortCriteria) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Issue> criteriaQuery = IssuesCriteriaBuilder.createQuery(operatorTypeName, criteria, excludedIssueStates, activityStates,
                criteriaBuilder, sortCriteria);
        return entityManager.createQuery(criteriaQuery)
                .setFirstResult(criteria.getFirstResult())
                .setMaxResults(criteria.getMaxResults())
                .getResultList();
    }

    /**
     * Pobieranie ilości wszystkich filtrowanych zleceń z obszaru danego operatora
     *
     * @param operatorTypeName    - typ operatora do rozpoznania obszaru
     * @param criteria            - kryteria filtrowania
     * @param excludedIssueStates - lista statusów zleceń wyłączonych z wyszukiwania
     * @param activityStates      - lista statusów czynności windykacyjnych
     * @return ilość zleceń
     */
    public Long findAllFilteredIssuesCount(final String operatorTypeName, final IssuesSearchCriteria criteria,
                                           final List<IssueState> excludedIssueStates, final List<ActivityState> activityStates) {
        final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Long> countQuery = IssuesCriteriaBuilder.createCountQuery(operatorTypeName, criteria, excludedIssueStates, activityStates,
                criteriaBuilder);
        return entityManager.createQuery(countQuery).getSingleResult();
    }

    /**
     * Znajduje w podanej liście identyfikatorów zleceń te, które nie mogą być zarządzane przez operatora o przekazanym typie
     *
     * @param operatorTypeName - typ operatora zarządzającego zleceniami
     * @param issueIds         - lista identyfikatorów zleceń
     * @return lista identyfikatorów zleceń
     */
    public List<Long> findNotManagedIssues(final String operatorTypeName, final List<Long> issueIds) {
        return entityManager.createQuery("select i.id from Issue i " +
                        "join i.issueType it join it.operatorTypes ot join ot.parentOperatorType pot " +
                        "where i.id in (:issueIds) and pot.typeName != :operatorTypeName",
                Long.class)
                .setParameter("issueIds", issueIds)
                .setParameter("operatorTypeName", OperatorNameType.valueOf(operatorTypeName))
                .getResultList();
    }

    /**
     * Przypisuje zgłoszenia o podanych identyfikatorach do operatora o podanym identyfikatorze
     *
     * @param operatorId - identyfikator operatora do przypisania
     * @param issueIds   - lista zleceń do przypisania
     * @return ilość zaktualizowanych zleceń
     */
    public int updateIssuesAssignment(final Long operatorId, final List<Long> issueIds) {
        return entityManager.createQuery("update Issue i set i.assignee.id =:operatorId where i.id  in (:issueIds)")
                .setParameter("issueIds", issueIds)
                .setParameter("operatorId", operatorId)
                .executeUpdate();
    }

    /**
     * Przypisuje wszystkie zlecenia spełniające kryteria wyszukiwania do operatora o podanym identyfikatorze
     *
     * @param operatorId       - identyfikator operatora do przypisania
     * @param operatorTypeName - nazwa typu operatora zarządzającego zleceniami
     * @param criteria         - kryteria wyszukiwania zleceń do przypisania
     * @return ilość zaktualizowanych zleceń
     */
    public int updateIssuesAssignment(final Long operatorId, final String operatorTypeName, final IssuesSearchCriteria criteria) {
        final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        return entityManager.createQuery(IssuesCriteriaBuilder.createUpdateQuery(operatorId, operatorTypeName, criteria,
                criteriaBuilder)).executeUpdate();
    }

    /**
     * Ustawia wszystkim zleceniom spełniającym kryteria wyszukiwania podany priorytet
     *
     * @param priority         - priorytet do ustawienia
     * @param operatorTypeName - nazwa typu operatora zarządzającego zleceniami
     * @param criteria         - kryteria wyszukiwania zleceń do ustawienia
     * @return ilość zaktualizowanych zleceń
     */
    public int updateIssuesPriority(final Integer priority, final String operatorTypeName, final IssuesSearchCriteria criteria) {
        final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        return entityManager
                .createQuery(IssuesCriteriaBuilder.createUpdatePriorityQuery(priority, operatorTypeName, criteria, criteriaBuilder))
                .executeUpdate();
    }

    /**
     * Przypisuje zleceniom o podanych identyfikatorach podany priorytet
     *
     * @param priority - priorytet do ustawienia
     * @param issueIds - lista zleceń do aktualizacji
     */
    public void updateIssuesPriority(final Integer priority, final List<Long> issueIds) {
        entityManager.createQuery("update Issue i set i.priority =:priority where i.id  in (:issueIds)")
                .setParameter("issueIds", issueIds)
                .setParameter("priority", priority)
                .executeUpdate();
    }

    /**
     * Pobieranie zleceń o podanym typie i statusie, stworzonych przez system w podanym przedziale dat (data od, data do>
     *
     * @param issueTypeName - typ zlecenia
     * @param issueState    - status zlecenia
     * @param fromDate      - data od kiedy mają być szukane zlecenia, przedział otwarty
     * @param toDate        - data do kiedy mają być szukane zlecenia, przedział domknięty
     * @return lista zleceń
     */
    public List<Issue> findIssuesCreatedBySystem(final IssueTypeName issueTypeName, final IssueState issueState, final Date fromDate, final Date toDate) {
        return entityManager.createQuery("select i from Issue i where i.issueState =:issueState and i.parentIssue is not null " +
                " and i.issueType.name =:issueTypeName and i.startDate > :fromDate and i.startDate <= :toDate", Issue.class)
                .setParameter("issueTypeName", issueTypeName)
                .setParameter("issueState", issueState)
                .setParameter("fromDate", fromDate)
                .setParameter("toDate", toDate)
                .getResultList();
    }

    /**
     * Pobranie identyfikatora aktywnego zlecenia (jeśli istnieje) po identyfikatorze firmy
     *
     * @param extCompanyId - identyfikator firmy (AIDA)
     * @return identyfikator aktywnego zlecenia (jeśli istnieje)
     */
    public Optional<Long> findCompanyActiveIssueId(final Long extCompanyId) {
        final List<Long> issueIds = entityManager.createQuery("select i.id from Issue i where i.issueState not in (:issueStates) " +
                "and i.customer.company.extCompanyId = :extCompanyId", Long.class)
                .setParameter("issueStates", CLOSED_ISSUE_STATES)
                .setParameter("extCompanyId", extCompanyId)
                .getResultList();

        return checkForSingleResult(issueIds);
    }

    /**
     * Pobieranie portfelu z otwartych zleceń klientów po typie zlecenia i segmencie klienta
     *
     * @param issueTypeName - typ zlecenia
     * @param segment       - segment klienta
     * @return portfel
     */
    public Optional<IssueWallet> findWalletsByIssueType(final IssueTypeName issueTypeName, final Segment segment, final List<IssueState> issueStates) {
        final String sql = "select new com.codersteam.alwin.jpa.IssueWallet(count(distinct i.customer.company), " +
                "sum(i.customer.company.involvement), sum(i.currentBalancePLN), sum(i.currentBalanceEUR)) " +
                "from Issue i " +
                "where i.excludedFromStats = false " +
                "and i.issueState in (:issueStates) " +
                "and i.issueType.name =:issueTypeName " +
                "and i.customer.company.segment =:segment";
        final TypedQuery<IssueWallet> query = entityManager.createQuery(sql, IssueWallet.class)
                .setParameter("issueStates", issueStates)
                .setParameter("issueTypeName", issueTypeName)
                .setParameter("segment", segment);
        final IssueWallet wallet = query.getSingleResult();
        return wallet.isEmpty() ? Optional.empty() : Optional.of(wallet);
    }

    /**
     * Pobieranie portfeli dla etykiet z otwartych zleceń
     *
     * @return portfele dla etykiet
     */
    public List<TagWallet> findTagWallets() {
        final String sql = "select new com.codersteam.alwin.jpa.TagWallet(count(distinct i.customer.company), " +
                "sum(i.customer.company.involvement), sum(i.currentBalancePLN), sum(i.currentBalanceEUR), t) " +
                "from IssueTag it, Issue i, Tag t " +
                "where it.issueId = i.id and it.tagId =t.id and i.excludedFromStats = false and i.issueState in (:issueStates) " +
                "and i.issueType.name in :issueTypeNames " +
                "group by t";

        return entityManager.createQuery(sql, TagWallet.class)
                .setParameter("issueStates", OPEN_ISSUE_STATES)
                .setParameter("issueTypeNames", PHONE_DEBT_COLLECTION)
                .getResultList();
    }

    /**
     * Pobieranie portfela ze zleceń o podanym statusie
     *
     * @param issueState - status zleceń
     * @return portfele dla statusów
     */
    public Wallet findWalletByIssueState(final IssueState issueState) {
        final String sql = "select new com.codersteam.alwin.jpa.Wallet(count(distinct i.customer.company), " +
                "sum(i.customer.company.involvement), sum(i.currentBalancePLN), sum(i.currentBalanceEUR)) " +
                "from Issue i where i.excludedFromStats = false and i.issueState = :issueState " +
                "and i.issueType.name in :issueTypeNames";

        return entityManager.createQuery(sql, Wallet.class)
                .setParameter("issueState", issueState)
                .setParameter("issueTypeNames", PHONE_DEBT_COLLECTION)
                .getSingleResult();
    }

    /**
     * Przypisuje do zlecenia o podanym identyfikatorze listę podanych etykiet. Etykieta jest ignorowana jeżeli była już przypisana do zlecenia
     *
     * @param issueId - identyfikator zlecenia
     * @param tagsIds - identyfikatory etykiet
     */
    public void assignTags(final long issueId, final List<Long> tagsIds) {
        final Issue issue = entityManager.getReference(Issue.class, issueId);
        final Set<Tag> tags = new HashSet<>();
        tagsIds.forEach(tag -> tags.add(entityManager.getReference(Tag.class, tag)));
        issue.setTags(tags);
        update(issue);
    }

    /**
     * Przypisuje do odfiltrowanych zleceń etykiety o podanych identyfikatorach
     *
     * @param tagIds              - identyfikatory etykiet do przypisania
     * @param operatorTypeName    - typ operatora, któremu podlegają zlecenia
     * @param criteria            - kryteria filtrowania zleceń
     * @param excludedIssueStates - lista statusów zleceń wyłączonych z wyszukiwania
     * @param activityStates      - lista statusów czynności windykacyjnych
     */
    public void assignIssuesTags(final Set<Long> tagIds, final String operatorTypeName, final IssuesSearchCriteria criteria,
                                 final List<IssueState> excludedIssueStates, final List<ActivityState> activityStates) {
        final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Issue> criteriaQuery = IssuesCriteriaBuilder.createQuery(operatorTypeName, criteria, excludedIssueStates, activityStates,
                criteriaBuilder, new LinkedHashMap<>());
        final TypedQuery<Issue> query = entityManager.createQuery(criteriaQuery);

        final List<Issue> issues = query.getResultList();
        prepareIssueTags(tagIds, issues);
    }

    private void prepareIssueTags(final Set<Long> tagIds, final List<Issue> issues) {
        issues.forEach(issue -> {
            final Set<Long> issueTagIds = issue.getTags().stream().map(Tag::getId).collect(Collectors.toSet());
            tagIds.forEach(tagId -> {
                if (!issueTagIds.contains(tagId)) {
                    issueTagDao.create(new IssueTag(issue.getId(), tagId));
                }
            });
        });
    }

    /**
     * Przypisuje do podanych zleceń etykiety o podanych identyfikatorach
     *
     * @param tagIds   - identyfikatory etykiet do przypisania
     * @param issueIds - identyfikatory zleceń do których przypisane zostaną etykiety
     */
    public void assignIssuesTags(final Set<Long> tagIds, final List<Long> issueIds) {
        final List<Issue> issues = entityManager.createQuery("select i from Issue i where i.id in (:issueIds) ", Issue.class)
                .setParameter("issueIds", issueIds).getResultList();
        prepareIssueTags(tagIds, issues);
    }

    /**
     * Zwraca listę identyfikatorów zleceń, które powinny być oznaczone jako zaległe na podany dzień:
     * posiadają czynność, której data zaplanowania nie jest pusta i jest wcześniejsza niż podana data
     * status czynności wskazuje, że nie została ona wykonana
     * zlecenie nie ma przypisanej jeszcze predefiniowanej etykiety 'Zaległe'
     *
     * @param date - data
     * @return lista identyfikatorów zaległych zleceń
     */
    public List<Long> findOverdueIssueIdsPerDay(final Date date) {
        final String query = "select distinct i.id from Issue i join i.activities a where a.plannedDate is not null " +
                "and a.plannedDate < :plannedDate and a.state not in :closedActivityStates " +
                "and not exists(select 1 from IssueTag it2, Tag t2 where it2.tagId = t2.id and i.id = it2.issueId and t2.type = :overdueTagType)";
        return entityManager.createQuery(query, Long.class)
                .setParameter("closedActivityStates", CLOSED_STATES)
                .setParameter("overdueTagType", TagType.OVERDUE)
                .setParameter("plannedDate", date)
                .getResultList();
    }

    /**
     * Zwraca rozmiar kolejki zleceń do obsłużenia na dany dzień
     *
     * @param issueTypeName - typ zlecenia
     * @param segment       - segment klienta
     * @param forDate       - data dla której sprawdzana jest wielkość kolejki
     * @return liczba zleceń danego typu i segmentu do obsłużenia na dany dzień
     */
    public Long findCurrentIssueQueueCountByIssueTypeAndSegment(final IssueTypeName issueTypeName, final Segment segment, final Date forDate, final List<IssueState> issueStates) {
        final String query = "select count(distinct i) from Issue i join i.activities a where " +
                "i.excludedFromStats = false " +
                "and i.issueState in (:issueStates) " +
                "and i.issueType.name =:issueTypeName " +
                "and i.customer.company.segment =:segment " +
                "and a.plannedDate is not null " +
                "and a.plannedDate <= :plannedDate and a.state not in :closedActivityStates";

        return entityManager.createQuery(query, Long.class)
                .setParameter("issueStates", issueStates)
                .setParameter("issueTypeName", issueTypeName)
                .setParameter("segment", segment)
                .setParameter("closedActivityStates", CLOSED_STATES)
                .setParameter("plannedDate", forDate)
                .getSingleResult();
    }

    /**
     * Zwraca rozmiar kolejki zleceń przypisanych do danej etykiety do obsłużenia na dany dzień
     *
     * @param tagId   - identyfikator etykiety
     * @param forDate - data dla której sprawdzana jest wielkość kolejki
     * @return liczba zleceń przypisanych do danej etykiety do obsłużenia na dany dzień
     */
    public Long findCurrentIssueQueueCountByTag(final Long tagId, final Date forDate) {
        final String query = "select count(distinct i) from Issue i join i.activities a join i.tags t " +
                "where i.excludedFromStats = false " +
                "and a.plannedDate is not null " +
                "and t.id=:tagId " +
                "and a.plannedDate <= :plannedDate and a.state not in :closedActivityStates";

        return entityManager.createQuery(query, Long.class)
                .setParameter("closedActivityStates", CLOSED_STATES)
                .setParameter("plannedDate", forDate)
                .setParameter("tagId", tagId)
                .getSingleResult();
    }

    /**
     * Zwraca rozmiar kolejki zleceń o podanym statusie do obsłużenia na dany dzień
     *
     * @param issueState - status zleceń
     * @param forDate    - data dla której sprawdzana jest wielkość kolejki
     * @return liczba zleceń przypisanych do danej etykiety do obsłużenia na dany dzień
     */
    public Long findCurrentIssueQueueCountByIssueState(final IssueState issueState, final Date forDate) {
        final String query = "select count(distinct i) from Issue i join i.activities a " +
                "where i.excludedFromStats = false " +
                "and i.issueState = :issueState " +
                "and a.plannedDate is not null " +
                "and a.plannedDate <= :plannedDate and a.state not in :closedActivityStates";

        return entityManager.createQuery(query, Long.class)
                .setParameter("closedActivityStates", CLOSED_STATES)
                .setParameter("plannedDate", forDate)
                .setParameter("issueState", issueState)
                .getSingleResult();
    }

    /**
     * Pobiera listę identyfikatorów zleceń z niespłaconymi deklaracjami
     *
     * @param declaredPaymentDateStart - początek przedziału czasowego, w którym zadeklarowano spłatę
     * @param declaredPaymentDateEnd   - koniec przedziału czasowego, w którym zadeklarowano spłatę
     * @return lista identyfikatorów zleceń
     */
    public List<Long> findIssueIdsWithUnpaidDeclarations(final Date declaredPaymentDateStart, final Date declaredPaymentDateEnd) {
        return entityManager.createQuery("select distinct i.id from Activity a left join a.issue i left join a.declarations d " +
                "where (d.paid = false or d.paid is null) " +
                "and d.declaredPaymentDate >= :declaredPaymentDateStart and d.declaredPaymentDate <= :declaredPaymentDateEnd", Long.class)
                .setParameter("declaredPaymentDateStart", declaredPaymentDateStart)
                .setParameter("declaredPaymentDateEnd", declaredPaymentDateEnd)
                .getResultList();
    }

    /**
     * Utworzenie zlecenia
     *
     * @param issue - zlecenie
     */
    public void createIssue(final Issue issue) {
        issue.setIssueType(entityManager.getReference(IssueType.class, issue.getIssueType().getId()));
        create(issue);
    }

    /**
     * Pobieranie typów wybranych zleceń
     *
     * @param issuesIds - identyfikatory zleceń
     * @return lista typów wybranych zleceń
     */
    public List<IssueType> findOperatorTypesForIssues(final List<Long> issuesIds) {
        return entityManager.createQuery("select distinct i.issueType from Issue i where i.id in (:issuesIds)", IssueType.class)
                .setParameter("issuesIds", issuesIds)
                .getResultList();
    }

    /**
     * Pobieranie typów wybranych zleceń
     *
     * @param operatorTypeName    - typ operatora
     * @param criteria            - kryteria filtrowania zleceń
     * @param excludedIssueStates - pomijane typy statusy zleceń
     * @param activityStates      - statusy czynności
     * @return lista typów wybranych zleceń
     */
    public List<IssueType> findOperatorTypesForIssues(String operatorTypeName,
                                                      IssuesSearchCriteria criteria,
                                                      List<IssueState> excludedIssueStates,
                                                      List<ActivityState> activityStates) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<IssueType> criteriaQuery =
                IssuesCriteriaBuilder.createOperatorTypeQuery(operatorTypeName, criteria, excludedIssueStates, activityStates, criteriaBuilder);
        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}
