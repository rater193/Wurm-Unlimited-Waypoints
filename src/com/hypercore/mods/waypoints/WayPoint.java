package com.hypercore.mods.waypoints;

import java.util.ArrayList;

public class WayPoint {
	public static ArrayList<WayPoint> waypoints = new ArrayList<WayPoint>();
	
	public Vector3 position;
	public String name;
	public float r;
	public float g;
	public float b;
	
	public WayPoint(float x, float y, float z, String name, float colorR, float colorG, float colorB) {
		position = new Vector3(x, y, z);
		this.name = name;
		r = colorR;
		g = colorG;
		b = colorB;
		waypoints.add(this);
	}
	
	public void Delete() {
		waypoints.remove(this);
	}
}
