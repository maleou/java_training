package homework.day05.gateway;

import homework.day05.gateway.backendserver.HttpServer01;
import homework.day05.gateway.backendserver.HttpServer02;
import homework.day05.gateway.backendserver.HttpServer03;
import homework.day05.gateway.config.NettyConfig;
import homework.day05.gateway.initializer.HttpServerInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class NettyEchoServer {

    private final int serverPort;
    ServerBootstrap b = new ServerBootstrap();

    public NettyEchoServer(int port) {
        this.serverPort = port;
    }

    public void runServer() {
        // 创建反应器线程组
        NioEventLoopGroup bossLoopGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workerLoopGroup = new NioEventLoopGroup();

        try {
            // 1 设置反应器线程组
            b.group(bossLoopGroup, workerLoopGroup);
            // 2 设置 nio 类型的通道
            b.channel(NioServerSocketChannel.class);
            // 3 设置监听端口
            b.localAddress(serverPort);
            // 4 设置通道的参数
            b.option(ChannelOption.SO_KEEPALIVE, true); // 心跳
            b.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
            // 5 装配子通道流水线
            b.childHandler(new HttpServerInitializer());

            // 6 开始绑定服务器
            ChannelFuture channelFuture = b.bind().sync(); // 阻塞至绑定成功
            log.info(" 服务器启动成功，监听端口：" + channelFuture.channel().localAddress());

            // 7 等待通道关闭的异步任务结束
            ChannelFuture closeFuture = channelFuture.channel().closeFuture();
            closeFuture.sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // 8 关闭 eventLoopGroup
            workerLoopGroup.shutdownGracefully();
            bossLoopGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        new NettyEchoServer(NettyConfig.SOCKET_SERVER_PORT).runServer();
    }
}
