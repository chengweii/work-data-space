package util;

import lombok.Data;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 描述信息
 *
 * @author chengwei11
 * @date 2019/3/26
 */
public class BeanUtils {

    public static void main(String[] args) {
        TestData d1 = new TestData();
        d1.value1 = "1";
        //d1.value2 = 1L;
        d1.value3 = new Date();

        TestData d2 = new TestData();
        d2.value1 = "2";
        d2.value2 = 2L;
        d2.value3 = new Date();

        copyPropertiesIgnoreNull(d1, d2);

        System.out.println(d2.toString());
    }

    @Data
    static class TestData {
        private String value1;
        private Long value2;
        private Date value3;
    }

    /**
     * 获取Null值属性的名称
     *
     * @param source
     * @return
     */
    private static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) {
                emptyNames.add(pd.getName());
            }
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    /**
     * 拷贝时忽略Null值属性
     *
     * @param src
     * @param target
     */
    public static void copyPropertiesIgnoreNull(Object src, Object target) {
        org.springframework.beans.BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
    }
}
