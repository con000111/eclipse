package com.uav.entity;

import java.util.Date;

public class Punch {
	int uid;//打卡用户
	int sum;//连续打卡时间
	Date punchtime;//今日打卡时间
	int count;//总打卡人数
	String ifpunch;
	public String getIfpunch() {
		return ifpunch;
	}
	public void setIfpunch(String ifpunch) {
		this.ifpunch = ifpunch;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public int getSum() {
		return sum;
	}
	public void setSum(int sum) {
		this.sum = sum;
	}
	public Date getPunchtime() {
		return punchtime;
	}
	public void setPunchtime(Date punchtime) {
		this.punchtime = punchtime;
	}

}
