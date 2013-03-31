package org.northennerds.enums;

public enum Temperatures {
	t00(47), t08(-1), t10(-24), t16(-69), t17(-86), t18(-103), t19(-120), t20(-137), t21(-154), t22(-171), t23(-188), t24(-204);

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
