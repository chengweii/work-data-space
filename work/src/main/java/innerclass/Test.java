package innerclass;

public class Test {
    public static void main(String[] args){
        InnerClassTest.NestedStaticClass a = new InnerClassTest.NestedStaticClass();
        InnerClassTest.InnerClass innerClass = (new InnerClassTest()).new InnerClass();
        System.out.println(innerClass.getName());
    }
}
