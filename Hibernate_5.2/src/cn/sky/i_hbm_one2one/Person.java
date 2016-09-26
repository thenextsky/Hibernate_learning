package cn.sky.i_hbm_one2one;

public class Person {

	private Integer id;
	private String name;
	private IdCard idCard;// = new IdCard();不能2边都初始化，否则形成递归，栈溢出
	
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
	public IdCard getIdCard() {
		return idCard;
	}
	public void setIdCard(IdCard idCard) {
		this.idCard = idCard;
	}
	
}
