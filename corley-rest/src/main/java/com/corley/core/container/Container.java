package com.corley.core.container;

import java.util.List;

/**
 * Name:
 * Description:<p></p>
 * Create by daishenglei@bestpay.com.cn on 2018-05-03 19:48
 */
public interface Container {


    Object getBeanByName(String name);

    List<Object> getBeansByType(Class<?> clazz);

}
