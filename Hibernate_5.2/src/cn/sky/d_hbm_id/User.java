package cn.sky.d_hbm_id;

public class User {
	
	private Integer id;//建议用Integer而不是int
	private String name;
	
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
