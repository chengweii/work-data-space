package spring.autowire;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.stereotype.Service;

/**
 * 描述信息
 *
 * @author chengwei11
 * @date 2019/12/31
 */
@Service
public class AutowireServiceTest {
    @Autowired
    protected AutowireCapableBeanFactory capableBeanFactory;

    public String getName(){
        return "222";
    }
}
