package com.codersteam.alwin.db.dao.write;

import com.codersteam.alwin.db.dao.InvoiceDao;
import com.codersteam.alwin.jpa.issue.Invoice;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.junit.Test;

import javax.ejb.EJB;
import java.util.Optional;

import static com.codersteam.alwin.testdata.InvoiceTestData.ID_100;
import static com.codersteam.alwin.testdata.InvoiceTestData.ID_11;
import static com.codersteam.alwin.testdata.InvoiceTestData.ID_29;
import static com.codersteam.alwin.testdata.InvoiceTestData.INVOICE_NUMBER_11;
import static com.codersteam.alwin.testdata.InvoiceTestData.invoiceToCreate;
import static com.codersteam.alwin.testdata.InvoiceTestData.testInvoice29;
import static com.codersteam.alwin.testdata.InvoiceTestData.testInvoice32;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Piotr Naroznik
 */
public class InvoiceDaoTestIT extends WriteTestBase {

    @EJB
    private InvoiceDao invoiceDao;

    @Test
    public void shouldCreateInvoiceWithCorrections() {
        // when
        invoiceDao.create(invoiceToCreate());

        final Optional<Invoice> invoiceByNumber = invoiceDao.findInvoiceByNumber(INVOICE_NUMBER_11);
        // then
        assertTrue(invoiceByNumber.isPresent());
        assertEquals(3, invoiceByNumber.get().getCorrections().size());
        assertEquals(4, invoiceDao.all().size());
    }

    @Test
    @UsingDataSet({"test-permission.json", "test-data.json", "test-issue.json", "test-issue-invoice.json"})
    public void shouldUpdateInvoiceWithCorrections() {
        // given
        final Invoice invoice = invoiceDao.get(ID_11).get();
        invoice.getCorrections().clear();
        invoice.getCorrections().addAll(asList(testInvoice32(), testInvoice29()));

        // when
        invoiceDao.update(invoice);

        // then
        final Optional<Invoice> invoiceById = invoiceDao.get(ID_11);
        // then
        assertTrue(invoiceById.isPresent());
        assertEquals(2, invoiceById.get().getCorrections().size());
        assertEquals(ID_100, invoiceById.get().getCorrections().get(0).getId().longValue());
        assertEquals(ID_29, invoiceById.get().getCorrections().get(1).getId().longValue());
        assertEquals(26, invoiceDao.all().size());
    }

    @Test
    @UsingDataSet({"test-permission.json", "test-data.json", "test-issue.json", "test-issue-invoice.json"})
    public void shouldRemoveInvoiceCorrections() {
        // given
        final Invoice invoice = invoiceDao.get(ID_11).get();
        invoice.getCorrections().clear();

        // when
        invoiceDao.update(invoice);

        // then
        final Optional<Invoice> invoiceById = invoiceDao.get(ID_11);
        // then
        assertTrue(invoiceById.isPresent());
        assertEquals(0, invoiceById.get().getCorrections().size());
        assertEquals(24, invoiceDao.all().size());
    }
}
