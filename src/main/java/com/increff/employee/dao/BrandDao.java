package com.increff.employee.dao;

import java.util.List;

import javax.persistence.*;

import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.pojo.ProductPojo;
import org.springframework.stereotype.Repository;

@Repository
public class BrandDao {
//fsz
    private static String delete_id = "delete from BrandPojo p where id=:id";
    private static String select_id = "select p from BrandPojo p where id=:id";
    private static String select_all = "select p from BrandPojo p";
    private static String select_pojo = "select p from BrandPojo p where brand=:brand and category=:category";


//    private int id;
    @PersistenceContext
    EntityManager em;

    public void insert(BrandPojo p) {
//        removed the auto incrementing of ID manually
        em.persist(p);
    }

    public int delete(int id) {
        Query query = em.createQuery(delete_id);
        query.setParameter("id", id);
        return query.executeUpdate();
    }

    public BrandPojo select(int id) {
            TypedQuery<BrandPojo> query = em.createQuery(select_id, BrandPojo.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    public List<BrandPojo> selectAll() {
        TypedQuery<BrandPojo> query = em.createQuery(select_all, BrandPojo.class);
        return query.getResultList();
    }

    public void update(BrandPojo p) {
//        return;
    }

    public BrandPojo getBrandIdPojo(String brandName, String categoryName) {
//        removed the auto incrementing of ID manually
//        em.persist(p);
        try{
//            System.out.println(brandName + categoryName);
            TypedQuery<BrandPojo> query = em.createQuery(select_pojo, BrandPojo.class);
            query.setParameter("brand", brandName);
            query.setParameter("category", categoryName);
//            System.out.println("Product exists");
            return query.getSingleResult();
        }
        catch(Exception e){
            System.out.println(e);
            return null;
        }
    }
    TypedQuery<BrandPojo> getQuery(String jpql) {
        return em.createQuery(jpql, BrandPojo.class);
    }

}