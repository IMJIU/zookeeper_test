package com.zclient.t03_lock;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.BytesPushThroughSerializer;

import com.zclient.t03_lock.interfaces.DistributedLock;  
  
public class TestDistributedLock {  
      
    public static void main(String[] args) {  
          
        final ZkClient zkClient1 = new ZkClient("localhost:2181", 5000, 5000, new BytesPushThroughSerializer());  
        final DistributedLock mutex1 = new SimpleDistributedLock(zkClient1,"/lock1", "Mutex");  
          
        final ZkClient zkClient2 = new ZkClient("localhost:2181", 5000, 5000, new BytesPushThroughSerializer());  
        final DistributedLock mutex2 = new SimpleDistributedLock(zkClient2,"/lock1", "Mutex");  
          
        try {  
            mutex1.acquire();  
            System.out.println("Client1 locked");  
            Thread client2Thd = new Thread(new Runnable() {  
                  
                public void run() {  
                    try {  
                        mutex2.acquire();  
                        System.out.println("Client2 locked");  
                        Thread.sleep(5000);  
                        mutex2.release();  
                        System.out.println("Client2 released lock");  
                          
                    } catch (Exception e) {  
                        e.printStackTrace();  
                    }                 
                }  
            });  
            client2Thd.start();  
            Thread.sleep(5000);  
            mutex1.release();             
            System.out.println("Client1 released lock");  
              
            client2Thd.join();  
              
        } catch (Exception e) {  
  
            e.printStackTrace();  
        }  
          
    }  
  
}  