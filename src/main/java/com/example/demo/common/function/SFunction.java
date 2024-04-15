package com.example.demo.common.function;

import com.example.demo.model.pojo.Role;
import com.example.demo.model.pojo.User;
import lombok.Data;

import java.beans.Introspector;
import java.io.Serializable;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

@FunctionalInterface
public interface SFunction<T, R> extends Serializable, Function<T, R> {

    static <T, R> String getFieldName(Function<T, R> function) {
        try {
            Method method = function.getClass().getDeclaredMethod("writeReplace");
            method.setAccessible(true);
            SerializedLambda serializedLambda = (SerializedLambda) method.invoke(function);
            String implMethodName = serializedLambda.getImplMethodName();
            String fieldName = "";
            if (implMethodName.startsWith("is")) {
                fieldName = implMethodName.replace("is", "");
            } else if (implMethodName.startsWith("get")) {
                fieldName = implMethodName.replace("get", "");
            } else {
                throw new RuntimeException("get方法名称[" + implMethodName + "]不符合Java Bean规范");
            }
            fieldName = Introspector.decapitalize(fieldName);
            return fieldName;
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw e;
        }
    }

    static void main(String[] args) {
        String fieldName = getFieldName(TestField::getId);
        System.out.println(fieldName);


    }


}

class TestField {

    private Integer id;

    private String isShow;

    private String isIsShow;

    private Integer getId;

    private Boolean flag;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIsShow() {
        return isShow;
    }

    public void setIsShow(String isShow) {
        this.isShow = isShow;
    }

    public String getIsIsShow() {
        return isIsShow;
    }

    public void setIsIsShow(String isIsShow) {
        this.isIsShow = isIsShow;
    }

    public Integer getGetId() {
        return getId;
    }

    public void setGetId(Integer getId) {
        this.getId = getId;
    }

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }
}
