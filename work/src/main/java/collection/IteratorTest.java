package collection;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class IteratorTest {
    public static void main(String[] args) {
        testForeachRemove();
        testForRemove();
        testIteratorRemove();
    }

    public static void testForRemove() {
        List<String> list = new ArrayList<String>();
        list.add("1");
        list.add("2");
        list.add("3");

        for (int i = 0; i < list.size(); i++) {
            String item = list.get(i);
            list.remove(i);
            i--;
            System.out.println("testForRemove:" + item);
        }

        System.out.println("testForRemove:" + list.size());
    }

    public static void testForeachRemove() {
        List<String> list = new ArrayList<String>();
        list.add("1");
        list.add("2");
        list.add("3");

        try {
            for (String item : list) {
                // checkForComodification java.util.ConcurrentModificationException
                list.remove(item);
                System.out.println("testForeachRemove:" + item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("testForeachRemove:" + list.size());
    }

    public static void testIteratorRemove() {
        List<String> list = new ArrayList<String>();
        list.add("1");
        list.add("2");
        list.add("3");

        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            String item = iterator.next();
            iterator.remove();
            System.out.println("testIteratorRemove:" + item);
        }

        System.out.println("testIteratorRemove:" + list.size());
    }
}
