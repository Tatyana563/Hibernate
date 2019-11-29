package com.home.persistense.util;

import com.home.persistense.model.CityEntity;
import com.home.persistense.model.CountryEntity;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import javax.sql.DataSource;

public class HibernateConnectionUtil {
//Double-checked locking
    private static volatile SessionFactory sessionFactory;

    public static SessionFactory getConnection() {

        if (sessionFactory == null) {
            synchronized (HibernateConnectionUtil.class) {
                if (sessionFactory == null) {
                    Configuration configuration = new Configuration().configure();
                    configuration.addAnnotatedClass(CityEntity.class);
                    configuration.addAnnotatedClass(CountryEntity.class);
                    StandardServiceRegistryBuilder
                            builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
                    sessionFactory = configuration.buildSessionFactory(builder.build());

                }
            }
        }
        return sessionFactory;
    }
}
