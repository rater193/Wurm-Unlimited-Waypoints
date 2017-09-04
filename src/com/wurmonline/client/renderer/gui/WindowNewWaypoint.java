package com.wurmonline.client.renderer.gui;

import com.hypercore.mods.waypoints.WayPoint;

public class WindowNewWaypoint extends WWindow implements InputFieldListener {
	
	public static String panelName = "New Waypoint";

    private WurmInputField ifPosX;
    private WurmInputField ifPosY;
    private WurmInputField ifPosZ;
	private WurmBorderPanel mainPanel;
	
	public WindowNewWaypoint(float posx, float posy, float posz, float colorr, float colorg, float colorb) {
		super(panelName, true);
		setTitle(panelName);
		mainPanel = new WurmBorderPanel(panelName);
		this.resizable = false;
		
		WurmArrayPanel<FlexComponent> buttons = new WurmArrayPanel<FlexComponent>("Waypoint Manager Buttons", WurmArrayPanel.DIR_VERTICAL);
		buttons.setInitialSize(32, 256, false);
		
		WindowNewWaypoint instance = this;
		
		//The x position of the waypoint
		ifPosX = new WurmInputField("X", this);
		ifPosX.prompt = "X: ";
		ifPosX.setText(posx+"");
		
		
		ifPosY = new WurmInputField("Y", this);
		ifPosY.prompt = "Y: ";
		ifPosY.setText(posy+"");

		ifPosZ = new WurmInputField("Z", this);
		ifPosZ.prompt = "Z: ";
		ifPosZ.setText(posz+"");
		
		
		WButton btnCreateWaypoint = new WButton("Crete Waypoint") {
			
			@Override
			protected void leftPressed(int xMouse, int yMouse, int clickCount) {
				super.leftPressed(xMouse, yMouse, clickCount);
				try {
					new WayPoint(
							Float.parseFloat(ifPosX.getText()),
							Float.parseFloat(ifPosY.getText()),
							Float.parseFloat(ifPosZ.getText()),
							"<NotSet>", colorr, colorg, colorb);
					
					hud.toggleComponent(instance);
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
			
		};
		btnCreateWaypoint.setPosition(4, 4);
		btnCreateWaypoint.setSize(248, 24);


		buttons.addComponent(ifPosX);
		buttons.addComponent(ifPosY);
		buttons.addComponent(ifPosZ);
		buttons.addComponent(btnCreateWaypoint);
		
		mainPanel.setComponent(buttons, WurmBorderPanel.CENTER);
		
		setComponent(mainPanel);
		setInitialSize(256, 128, false);
		layout();
		
		sizeFlags = FlexComponent.FIXED_WIDTH;
		this.closeable = true;
	}

	@Override
	void closePressed() {
		hud.toggleComponent(this);
	}

	@Override
	public void handleInput(String p0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleInputChanged(WurmInputField p0, String p1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleEscape(WurmInputField wif) {
		// TODO Auto-generated method stub
	}
}
