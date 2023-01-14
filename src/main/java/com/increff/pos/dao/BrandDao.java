package com.increff.pos.dao;

import java.util.List;
//import static com.increff.pos.util.BrandDaoHelper.*;
import javax.persistence.*;

import com.increff.pos.pojo.BrandPojo;
import org.springframework.stereotype.Repository;

@Repository
public class BrandDao {
//fsz
    private static String delete_id = "delete from BrandPojo p where id=:id";
    private static String select_id = "select p from BrandPojo p where id=:id";
    private static String select_all = "select p from BrandPojo p";
    private static String select_pojo = "select p from BrandPojo p where brand=:brand and category=:category";
    @PersistenceContext
    EntityManager em;

    public void insert(BrandPojo p) {
        em.persist(p);
    }

    public int delete(int id) {
        Query query = em.createQuery(delete_id);
        query.setParameter("id", id);
        return query.executeUpdate();
    }

    public BrandPojo select(int id) {
        try{
            TypedQuery<BrandPojo> query = getQuery(select_id);
            query.setParameter("id", id);
            return query.getSingleResult();
        }
        catch(NoResultException e){
            return null;
        }

    }
    public List<BrandPojo> selectAll() {
        TypedQuery<BrandPojo> query = getQuery(select_all);
        return query.getResultList();
    }

    public BrandPojo getBrandPojoFromBrandCategory(String brandName, String categoryName) {
        try{
            System.out.println(brandName + categoryName);
            TypedQuery<BrandPojo> query = getQuery(select_pojo);
            query.setParameter("brand", brandName);
            query.setParameter("category", categoryName);
            return query.getSingleResult();
        }
        catch(NoResultException e){
            return null;
        }
    }
    public TypedQuery<BrandPojo> getQuery(String jpql) {
        return em.createQuery(jpql, BrandPojo.class);
    }



}