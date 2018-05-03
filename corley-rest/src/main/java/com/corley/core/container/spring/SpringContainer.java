package com.corley.core.container.spring;

import com.corley.core.container.Container;
import org.springframework.context.ApplicationContext;

import java.util.List;

/**
 * ClassName:
 * Description:<p></p>
 * Create by daishenglei@bestpay.com.cn on 2018-05-03 19:50
 */
public class SpringContainer implements Container{



    private ApplicationContext springContext;

    private static volatile SpringContainer container;

    private SpringContainer(){}


    public static synchronized SpringContainer newInstances(){
        if(null == container){
            container = new SpringContainer();
        }
        return container;
    }

    public Object getBeanByName(String name) {
        return null;
    }

    public List<Object> getBeansByType(Class<?> clazz) {
        return null;
    }
}
