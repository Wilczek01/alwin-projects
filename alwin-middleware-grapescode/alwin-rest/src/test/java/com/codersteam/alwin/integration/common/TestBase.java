package com.codersteam.alwin.integration.common;

import com.codersteam.alwin.auth.model.BalanceStartAndAdditionalResponse;
import com.codersteam.alwin.dms.DmsClient;
import com.codersteam.alwin.integration.mock.*;
import com.codersteam.alwin.integration.read.ReadTestBase;
import com.codersteam.alwin.integration.write.WriteTestBase;
import com.codersteam.alwin.model.ErrorResponse;
import com.codersteam.alwin.testdata.AbstractIssuesTestData;
import com.codersteam.alwin.testdata.aida.AidaCompanyTestData;
import org.eu.ingwar.tools.arquillian.extension.suite.annotations.ArquilianSuiteDeployment;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.persistence.Cleanup;
import org.jboss.arquillian.persistence.TestExecutionPhase;
import org.jboss.arquillian.persistence.core.configuration.ConfigurationImporter;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.importer.ZipImporter;
import org.jboss.shrinkwrap.api.spec.EnterpriseArchive;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.After;
import org.junit.Rule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.transaction.UserTransaction;
import java.io.File;
import java.lang.invoke.MethodHandles;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.codersteam.alwin.integration.common.HttpRestTestRunner.ALL_TEST_CLASS_NAMES_FOR_LOGGER;

/**
 * @author Michal Horowic
 */
@RunWith(Arquillian.class)
@ArquilianSuiteDeployment
@Cleanup(phase = TestExecutionPhase.NONE)
public abstract class TestBase {

    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

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
    public static EnterpriseArchive createDeployment() {
        final JavaArchive[] httpClientLibs = resolve("org.apache.httpcomponents:httpclient");
        final JavaArchive[] httpComponentsLibs = resolve("org.apache.httpcomponents:fluent-hc");
        final JavaArchive[] gsonLibs = resolve("com.google.code.gson:gson");

        final JavaArchive testArchive = buildTestArquillianArchive();
        final JavaArchive alwinFrontendArchive = buildAlwinFrontendArchive();
        final JavaArchive alwinDbArchive = buildAlwinDbArchive();

        final EnterpriseArchive ear = buildAlwinEarPackage();
        ear.addAsLibraries(alwinFrontendArchive);
        ear.addAsLibraries(alwinDbArchive);
        ear.addAsLibraries(testArchive);
        ear.addAsLibraries(httpClientLibs);
        ear.addAsLibraries(httpComponentsLibs);
        ear.addAsLibraries(gsonLibs);
        ear.addAsLibraries(resolve("org.assertj:assertj-core"));
        ear.addAsResource("test-persistence.xml", "META-INF/persistence.xml");
        ear.addAsManifestResource("test-ds.xml", "test-ds.xml");
        mockServices(ear);
        return ear;
    }

    private static void mockServices(final EnterpriseArchive ear) {
        ear.getAsType(JavaArchive.class, "alwin-core.jar").deleteClass("com.codersteam.alwin.core.service.impl.DateProviderImpl");
        ear.getAsType(JavaArchive.class, "alwin-core.jar").deleteClass("com.codersteam.alwin.core.service.impl.UUIDGeneratorServiceImpl");
        ear.getAsType(JavaArchive.class, "alwin-core.jar").deleteClass("com.codersteam.alwin.core.service.impl.AidaServiceImpl");
        ear.getAsType(JavaArchive.class, "alwin-core.jar").deleteClass("com.codersteam.alwin.core.service.impl.message.SmsSenderServiceImpl");
        ear.getAsType(JavaArchive.class, "alwin-core.jar").deleteClass("com.codersteam.alwin.core.service.impl.scheduler.BatchProcessScheduler");
        ear.getAsType(JavaArchive.class, "alwin-core.jar").deleteClass("com.codersteam.alwin.core.service.impl.scheduler.BatchProcessSchedulerServiceImpl");
        ear.getAsType(JavaArchive.class, "alwin-core.jar").deleteClass("com.codersteam.alwin.core.service.impl.email.EmailSenderServiceImpl");
        ear.getAsType(JavaArchive.class, "alwin-dms-connector.jar").deleteClass(DmsClient.class);
        ear.getAsType(JavaArchive.class, "alwin-core.jar").addPackage(AidaServiceMock.class.getPackage());
    }

    private static JavaArchive[] resolve(final String dependency) {
        return Maven.resolver().loadPomFromFile("pom.xml").resolve(dependency).withTransitivity().as(JavaArchive.class);
    }

    private static EnterpriseArchive buildAlwinEarPackage() {
        final EnterpriseArchive ear = ShrinkWrap.create(EnterpriseArchive.class, "application-ear.ear")
                .as(ZipImporter.class)
                .importFrom(new File("../alwin-middleware-ear/target/alwin-middleware.ear"))
                .as(EnterpriseArchive.class);

        ear.delete("lib/alwin-frontend-jpa.jar");
        ear.delete("lib/alwin-db.jar");
        removeAlwinRestDocsWar(ear);

        return ear;
    }

    // arquillian ma problem z kilkoma warami w earze, dlatego usuwamy rest docs'y
    private static void removeAlwinRestDocsWar(final EnterpriseArchive ear) {
        ear.delete("alwin-rest-doc.war");
        ear.delete("META-INF/application.xml");
        ear.addAsResource("test-application.xml", "META-INF/application.xml");
    }

    private static JavaArchive buildAlwinDbArchive() {
        final JavaArchive alwinDbArchive = ShrinkWrap.create(JavaArchive.class, "alwin-db.jar")
                .as(ZipImporter.class)
                .importFrom(new File("../alwin-db/target/alwin-db.jar"))
                .as(JavaArchive.class);
        alwinDbArchive.addAsManifestResource("test-ds.xml", "test-ds.xml");
        return alwinDbArchive;
    }

    private static JavaArchive buildAlwinFrontendArchive() {
        final JavaArchive alwinFrontendArchive = ShrinkWrap.create(JavaArchive.class, "alwin-frontend-jpa.jar")
                .as(ZipImporter.class)
                .importFrom(new File("../alwin-frontend-jpa/target/alwin-frontend-jpa.jar"))
                .as(JavaArchive.class);
        alwinFrontendArchive.delete("META-INF/persistence.xml");
        alwinFrontendArchive.addAsResource("test-persistence.xml", "META-INF/persistence.xml");
        return alwinFrontendArchive;
    }

    private static JavaArchive buildTestArquillianArchive() {
        final JavaArchive test = ShrinkWrap.create(JavaArchive.class, "arquillian-tests.jar")
                .addPackage(ReadTestBase.class.getPackage())
                .addPackage(WriteTestBase.class.getPackage())
                .addPackage(TestBase.class.getPackage())
                .addPackage(AbstractIssuesTestData.class.getPackage())
                .addPackage(AidaCompanyTestData.class.getPackage())
                .addPackage(ErrorResponse.class.getPackage())
                .addClass(BalanceStartAndAdditionalResponse.class)
                .addAsResource("datasets", "datasets")
                .addAsResource("scripts", "scripts")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");

        test.addAsResource("messages/request", "messages/request");
        test.addAsResource("messages/response", "messages/response");
        return test;
    }

    protected void commitTrx() throws Exception {
        utx.commit();
        utx.begin();
    }

    @After
    public void cleanup() {
        DateProviderMock.reset();
        InvolvementServiceMock.reset();
        SegmentServiceMock.reset();
        InvoiceServiceMock.reset();
        ContractServiceMock.reset();
        CompanyServiceMock.reset();
        CurrencyExchangeRateServiceMock.reset();
        ExcessPaymentServiceMock.reset();
        ContractSubjectServiceMock.reset();
    }
}
