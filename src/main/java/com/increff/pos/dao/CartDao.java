package com.increff.pos.dao;

import com.increff.pos.pojo.CartPojo;
import com.increff.pos.pojo.CartPojo;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.List;

@Repository
public class CartDao {
    //This is JPQL : Java Persistence Query language
    private static String delete_id = "delete from CartPojo p where id=:id";
    private static String select_id = "select p from CartPojo p where id=:id";
    private static String select_cart_id = "select p from CartPojo p where productName=:name";

    //    private static String select_barcode = "select p from CartPojo p where barcode=:barcode";
//    private static String select_all_from_ID = "select p from CartPojo p where orderId=:id";
    private static String select_all = "select p from CartPojo p";
    private static String delete_all = "delete from CartPojo p";

    //    private int id;
    @PersistenceContext
    EntityManager em;

    public void insert(CartPojo p) {
        em.persist(p);
    }

//    public CartPojo getPojo(String barcode) {
////        removed the auto incrementing of ID manually
////        em.persist(p);
//        try{
//            TypedQuery<CartPojo> query = em.createQuery(select_barcode, CartPojo.class);
//            query.setParameter("barcode", barcode);
//            System.out.println("OrderItem exists");
//            return query.getSingleResult();
//        }
//        catch(Exception e){
//            System.out.println(e);
//            return null;
//        }
//    }

    public int delete(int id) {
        Query query = em.createQuery(delete_id);
        query.setParameter("id", id);
        return query.executeUpdate();
    }

    public CartPojo select(int id) {
        try{
            TypedQuery<CartPojo> query = em.createQuery(select_id, CartPojo.class);
            query.setParameter("id", id);
            return query.getSingleResult();
        }

         catch (NoResultException e){
            return null;
        }
    }

    public CartPojo getCartIdFromProductName(String name) {
        try{
            TypedQuery<CartPojo> query = em.createQuery(select_cart_id, CartPojo.class);
            query.setParameter("name", name);
            return query.getSingleResult();
        }
        catch (NoResultException e){
            return null;
        }

    }

    public List<CartPojo> selectAll() {
        TypedQuery<CartPojo> query = em.createQuery(select_all, CartPojo.class);
        return query.getResultList();
    }
    //
//    public int deleteAll() {
//        TypedQuery<CartPojo> query = em.createQuery(select_all, CartPojo.class);
//        return query.executeUpdate();
//    }
//
//    public List<CartPojo> selectAll(int id) {
//        TypedQuery<CartPojo> query = em.createQuery(select_all_from_ID, CartPojo.class);
//        query.setParameter("id", id);
//        return query.getResultList();
//    }
    public void update(CartPojo p) {
//        return;
    }

    TypedQuery<CartPojo> getQuery(String jpql) {
        return em.createQuery(jpql, CartPojo.class);
    }

}