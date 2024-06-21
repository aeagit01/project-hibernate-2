package com.javarush.dao;

import com.javarush.domain.*;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

public class CityDAO extends GenericDAO<City> {
    public CityDAO(SessionFactory sessionFactory) {
        super(City.class, sessionFactory);
    }

    public City getByName(String city) {
        Query<City> query = getCurrentSession().createQuery("select c from City c where c.city = :city", City.class);
        query.setParameter("city", city);
        query.setMaxResults(1);
        return (City) query.getSingleResult();

    }
}
