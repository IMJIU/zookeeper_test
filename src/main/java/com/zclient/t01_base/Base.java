package com.zclient.t01_base;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

public class Base {
	static ZkClient zkClient;

	public static void main(String[] args) {
//		create("/testUserNode");
//		read();
//		exists();
		delete("/testUserNode",true);
	}

	private static void connect() {
		// zk集群的地址
//		String ZKServers = "192.168.30.164:2181,192.168.30.165:2181,192.168.30.166:2181";
		String ZKServers = "localhost:2181";

		/**
		 * 创建会话 new SerializableSerializer() 创建序列化器接口，用来序列化和反序列化
		 */
		zkClient = new ZkClient(ZKServers, 10000, 10000, new SerializableSerializer());

		System.out.println("conneted ok!");
	}

	public static void create(String p) {
		connect();

		User user = new User();
		user.setId(1);
		user.setName("testUser");

		/**
		 * "/testUserNode" :节点的地址 user：数据的对象 CreateMode.PERSISTENT：创建的节点类型
		 */
		String path = zkClient.create(p, user, CreateMode.PERSISTENT);
		// 输出创建节点的路径
		System.out.println("created path:" + path);
	}

	public static void read() {
		connect();

		Stat stat = new Stat();
		// 获取 节点中的对象
		User user = zkClient.readData("/testUserNode", stat);
		System.out.println(user.getName());
		System.out.println(stat);
	}
	public static void set(String path) {
		connect();

		Stat stat = new Stat();
		// 获取 节点中的对象
		User user = zkClient.readData(path, stat);
		System.out.println(user.getName());
		System.out.println(stat);
	}
	public static void exists() {
		connect();

		boolean e = zkClient.exists("/testUserNode");
		// 返回 true表示节点存在 ，false表示不存在
		System.out.println(e);
	}

	public static void delete(String path , boolean recurisive) {
		connect();
		if(recurisive){
			// 删除含有子节点的节点
			System.out.println(zkClient.deleteRecursive(path));
		}else{
			// 删除单独一个节点，返回true表示成功
			System.out.println(zkClient.delete(path));
		}
	}
}