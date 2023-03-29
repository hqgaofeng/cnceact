package com.cnce.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;

/**
 * 获取id
 * 
 * @author wsfeng
 *
 */
public class MajorKey {
	private static Logger logger = LoggerFactory.getLogger(MajorKey.class);
	private final long workerId;

	private final static long twepoch = 1288834974657L;

	private long sequence = 0L;

	private final static long workerIdBits = 4L;

	private final static long maxWorkerId = -1L ^ -1L << workerIdBits;

	private final static long sequenceBits = 10L;

	private final static long workerIdShift = sequenceBits;

	private final static long timestampLeftShift = sequenceBits + workerIdBits;

	public final static long sequenceMask = -1L ^ -1L << sequenceBits;

	private long lastTimestamp = -1L;

	private MajorKey(final long workerId) {
		super();
		if (workerId > maxWorkerId || workerId < 0) {
			throw new IllegalArgumentException(
					String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
		}
		this.workerId = workerId;
	}

	private synchronized long KeyId() {
		long timestamp = this.timeGen();
		if (this.lastTimestamp == timestamp) {
			this.sequence = (this.sequence + 1) & sequenceMask;
			if (this.sequence == 0) {
				timestamp = this.tilNextMillis(this.lastTimestamp);
			}
		} else {
			this.sequence = 0;
		}
		if (timestamp < this.lastTimestamp) {
			try {
				throw new Exception(String.format("Clock moved backwards. Refusing to generate id for %d milliseconds",
						this.lastTimestamp - timestamp));
			} catch (Exception e) {
				logger.error("MajorKey异常！",e);
			}
		}

		this.lastTimestamp = timestamp;
		long id = ((timestamp - twepoch << timestampLeftShift)) | (this.workerId << workerIdShift)
				| (this.sequence);
		return id;
	}

	private long tilNextMillis(final long lastTimestamp) {
		long timestamp = this.timeGen();
		while (timestamp <= lastTimestamp) {
			timestamp = this.timeGen();
		}
		return timestamp;
	}

	private long timeGen() {
		return System.currentTimeMillis();
	}

	private static MajorKey instance;

	private static long getWorkId() {
		long workID = 2;
		try {
			String ip = InetAddress.getLocalHost().getHostAddress();
			if (!"127.0.0.1".equals(ip)) {
				String last = ip.substring(ip.length() - 1);
				return Long.valueOf(last);
			}
		} catch (Exception e) {
			logger.error("MajorKey异常！",e);
		}
		return workID;
	}

	private static MajorKey getInstance() {
		if (instance == null) {
			synchronized (MajorKey.class) {
				if (instance == null) {
					instance = new MajorKey(getWorkId());
				}
			}
		}
		return instance;
	}

	public static Long getKeyID() {
		if (instance == null) {
			instance = getInstance();
		}
		return instance.KeyId();
	}
	public static String getStringId(){
		return String.valueOf(getKeyID());
	}
}
