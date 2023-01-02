package com.increff.employee.dao;

import com.increff.employee.pojo.OrderPojo;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class OrderDao {
//This is JPQL : Java Persistence Query language
    private static String delete_id = "delete from OrderPojo p where id=:id";
    private static String select_id = "select p from OrderPojo p where id=:id";
    private static String select_barcode = "select p from OrderPojo p where barcode=:barcode";
    private static String select_all = "select p from OrderPojo p";

//    private int id;
    @PersistenceContext
    EntityManager em;

    public void insert(OrderPojo p) {
//        removed the auto incrementing of ID manually
        em.persist(p);
    }

    public OrderPojo getPojo(String barcode) {
//        removed the auto incrementing of ID manually
//        em.persist(p);
        try{
            TypedQuery<OrderPojo> query = em.createQuery(select_barcode, OrderPojo.class);
            query.setParameter("barcode", barcode);
            System.out.println("Order exists");
            return query.getSingleResult();
        }
        catch(Exception e){
            System.out.println(e);
            return null;
        }
    }

    public int delete(int id) {
        Query query = em.createQuery(delete_id);
        query.setParameter("id", id);
        return query.executeUpdate();
    }

    public OrderPojo select(int id) {
            TypedQuery<OrderPojo> query = em.createQuery(select_id, OrderPojo.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    public List<OrderPojo> selectAll() {
        TypedQuery<OrderPojo> query = em.createQuery(select_all, OrderPojo.class);
        return query.getResultList();
    }

    public void update(OrderPojo p) {
//        return;
    }

    TypedQuery<OrderPojo> getQuery(String jpql) {
        return em.createQuery(jpql, OrderPojo.class);
    }

}