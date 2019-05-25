package service;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.stream.ChunkedWriteHandler;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;


@Service(value = "proxyServerService")
public class ProxyServerService {
    private ChannelFuture cf;

    private Channel channel;

    private class ProxyServerOutBoundHandler extends ChannelOutboundHandlerAdapter{

    }
    private class ProxyServerInBoundHandler extends SimpleChannelInboundHandler<Object> {

        private Socket messageSender;

        private Integer port;

        private String host = "";

        @Override
        public void channelActive(ChannelHandlerContext ctx){
            try{
                this.messageSender = new Socket();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        @Override
        public void channelInactive(ChannelHandlerContext ctx){
            try{
                this.messageSender.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        public void channelRead0(ChannelHandlerContext ctx, Object obj){
            String text = ((TextWebSocketFrame)obj).text();
            if(host.length()>0){
                try{
                    messageSender.connect(new InetSocketAddress(host, port));
                    OutputStream os = messageSender.getOutputStream();

                }catch (IOException e){
                    e.printStackTrace();
                }

            }
        }
        @Override
        public  void exceptionCaught(ChannelHandlerContext ctx, Throwable t){
            t.printStackTrace();
            ctx.close();
        }
        @Override
        public void channelReadComplete(ChannelHandlerContext ctx) {
            ctx.flush();
        }


    }

    private class ProxyInitializeHandler extends ChannelInitializer<SocketChannel>{
        @Override
        public void initChannel(SocketChannel sc) throws Exception{
            sc.pipeline().addLast("http-codec", new HttpClientCodec());
            sc.pipeline().addLast("http-chunked", new ChunkedWriteHandler());
            sc.pipeline().addLast(new ProxyServerInBoundHandler());
        }
    }


    public void startServer(Integer port){
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try{
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new ProxyInitializeHandler()).bind(port)
                    .sync().channel().closeFuture().sync();

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }


}

