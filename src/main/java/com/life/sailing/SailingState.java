package com.life.sailing;

import com.life.sailing.util.Reference;

import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class SailingState {
	
	private final float MAX_FUEL = 360.0F;
	private final float FAST_SAILING_COOLTIME = 10.0F;
	
	private ResourceLocation fast_sailing_on = new ResourceLocation(Reference.MOD_ID, "textures/fast_sailing_on.png");
	private ResourceLocation fast_sailing_off = new ResourceLocation(Reference.MOD_ID, "textures/fast_sailing_off.png");
	private ResourceLocation texture_cooltime = new ResourceLocation(Reference.MOD_ID, "textures/cooltime.png");
	
	private int fuel;
	private boolean fastSailing;
	private int cooltime;
	
	public SailingState() {
		fuel = -1;
		fastSailing = false;
		cooltime = 0;
	}
	
	public void update(int fuel, boolean fastSailing, int cooltime) {
		this.fuel = fuel;
		this.fastSailing = fastSailing;
		this.cooltime = cooltime;
	}
	
	public int getFuel() {
		return fuel;
	}
	
	public boolean isFastSailing() {
		return fastSailing;
	}
	
	public int getCooltime() {
		return cooltime;
	}
	
	public void render(ScaledResolution resolution, float partialTicks) {
		if(fuel == -1)
			return;
		
		int width = resolution.getScaledWidth();
		int height = resolution.getScaledHeight();
		
		GlStateManager.pushMatrix();
	    GlStateManager.disableLighting();
		
	    if(fuel == 0) {
		    Render.drawString("§c연료 0%", width * 0.5006F, height * 0.5899F, height * 0.0463F, 1);
	    } else {
		    int f = (int) Math.ceil(fuel / MAX_FUEL * 100.0F);
	    	Render.drawString(String.format("연료 %d%%", f), width * 0.5006F, height * 0.5899F, height * 0.0463F, 1);
	    }
	    
	    if(fastSailing) {
		    Render.bindTexture(fast_sailing_on);
	 		Render.setColor(0xffffffff);
	 		Render.drawTexturedRect(width * 0.4708F, height * 0.6296F, width * 0.0583F, height * 0.1037F);
	    } else {
		    Render.bindTexture(fast_sailing_off);
	 		Render.setColor(0xffffffff);
	 		Render.drawTexturedRect(width * 0.4708F, height * 0.6296F, width * 0.0583F, height * 0.1037F);
	    }
	    
	    Render.drawString("SPACE", width * 0.5006F, height * 0.7343F, height * 0.0463F, 1);
	    
	    if(cooltime > 0) {
	    	Render.bindTexture(texture_cooltime);
	 		Render.setColor(0xffffffff);
	 		Render.drawTexturedRect(width * 0.4771F, height * (0.6417F + (1 - (cooltime / FAST_SAILING_COOLTIME)) * 0.0815F), width * 0.0458F, height * 0.0815F * (cooltime / FAST_SAILING_COOLTIME));
	    }
		
		GlStateManager.popMatrix();
	}
}
