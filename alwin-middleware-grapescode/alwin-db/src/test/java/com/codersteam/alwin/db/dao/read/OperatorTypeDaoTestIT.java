package com.codersteam.alwin.db.dao.read;

import com.codersteam.alwin.db.dao.OperatorTypeDao;
import com.codersteam.alwin.jpa.operator.OperatorType;
import org.junit.Test;

import javax.ejb.EJB;
import java.util.List;

import static com.codersteam.alwin.testdata.OperatorTypeTestData.allOperatorTypes;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertEquals;

public class OperatorTypeDaoTestIT extends ReadTestBase {

    @EJB
    private OperatorTypeDao operatorDao;

    @Test
    public void shouldFindAllOperatorTypes() {
        // when
        final List<OperatorType> operatorTypes = operatorDao.findAll();

        // then
        assertThat(operatorTypes).isEqualToComparingFieldByFieldRecursively(allOperatorTypes());
    }

    @Test
    public void shouldReturnDaoType() {
        // when
        final Class<OperatorType> type = operatorDao.getType();

        // then
        assertEquals(OperatorType.class, type);
    }
}
