package com.sinitek.demo.utils;

import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationContext;
import org.springframework.beans.BeansException;
import org.springframework.orm.hibernate3.HibernateTemplate;

/**
 * User: Friszart
 * Date: 2007-12-18
 */
public class AppContext implements ApplicationContextAware {
    private static ApplicationContext springContext = null;

    public void setApplicationContext(ApplicationContext context) throws BeansException {
        springContext = context;
    }

    public static Object getBean(String name) {
        return springContext.getBean(name);
    }

}