package com.zclient.t03;

import java.util.concurrent.TimeUnit;

import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.CreateMode;

import com.zclient.t03.interfaces.DistributedLock;

public class SimpleDistributedLock extends BaseDistributedLock implements DistributedLock {
	private String curLockName = null;

	public SimpleDistributedLock(ZkClient client, String path, String lockName) {
		super(client, path, lockName);
	}

	@Override
	public void acquire() throws Exception {
		curLockName = attemptLock(0l, null);
	}

	@Override
	public boolean acquire(long time, TimeUnit unit) throws Exception {
		if ((curLockName = attemptLock(time, unit)) == null) {
			return false;
		}
		return true;
	}

	@Override
	public void release() throws Exception {
		System.out.println("release:" + curLockName);
		releaseLock(curLockName);
	}

}
