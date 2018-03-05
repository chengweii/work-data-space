package collection;

import java.util.*;

public class ComparatorTest {
    public static void main(String[] args){
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map =new HashMap<String, Object>();
        map.put("depotId","1");
        map.put("productId","11");
        Map<String, Object> map1 =new HashMap<String, Object>();
        map1.put("depotId","2");
        map1.put("productId","22");
        Map<String, Object> map2 =new HashMap<String, Object>();
        map2.put("depotId","3");
        map2.put("productId","33");
        list.add(map1);
        list.add(map2);
        list.add(map);
        System.out.println(list);
        sortStockList(list);
        System.out.println(list);
    }

    private static void sortStockList(List<Map<String, Object>> list) {
        list.sort(new Comparator<Map<String, Object>>() {
            @Override
            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                String o1Key = String.valueOf(o1.get("depotId")) + "_" + String.valueOf(o1.get("productId"));
                String o2Key = String.valueOf(o2.get("depotId")) + "_" + String.valueOf(o2.get("productId"));
                return o1Key.compareTo(o2Key);
            }
        });
    }

}
