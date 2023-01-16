package com.increff.pos.dao;

import com.increff.pos.pojo.CartPojo;
import com.increff.pos.pojo.OrderItemPojo;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.List;

@Repository
public class OrderItemDao {
//This is JPQL : Java Persistence Query language
    private static String delete_id = "delete from OrderItemPojo p where id=:id";
    private static String select_id = "select p from OrderItemPojo p where id=:id";
    private static String select_all_from_ID = "select p from OrderItemPojo p where orderId=:id";
    private static String select_all = "select p from OrderItemPojo p";

    private static String select_OrderItem_by_ProductId = "select p from OrderItemPojo p where productId=:productId and orderId=:orderId";

//    private int id;
    @PersistenceContext
    EntityManager em;

    public void insert(OrderItemPojo p) {
        em.persist(p);
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

    public OrderItemPojo getOrderItemPojoFromProductId(int productId,int orderId) {
        try{
            TypedQuery<OrderItemPojo> query = em.createQuery(select_OrderItem_by_ProductId, OrderItemPojo.class);
            query.setParameter("productId", productId);
            query.setParameter("orderId", orderId);
            return query.getSingleResult();
        }
        catch (NoResultException e){
            return null;
        }

    }
    public void update(OrderItemPojo p) {
//        return;
    }

    TypedQuery<OrderItemPojo> getQuery(String jpql) {
        return em.createQuery(jpql, OrderItemPojo.class);
    }

}