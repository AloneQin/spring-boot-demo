package com.example.demo.utils;

import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.function.SFunction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

class SmartBeanUtilsTest {

    public static void main(String[] args) {
        // 拷贝对象
//        Obj1 obj1 = new Obj1("obj1", 1, new ChildObj(1));
//        Obj2 obj2 = SmartBeanUtils.copyProperties(obj1, Obj2::new);
//        obj1.setName("xxx");
//        obj1.setNo(2);
//        obj1.getChildObj().setNo(2);
//        System.out.println(obj2);
//
//        // 拷贝集合
//        List<Obj1> obj1List = Arrays.asList(new Obj1("obj1", 1, new ChildObj(1)));
//        List<Obj2> obj2List = SmartBeanUtils.copyPropertiesList(obj1List, Obj2::new);
//        obj1List.get(0).setName("xxx");
//        obj1List.get(0).setNo(2);
//        obj1List.get(0).getChildObj().setNo(2);
//        System.out.println(obj2List);
//
//        // 拷贝分页对象
//        Page<Obj1> obj1Page = new Page<>(1, 10);
//        obj1Page.setRecords(Arrays.asList(new Obj1("obj1", 1, new ChildObj(1))));
//        Page<Obj2> obj2Page = SmartBeanUtils.copyPropertiesPage(obj1Page, Obj2::new);
//        obj1Page.getRecords().get(0).setName("xxx");
//        obj1Page.getRecords().get(0).setNo(2);
//        obj1Page.getRecords().get(0).getChildObj().setNo(2);
//        System.out.println(FastjsonUtils.toString(obj2Page));
//
//        // 拷贝泛型对象
//        Map<String, Obj1> obj1Map = Map.of("a", new Obj1("obj1", 1, new ChildObj(1)));
//        Map<String, Obj2> obj2Map = SmartBeanUtils.copyPropertiesGeneric(obj1Map, new TypeReference<>() {});
//        obj1Map.get("a").setNo(2);
//        obj1Map.get("a").getChildObj().setNo(2);
//        System.out.println(obj2Map);
//
//        // 打印字段名称
//        System.out.println(SmartBeanUtils.getFieldName(TestPojo::getIsShow));

        String fieldName = SFunction.getFieldName(TestPojo::getId);
        System.out.println(fieldName);
    }

}

@Data
@NoArgsConstructor
@AllArgsConstructor
class Obj1 {

    private String name;

    private Integer no;

    private ChildObj childObj;

}

@Data
@NoArgsConstructor
@AllArgsConstructor
class Obj2 {

    private String name;

    private Integer no;

    private ChildObj childObj;

}

@Data
@NoArgsConstructor
@AllArgsConstructor
class ChildObj {

    private Integer no;

}

class TestPojo {

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

    public String xxxIsIsShow() {
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

    public Boolean isFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }
}