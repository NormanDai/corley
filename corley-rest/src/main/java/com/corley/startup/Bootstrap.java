/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.corley.startup;


import com.corley.core.context.CarleyClassLoader;
import com.corley.core.context.SystemContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Properties;

/**
 * @author Corley  Norman.S.L.Dai    mail:daishenglei@foxmail.com
 */
public class Bootstrap {

    protected ClassLoader commonLoader = null;

    protected CarleyClassLoader carleyClassLoader = null;

    /**
     * 系统主函数
     *
     * @param args
     */
    public static void main(String[] args) {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.init(args);
    }


    /**
     * 系统初始化
     *
     * @param args
     */
    private void init(String[] args) {
        initClassLoaders();
        initSystemConf();
        loadAppJars();

        String springContainerConfPath = SystemContext.newInstances().getSystemConf("system.conf.container.path");
        InputStream resourceAsStream = commonLoader.getResourceAsStream(springContainerConfPath);

        ApplicationContext context = new ClassPathXmlApplicationContext(springContainerConfPath);
        System.out.println(context);






    }


    private void loadAppJars() {
        if (null == carleyClassLoader) {
            carleyClassLoader = new CarleyClassLoader(getURLsByFiles(),commonLoader);
        }

//        try {
//            Class<?> aClass = carleyClassLoader.loadClass("com.norman.agent.MyAgent.class");
//            System.out.println(aClass);
//        }catch (Exception ex){
//            ex.printStackTrace();
//        }
    }

    private URL[] getURLsByFiles() {
        File[] appjarsFile = loadAppjarsFile();
        URL[] urls = new URL[appjarsFile.length];
        for (int i = 0; i < appjarsFile.length; i++) {
            try {
                urls[i] = appjarsFile[i].toURI().toURL();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return urls;
    }

    /**
     * 加载apps 下的所有文件
     *
     * @return
     */
    private File[] loadAppjarsFile() {
        try {
            File appsPathFile = new File(commonLoader.getResource("apps").toURI());
            return appsPathFile.listFiles(new FilenameFilter() {
                public boolean accept(File dir, String name) {
                    return name.endsWith(".jar");
                }
            });
        } catch (URISyntaxException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * 初始化类加载器
     */
    private void initClassLoaders() {
        // commonLoader = Bootstrap.class.getClassLoader();
        commonLoader = ClassLoader.getSystemClassLoader();
    }

    /**
     * 初始化系统配置
     */
    private void initSystemConf() {
        Properties properties = new Properties();
        InputStream resourceAsStream = commonLoader.getResourceAsStream("profile/corley.properties");
        try {
            properties.load(resourceAsStream);
            Enumeration<?> enumeration = properties.propertyNames();
            while (enumeration.hasMoreElements()) {
                String item = (String) enumeration.nextElement();
                String vale = properties.getProperty(item);
                SystemContext.newInstances().putSystemConf(item, vale);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


}
