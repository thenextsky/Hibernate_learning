package cn.sky.i_hbm_one2one;

public class IdCard {
	
	private Integer id;
	private String number;
	private Person person = new Person();//不能初始化，因为是临时状态，如果初始化则抛异常：引用了临时状态的对象？好像可以初始化
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}
	
	
}
