package spring.aspect;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author: chengwei11
 * @since: 2018/10/30 11:24
 * @description: 启动AspectJ自动代理
 */
@Configuration
@EnableAspectJAutoProxy
@ComponentScan
public class ConcertConfig {
    /**
     * 声明Audience bean
     *
     * @return
     */
    @Bean
    public AdviceTest adviceTest() {
        return new AdviceTest();
    }
}
