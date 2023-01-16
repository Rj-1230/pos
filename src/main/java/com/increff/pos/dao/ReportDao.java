package com.increff.pos.dao;

import com.increff.pos.pojo.DailyReportPojo;
import com.increff.pos.pojo.UserPojo;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.List;

@Repository
public class ReportDao extends AbstractDao{
    private static String SELECT_BY_ID = "select p from DailyReportPojo p where date=:date";

    private static String SELECT_ALL = "select p from DailyReportPojo p";

    public void insert(DailyReportPojo p) {
        em().persist(p);
    }
    public DailyReportPojo select(LocalDate date) {
        TypedQuery<DailyReportPojo> query = getQuery(SELECT_BY_ID, DailyReportPojo.class);
        query.setParameter("date", date);
        return query.getResultStream().findFirst().orElse(null);
    }
    public List<DailyReportPojo> selectAll() {
        TypedQuery<DailyReportPojo> query = getQuery(SELECT_ALL, DailyReportPojo.class);
        return query.getResultList();
    }
}