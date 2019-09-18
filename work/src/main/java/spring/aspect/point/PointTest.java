package spring.aspect.point;

import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;

/**
 * @author: chengwei11
 * @since: 2018/10/30 10:48
 * @description:
 */
@Service
public class PointTest extends BasePointTest {

    public void save(String name, String value) {
        this.<PointTest>getSelf().save2("22", "33");
        System.out.println("测试中1");
        //return "";
    }

    public void save2(String name, String value) {
        System.out.println("测试中2");
        //return "";
    }
}
