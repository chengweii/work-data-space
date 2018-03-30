package classloader;

public class MyClass {
    private String name;

    public MyClass() {
    }

    public MyClass(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        return "I am a MyClass, my name is " + name;
    }
}
