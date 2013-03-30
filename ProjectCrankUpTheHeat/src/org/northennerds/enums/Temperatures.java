package org.northennerds.enums;

public enum Temperatures {
	t00(48), t08(-2), t10(-25), t16(-70), t17(-90), t18(-108), t19(-124), t20(-141), t21(-157), t22(-174), t23(-191), t24(-206);

	private int angle;
	
	Temperatures(int angle) {
		this.angle = angle;
	}
	
	public int getAngle()
	{
		return this.angle;
	}
//	private int temp00 = 48;
//	private int temp08 = -2;
//	private int temp10 = -25;
//	private int temp16 = -70;
//	private int temp17 = -90;
//	private int temp18 = -108;
//	private int temp19 = -124;
//	private int temp20 = -141;
//	private int temp21 = -157;
//	private int temp22 = -174;
//	private int temp23 = -191;
//	private int temp24 = -206;
}
