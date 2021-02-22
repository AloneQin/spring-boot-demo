package com.example.demo.common.filter;

import org.apache.tomcat.util.http.FastHttpDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.*;

/**
 * 自定义 HttpServletRequestWrapper 添加或修改请求头、参数列表
 */
public class MapRequestWrapper extends HttpServletRequestWrapper {

    /**
     * 请求头集合
     */
    private Map<String, String> headerMap = new HashMap<>();

    /**
     * 参数集合
     */
    private Map<String, String[]> paramMap = new HashMap<>();

    public MapRequestWrapper(HttpServletRequest request) {
        super(request);
        // 将 request 中的参数赋值给当前 map
        paramMap.putAll(request.getParameterMap());
    }

    public void addHeader(String name, String value) {
        headerMap.put(name, value);
    }

    @Override
    public String getHeader(String name) {
        String value = super.getHeader(name);
        if (headerMap.containsKey(name)) {
            return headerMap.get(name);
        }

        return value;
    }

    @Override
    public Enumeration<String> getHeaderNames() {
        ArrayList<String> nameList = Collections.list(super.getHeaderNames());
        for (String name : headerMap.keySet()) {
            nameList.add(name);
        }

        return Collections.enumeration(nameList);
    }

    @Override
    public Enumeration<String> getHeaders(String name) {
        List<String> valueList = Collections.list(super.getHeaders(name));
        if (headerMap.containsKey(name)) {
            valueList = Arrays.asList(headerMap.get(name));
        }

        return Collections.enumeration(valueList);
    }

    @Override
    public int getIntHeader(String name) {
        return Integer.valueOf(getHeader(name));
    }

    @Override
    public long getDateHeader(String name) {
        String value = getHeader(name);
        if (value == null) {
            return -1L;
        } else {
            long date = FastHttpDateFormat.parseDate(value);
            if (date == -1L) {
                throw new IllegalArgumentException(value);
            } else {
                return date;
            }
        }
    }

    public void addParameter(String name, String value) {
        String[] valueArr = paramMap.get(name);
        if (valueArr == null) {
            valueArr = new String[]{name};
            paramMap.put(name, valueArr);
        } else {
            // 将添加的参数作为数组的首元素，并拷贝后续元素
            String[] newValueArr = new String[valueArr.length + 1];
            newValueArr[0] = value;
            for (int i = 0; i < valueArr.length; i++) {
                newValueArr[i+1] = valueArr[i];
            }
            paramMap.put(name, newValueArr);
        }
    }

    public void addParameter(String name, String[] valueArr) {
        paramMap.put(name, valueArr);
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        return paramMap;
    }

    @Override
    public String getParameter(String name) {
        String value = super.getParameter(name);
        if (paramMap.containsKey(name)) {
            String[] valueArr = paramMap.get(name);
            if (valueArr != null && valueArr.length != 0) {
                return valueArr[0];
            }
        }

        return value;
    }

    @Override
    public Enumeration<String> getParameterNames() {
        ArrayList<String> paramList = Collections.list(super.getParameterNames());
        for (String name : paramMap.keySet()) {
            paramList.add(name);
        }

        return Collections.enumeration(paramList);
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] valueArr = super.getParameterValues(name);
        if (paramMap.containsKey(name)) {
            return paramMap.get(name);
        }

        return valueArr;
    }
}