package com.git.wuqf.remoting.netty;

import com.git.wuqf.remoting.ChannelHandler;
import com.git.wuqf.remoting.RemotingException;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import com.git.wuqf.xiaokuo.common.URL;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by wuqf on 17-2-24.
 */
public class NettyHandler extends ChannelInboundHandlerAdapter {

    private final Map<String, Channel> channels = new ConcurrentHashMap<>();

    private URL url;

    private ChannelHandler handler;


    public NettyHandler(URL url, ChannelHandler handler) {
        this.url = url;
        this.handler=handler;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws RemotingException {
        NettyChannel channel=NettyChannel.getOrAddChannel(ctx.channel(),url,handler);
        handler.received(channel,msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }
}
