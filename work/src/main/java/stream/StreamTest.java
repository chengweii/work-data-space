package stream;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 描述信息
 *
 * @author chengwei11
 * @since 2019/1/21
 */
public class StreamTest {
    public static void main(String[] args) throws Exception {
        StreamTest streamTest = new StreamTest();
        streamTest.serialize();
        streamTest.parallel();
    }

    public void serialize() {
        long start = System.currentTimeMillis();
        for (int i = 1; i < 20; i++) {
            task();
        }
        long end = System.currentTimeMillis();
        System.out.println("串行执行时间："+(end - start));
    }

    public void parallel() {
        long start = System.currentTimeMillis();

        List<String> s2 = Lists.newArrayList("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "");
        List<String> s3 = s2.parallelStream().map(e -> {
            task();
            return e + "2";
        }).collect(Collectors.toList());

        long end = System.currentTimeMillis();
        System.out.println("并行执行时间："+(end - start));
    }

    public void task() {
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
