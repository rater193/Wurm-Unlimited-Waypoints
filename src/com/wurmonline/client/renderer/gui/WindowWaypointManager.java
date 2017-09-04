package com.wurmonline.client.renderer.gui;

import com.hypercore.mods.waypoints.WayPointMod;

public class WindowWaypointManager extends WWindow {
	
	//This is a list of configurable values for t he waypoint manager panel
	public static String panelName = "Waypoint Manager";
	
	private WurmBorderPanel mainPanel;
	
	public WindowWaypointManager() {
		super(panelName, true);
		
		setTitle(panelName);
		mainPanel = new WurmBorderPanel(panelName);
		this.resizable = false;
		
		WurmArrayPanel<FlexComponent> buttons = new WurmArrayPanel<FlexComponent>("Waypoint Manager Buttons", WurmArrayPanel.DIR_VERTICAL);
		buttons.setInitialSize(32, 256, false);
		
		WButton btnNewWaypoint = new WButton("New Waypoint") {
			
			@Override
			protected void leftPressed(int xMouse, int yMouse, int clickCount) {
				super.leftPressed(xMouse, yMouse, clickCount);
				hud.toggleComponent(new WindowNewWaypoint(WayPointMod.world.getPlayerPosX(), WayPointMod.world.getPlayerPosH(), WayPointMod.world.getPlayerPosY(), 1, 1, 1));
			}
			
		};
		btnNewWaypoint.setSize(256, 24);
		buttons.addComponent(btnNewWaypoint);

		
		WButton btnWaypointList = new WButtonWaypointItem("Waypoint List");
		
		btnWaypointList.setSize(256, 224);
		buttons.addComponent(btnWaypointList);
		
		mainPanel.setComponent(buttons, WurmBorderPanel.WEST);
		
		setComponent(mainPanel);
		setInitialSize(256, 320, false);
		layout();
		
		sizeFlags = FlexComponent.FIXED_WIDTH;
		this.closeable = true;
		
	}

	@Override
	void closePressed() {
		hud.toggleComponent(this);
	}
}
