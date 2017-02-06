package com.curator.t04_master;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderLatch;
import org.apache.curator.retry.RetryNTimes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ZKConnectionUtil {

    private  static CuratorFramework client ;

    private static Object lock = new Object();

    private static LeaderLatch leaderLatch;

    private static Logger logger = LoggerFactory.getLogger(ZKConnectionUtil.class);

    /**
     * @描述：是否是leader
     * @return
     * @return boolean
     * @exception
     * @createTime：2016年11月22日
     * @author: songqinghu
     */
    public static boolean isLeader(CuratorFramework client){

        return leaderLatch.hasLeadership();
    }

//            solr.zk.check.namespace=contentcheck
//            solr.zk.check.flag.path=/flag
    /**
     * 
     * @描述：获取单例的zookeeper连接
     * @return void
     * @exception
     * @createTime：2016年8月24日
     * @author: songqinghu
     */
    public static CuratorFramework getZKConnection(){

        if(client ==null){
            synchronized (lock) {
                if(client ==null){
                    int connectionTimeoutMs=5000;
                    String connectString=PropertiesUtil.getProProperties("solr.zk.url","");
                    String namespace=PropertiesUtil.getProProperties("solr.zk.porsche.namespace","");
                    //String connectString="10.125.2.48:3181";
                    //String namespace="contentcheck";
                    //byte[] defaultData=IpUtil.getInNetworkIp().getBytes();
                    RetryPolicy retryPolicy= new RetryNTimes(Integer.MAX_VALUE, 1000);

                    client = CuratorFrameworkFactory.
                            builder().
//        aclProvider(aclProvider).
//        authorization(scheme, auth).
                            connectionTimeoutMs(connectionTimeoutMs).
                            connectString(connectString).
                          //  defaultData(defaultData).
                            namespace(namespace).
                            retryPolicy(retryPolicy).
                            build();

                    client.start();

                    //选举
                    String leaderPath = PropertiesUtil.getProProperties("solr.zk.porsche.leader", "");
                    leaderLatch = new LeaderLatch(client, leaderPath);
                    try {
                        leaderLatch.start();
                    } catch (Exception e) {
                        logger.error("get leaderlatch occor error and error is : " + e);    
                    }  

                }
            }
        }
        return client;
    }


}