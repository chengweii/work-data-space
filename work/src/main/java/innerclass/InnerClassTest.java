package innerclass;

public class InnerClassTest {
    private String outerValue = "1";
    private static String outerStaticValue = "23";

    public static class NestedStaticClass {
        public String name;

        public String getName() {
            //return outerValue;
            return outerStaticValue;
        }
    }

    public class InnerClass {
        public String name;

        //public static String value;
        public String getName() {
            //return outerValue;
            return outerStaticValue + outerValue;
        }
    }
}
