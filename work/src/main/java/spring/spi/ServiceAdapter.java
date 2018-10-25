package spring.spi;

/**
 * @author: chengwei11
 * @since: 2018/10/23 17:56
 * @description: 服务适配器
 */
public interface ServiceAdapter {
    /**
     * 获取应用业务编号
     *
     * @return 应用业务编号
     */
    String getBusinessKey();
}
