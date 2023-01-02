package com.increff.employee.dao;

import com.increff.employee.pojo.OrderItemPojo;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class OrderItemDao {
//This is JPQL : Java Persistence Query language
    private static String delete_id = "delete from OrderItemPojo p where id=:id";
    private static String select_id = "select p from OrderItemPojo p where id=:id";
    private static String select_barcode = "select p from OrderItemPojo p where barcode=:barcode";
    private static String select_all_from_ID = "select p from OrderItemPojo p where orderId=:id";
    private static String select_all = "select p from OrderItemPojo p";

//    private int id;
    @PersistenceContext
    EntityManager em;

    public void insert(OrderItemPojo p) {
//        removed the auto incrementing of ID manually
        System.out.println("c");

        em.persist(p);
    }

    public OrderItemPojo getPojo(String barcode) {
//        removed the auto incrementing of ID manually
//        em.persist(p);
        try{
            TypedQuery<OrderItemPojo> query = em.createQuery(select_barcode, OrderItemPojo.class);
            query.setParameter("barcode", barcode);
            System.out.println("OrderItem exists");
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

    public OrderItemPojo select(int id) {
            TypedQuery<OrderItemPojo> query = em.createQuery(select_id, OrderItemPojo.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    public List<OrderItemPojo> selectAll() {
        TypedQuery<OrderItemPojo> query = em.createQuery(select_all, OrderItemPojo.class);
        return query.getResultList();
    }

    public List<OrderItemPojo> selectAll(int id) {
        TypedQuery<OrderItemPojo> query = em.createQuery(select_all_from_ID, OrderItemPojo.class);
        query.setParameter("id", id);
        return query.getResultList();
    }
    public void update(OrderItemPojo p) {
//        return;
    }

    TypedQuery<OrderItemPojo> getQuery(String jpql) {
        return em.createQuery(jpql, OrderItemPojo.class);
    }

}