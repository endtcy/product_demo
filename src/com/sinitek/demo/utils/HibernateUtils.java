package com.sinitek.demo.utils;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.hibernate.SessionFactory;

/**
 * User: Friszart
 * Date: 2007-12-20
 */
public class HibernateUtils {
    public static HibernateTemplate getHibernateTemplate(){
        SessionFactory sessionFactory = (SessionFactory) AppContext.getBean("sessionFactory");
        return new HibernateTemplate(sessionFactory);
    }
}
