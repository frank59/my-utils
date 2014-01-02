package com.sinaapp.frankwang.myutils.log;

import java.util.ArrayList;
import java.util.List;

public class TimeCostRecordUtil {
	
	private static ThreadLocal<TimeCostRecordUtil> instances = new ThreadLocal<TimeCostRecordUtil>();
	
	private List<CostRecord> recordList = new ArrayList<CostRecord>();
	
	private CostRecord start ;
	private CostRecord last ;
	
	private TimeCostRecordUtil() {
		
	}
	
	
	public long addRecord(String record) {
		CostRecord newRecord = new CostRecord(record, System.currentTimeMillis());
		recordList.add(newRecord);
		long span = newRecord.getTimePoint() - last.getTimePoint();
		last = newRecord;
		return span;
	}
	
	private String getRecordsLog() {
		StringBuilder builder = new StringBuilder();
		builder.append("[");
		if (start != null && last != null) {
			builder.append("start");
			CostRecord current = start;
			for (CostRecord item : recordList) {
				builder.append("--").append(item.getTimePoint() - current.getTimePoint()).append("ms--").append(item.recordStr);
				current = item;
			}
			builder.append(", cost=").append(current.timePoint - start.timePoint).append("ms");
		}
		builder.append("]");
		return builder.toString();
	}
	
	
	public static void start() {
		TimeCostRecordUtil instance = new TimeCostRecordUtil();
		instance.start = new CostRecord("start", System.currentTimeMillis());
		instance.last = instance.start;
		instances.set(instance);
	}
	
	public static long newRecord(String record) {
		TimeCostRecordUtil instance = instances.get();
		if (instance == null) {
			start();
			return 0;
		}
		return instance.addRecord(record);
	}
	
	public static String getCostLog() {
		TimeCostRecordUtil instance = instances.get();
		if (instance == null) {
			return "没有记录";
		}
		return instance.getRecordsLog();
	}
	
	public static void clean() {
		TimeCostRecordUtil instance = instances.get();
		if (instance != null) {
			instance.recordList.clear();
			instance.start = null;
			instance.last = null;
		}
	}

	static class CostRecord {
		
		private String recordStr;
		private long timePoint;
		public CostRecord(String recordStr, long timePoint) {
			this.recordStr = recordStr;
			this.timePoint = timePoint;
		}
		public String getRecordStr() {
			return recordStr;
		}
		public void setRecordStr(String recordStr) {
			this.recordStr = recordStr;
		}
		public long getTimePoint() {
			return timePoint;
		}
		public void setTimePoint(long timePoint) {
			this.timePoint = timePoint;
		}
	}
}
