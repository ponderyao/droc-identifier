package io.github.ponderyao.droc.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * Spring Bean 工具类
 *
 * @author PonderYao
 * @since 1.1.0
 */
@SuppressWarnings("unchecked")
@Component(value = "DrocSpringBeanUtils")
public class SpringBeanUtils implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringBeanUtils.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return SpringBeanUtils.applicationContext;
    }

    public static <T> T getBean(Class<T> clazz) {
        T bean = null;
        try {
            bean = applicationContext.getBean(clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (bean == null) {
            String beanName = clazz.getSimpleName();
            beanName = Character.toLowerCase(beanName.charAt(0)) + beanName.substring(1);
            bean = (T) applicationContext.getBean(beanName);
        }
        return bean;
    }
    
    public static Map<String, Object> getBeansByAnnotation(Class<? extends Annotation> annotation) {
        return SpringBeanUtils.applicationContext.getBeansWithAnnotation(annotation);
    }

}
