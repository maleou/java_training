package homework.day05.gateway;

import homework.day05.gateway.backendserver.HttpServer01;
import homework.day05.gateway.backendserver.HttpServer02;
import homework.day05.gateway.backendserver.HttpServer03;
import homework.day05.gateway.config.NettyConfig;
import homework.day05.gateway.config.RouteConfig;
import homework.day05.gateway.handler.NettyEchoClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;

public class NettyEchoClient {

    private int serverPort;
    private String serverIp;
    Bootstrap b = new Bootstrap();

    public NettyEchoClient(String ip, int port) {
        this.serverPort = port;
        this.serverIp = ip;
    }

    public void runClient() {
        NioEventLoopGroup workerLoopGroup = new NioEventLoopGroup();

        try {
            b.group(workerLoopGroup);
            b.channel(NioSocketChannel.class);
            b.remoteAddress(serverIp, serverPort);
            b.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
            b.handler(new ChannelInitializer<SocketChannel>() {

                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    socketChannel.pipeline()
                            // 发送 httprequest
                            .addLast(new HttpRequestEncoder())
                            // 接收 httpresponse
                            .addLast(new HttpResponseDecoder())
                            .addLast(NettyEchoClientHandler.getInstance());
                }
            });

            ChannelFuture connectFuture = b.connect();
            connectFuture.addListener((ChannelFuture futureListener) -> {
                if (futureListener.isSuccess()) {
                    System.out.println("Client连接成功");
                } else {
                    System.out.println("Client连接失败");
                }
            });
            connectFuture.sync();
            Channel channel = connectFuture.channel();
            Scanner scanner = new Scanner(System.in);

            List<String> serverList = RouteConfig.ROUTE_MAP.keySet().stream().sorted().collect(Collectors.toList());

            System.out.println("请输入你要调用的网关：\r\n" + serverList);
            while (scanner.hasNext()) {
                String next = scanner.next();
                if ("exit".equals(next)) {
                    channel.closeFuture().sync();
                    System.exit(0);
                }
                if (serverList.contains(next)) {
                    // 伪路由
                    Random random = new Random();
                    String uri = RouteConfig.ROUTE_MAP.get(next).get(random.nextInt(RouteConfig.ROUTE_MAP.get(next).size()));
                    System.out.println(uri);
                    String msg = "hello";
                    DefaultFullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET,
                            new URI(uri).toASCIIString(), Unpooled.wrappedBuffer(msg.getBytes(StandardCharsets.UTF_8)));
                    // 构建http请求
                    request.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
                    request.headers().set(HttpHeaderNames.CONTENT_LENGTH, request.content().readableBytes());


                    channel.writeAndFlush(request);
//                    channel.closeFuture().sync();
                } else {
                    System.out.println("调用网关不存在");
                }
                System.out.println("请输入你要调用的网关：");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            workerLoopGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        new Thread(() -> {
            try {
                new HttpServer01().runServer();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                new HttpServer02().runServer();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                new HttpServer03().runServer();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
        new NettyEchoClient("127.0.0.1", NettyConfig.SOCKET_SERVER_PORT).runClient();
    }
}
