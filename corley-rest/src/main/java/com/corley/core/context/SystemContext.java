package com.corley.core.context;


import java.util.concurrent.ConcurrentHashMap;

public class SystemContext {

    private static volatile SystemContext context;

    private static volatile ConcurrentHashMap<String,String> SYSTEM_CONF = new ConcurrentHashMap<String, String>();


    private SystemContext() {
    }

    public static synchronized SystemContext newInstances() {
        if (null == context) {
            context = new SystemContext();
        }
        return context;
    }

    public String getSystemConf(String item){
        return SYSTEM_CONF.get(item);
    }


    public void putSystemConf(String item, String value){
        SYSTEM_CONF.put(item,value);
    }

    public ConcurrentHashMap<String,String> getSystemAllConf(){
        return SYSTEM_CONF;
    }
}
