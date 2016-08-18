package cn.sky.b_dao;

import java.util.List;

import cn.sky.a_helloworld.User;

public class QueryResult {

	public int getCurrentIndex() {
		return currentIndex;
	}
	public void setCurrentIndex(int currentIndex) {
		this.currentIndex = currentIndex;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getTotalRecord() {
		return totalRecord;
	}
	public void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
	}
	public List<User> getList() {
		return list;
	}
	public void setList(List<User> list) {
		this.list = list;
	}
	private int currentIndex;//当前页
	private int pageSize;//每页大小
	private int totalRecord;//总记录数
	private List<User> list;
	
}
