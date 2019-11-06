package enums;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 描述信息
 *
 * @author chengwei11
 * @date 2019/11/6
 */
public enum TestEnum implements BaseEnum {
    T1(1, "11"),
    T2(2, "22");
    /**
     * 码值
     */
    private int code;
    /**
     * 描述
     */
    private String msg;

    TestEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public Map<Integer, String> getValues() {
        Map<Integer, String> map = new HashMap<>();
        TestEnum[] enums = TestEnum.values();
        for (TestEnum testEnum : enums) {
            map.put(testEnum.code, testEnum.msg);
        }
        return map;
    }

    public static void main(String[] args) {
        try {
            Class<?> clz = Class.forName(TestEnum.class.getName());
            Method values = clz.getDeclaredMethod(BaseEnum.class.getDeclaredMethods()[0].getName());
            Object[] oo = clz.getEnumConstants();
            Object invoke = values.invoke(oo[0]);
            System.out.println(invoke);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
