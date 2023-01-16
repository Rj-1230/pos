package com.increff.pos.dao;

import com.increff.pos.pojo.ProductPojo;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.List;

@Repository
public class ProductDao {
//This is JPQL : Java Persistence Query language
    private static String delete_id = "delete from ProductPojo p where id=:id";
    private static String select_id = "select p from ProductPojo p where id=:id";
    private static String select_barcode = "select p from ProductPojo p where barcode=:barcode";
    private static String select_all = "select p from ProductPojo p";

    @PersistenceContext
    EntityManager em;

    public void insert(ProductPojo p) {
        em.persist(p);
    }

    public ProductPojo getProductPojoFromBarcode(String barcode) {
        try{
            TypedQuery<ProductPojo> query = getQuery(select_barcode);
            query.setParameter("barcode", barcode);
            return query.getSingleResult();
        }
        catch(NoResultException e){
            return null;
        }
    }

    public int delete(int id) {
        Query query = em.createQuery(delete_id);
        query.setParameter("id", id);
        return query.executeUpdate();
    }

    public ProductPojo select(int id) {
        try {
            TypedQuery<ProductPojo> query = getQuery(select_id);
            query.setParameter("id", id);
            return query.getSingleResult();
        }
        catch(NoResultException e){
            return null;
        }
    }

    public List<ProductPojo> selectAll() {
        TypedQuery<ProductPojo> query = getQuery(select_all);
        return query.getResultList();
    }


    TypedQuery<ProductPojo> getQuery(String jpql) {
        return em.createQuery(jpql, ProductPojo.class);
    }

}