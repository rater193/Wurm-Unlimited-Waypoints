package com.hypercore.mods.waypoints;

import java.util.logging.Logger;

public class Debug {
    private static final Logger logger = Logger.getLogger(WayPointMod.class.getName());

	public static void Init() {
		
	}
	
	public static void log(Object message) {
		logger.info("[WayPoint Manager]" + message);
	}

}
