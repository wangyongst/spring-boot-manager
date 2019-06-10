package com.spring.boot.manager.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class RequestWrapper extends HttpServletRequestWrapper {
    private Map params;

    public RequestWrapper(HttpServletRequest request) {
        super(request);
        this.params = new HashMap(request.getParameterMap());
        ;
    }

    public Map getParameterMap() {
        return params;
    }

    public Enumeration getParameterNames() {
        Vector vector = new Vector(params.keySet());
        return vector.elements();
    }

    public String[] getParameterValues(String name) {
        Object object = params.get(name);
        if (object == null) {
            return null;
        } else if (object instanceof String[]) {
            return (String[]) object;
        } else if (object instanceof String) {
            return new String[]{(String) object};
        } else {
            return new String[]{object.toString()};
        }
    }

    public String getParameter(String name) {
        Object object = params.get(name);
        if (object == null) {
            return null;
        } else if (object instanceof String[]) {
            String[] strArr = (String[]) object;
            if (strArr.length > 0) {
                return strArr[0];
            } else {
                return null;
            }
        } else if (object instanceof String) {
            return (String) object;
        } else {
            return object.toString();
        }
    }

}
