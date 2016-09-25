package cn.sky.h_hbm_session_method;

public class User {
	
	private Integer id;
	private String name;
	private byte[] bt = new byte[1024*1024*10];//10M
	
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

}
