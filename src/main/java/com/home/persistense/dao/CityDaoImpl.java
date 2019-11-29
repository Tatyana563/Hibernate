package com.home.persistense.dao;

import com.home.persistense.model.CityEntity;
import com.home.persistense.model.enumaration.Type;
import com.home.persistense.util.HibernateConnectionUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.*;
import org.apache.log4j.Logger;


public class CityDaoImpl implements CityDao {
    private static final Logger log = Logger.getLogger(CityDaoImpl.class);

    private static CityDao cityDao;
//singeltone
    private CityDaoImpl(){};

    public static CityDao getInstance() {
        if (cityDao == null) {
            cityDao = new CityDaoImpl();
        }
        return cityDao;
    }


    @Override
    public void updateCityPopulation(int townId, int townPopulation) {
        Transaction tx = null;
        try (Session session = HibernateConnectionUtil.getConnection().openSession()) {
             tx = session.beginTransaction();
            session.createNamedStoredProcedureQuery("changePopulation")
                    .setParameter("p_id", townId)
                    .setParameter("p_population", townPopulation).execute();

            tx.commit();
        } catch (Exception e) {
            log.error("Error was got while city population was updated"+e.getMessage());
            e.printStackTrace();
            if (tx != null) {
                tx.rollback();
            }
        }
    }



    @Override
    public void save(CityEntity entity) {
        Transaction tx = null;
        try(Session session = HibernateConnectionUtil.getConnection().openSession()){
        tx = session.beginTransaction();
            if(Objects.isNull(entity)) throw new IllegalArgumentException("Please set a city");
            if(Objects.isNull(entity.getId())){
                //entityManager.persist(entity);
                session.save(entity);
            }

            else{
                session.merge(entity);
            }
            tx.commit();
        }
        catch (Exception e) {
            e.printStackTrace();
            log.error("Error was got while city was saved"+e.getMessage());
            if (tx != null) {
                tx.rollback();
            }
        }

    }

    @Override
    public Collection<CityEntity> findAll() {

        try(Session session = HibernateConnectionUtil.getConnection().openSession()) {


            CriteriaBuilder cb = session.getCriteriaBuilder();

            CriteriaQuery<CityEntity> query = cb.createQuery(CityEntity.class);
            Root<CityEntity> root = query.from(CityEntity.class);
            query = query.select(root);

            return session.createQuery(query).getResultList();
        }
        catch (Exception e) {
            log.error("Error was got while all cities were found"+e.getMessage());
            throw new CityDBException("DB connectivity/mapping issue");
            //return Collections.emptyList();
        }
    }

    @Override
    public Optional<CityEntity> findById(Integer id) {
        try (Session session = HibernateConnectionUtil.getConnection().openSession()) {
            final CityEntity cityEntity =
                    session.find(CityEntity.class, id);
            if (Objects.isNull(cityEntity)) throw new NullPointerException("City was not found");
            return Optional.of(cityEntity);
        }

    }

    @Override
    public void delete(CityEntity entity) {
        Transaction tx = null;
        try (Session session = HibernateConnectionUtil.getConnection().openSession()) {
            tx = session.beginTransaction();
            if (Objects.isNull(entity)) throw new NullPointerException("city must be set");
            session.remove(entity);
            tx.commit();
        }
        catch (Exception e) {
            e.printStackTrace();
            log.error("Error was got while city type was deleted"+e.getMessage());
            if (tx != null) {
                tx.rollback();
            }
        }
    }

    @Override
    public void updateCityType(int townId, Type type) {
        Transaction tx = null;
        try (Session session = HibernateConnectionUtil.getConnection().openSession()) {
            tx = session.beginTransaction();
            session.createStoredProcedureQuery("update_city_type")
                    .registerStoredProcedureParameter("p_id", Integer.class, ParameterMode.IN)
                    .registerStoredProcedureParameter("p_type", String.class, ParameterMode.IN)
                    .setParameter("p_id",townId)
                    .setParameter("p_type", type.name()).execute();
            tx.commit();


        }
        catch (Exception e) {
            e.printStackTrace();
            log.error("Error was got while city type was updated"+e.getMessage());
            if (tx != null) {
                tx.rollback();
            }
        }
        }

    }






