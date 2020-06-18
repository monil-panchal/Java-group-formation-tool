//package com.assessme.config;
//
//import org.springframework.beans.BeansException;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.ApplicationContextAware;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.stereotype.Service;
//
///**
// * @author: monil
// * Created on: 2020-06-17
// */
//@Configuration
//public class BeanConfig implements ApplicationContextAware {
//    private static ApplicationContext context;
//
//    @Override
//    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
//        context = applicationContext;
//    }
//
//    public static <T> T getBean(Class<T> beanClass) {
//        return context.getBean(beanClass);
//    }
//}
