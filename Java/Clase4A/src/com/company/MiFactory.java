package com.company;

import java.io.FileInputStream;
import java.util.Properties;
import java.io.File;

public class MiFactory {
    public static Object getInstance(String objName){
        Object obj = null;

        try {
            Properties properties = new Properties();
            properties.load(new FileInputStream(new File(objName)));

            String classNameSorter = properties.get("sorter").toString();
            System.out.println(classNameSorter);

            Class c = Class.forName(classNameSorter);
            obj = c.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return obj;
    }
}
