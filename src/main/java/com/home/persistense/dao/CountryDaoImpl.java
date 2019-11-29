package com.home.persistense.dao;

import com.home.persistense.model.CountryEntity;
import com.home.persistense.model.enumaration.Currency;
import com.home.persistense.model.enumaration.FilterKey;
import com.home.persistense.util.HibernateConnectionUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.sql.CallableStatement;
import java.util.*;
import java.util.stream.Collectors;

public class CountryDaoImpl implements CountryDao {
    private static CountryDao countrydao;

    //singletone
    public static CountryDao getInstance() {
        if (countrydao == null) {
            countrydao = new CountryDaoImpl();
        }
        return countrydao;
    }

    @Override
    public void updateCountryDescription(int townId, String Countrydescription) {
        Transaction tx = null;
        try (Session session = HibernateConnectionUtil.getConnection().openSession()) {
            tx = session.beginTransaction();
            session.createNamedStoredProcedureQuery("changeDescription")
                    .setParameter("p_id", townId)
                    .setParameter("p_description", Countrydescription).execute();
            tx.commit();

        } catch (Exception e) {
            e.printStackTrace();
            if (tx != null) {
                tx.rollback();
            }
        }
    }

    @Override
    public void updateCountryPopulation(int townId, Integer population) {
        Transaction tx = null;
        try (Session session = HibernateConnectionUtil.getConnection().openSession()) {
            tx = session.beginTransaction();
            session.doWork(connection -> {
                try (CallableStatement function =
                             connection.prepareCall("{call update_country_population(?,?)}")) {
                    function.setInt(1, townId);
                    function.setInt(2, population);
                    function.execute();
                }
            });
        }
    }

    @Override
    public Collection<CountryEntity> findByCurrency(Currency currency) {
        Transaction tx = null;
        try (Session session = HibernateConnectionUtil.getConnection().openSession()) {
            tx = session.beginTransaction();
            Collection<CountryEntity> countryEntities = (Collection<CountryEntity>) session.createQuery("select c from CountryEntity c where c.currency=:p_currency")
                    .setParameter("p_currency", currency)
                    .getResultStream().peek(item -> ((CountryEntity) item).getCities().size()).collect(Collectors.toList());

            tx.commit();
            return countryEntities;
        } catch (Exception e) {
            if (tx != null) {
                try {
                    tx.rollback();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
        return Collections.emptyList();
    }

    @Override
    public Collection<CountryEntity> findAllByFilter(Map<FilterKey, Object> filter) {
        Transaction tx = null;
        try (Session session = HibernateConnectionUtil.getConnection().openSession()) {
            tx = session.beginTransaction();
            final StringBuilder sql = new StringBuilder("select c from CountryEntity c ");
            final StringBuilder where = new StringBuilder(" where 1=1 ");
            if (filter.containsKey(com.home.persistense.model.enumaration.FilterKey.CITY_TYPE)) {
                sql.append(" inner join c.cities cc ");
                where.append(" ").append("and cc.square>=50");
            }
            Query query = session.createQuery(sql.append(where).toString());
            tx.commit();
            return query.getResultList();

        } catch (Exception e) {
            if (tx != null) {
                try {
                    tx.rollback();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
        return Collections.emptyList();
    }

    @Override
    public void save(CountryEntity entity) {
        Transaction tx = null;
        try (Session session = HibernateConnectionUtil.getConnection().openSession()) {
            tx = session.beginTransaction();
            if (Objects.isNull(entity)) throw new IllegalArgumentException("Please set a country");
            if (Objects.isNull(entity.getId())) {
                session.save(entity);
            } else {
                session.merge(entity);
            }
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (tx != null) {
                tx.rollback();
            }
        }


    }

    @Override
    public Collection<CountryEntity> findAll() {
        try (Session session = HibernateConnectionUtil.getConnection().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<CountryEntity> query = cb.createQuery(CountryEntity.class);
            Root<CountryEntity> root = query.from(CountryEntity.class);
            query = query.select(root);
            return session.createQuery(query).getResultList();
        }
    }

    @Override
    public Optional<CountryEntity> findById(Integer id) {

        try (Session session = HibernateConnectionUtil.getConnection().openSession()) {
            final CountryEntity countryEntity =
                    session.find(CountryEntity.class, id);
            if (Objects.isNull(countryEntity)) throw new NullPointerException("Country was not found");

            return Optional.of(countryEntity);
        }

    }

    @Override
    public void delete(CountryEntity entity) {
        Transaction tx = null;
        try (Session session = HibernateConnectionUtil.getConnection().openSession()) {
            tx = session.beginTransaction();
            if (Objects.isNull(entity)) throw new NullPointerException("country must be set");
            session.remove(entity);

        } catch (Exception e) {
            e.printStackTrace();
            if (tx != null) {
                tx.rollback();
            }
        }
    }
}
