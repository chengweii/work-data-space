package rabbitmq;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RabbitMQClient {
    private static final String CHARSET = "utf-8";

    private static final String HOST = "192.168.19.158";
    private static final int PORT = 15672;
    //登录WEB端后由请求头中获取authorization
    private static final String AUTHORIZATION = "Basic c2NtOm5vdGVyNzdAZGl2aQ==";

    private static final String HOST_NEW = "10.60.12.145";
    private static final int PORT_NEW = 15672;
    //登录WEB端后由请求头中获取authorization
    private static final String AUTHORIZATION_NEW = "Basic c2NtOm5vdGVyNzdAZGl2aQ==";

    private static final String QUEUES_FILE_PATH = "E://queues.json";
    private static final String EXCHANGES_FILE_PATH = "E://exchanges.json";
    private static final String BINDINGS_FILE_PATH = "E://bindings.json";

    public static Gson gson = null;

    static {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.setPrettyPrinting().serializeNulls().create();
    }

    public static void main(String[] args) {
        //test();
        //拷贝MQ的配置信息从某个节点到另一个节点
        copyRabbitMQConfigs(HOST, PORT, AUTHORIZATION, HOST_NEW, PORT_NEW, AUTHORIZATION_NEW);
    }

    private static void test() {
        RabbitMQQueue queue = new RabbitMQQueue();
        queue.name = "test.queue";
        queue.vhost = "scm";
        queue.auto_delete = "false";
        queue.durable = "true";
        Map<String, String> arguments = new HashMap<String, String>();
        arguments.put("x-dead-letter-exchange", "test.exchange");
        queue.arguments = arguments;
        createQueue(HOST, PORT, AUTHORIZATION, queue);
        System.out.println("创建队列完成");

        RabbitMQExchange exchange = new RabbitMQExchange();
        exchange.name = "test.exchange888";
        exchange.vhost = "scm";
        exchange.auto_delete = "false";
        exchange.durable = "true";
        exchange.internal = "true";
        exchange.type = "fanout";
        Map<String, String> arguments1 = new HashMap<String, String>();
        exchange.arguments = arguments1;
        createExchange(HOST, PORT, AUTHORIZATION, exchange);
        System.out.println("创建交换机完成");

        RabbitMQBinding binding = new RabbitMQBinding();
        binding.destination = "test.queue";
        binding.vhost = "scm";
        binding.destination_type = "queue";
        binding.properties_key = "16";
        binding.routing_key = "16";
        binding.source = "test.exchange888";
        Map<String, String> arguments2 = new HashMap<String, String>();
        binding.arguments = arguments2;
        createBinding(HOST, PORT, AUTHORIZATION, binding);
        System.out.println("绑定完成");

        List<RabbitMQQueue> rabbitMQQueues = queryQueues(HOST, PORT, AUTHORIZATION);
        System.out.println("查询队列列表:" + rabbitMQQueues);

        List<RabbitMQExchange> rabbitMQExchanges = queryExchanges(HOST, PORT, AUTHORIZATION);
        System.out.println("查询交换机列表:" + rabbitMQExchanges);

        List<RabbitMQBinding> rabbitMQBindings = queryBindings(HOST, PORT, AUTHORIZATION);
        System.out.println("查询绑定关系列表:" + rabbitMQBindings);
    }

    public static void copyRabbitMQConfigs(String sourceHost, int sourcePort, String sourceAuthorization, String destinationHost, int destinationPort, String destinationAuthorization) {
        List<RabbitMQQueue> rabbitMQQueues = queryQueues(sourceHost, sourcePort, sourceAuthorization);
        List<RabbitMQExchange> rabbitMQExchanges = queryExchanges(sourceHost, sourcePort, sourceAuthorization);
        List<RabbitMQBinding> rabbitMQBindings = queryBindings(sourceHost, sourcePort, sourceAuthorization);
        createQueuesFromJson(destinationHost, destinationPort, destinationAuthorization);
        createExchangesFromJson(destinationHost, destinationPort, destinationAuthorization);
        createBindingsFromJson(destinationHost, destinationPort, destinationAuthorization);
    }

    public static void createQueuesFromJson(String host, int port, String authorization) {
        String queuesJson = null;
        try {
            queuesJson = getFileContent(QUEUES_FILE_PATH);
            List<RabbitMQQueue> rabbitMQQueues = getEntityFromJson(queuesJson, new TypeToken<List<RabbitMQQueue>>() {
            });
            for (RabbitMQQueue entity : rabbitMQQueues) {
                createQueue(host, port, authorization, entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void createExchangesFromJson(String host, int port, String authorization) {
        String exchangesJson = null;
        try {
            exchangesJson = getFileContent(EXCHANGES_FILE_PATH);
            List<RabbitMQExchange> rabbitMQExchanges = getEntityFromJson(exchangesJson, new TypeToken<List<RabbitMQExchange>>() {
            });
            for (RabbitMQExchange entity : rabbitMQExchanges) {
                createExchange(host, port, authorization, entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void createBindingsFromJson(String host, int port, String authorization) {
        String bindingsJson = null;
        try {
            bindingsJson = getFileContent(BINDINGS_FILE_PATH);
            List<RabbitMQBinding> rabbitMQBindings = getEntityFromJson(bindingsJson, new TypeToken<List<RabbitMQBinding>>() {
            });
            for (RabbitMQBinding entity : rabbitMQBindings) {
                if (StringUtils.isNotEmpty(entity.source)) {
                    createBinding(host, port, authorization, entity);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<RabbitMQQueue> queryQueues(String host, int port, String authorization) {
        List<RabbitMQQueue> rabbitMQQueues = null;
        try {
            String result = httpGetRaw("/api/queues", host, port, authorization);
            writeFileContent(result, QUEUES_FILE_PATH);
            rabbitMQQueues = getEntityFromJson(result, new TypeToken<List<RabbitMQQueue>>() {
            });
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return rabbitMQQueues;
    }

    public static List<RabbitMQExchange> queryExchanges(String host, int port, String authorization) {
        List<RabbitMQExchange> rabbitExchanges = null;
        try {
            String result = httpGetRaw("/api/exchanges", host, port, authorization);
            writeFileContent(result, EXCHANGES_FILE_PATH);
            rabbitExchanges = getEntityFromJson(result, new TypeToken<List<RabbitMQExchange>>() {
            });
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return rabbitExchanges;
    }

    public static List<RabbitMQBinding> queryBindings(String host, int port, String authorization) {
        List<RabbitMQBinding> rabbitMQBindings = null;
        try {
            String result = httpGetRaw("/api/bindings", host, port, authorization);
            writeFileContent(result, BINDINGS_FILE_PATH);
            rabbitMQBindings = getEntityFromJson(result, new TypeToken<List<RabbitMQBinding>>() {
            });
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return rabbitMQBindings;
    }

    public static void createExchange(String host, int port, String authorization, RabbitMQExchange exchange) {
        String requestJson = toJson(exchange);
        String url = "/api/exchanges/" + exchange.vhost + "/" + exchange.name;
        httpPutRaw(url, host, port, authorization, requestJson);
    }

    public static void createQueue(String host, int port, String authorization, RabbitMQQueue queue) {
        String requestJson = toJson(queue);
        String url = "/api/queues/" + queue.vhost + "/" + queue.name;
        httpPutRaw(url, host, port, authorization, requestJson);
    }

    public static void createBinding(String host, int port, String authorization, RabbitMQBinding binding) {
        String requestJson = toJson(binding);
        String url = "/api/bindings/" + binding.vhost + "/e/" + binding.source + "/q/" + binding.destination;
        httpPostRaw(url, host, port, authorization, requestJson);
    }

    public static String httpGetRaw(String url, String host, int port, String authorization) {
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();

        HttpHost httpHost = new HttpHost(host, port);

        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("Connection", "keep-alive");
        httpGet.setHeader("Content-type", "application/json");
        httpGet.setHeader("authorization", authorization);

        CloseableHttpResponse httpResponse = null;
        String content = null;
        try {
            httpResponse = closeableHttpClient.execute(httpHost, httpGet);
            HttpEntity entity = httpResponse.getEntity();
            content = EntityUtils.toString(entity, CHARSET);
            return content;
            //System.out.println(content);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                httpResponse.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            closeableHttpClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void httpPostRaw(String url, String host, int port, String authorization, String requestJson) {
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();

        HttpHost httpHost = new HttpHost(host, port);

        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Connection", "keep-alive");
        httpPost.setHeader("Content-type", "application/json");
        httpPost.setHeader("authorization", authorization);

        StringEntity stringEntity = new StringEntity(requestJson, CHARSET);
        httpPost.setEntity(stringEntity);

        CloseableHttpResponse httpResponse = null;
        String content = null;
        try {
            httpResponse = closeableHttpClient.execute(httpHost, httpPost);
            //HttpEntity entity = httpResponse.getEntity();
            //content = EntityUtils.toString(entity, CHARSET);
            //System.out.println(content);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                httpResponse.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            closeableHttpClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void httpPutRaw(String url, String host, int port, String authorization, String requestJson) {
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();

        HttpHost httpHost = new HttpHost(host, port);

        HttpPut httpput = new HttpPut(url);
        httpput.setHeader("Connection", "keep-alive");
        httpput.setHeader("Content-type", "application/json");
        httpput.setHeader("authorization", authorization);

        StringEntity stringEntity = new StringEntity(requestJson, CHARSET);
        httpput.setEntity(stringEntity);

        CloseableHttpResponse httpResponse = null;
        String content = null;
        try {
            httpResponse = closeableHttpClient.execute(httpHost, httpput);
            //HttpEntity entity = httpResponse.getEntity();
            //content = EntityUtils.toString(entity, CHARSET);
            //System.out.println(content);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                httpResponse.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            closeableHttpClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeFileContent(String content, String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists())
            file.createNewFile();
        FileOutputStream out = new FileOutputStream(file, false);
        out.write(content.getBytes(CHARSET));
        out.close();
    }

    public static String getFileContent(String filePath) throws IOException {
        String line;
        StringBuilder stringBuilder = new StringBuilder();
        InputStreamReader streamReader = new InputStreamReader(new FileInputStream(new File(filePath)), CHARSET);
        BufferedReader reader = new BufferedReader(streamReader);
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
        }
        reader.close();
        return stringBuilder.toString();
    }

    public static <T> T getEntityFromJson(String json, TypeToken<?> typeToken) {
        if (StringUtils.isEmpty(json))
            return null;

        T data = null;
        try {
            data = gson.fromJson(json, typeToken.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    public static String toJson(Object src) throws JsonSyntaxException {
        return gson.toJson(src);
    }

    public static class RabbitMQQueue {
        public String name;
        public String vhost;
        public String durable;
        public String auto_delete;
        public Map<String, String> arguments;
    }

    public static class RabbitMQExchange {
        public String name;
        public String vhost;
        public String type;
        public String auto_delete;
        public String durable;
        public String internal;
        public Map<String, String> arguments;
    }

    public static class RabbitMQBinding {
        public String destination;
        public String destination_type;
        public String properties_key;
        public String routing_key;
        public String source;
        public String vhost;
        public Map<String, String> arguments;
    }
}
