package org.northennerds.enums;

public enum Temperatures {
	t00(47), t08(-1), t10(-24), t16(-69), t17(-86), t18(-103), t19(-120), t20(-137), t21(-154), t22(-171), t23(-188), t24(204);

	private int angle;

	Temperatures(int angle) {
		this.angle = angle;
	}

	public int getAngle() {
		return this.angle;
	}
}
