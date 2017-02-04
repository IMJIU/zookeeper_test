package com.zclient.t03;

import java.util.concurrent.TimeUnit;

import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.CreateMode;

public class SimpleDistributedLock extends BaseDistributedLock implements DistributedLock {
	private static final String lockPath = "/simple_lock";

	public SimpleDistributedLock(ZkClient client, String path, String lockName) {
		super(client, path, lockName);
	}

	public SimpleDistributedLock(ZkClient client, String lockname) {
		super(client, lockPath, lockname);
	}

	@Override
	public void acquire() throws Exception {
		String path = client.create(lockPath, null, CreateMode.PERSISTENT);
	}

	@Override
	public boolean acquire(long time, TimeUnit unit) throws Exception {
		if (attemptLock(time, unit) == null) {
			return false;
		}
		return true;
	}

	@Override
	public void release() throws Exception {
		releaseLock(lockPath);
	}

}
