package com.life.sailing.network;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.nio.charset.StandardCharsets;

import com.life.sailing.M;

public class Message implements IMessage {

    public String data;

    public Message() {

    }

    public Message(String data) {
        this.data = data;
    }


    @Override
    public void fromBytes(ByteBuf buf) {
        int len = buf.readInt();
        data = buf.toString(buf.readerIndex(), len, StandardCharsets.UTF_8);
        buf.readerIndex(buf.readerIndex() + len);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        byte[] bytes = data.getBytes(StandardCharsets.UTF_8);
        buf.writeInt(bytes.length);
        buf.writeBytes(bytes);
    }


    public static class Handle implements IMessageHandler<Message, IMessage> {
        @Override
        public IMessage onMessage(Message message, MessageContext ctx) {
            M.getInstance().handleMessage(message);
            return null;
        }
    }
}
