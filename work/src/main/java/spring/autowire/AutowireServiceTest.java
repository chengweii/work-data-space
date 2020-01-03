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
public class AutowireServiceTest implements AutowireServiceTestApi{
    @Autowired
    protected AutowireCapableBeanFactory capableBeanFactory;

    @Override
    public String getName(){
        return "222";
    }
}
