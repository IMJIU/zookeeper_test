package com.zclient.t01_base;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;
/**
 * 订阅节点的数据内容的变化
 * @author zhoulf
 *
 */
public class SubscribeDataChanges {
	private static class ZKDataListener implements IZkDataListener {

		public void handleDataChange(String dataPath, Object data) throws Exception {
			System.out.println(dataPath + ":" + data.toString());
		}

		public void handleDataDeleted(String dataPath) throws Exception {
			System.out.println(dataPath);
		}

	}

	public static void main(String[] args) throws InterruptedException {
		// zk集群的地址
//		String ZKServers = "192.168.30.164:2181,192.168.30.165:2181,192.168.30.166:2181";
		String ZKServers = "localhost:2181";
		ZkClient zkClient = new ZkClient(ZKServers, 10000, 10000, new SerializableSerializer());
		System.out.println("conneted ok!");

		zkClient.subscribeDataChanges("/testUserNode", new ZKDataListener());
		Thread.sleep(Integer.MAX_VALUE);

	}
}