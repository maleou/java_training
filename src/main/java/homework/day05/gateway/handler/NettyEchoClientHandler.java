package homework.day05.gateway.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponse;

public class NettyEchoClientHandler extends ChannelInboundHandlerAdapter {

    private static volatile NettyEchoClientHandler instance;

    private NettyEchoClientHandler() {
    }

    public static NettyEchoClientHandler getInstance() {
        if (instance == null) {
            synchronized (NettyEchoClientHandler.class) {
                if (instance == null) {
                    instance = new NettyEchoClientHandler();
                }
            }
        }
        return instance;
    }

    /**
     * 出站
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof HttpResponse) {
            HttpResponse response = (HttpResponse) msg;
            System.out.println("CONTENT_TYPE:" + response.headers().get(HttpHeaderNames.CONTENT_TYPE));
        }

        if (msg instanceof HttpContent) {
            HttpContent content = (HttpContent) msg;
            ByteBuf buf = content.content();
            System.out.println(buf.toString(io.netty.util.CharsetUtil.UTF_8));
            buf.release();
        }

    }
}
