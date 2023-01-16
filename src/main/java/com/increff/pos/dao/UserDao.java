package com.increff.pos.dao;

import com.increff.pos.pojo.UserPojo;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class UserDao extends AbstractDao {

    private static String DELETE_BY_ID = "delete from UserPojo p where id=:id";
    private static String select_id = "select p from UserPojo p where id=:id";
    private static String SELECT_BY_EMAIL = "select p from UserPojo p where email=:email";
    private static String SELECT_ALL = "select p from UserPojo p";


    @Transactional
    public void insert(UserPojo p) {
        em().persist(p);
    }

    public int delete(int id) {
        Query query = em().createQuery(DELETE_BY_ID);
        query.setParameter("id", id);
        return query.executeUpdate();
    }

    public UserPojo select(int id) {
        TypedQuery<UserPojo> query = getQuery(select_id, UserPojo.class);
        query.setParameter("id", id);
        return getSingle(query);
    }

    public UserPojo select(String email) {
        TypedQuery<UserPojo> query = getQuery(SELECT_BY_EMAIL, UserPojo.class);
        query.setParameter("email", email);
        return getSingle(query);
    }

    public List<UserPojo> selectAll() {
        TypedQuery<UserPojo> query = getQuery(SELECT_ALL, UserPojo.class);
        return query.getResultList();
    }
}