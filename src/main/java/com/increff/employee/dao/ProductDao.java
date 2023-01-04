package com.increff.employee.dao;

import com.increff.employee.pojo.ProductPojo;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.sql.SQLOutput;
import java.util.List;

@Repository
public class ProductDao {
//This is JPQL : Java Persistence Query language
    private static String delete_id = "delete from ProductPojo p where id=:id";
    private static String select_id = "select p from ProductPojo p where id=:id";
    private static String select_barcode = "select p from ProductPojo p where barcode=:barcode";
    private static String select_all = "select p from ProductPojo p";

//    private int id;
    @PersistenceContext
    EntityManager em;

    public void insert(ProductPojo p) {
//        removed the auto incrementing of ID manually
        em.persist(p);
    }

    public ProductPojo getPojo(String barcode) {
//        removed the auto incrementing of ID manually
//        em.persist(p);
        try{
            TypedQuery<ProductPojo> query = em.createQuery(select_barcode, ProductPojo.class);
            query.setParameter("barcode", barcode);
            System.out.println("Product exists");
            return query.getSingleResult();
        }
        catch(Exception e){
            System.out.println(e);
            return null;
        }
    }

    public ProductPojo getPojo(int id) {
//        removed the auto incrementing of ID manually
//        em.persist(p);
        try{
            TypedQuery<ProductPojo> query = em.createQuery(select_id, ProductPojo.class);
            query.setParameter("id", id);
            System.out.println("Product exists");
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

    public ProductPojo select(int id) {
            TypedQuery<ProductPojo> query = em.createQuery(select_id, ProductPojo.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    public List<ProductPojo> selectAll() {
        TypedQuery<ProductPojo> query = em.createQuery(select_all, ProductPojo.class);
        return query.getResultList();
    }

    public void update(ProductPojo p) {
//        return;
    }

    TypedQuery<ProductPojo> getQuery(String jpql) {
        return em.createQuery(jpql, ProductPojo.class);
    }

}