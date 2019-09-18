package spring.aspect.point;

import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author: chengwei11
 * @since: 2018/10/30 10:48
 * @description:
 */
public class BasePointTest {
    protected <T> T self(T current) {
        return (T)(AopContext.currentProxy() != null ?  AopContext.currentProxy() : this);
    }
}
