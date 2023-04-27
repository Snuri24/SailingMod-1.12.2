package com.life.sailing.network;

import com.life.sailing.proxy.ClientProxy;

public class Packet {

	protected StringBuilder builder;
	
	public Packet(String type) {
		builder = new StringBuilder(type);
	}
	
	public void sendToServer() {
		ClientProxy.NETWORK.sendToServer(new Message(builder.toString()));
	}
}
