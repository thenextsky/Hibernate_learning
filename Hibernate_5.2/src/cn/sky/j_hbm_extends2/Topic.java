package cn.sky.j_hbm_extends2;

public class Topic extends Article{
	
	private int type;//精华、置顶……

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Topic [type=" + type + "]";
	}

}
