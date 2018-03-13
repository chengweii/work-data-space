package rabbitmq;

import com.google.gson.reflect.TypeToken;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import util.GsonUtil;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class RabbitMQUtil {

    private static final String HOST = "**.**.**.**";
    private static final int PORT = 15672;
    private static final String USERNAME = "***";
    private static final String PASSWORD = "***";

    public static void main(String[] args) throws IOException, TimeoutException {
        List<RabbitMQExchange> rabbitMQExchanges = queryRabbitMQExchanges(HOST, PORT, USERNAME, PASSWORD);
        System.out.println("queryRabbitMQExchanges:" + rabbitMQExchanges);

        List<RabbitMQQueue> rabbitMQQueues = queryRabbitMQQueues(HOST, PORT, USERNAME, PASSWORD);
        System.out.println("queryRabbitMQQueues:" + rabbitMQQueues);

        RabbitMQQueue queue = new RabbitMQQueue();
        queue.name = "nicaicai";
        queue.vhost = "scm";
        Map<String, String> arguments = new HashMap<String, String>();
        arguments.put("x-dead-letter-exchange", "front.exchange.dlq.partner");
        queue.arguments = arguments;
        String result = createRabbitMQQueue(HOST, PORT, USERNAME, PASSWORD, queue);
        System.out.println("createRabbitMQQueue:" + result);

        RabbitMQExchangeParam exchange = new RabbitMQExchangeParam();
        exchange.vhost = "scm";
        exchange.name = "nicaicaiexchange";
        exchange.content.type = "direct";
        exchange.content.auto_delete = false;
        exchange.content.durable = true;
        Map<String, String> arguments1 = new HashMap<String, String>();
        exchange.content.arguments = arguments1;
        String result1 = createRabbitMQExchange(HOST, PORT, USERNAME, PASSWORD, exchange);
        System.out.println("createRabbitMQExchange:" + result1);
    }

    public static List<RabbitMQExchange> queryRabbitMQExchanges(String host, int port, String username, String password) {
        List<RabbitMQExchange> rabbitMQExchanges = null;
        try {
            HttpHost httpHost = new HttpHost(host, port);
            HttpGet httpGet = new HttpGet("/api/exchanges");
            BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();
            credentialsProvider.setCredentials(new AuthScope(host, port), new UsernamePasswordCredentials(username, password));
            CloseableHttpClient client = HttpClients.custom().setDefaultCredentialsProvider(credentialsProvider).build();
            HttpResponse response = client.execute(httpHost, httpGet);
            HttpEntity entity = response.getEntity();
            String exchanges = EntityUtils.toString(entity);
            rabbitMQExchanges = GsonUtil.getEntityFromJson(exchanges, new TypeToken<List<RabbitMQExchange>>() {
            });
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return rabbitMQExchanges;
    }

    public static List<RabbitMQQueue> queryRabbitMQQueues(String host, int port, String username, String password) {
        List<RabbitMQQueue> rabbitMQQueues = null;
        try {
            HttpHost httpHost = new HttpHost(host, port);
            HttpGet httpGet = new HttpGet("/api/queues");
            BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();
            credentialsProvider.setCredentials(new AuthScope(host, port), new UsernamePasswordCredentials(username, password));
            CloseableHttpClient client = HttpClients.custom().setDefaultCredentialsProvider(credentialsProvider).build();
            HttpResponse response = client.execute(httpHost, httpGet);
            HttpEntity entity = response.getEntity();
            String queues = EntityUtils.toString(entity);
            rabbitMQQueues = GsonUtil.getEntityFromJson(queues, new TypeToken<List<RabbitMQQueue>>() {
            });
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return rabbitMQQueues;
    }

    public static String createRabbitMQQueue(String host, int port, String username, String password, RabbitMQQueue queue) {
        List<RabbitMQQueue> rabbitMQQueues = null;
        try {
            HttpHost httpHost = new HttpHost(host, port);
            HttpPost httpPost = new HttpPost("/api/queues/%2f/" + queue.name);
            httpPost.setHeader("Content-type", "application/json; charset=utf-8");

            String params = GsonUtil.toJson(queue);
            StringEntity requestEntity = new StringEntity(params, Charset.forName("UTF-8"));
            requestEntity.setContentEncoding("UTF-8");
            requestEntity.setContentType("application/json");
            httpPost.setEntity(requestEntity);

            BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();
            credentialsProvider.setCredentials(new AuthScope(host, port), new UsernamePasswordCredentials(username, password));
            CloseableHttpClient client = HttpClients.custom().setDefaultCredentialsProvider(credentialsProvider).build();
            HttpResponse response = client.execute(httpHost, httpPost);
            HttpEntity entity = response.getEntity();
            return EntityUtils.toString(entity);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static String createRabbitMQExchange(String host, int port, String username, String password, RabbitMQExchangeParam exchange) {
        List<RabbitMQQueue> rabbitMQQueues = null;
        try {
            HttpHost httpHost = new HttpHost(host, port);
            HttpPost httpPost = new HttpPost("/api/exchanges/%2f/" + exchange.name);
            httpPost.setHeader("Content-type", "application/json; charset=utf-8");

            String params = GsonUtil.toJson(exchange.content);
            StringEntity requestEntity = new StringEntity(params, Charset.forName("UTF-8"));
            requestEntity.setContentEncoding("UTF-8");
            requestEntity.setContentType("application/json");
            httpPost.setEntity(requestEntity);

            BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();
            credentialsProvider.setCredentials(new AuthScope(host, port), new UsernamePasswordCredentials(username, password));
            CloseableHttpClient client = HttpClients.custom().setDefaultCredentialsProvider(credentialsProvider).build();
            HttpResponse response = client.execute(httpHost, httpPost);
            HttpEntity entity = response.getEntity();
            return EntityUtils.toString(entity);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static void closeRabbitMQSession(RabbitMQSession rabbitMQSession) {
        try {
            rabbitMQSession.channel.close();
            rabbitMQSession.connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public static RabbitMQSession getRabbitMQSession(String host, int port, String username, String password) {
        RabbitMQSession rabbitMQSession = new RabbitMQSession();
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(host);
            factory.setPort(port);
            factory.setUsername(username);
            factory.setPassword(password);

            Connection connection = null;
            Channel channel = null;

            rabbitMQSession.connection = factory.newConnection();
            rabbitMQSession.channel = connection.createChannel();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return rabbitMQSession;
    }

    public static class RabbitMQSession {
        public Connection connection = null;
        public Channel channel = null;
    }

    public static class RabbitMQExchange {
        public String name;
        public String vhost;
        public String type;
        public Boolean auto_delete;
        public Boolean durable;
        public Map<String, String> arguments;
    }

    public static class RabbitMQQueue {
        public String name;
        public String vhost;
        public Map<String, String> arguments;
    }

    public static class RabbitMQExchangeParam {
        public String name;
        public String vhost;
        public Content content = new Content();

        public static class Content {
            public String type;
            public Boolean auto_delete;
            public Boolean durable;
            public Map<String, String> arguments;
        }
    }
}
