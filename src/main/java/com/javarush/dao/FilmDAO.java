package com.javarush.dao;

import com.javarush.domain.*;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

public class FilmDAO extends GenericDAO<Film> {
    public FilmDAO(SessionFactory sessionFactory) {
        super(Film.class, sessionFactory);
    }

    public Film getAvalableFilmForRent() {
        Query<Film> query = getCurrentSession().createQuery("select fl from Film fl where fl.id not in (select distinct film.id from Inventory)", Film.class);
        query.setMaxResults(1);
        return query.getSingleResult();
    }
}
