package cn.sky.e_hbm_collection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class User {
	
	private Integer id;
	private String name;
	private Set<String> addressSet;//集合要用接口，而不能声明为具体的实现类，因为Hibernate执行session.save的时候会根据接口转型成别的实现类
	private List<String> addressList = new ArrayList<String>();//是否初始化均可
	private String[] addressArray = null;
	private Map<String,String> addressMap = new HashMap<String,String>();
	private List<String> addressBag = new ArrayList<String>();
	
	public List<String> getAddressBag() {
		return addressBag;
	}
	public void setAddressBag(List<String> addressBag) {
		this.addressBag = addressBag;
	}
	public Map<String, String> getAddressMap() {
		return addressMap;
	}
	public void setAddressMap(Map<String, String> addressMap) {
		this.addressMap = addressMap;
	}
	public String[] getAddressArray() {
		return addressArray;
	}
	public void setAddressArray(String[] addressArray) {
		this.addressArray = addressArray;
	}
	public List<String> getAddressList() {
		return addressList;
	}
	public void setAddressList(List<String> addressList) {
		this.addressList = addressList;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Set<String> getAddressSet() {
		return addressSet;
	}
	public void setAddressSet(Set<String> addressSet) {
		this.addressSet = addressSet;
	}

}
