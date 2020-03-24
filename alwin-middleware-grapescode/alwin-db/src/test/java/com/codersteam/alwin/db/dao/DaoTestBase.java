package com.codersteam.alwin.db.dao;

import com.codersteam.aida.core.api.model.AidaCompanyDto;
import com.codersteam.alwin.common.search.criteria.SearchCriteria;
import com.codersteam.alwin.common.search.param.ContractTerminationCriteriaParams;
import com.codersteam.alwin.common.search.util.PostalCodeUtils;
import com.codersteam.alwin.db.dao.criteria.CompanyIssuesCriteriaBuilder;
import com.codersteam.alwin.db.dao.criteria.strategy.OrderIssue;
import com.codersteam.alwin.db.dao.listener.OperatorRevisionListener;
import com.codersteam.alwin.db.dao.read.ReadTestBase;
import com.codersteam.alwin.db.dao.write.WriteTestBase;
import com.codersteam.alwin.db.util.AuditQueryUtils;
import com.codersteam.alwin.jpa.User;
import com.codersteam.alwin.jpa.activity.Activity;
import com.codersteam.alwin.jpa.audit.AuditLogEntry;
import com.codersteam.alwin.jpa.customer.Customer;
import com.codersteam.alwin.jpa.holiday.Holiday;
import com.codersteam.alwin.jpa.issue.Issue;
import com.codersteam.alwin.jpa.notice.DemandForPayment;
import com.codersteam.alwin.jpa.operator.Operator;
import com.codersteam.alwin.jpa.scheduler.SchedulerConfiguration;
import com.codersteam.alwin.jpa.scheduler.SchedulerExecutionInfo;
import com.codersteam.alwin.jpa.termination.ContractTermination;
import com.codersteam.alwin.jpa.termination.FormalDebtCollectionInvoice;
import com.codersteam.alwin.jpa.type.OperatorNameType;
import com.codersteam.alwin.testdata.IssueTestData;
import com.codersteam.alwin.testdata.aida.AidaCompanyTestData;
import com.codersteam.alwin.testdata.assertion.Assertions;
import com.codersteam.alwin.testdata.criteria.IssueSearchCriteriaTestData;
import org.eu.ingwar.tools.arquillian.extension.suite.annotations.ArquilianSuiteDeployment;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.persistence.Cleanup;
import org.jboss.arquillian.persistence.TestExecutionPhase;
import org.jboss.arquillian.persistence.core.configuration.ConfigurationImporter;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.importer.ZipImporter;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Rule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import javax.transaction.UserTransaction;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.codersteam.alwin.db.dao.DaoTestRunner.ALL_TEST_CLASS_NAMES_FOR_LOGGER;

@RunWith(Arquillian.class)
@ArquilianSuiteDeployment
@Cleanup(phase = TestExecutionPhase.NONE)
public abstract class DaoTestBase {

    private static final Logger LOG = Logger.getLogger(DaoTestBase.class.getName());

    static {
        // usunięcie powtarzającego się logu podczas testów integracyjnych : "does not support properties provided '[$jacocoData]"
        Logger.getLogger(ConfigurationImporter.class.getName()).setLevel(Level.SEVERE);
    }

    @Rule
    public TestWatcher testLoggerWatcher = new TestWatcher() {
        @Override
        protected void starting(final Description description) {
            if (!ALL_TEST_CLASS_NAMES_FOR_LOGGER.contains(description.getClassName())) {
                ALL_TEST_CLASS_NAMES_FOR_LOGGER.add(description.getClassName());
                LOG.info("Starting test: " + description.getClassName());
            }
        }
    };

    @Inject
    private UserTransaction utx;

    @Deployment
    public static WebArchive createDeployment() {
        final JavaArchive alwinCoreApiArchive = ShrinkWrap.create(JavaArchive.class, "alwin-core-api.jar")
                .as(ZipImporter.class)
                .importFrom(new File("../alwin-core-api/target/alwin-core-api.jar"))
                .as(JavaArchive.class);

        return ShrinkWrap.create(WebArchive.class)
                .addAsLibraries(resolveLibrary("com.google.guava:guava"))
                .addAsLibraries(resolveLibrary("org.assertj:assertj-core"))
                .addAsLibraries(resolveLibrary("com.codersteam.alwin:alwin-common:1.15.1.20181026"))
                .addPackage(Customer.class.getPackage())
                .addPackage(Operator.class.getPackage())
                .addPackage(Issue.class.getPackage())
                .addPackage(Activity.class.getPackage())
                .addPackage(User.class.getPackage())
                .addPackage(Operator.class.getPackage())
                .addPackage(OperatorNameType.class.getPackage())
                .addPackage(UserDao.class.getPackage())
                .addPackage(IssueTestData.class.getPackage())
                .addPackage(IssueSearchCriteriaTestData.class.getPackage())
                .addPackage(Assertions.class.getPackage())
                .addPackage(SearchCriteria.class.getPackage())
                .addPackage(ContractTerminationCriteriaParams.class.getPackage())
                .addPackage(PostalCodeUtils.class.getPackage())
                .addPackage(CompanyIssuesCriteriaBuilder.class.getPackage())
                .addPackage(OperatorRevisionListener.class.getPackage())
                .addPackage(AuditLogEntry.class.getPackage())
                .addPackage(AuditQueryUtils.class.getPackage())
                .addPackage(DemandForPayment.class.getPackage())
                .addPackage(Holiday.class.getPackage())
                .addPackage(ReadTestBase.class.getPackage())
                .addPackage(WriteTestBase.class.getPackage())
                .addPackage(AidaCompanyDto.class.getPackage())
                .addPackage(AidaCompanyTestData.class.getPackage())
                .addPackage(SchedulerExecutionInfo.class.getPackage())
                .addPackage(SchedulerConfiguration.class.getPackage())
                .addPackage(ContractTermination.class.getPackage())
                .addPackage(FormalDebtCollectionInvoice.class.getPackage())
                .addPackage(OrderIssue.class.getPackage())
                .addAsLibraries(alwinCoreApiArchive)
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
                .addAsResource("datasets", "datasets")
                .addAsResource("scripts", "scripts")
                .addAsWebInfResource("test-ds.xml", "test-ds.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    private static File[] resolveLibrary(final String library) {
        return Maven.resolver()
                .loadPomFromFile("pom.xml")
                .resolve(library)
                .withTransitivity().as(File.class);
    }

    protected void commitTrx() throws Exception {
        utx.commit();
        utx.begin();
    }
}
