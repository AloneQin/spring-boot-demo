package com.example.demo.version;

/**
 * JDK 11 to 17 测试
 */
public class Jdk11To17Test {

    public static void main(String[] args) {
        // switch 增强
        int i = 1;
        String name = switch (i) {
            case 1 -> "one";
            case 2 -> "two";
            default -> "other";
        };
        System.out.println(name);

        // 文本块
        String text = """
              <html>
                  <body>
                      <p>Hello, World</p>
                  </body>
              </html>
              """;

        System.out.println(text);

        // instanceof 模式匹配
        Object obj = "Hello JDK 16";
        if (obj instanceof String str && str.startsWith("Hello")) {
            System.out.println(str);
        }

        // 密封类
        Shape shape = new Circle();
        System.out.println(shape.name());

        // record 类
        Person person = new Person("Alice", 18);
        System.out.println(person.name());
        System.out.println(person.hashCode());

        // 恢复始终严格的浮点语义
        double a = 0.1;
        double b = 0.2;
        // 0.30000000000000004
        System.out.println("浮点运算结果: " + (a + b));
        System.out.println("是否等于0.3: " + (a + b == 0.3));

        // 空指针异常详细信息
        try {
            String s = null;
            System.out.println(s.length());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    static sealed abstract class Shape
        permits Circle, Rectangle, Triangle {
        abstract String name();
    }

    /**
     * 子类必须是 final、sealed 或 non-sealed
     * <br> final - 不能再被继承
     * <br> sealed - 继续密封
     * <br> non-sealed - 开放继承
     */
    static final class Circle extends Shape {
        @Override
        public String name() {
            return "Circle";
        }
    }

    static final class Rectangle extends Shape {
        @Override
        String name() {
            return "Rectangle";
        }
    }

    static final class Triangle extends Shape {
        @Override
        String name() {
            return "Triangle";
        }
    }

    record Person(String name, int age) {}
}
