package com.zclient;

import java.util.List;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;

public class CreateSessionSample {
	static ZkClient zkClient;
	public static void main(String[] args) throws Exception {
		// ZkClient通过内部封装，将zookeeper异步的会话过程同步化了
		zkClient = new ZkClient("localhost:2181", 5000);
		System.out.println("ZooKeeper session established.");

		create(zkClient);

		delete(zkClient);

		read();
		
	}
	private static void create(ZkClient zkClient) {
		// ZKClient可以递归创建父节点
		zkClient.createPersistent("/zk-book/c1", true);
	}
	private static void delete(ZkClient zkClient) {
		/**
		 * 之间已经创建了节点/zk-boot/c1 该接口可以递归删除节点
		 */
		zkClient.deleteRecursive("/zk-book");
	}
	public static void update() throws Exception {
    	String path = "/zk-book";
        zkClient.createEphemeral(path, new Integer(1));
        zkClient.writeData(path, new Integer(1));
    }
	private static void read() throws InterruptedException {
		String path = "/zk-book";
		/**
		 * 注册回调接口 Listener不是一次性的，注册一次就会一直生效
		 */
		zkClient.subscribeChildChanges(path, new IZkChildListener() {

			@Override
			public void handleChildChange(String parentPath, List<String> currentChildren) throws Exception {
				System.out.println(parentPath + " 's child changed, currentChildren:" + currentChildren);
			}

		});
		/**
		 * 第一次创建当前节点，客户端会收到通知 /zk-book 's child changed, currentChildren:[]
		 */
		zkClient.createPersistent(path);
		Thread.sleep(1000);
		/**
		 * 创建子节点，客户端会收到通知 /zk-book 's child changed, currentChildren:[c1]
		 */
		zkClient.createPersistent(path + "/c1");
		Thread.sleep(1000);
		/**
		 * 删除子节点，客户端会收到通知 /zk-book 's child changed, currentChildren:[]
		 */
		zkClient.delete(path + "/c1");
		Thread.sleep(1000);
		/**
		 * 删除当前节点，客户端会收到通知 /zk-book 's child changed, currentChildren:null
		 */
		zkClient.delete(path);
		Thread.sleep(Integer.MAX_VALUE);
	}
}