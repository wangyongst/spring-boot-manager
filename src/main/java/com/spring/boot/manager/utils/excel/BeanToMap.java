package com.spring.boot.manager.utils.excel;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BeanToMap<T> {


    public String getMethodName(String fieldName){
        byte[] buffer = fieldName.getBytes();
        buffer[0] = (byte)(buffer[0]-32);
        String name = new String(buffer);
        return "get"+name;
    }

    public Map<String,Object> getMap(T entity){
        Field[] fields = entity.getClass().getDeclaredFields();
        Map<String,Object> map = new HashMap<>();
        for (int j = 0; j < fields.length; j++) {
            try {
                Method method = entity.getClass().getMethod(getMethodName(fields[j].getName()));
                map.put(fields[j].getName(),method.invoke(entity));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return map;
    }


}