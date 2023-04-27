package com.life.sailing;

import org.lwjgl.input.Keyboard;

import com.life.sailing.network.ClientPacketFastSailing;
import com.life.sailing.network.Message;
import com.life.sailing.network.PacketType;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.entity.EntityMountEvent;

public class M {
	
	private static M instance;
	
	public Minecraft mc;

	private SailingState sailingState;

	public static M getInstance() {
		if(instance == null) {
			instance = new M();
		}
		
		return instance;
	}
	
	private M() {
		mc = Minecraft.getMinecraft();
		sailingState = null;
	}
	
	public void onEntityMount(EntityMountEvent event) {
		if(event.getEntity() != mc.player)
			return;
		if(event.getEntityBeingMounted() == null || !(event.getEntityBeingMounted() instanceof EntityBoat))
			return;
		
		EntityBoat boat = (EntityBoat) event.getEntityBeingMounted();
		if(event.isDismounting()) {
			sailingState = null;
		} else if(boat.getPassengers().size() == 0 || !(boat.getPassengers().get(0) instanceof EntityPlayer)) {
			sailingState = new SailingState();
		}
	}
	
	public void onTick() {
		
	}
	
	public void onRender(ScaledResolution resolution, float partialTicks) {
		if(mc.player == null)
			return;
		
		if(sailingState != null) {
			sailingState.render(resolution, partialTicks);
		}
	}
	
	public void keyEvent() {
		if(mc.player == null)
			return;
		
		if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
			if(sailingState != null && sailingState.getFuel() > 0 && sailingState.getCooltime() <= 0) {
				Entity ridingEntity = mc.player.getRidingEntity();
				if(ridingEntity.isInWater()) {
					ClientPacketFastSailing packet = new ClientPacketFastSailing(ridingEntity.getUniqueID());
					packet.sendToServer();
				} else {
					mc.player.sendStatusMessage(new TextComponentString("§c육지에서는 스킬을 사용할 수 없습니다."), true);
				}
			}
		}
	}
	
	// PacketHandle
	public void handleMessage(Message message) {
		String code = message.data.substring(0, 2);
		if(code.equals(PacketType.SERVER_SAILING_STATE)) {
			String[] dataArr = message.data.substring(2).split(":");
			if(sailingState != null) {
				sailingState.update(Integer.parseInt(dataArr[0]), dataArr[1].equals("1") ? true : false, Integer.parseInt(dataArr[2]));
			}
		}
	}
}
