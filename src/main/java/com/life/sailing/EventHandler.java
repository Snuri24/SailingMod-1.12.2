package com.life.sailing;

import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.entity.EntityMountEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class EventHandler {
	
	private M m;
	
	public EventHandler() {
		this.m = M.getInstance();
	}
	
	@SubscribeEvent
	public void entityMount(EntityMountEvent event) {
		m.onEntityMount(event);
	}
	
	@SubscribeEvent
	public void clientTick(TickEvent.ClientTickEvent event) {
		if(event.phase == TickEvent.Phase.END) {
			m.onTick();
		}
	}
	
	@SubscribeEvent
	public void renderGameOverlay(RenderGameOverlayEvent.Post event) {
		if(event.getType() == RenderGameOverlayEvent.ElementType.ALL) {
			m.onRender(event.getResolution(), event.getPartialTicks());
		}
	}
	
	@SubscribeEvent
	public void keyEvent(InputEvent.KeyInputEvent event) {
		m.keyEvent();
	}
}
