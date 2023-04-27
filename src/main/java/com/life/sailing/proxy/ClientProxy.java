package com.life.sailing.proxy;

import java.io.File;

import com.life.sailing.EventHandler;
import com.life.sailing.network.Message;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class ClientProxy extends CommonProxy {
	
	public static final SimpleNetworkWrapper NETWORK = NetworkRegistry.INSTANCE.newSimpleChannel("sailing");
	
	@Override
	public void preInit(File configFile) {
		
	}
	
	@Override
	public void init() {
		EventHandler eventHandler = new EventHandler();
		MinecraftForge.EVENT_BUS.register(eventHandler);
		
		NETWORK.registerMessage(Message.Handle.class, Message.class, 0, Side.CLIENT);
	}
	
	@Override
	public void postInit() {
		
	}
}
