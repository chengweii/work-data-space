/**
 * @author: chengwei11
 * @since: 2018/9/18 17:29
 * @description: 简单延迟任务数据
 */
public class SimpleDelayJobData<T> {
    private String environment;
    private String businessKey;
    private T delayJobData;

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public String getBusinessKey() {
        return businessKey;
    }

    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
    }

    public T getDelayJobData() {
        return delayJobData;
    }

    public void setDelayJobData(T delayJobData) {
        this.delayJobData = delayJobData;
    }
}
