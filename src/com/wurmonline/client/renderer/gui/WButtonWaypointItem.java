package com.wurmonline.client.renderer.gui;

import org.lwjgl.opengl.GL11;

public class WButtonWaypointItem extends WButton {
	
	public boolean enabled = true;
	public int hPadding = 0;
	public int vPadding = 0;

	WButtonWaypointItem(String aLabel) {
		super(aLabel);
	}
	

    protected void renderComponent(final float alpha) {
        if (!this.hoverMode || this.hovered) {
            GL11.glEnable(3553);
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            float r2 = this.r;
            float g2 = this.g;
            float b2 = this.b;
            if (!this.enabled) {
                r2 /= 2.0f;
                g2 /= 2.0f;
                b2 /= 2.0f;
            }
            GL11.glColor4f(r2, g2, b2, 1.0f);
            final int yo = ((this.isCloseHovered || this.isDown) && this.enabled) ? 16 : 0;
            WButton.panelTexture.switchTo();
            this.drawTexture(this.x, this.y, 8, this.height, 64, 16 + yo, 8, 16);
            this.drawTexture(this.x + this.width - 8, this.y, 8, this.height, 88, 16 + yo, 8, 16);
            if (this.width > 16) {
                WButton.panelTextureTilingH.switchTo();
                this.drawTexTilingH(this.x + 8, this.y, this.width - 16, this.height, 61 + yo, 16);
            }
            GL11.glDisable(3553);
            GL11.glDisable(3042);
        }
        final int yo2 = ((this.isCloseHovered || this.isDown) && this.enabled) ? 1 : 0;
        final float c = ((this.isCloseHovered || this.isDown) && this.enabled) ? 0.8f : 1.0f;
        GL11.glColor4f(this.rText * c, this.gText * c, this.bText * c, 1.0f);
        this.text.moveTo(this.x + 4 + this.hPadding, this.y + this.text.getHeight() + yo2 + this.vPadding);
        this.text.paint(this.label);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    }

}
