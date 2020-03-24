package com.codersteam.alwin.core.service.impl.customer


import com.codersteam.aida.core.api.service.CompanyService
import com.codersteam.aida.core.api.service.PersonService
import com.codersteam.alwin.core.api.service.AidaService
import com.codersteam.alwin.core.api.service.DateProvider
import com.codersteam.alwin.core.api.service.customer.SyncDataService
import com.codersteam.alwin.db.dao.LastDataSyncDao
import com.codersteam.alwin.jpa.LastDataSync
import spock.lang.Specification
import spock.lang.Subject

import static com.codersteam.alwin.core.service.AlwinParameters.SYNC_DATA_DAY_START
import static com.codersteam.alwin.jpa.type.LastDataSyncType.UPDATE_COMPANY_DATA
import static com.codersteam.alwin.testdata.LastDataSyncTestData.LAST_DATA_SYNC_TO_DATE_1
import static com.codersteam.alwin.testdata.LastDataSyncTestData.lastDataSync1
import static com.codersteam.alwin.testdata.TestDateUtils.parse
import static com.codersteam.alwin.testdata.aida.AidaCompanyTestData.COMPANY_ID_10
import static com.codersteam.alwin.testdata.aida.AidaCompanyTestData.aidaCompanyDto10
import static com.codersteam.alwin.testdata.aida.AidaPersonTestData.*
import static com.codersteam.alwin.testdata.assertion.LastDataSyncAssert.assertLastDataSync

class UpdateCompaniesServiceImplTest extends Specification {

    @Subject
    private UpdateCompaniesService service

    def aidaService = Mock(AidaService)
    def dateProvider = Mock(DateProvider)
    def personService = Mock(PersonService)
    def companyService = Mock(CompanyService)
    def lastDataSyncDao = Mock(LastDataSyncDao)
    def syncDataService = Mock(SyncDataService)
    def updateLastDataSyncService = new UpdateLastDataSyncServiceImpl(lastDataSyncDao)

    def "setup"() {
        aidaService.getPersonService() >> personService
        aidaService.getCompanyService() >> companyService
        service = new UpdateCompaniesService(lastDataSyncDao, aidaService, dateProvider, syncDataService, updateLastDataSyncService)
    }

    def "should not update companies if no modifications was found"() {
        given:
            def toDate = parse("2016-10-14 12:23:34.000000")
        and:
            def fromDate = parse("2016-10-12 12:23:34.000000")
        and:
            lastDataSyncDao.findByType(UPDATE_COMPANY_DATA) >> Optional.empty()
        and:
            dateProvider.getCurrentDate() >> toDate
        and:
            dateProvider.getDateStartOfDayMinusDays(SYNC_DATA_DAY_START) >> fromDate
        and:
            companyService.findModifiedCompaniesExclusiveFromDate(fromDate, toDate) >> []
        and:
            personService.findModifiedPersonsExclusiveFromDate(fromDate, toDate) >> []
        when:
            service.syncCompanyData()
        then:
            0 * syncDataService.updateCompanyIfExist(_, _, _)
            1 * lastDataSyncDao.update(_ as LastDataSync) >> { args ->
                assertLastDataSync((LastDataSync) args[0], fromDate, toDate, UPDATE_COMPANY_DATA)
            }
    }

    def "should not update companies if wasn't imported before"() {
        given:
            def toDate = parse("2016-10-14 12:23:34.000000")
        and:
            def fromDate = LAST_DATA_SYNC_TO_DATE_1
        and:
            lastDataSyncDao.findByType(UPDATE_COMPANY_DATA) >> Optional.of(lastDataSync1())
        and:
            dateProvider.getCurrentDate() >> toDate
        and:
            def aidaCompanyDto = aidaCompanyDto10()
        and:
            companyService.findModifiedCompaniesExclusiveFromDate(fromDate, toDate) >> [aidaCompanyDto]
        and:
            def aidaPersonDto = aidaPerson1()
        and:
            personService.findModifiedPersonsExclusiveFromDate(fromDate, toDate) >> [aidaPersonDto]
        when:
            service.syncCompanyData()
        then:
            1 * syncDataService.updateCompanyIfExist(aidaCompanyDto, null, COMPANY_ID_10)
            1 * syncDataService.updateCompanyIfExist(null, [aidaPersonDto], COMPANY_ID)
            1 * lastDataSyncDao.update(_ as LastDataSync) >> { args ->
                assertLastDataSync((LastDataSync) args[0], fromDate, toDate, UPDATE_COMPANY_DATA)
            }
    }

    def "should update companies"() {
        given:
            def toDate = parse("2016-10-14 12:23:34.000000")
        and:
            def fromDate = LAST_DATA_SYNC_TO_DATE_1
        and:
            lastDataSyncDao.findByType(UPDATE_COMPANY_DATA) >> Optional.of(lastDataSync1())
        and:
            dateProvider.getCurrentDate() >> toDate
        and:
            def aidaCompanyDto = aidaCompanyDto10()
        and:
            companyService.findModifiedCompaniesExclusiveFromDate(fromDate, toDate) >> [aidaCompanyDto]
        and:
            def aidaPersonDto2 = aidaPerson2()
        and:
            def aidaPersonDto3 = aidaPerson3()
        and:
            personService.findModifiedPersonsExclusiveFromDate(fromDate, toDate) >> [aidaPersonDto2, aidaPersonDto3]
        when:
            service.syncCompanyData()
        then:
            1 * syncDataService.updateCompanyIfExist(aidaCompanyDto, null, COMPANY_ID_10)
            1 * syncDataService.updateCompanyIfExist(null, [aidaPersonDto2, aidaPersonDto3], COMPANY_ID)
            1 * lastDataSyncDao.update(_ as LastDataSync) >> { args ->
                assertLastDataSync((LastDataSync) args[0], fromDate, toDate, UPDATE_COMPANY_DATA)
            }
    }

    def "should update company even if exception occurs"() {
        given:
            def toDate = parse("2016-10-14 12:23:34.000000")
        and:
            def fromDate = LAST_DATA_SYNC_TO_DATE_1
        and:
            lastDataSyncDao.findByType(UPDATE_COMPANY_DATA) >> Optional.of(lastDataSync1())
        and:
            dateProvider.getCurrentDate() >> toDate
        and:
            def aidaCompanyDto = aidaCompanyDto10()
        and:
            companyService.findModifiedCompaniesExclusiveFromDate(fromDate, toDate) >> [aidaCompanyDto]
        and:
            def aidaPersonDto2 = aidaPerson2()
        and:
            def aidaPersonDto3 = aidaPerson3()
        and:
            personService.findModifiedPersonsExclusiveFromDate(fromDate, toDate) >> [aidaPersonDto2, aidaPersonDto3]
        and:
            syncDataService.updateCompanyIfExist(_, _, _) >> { throw new RuntimeException("ERROR") }
        when:
            service.syncCompanyData()
        then:
            noExceptionThrown()
    }
}
