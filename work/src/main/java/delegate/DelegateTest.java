package delegate;

import java.util.function.Function;

/**
 * @author: chengwei11
 * @since: 2018/11/1 10:36
 * @description:
 */
public class DelegateTest {
    public Integer value;

    public boolean check(Integer p2) {
        return value.equals(p2);
    }

    public static void main(String[] args) {
        CountLimitor countLimitor = new CountLimitor();

        DelegateTest delegateTest = new DelegateTest();
        delegateTest.value = 2;

        Boolean result = countLimitor.limit(new Function<Integer, Boolean>() {
            @Override
            public Boolean apply(Integer input) {
                return delegateTest.check(input);
            }
        }, 8, 2, 1, 60 * 60, "jj", "3");
        System.out.println(result);
    }
}
