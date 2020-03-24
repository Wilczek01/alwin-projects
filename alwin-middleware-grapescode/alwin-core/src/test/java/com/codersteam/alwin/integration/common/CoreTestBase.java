package com.codersteam.alwin.integration.common;

import com.codersteam.alwin.core.service.impl.AidaServiceImpl;
import com.codersteam.alwin.core.service.impl.DateProviderImpl;
import com.codersteam.alwin.core.service.impl.UUIDGeneratorServiceImpl;
import com.codersteam.alwin.core.service.impl.email.EmailSenderServiceImpl;
import com.codersteam.alwin.core.service.impl.message.SmsSenderServiceImpl;
import com.codersteam.alwin.dms.DmsClient;
import com.codersteam.alwin.integration.IssueServiceImplTestIT;
import com.codersteam.alwin.integration.mock.*;
import com.codersteam.alwin.testdata.IssueTestData;
import com.codersteam.alwin.testdata.aida.AidaCompanyTestData;
import com.codersteam.alwin.testdata.assertion.Assertions;
import com.codersteam.alwin.testdata.criteria.IssuesSearchCriteriaTestData;
import org.eu.ingwar.tools.arquillian.extension.suite.annotations.ArquilianSuiteDeployment;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.persistence.CleanupUsingScript;
import org.jboss.arquillian.persistence.TestExecutionPhase;
import org.jboss.arquillian.persistence.core.configuration.ConfigurationImporter;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.importer.ZipImporter;
import org.jboss.shrinkwrap.api.spec.EnterpriseArchive;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.After;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import javax.transaction.UserTransaction;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Michal Horowic
 */
@RunWith(Arquillian.class)
@ArquilianSuiteDeployment
@CleanupUsingScript(value = "cleanup.sql", phase = TestExecutionPhase.BEFORE)
public abstract class CoreTestBase {

    static {
        // usunięcie powtarzającego się logu podczas testów integracyjnych : "does not support properties provided '[$jacocoData]"
        Logger.getLogger(ConfigurationImporter.class.getName()).setLevel(Level.SEVERE);
    }

    @Inject
    private UserTransaction utx;

    @Deployment
    public static EnterpriseArchive createDeployment() {
        final JavaArchive testArchive = buildTestArquillianArchive();
        final JavaArchive alwinFrontendArchive = buildAlwinFrontendArchive();
        final JavaArchive alwinDbArchive = buildAlwinDbArchive();
        final JavaArchive alwinTestDataArchive = buildAlwinTestDataArchive();

        final EnterpriseArchive ear = buildAlwinEarPackage();
        ear.addAsLibraries(resolveLibrary("org.assertj:assertj-core"));
        ear.addAsLibraries(alwinFrontendArchive);
        ear.addAsLibraries(alwinDbArchive);
        ear.addAsLibraries(alwinTestDataArchive);
        ear.addAsLibraries(testArchive);
        ear.addAsResource("test-persistence.xml", "META-INF/persistence.xml");
        ear.addAsManifestResource("test-ds.xml", "test-ds.xml");

        mockServices(ear);
        return ear;
    }

    private static void mockServices(final EnterpriseArchive ear) {
        ear.getAsType(JavaArchive.class, "alwin-core.jar").deleteClass(DateProviderImpl.class);
        ear.getAsType(JavaArchive.class, "alwin-core.jar").deleteClass(UUIDGeneratorServiceImpl.class);
        ear.getAsType(JavaArchive.class, "alwin-core.jar").deleteClass(AidaServiceImpl.class);
        ear.getAsType(JavaArchive.class, "alwin-core.jar").deleteClass(EmailSenderServiceImpl.class);
        ear.getAsType(JavaArchive.class, "alwin-core.jar").deleteClass(SmsSenderServiceImpl.class);
        ear.getAsType(JavaArchive.class, "alwin-dms-connector.jar").deleteClass(DmsClient.class);
        ear.getAsType(JavaArchive.class, "alwin-core.jar").addPackage(AidaServiceMock.class.getPackage());
    }

    private static EnterpriseArchive buildAlwinEarPackage() {
        final EnterpriseArchive ear = ShrinkWrap.create(EnterpriseArchive.class, "application-ear.ear")
                .as(ZipImporter.class)
                .importFrom(new File("../alwin-middleware-ear/target/alwin-middleware.ear"))
                .as(EnterpriseArchive.class);

        ear.delete("lib/alwin-frontend-jpa.jar");
        ear.delete("lib/alwin-db.jar");
        ear.delete("lib/alwin-leo-connector-jpa.jar");
        ear.delete("alwin-rest-doc.war");
        ear.delete("alwin-rest.war");
        ear.delete("META-INF/application.xml");
        return ear;
    }

    private static JavaArchive buildAlwinDbArchive() {
        final JavaArchive alwinDbArchive = ShrinkWrap.create(JavaArchive.class, "alwin-db.jar")
                .as(ZipImporter.class)
                .importFrom(new File("../alwin-db/target/alwin-db.jar"))
                .as(JavaArchive.class);
        alwinDbArchive.addAsManifestResource("test-ds.xml", "test-ds.xml");
        return alwinDbArchive;
    }

    private static JavaArchive buildAlwinTestDataArchive() {
        return ShrinkWrap.create(JavaArchive.class, "alwin-test-data-tests.jar")
                .as(ZipImporter.class)
                .importFrom(new File("../alwin-test-data/target/alwin-test-data-tests.jar"))
                .as(JavaArchive.class);
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
        return ShrinkWrap.create(JavaArchive.class, "arquillian-tests.jar")
                .addPackage(IssueServiceImplTestIT.class.getPackage())
                .addPackage(CoreTestBase.class.getPackage())
                .addPackage(IssueTestData.class.getPackage())
                .addPackage(IssuesSearchCriteriaTestData.class.getPackage())
                .addPackage(AidaCompanyTestData.class.getPackage())
                .addPackage(Assertions.class.getPackage())
                .addAsResource("datasets", "datasets")
                .addAsResource("scripts", "scripts")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
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

    @After
    public void cleanup() {
        DateProviderMock.reset();
        CurrencyExchangeRateServiceMock.reset();
        InvoiceServiceMock.reset();
        ContractServiceMock.reset();
        CompanyServiceMock.reset();
        InvolvementServiceMock.reset();
        SegmentServiceMock.reset();
        ExcessPaymentServiceMock.reset();
        ContractSubjectServiceMock.reset();
    }
}
