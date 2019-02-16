package collection;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述信息
 *
 * @author chengwei11
 * @date 2019/2/16
 */
public class ListRemoveNull {
    public static void main(String[] args) {
        List<String> list2 = new ArrayList<>();
        list2.add(null);
        list2.add(null);
        list2.add(null);
        list2.add(null);
        list2.add(null);
        list2.add(null);
        list2.add(null);
        list2.add("3232");

        list2.removeAll(Lists.newArrayList(null,null));

        System.out.println(list2.size());
        System.out.println(list2);
    }
}
