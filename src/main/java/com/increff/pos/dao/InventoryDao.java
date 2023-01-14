package com.increff.pos.dao;

import com.increff.pos.pojo.InventoryPojo;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class InventoryDao {
    //fsz
    private static String delete_id = "delete from InventoryPojo p where id=:id";
    private static String select_id = "select p from InventoryPojo p where id=:id";
    private static String select_all = "select p from InventoryPojo p";

    //    private int id;
    @PersistenceContext
    EntityManager em;

    public void insert(InventoryPojo p) {
//        removed the auto incrementing of ID manually
        em.persist(p);
    }

    public int delete(int id) {
        Query query = em.createQuery(delete_id);
        query.setParameter("id", id);
        return query.executeUpdate();
    }

    public InventoryPojo select(int id) {
        TypedQuery<InventoryPojo> query = em.createQuery(select_id, InventoryPojo.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    public List<InventoryPojo> selectAll() {
        TypedQuery<InventoryPojo> query = em.createQuery(select_all, InventoryPojo.class);
        return query.getResultList();
    }

    public void update(InventoryPojo p) {
//        return;
    }

    TypedQuery<InventoryPojo> getQuery(String jpql) {
        return em.createQuery(jpql, InventoryPojo.class);
    }

}