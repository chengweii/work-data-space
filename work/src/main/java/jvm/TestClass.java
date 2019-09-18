package jvm;

public class TestClass {
    public static void main(String[] args) {
        nihao1(TestClass.class);
    }

    public static void nihao1(Class<?> clz) {
        nihao(clz);
    }

    public static void nihao(Class<?> clz) {
        System.out.println(clz.getName() + "." + Thread.currentThread().getStackTrace()[2].getMethodName());
    }
}
