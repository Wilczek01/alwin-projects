package com.codersteam.alwin.db.dao.read;

import com.codersteam.alwin.db.dao.FormalDebtCollectionInvoiceDao;
import com.codersteam.alwin.jpa.termination.FormalDebtCollectionInvoice;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.Test;

import javax.ejb.EJB;
import java.util.List;

import static com.codersteam.alwin.testdata.FormalDebtCollectionInvoiceTestData.ALL_FORMAL_DEBT_COLLECTION_INVOICES;
import static com.codersteam.alwin.testdata.assertion.TimestampToDateComparator.TIMESTAMP_TO_DATE_COMPARATOR;
import static org.junit.Assert.assertEquals;

/**
 * @author Dariusz Rackowski
 */
public class FormalDebtCollectionInvoiceDaoReadTestIT extends ReadTestBase {

    @EJB
    private FormalDebtCollectionInvoiceDao formalDebtCollectionInvoiceDao;

    @Test
    public void shouldReturnDaoType() {
        // when
        final Class<FormalDebtCollectionInvoice> type = formalDebtCollectionInvoiceDao.getType();

        // then
        assertEquals(FormalDebtCollectionInvoice.class, type);
    }

    @Test
    public void shouldReturnAll() {
        // when
        final List<FormalDebtCollectionInvoice> formalDebtCollectionInvoices = formalDebtCollectionInvoiceDao.all();

        // then
        AssertionsForClassTypes.assertThat(formalDebtCollectionInvoices)
                .usingComparatorForFields(TIMESTAMP_TO_DATE_COMPARATOR, "issueDate", "realDueDate")
                .isEqualToComparingFieldByFieldRecursively(ALL_FORMAL_DEBT_COLLECTION_INVOICES);
    }

}
