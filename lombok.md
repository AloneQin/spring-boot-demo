## lombok常用注解

### @Cleanup
自动关流，使用在局部方法的变量前，默认调用`close()`方法，支持指定方法名称。
```java
public void copyFile(String in, String out) throws Exception {
    @Cleanup FileInputStream fileInputStream = new FileInputStream(in);
    @Cleanup FileOutputStream fileOutputStream = new FileOutputStream(out);
}
```

### @SneakyThrows
自动`try-catch`并将异常抛出，消除编译报错，使代码变简洁，并支持指定异常类型。
```java
@SneakyThrows
public void copyFile(String in, String out) {
    
}
```

### @Accessors
取存器注解，共有`fluent`、`chain`和`prefix`属性。
```java
@Data
@Accessors(chain = true, fluent = true, prefix = "xxx")
public class Porsche {
    private Integer id;
    private String name;
    private Double price;
    private Integer stock;
}
```

* chain 支持链式编程
```java
new Porsche().setId(1).setName("Taycan").setPrice(880000.00).setStock(110);
```

* fluent 使`getter/setter`方法名称与属性名相同
```java
new Tesla().id(1).vehicleName("Model S").vehicleType("轿跑").vehiclePrice("880000").factory("上海超级工厂");
```

* prefix 去除前缀
```java
@Data
@Accessors(prefix = "j")
public class Jaugar {
    private Integer jId;
    private String jName;
    private Double jPrice;
}

new Jaugar().setId(1);
```

### @Builder
建造者模式的使用。

### @Singular
对集合属性生成单独追加单个元素的方法，并支持连续追加。

```java
@Builder
class A {
    @Singular
    private List<String> listFields;
}

A.builder().listField("1").listField("2").build();
```