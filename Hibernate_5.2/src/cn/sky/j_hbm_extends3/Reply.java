package cn.sky.j_hbm_extends3;

public class Reply extends Article{
	
	private int floor;//楼层

	public int getFloor() {
		return floor;
	}

	public void setFloor(int floor) {
		this.floor = floor;
	}

	@Override
	public String toString() {
		return "Reply [floor=" + floor + "]";
	}
	

}
