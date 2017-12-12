package zookeeper;

import java.util.Arrays;
import java.util.List;

public class Client {

	public static void main(String[] args) throws Exception {
	       BaseZookeeper baseZookeeper = new BaseZookeeper();
	         
	        String host = "192.168.5.230:2181";
	         
	        // 连接zookeeper
	        baseZookeeper.connectZookeeper(host);
	        System.out.println("--------connect zookeeper ok-----------");
	         
	        // 获取某路径下所有节点
	        List<String> children = baseZookeeper.getChildren("/dubbo/com.jiuxian.service.sys.SystemService/providers");
	        for (String child : children)
	        {
	            System.out.println(child);
	        }
	        System.out.println("--------get children ok-----------");
	         
	        // 获取节点数据
	        byte [] nodeData = baseZookeeper.getData("/dubbo/com.jiuxian.service.sys.SystemService/providers");
	        System.out.println(Arrays.toString(nodeData));
	        System.out.println("--------get node data ok-----------");
	         
	        baseZookeeper.closeConnect();
	        System.out.println("--------close zookeeper ok-----------");
	}

}
