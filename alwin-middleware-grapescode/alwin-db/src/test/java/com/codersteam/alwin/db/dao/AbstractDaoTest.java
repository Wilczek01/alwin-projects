package com.codersteam.alwin.db.dao;

import com.codersteam.alwin.jpa.User;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class AbstractDaoTest {

    private AbstractDao<User> dao;

    @Before
    public void setup() {
        dao = new AbstractDao<User>() {
            @Override
            protected Class<User> getType() {
                return User.class;
            }
        };
    }

    @Test
    public void shouldReturnClassType() {
        // when
        Class<User> classType = dao.getType();

        // then
        assertEquals(User.class, classType);
    }

    @Test
    public void shouldGetSingleResult() {
        // given
        String first = "first";
        List<String> results = asList(first, "second", "third");

        // when
        Optional<String> singleResult = dao.checkForSingleResult(results);

        // then
        assertTrue(singleResult.isPresent());
        assertEquals(first, singleResult.get());
    }

    @Test
    public void shouldGetEmptySingleResult() {
        // given
        List<String> results = emptyList();

        // when
        Optional<String> singleResult = dao.checkForSingleResult(results);

        // then
        assertFalse(singleResult.isPresent());
    }

    @Test
    public void shouldPersistObject() {
        // given
        EntityManager entityManager = Mockito.mock(EntityManager.class);
        dao.entityManager = entityManager;
        User user = new User();

        // when
        dao.create(user);

        // then
        verify(entityManager, times(1)).persist(user);
    }

    @Test
    public void shouldUpdateObject() {
        // given
        EntityManager entityManager = Mockito.mock(EntityManager.class);
        dao.entityManager = entityManager;
        User user = new User();

        // when
        dao.update(user);

        // then
        verify(entityManager, times(1)).merge(user);
    }

    @Test
    public void shouldDeleteObject() {
        // given
        EntityManager entityManager = Mockito.mock(EntityManager.class);
        dao.entityManager = entityManager;
        User user = new User();
        Long userId = 100L;
        user.setId(userId);
        when(entityManager.find(dao.getType(), userId)).thenReturn(user);

        // when
        dao.delete(userId);

        // then
        verify(entityManager, times(1)).remove(user);
    }

    @Test
    public void shouldGetObject() {
        // given
        EntityManager entityManager = Mockito.mock(EntityManager.class);
        dao.entityManager = entityManager;
        User user = new User();
        Long userId = 100L;
        user.setId(userId);
        when(entityManager.find(dao.getType(), userId)).thenReturn(user);

        // when
        Optional<User> alwinUser = dao.get(userId);

        // then
        assertTrue(alwinUser.isPresent());
        assertEquals(user, alwinUser.get());
    }

    @Test
    public void shouldNotGetObject() {
        // given
        dao.entityManager = Mockito.mock(EntityManager.class);
        User user = new User();
        Long userId = 100L;
        user.setId(userId);

        // when
        Optional<User> alwinUser = dao.get(userId);

        // then
        assertFalse(alwinUser.isPresent());
    }

    @Test
    public void shouldGetAllObjects() {
        // given
        EntityManager entityManager = Mockito.mock(EntityManager.class);
        dao.entityManager = entityManager;
        List<User> users = asList(new User(), new User(), new User());
        CriteriaBuilder criteriaBuilder = Mockito.mock(CriteriaBuilder.class);
        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        CriteriaQuery<User> criteriaQuery = Mockito.mock(CriteriaQuery.class);
        when(criteriaBuilder.createQuery(dao.getType())).thenReturn(criteriaQuery);
        TypedQuery<User> query = Mockito.mock(TypedQuery.class);
        when(entityManager.createQuery(criteriaQuery)).thenReturn(query);
        when(query.getResultList()).thenReturn(users);

        // when
        List<User> alwinUsers = dao.all();

        // then
        assertEquals(users.size(), alwinUsers.size());
        assertEquals(users, alwinUsers);
    }
}