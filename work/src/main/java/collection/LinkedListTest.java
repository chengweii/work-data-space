package collection;

import java.util.LinkedList;
import java.util.List;

public class LinkedListTest {
    public static void main(String[] args) {
        List<String> list = new LinkedList<String>();
        list.get(1);
        for (String item : list) {
            System.out.println(item);
        }
    }
}
