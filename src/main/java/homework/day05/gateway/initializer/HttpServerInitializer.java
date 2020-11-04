package homework.day05.gateway.initializer;

import homework.day05.gateway.handler.NettyEchoServerHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.codec.http.HttpServerCodec;

public class HttpServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        channel.pipeline()
//                .addLast(new HttpServerCodec())
//                .addLast("httpAggregator", new HttpObjectAggregator(512 * 1024))
//                .addLast(NettyEchoServerHandler.getInstance())
                // 发送 httpresponse
                .addLast(new HttpResponseEncoder())
                // 接收 httprequest
                .addLast(new HttpRequestDecoder())
                .addLast(NettyEchoServerHandler.getInstance());
    }
}
