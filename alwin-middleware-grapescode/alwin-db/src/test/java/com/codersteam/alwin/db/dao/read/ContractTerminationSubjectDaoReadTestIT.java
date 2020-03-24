package com.codersteam.alwin.db.dao.read;

import com.codersteam.alwin.db.dao.ContractTerminationSubjectDao;
import com.codersteam.alwin.jpa.termination.ContractTerminationSubject;
import com.codersteam.alwin.testdata.ContractTerminationSubjectTestData;
import org.junit.Test;

import javax.ejb.EJB;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

/**
 * @author Dariusz Rackowski
 */
public class ContractTerminationSubjectDaoReadTestIT extends ReadTestBase {

    @EJB
    private ContractTerminationSubjectDao contractTerminationSubjectDao;

    @Test
    public void shouldReturnDaoType() {
        // when
        final Class<ContractTerminationSubject> type = contractTerminationSubjectDao.getType();

        // then
        assertEquals(ContractTerminationSubject.class, type);
    }

    @Test
    public void shouldReturnAll() {
        // when
        final List<ContractTerminationSubject> contractTerminationSubjects = contractTerminationSubjectDao.all();

        // then
        assertThat(contractTerminationSubjects).hasSize(ContractTerminationSubjectTestData.COUNT_OF_ALL);
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @Test
    public void shouldReturnOneById() {
        // when
        final Optional<ContractTerminationSubject> contractTerminationSubject = contractTerminationSubjectDao.get(ContractTerminationSubjectTestData.ID_128);

        // then
        assertThat(contractTerminationSubject).isPresent();
        assertThat(contractTerminationSubject.get())
                .isEqualToComparingFieldByFieldRecursively(ContractTerminationSubjectTestData.contractTerminationSubject128());
    }

}
