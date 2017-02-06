package com.zclient.t01_base;

import java.util.List;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;
/**
 * 订阅节点的信息改变（创建节点，删除节点，添加子节点）
 * @author zhoulf
 *
 */
public class SubscribeChildChanges {
	private static class ZKChildListener implements IZkChildListener {
		/**
		 * handleChildChange： 用来处理服务器端发送过来的通知 parentPath：对应的父节点的路径
		 * currentChilds：子节点的相对路径
		 */
		public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {
			System.out.println(parentPath);
			System.out.println(currentChilds.toString());
		}
	}

	public static void main(String[] args) throws InterruptedException {
		// zk集群的地址
//		String ZKServers = "192.168.30.164:2181,192.168.30.165:2181,192.168.30.166:2181";
		String ZKServers = "localhost:2181";
//		ZkClient zkClient = new ZkClient(ZKServers, 10000, 10000, new SerializableSerializer());
		ZkClient zkClient = new ZkClient(ZKServers, 10000, 10000);
		System.out.println("conneted ok!");
		/**
		 * "/testUserNode" 监听的节点，可以是现在存在的也可以是不存在的
		 */
		zkClient.subscribeChildChanges("/testUserNode", new ZKChildListener());
		Thread.sleep(Integer.MAX_VALUE);
	}
}