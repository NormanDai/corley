package com.corley.core.context;


import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

public class CarleyClassLoader extends URLClassLoader {

    public CarleyClassLoader(URL[] urls) {
        super(urls);
    }

    public CarleyClassLoader(URL[] urls, ClassLoader parent) {
        super(urls, parent);
    }

    public void addJar(URL url) {
        this.addURL(url);
    }

    public void addJar(File file) {
        try {
            addJar(file.toURI().toURL());
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void addJars(File[] files) {
        try {
            URL[] urls = new URL[files.length];
            for (int i = 0; i < files.length; i++) {
                try {
                    addURL(files[i].toURI().toURL());
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

}
