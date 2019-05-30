package com.november.first.common.utils;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
@Lazy(false)
public class SpringContextHolder implements ApplicationContextAware, DisposableBean {

private static ApplicationContext applicationContext = null;

public static ApplicationContext getApplicationContext(){
assertContextInjected();
return applicationContext;
}

@SuppressWarnings("unchecked")
public static <T> T getBean(String name){
assertContextInjected();
return (T)applicationContext.getBean(name);
}

public static <T> T getBean(Class<T> requiredType){
assertContextInjected();
return applicationContext.getBean(requiredType);
}

public static void clearHolder(){
applicationContext = null;
}

@Override
public void setApplicationContext(ApplicationContext appContext) throws BeansException {
applicationContext = appContext;
}

@Override
public void destroy() throws Exception {
SpringContextHolder.clearHolder();
}

private static void assertContextInjected() {
if (applicationContext == null) {
            throw new IllegalStateException("applicaitonContext not insert yet,please define SpringContextHolder in the applicationContext.xml");
                    }
                    }
                    
                    }
