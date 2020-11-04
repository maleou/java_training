package homework.day05.gateway.handler;

import homework.day05.gateway.config.NettyConfig;
import homework.day05.gateway.filter.HttpRequestFilter;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 所有的业务都在Handler中完成
 * ChannelInboundHandler
 *  处理入站的IO时间，ChannelInboundHandlerAdapter提供入站处理的默认实现
 *
 * @ChannelHandler.Sharable
 *  当前Handler是可被多个pipeline共享的(否则在addLast时会报错)，但是netty不保证Handler的单例，需要Handler自己实现
 */
@ChannelHandler.Sharable
public class NettyEchoServerHandler extends ChannelInboundHandlerAdapter implements HttpRequestFilter {

    private HttpRequest request;

    private static boolean flag = false;

    private NettyEchoServerHandler() {
        synchronized (NettyEchoServerHandler.class) {
            if (!flag) {
                flag = true;
            } else {
                throw new RuntimeException("单例模式被侵犯");
            }
        }
    }

    @Override
    public void filter(FullHttpRequest fullRequest, ChannelHandlerContext ctx) {
        fullRequest.headers().add("nio", NettyConfig.USERNAME);
    }

    private static class LazyHolder {
        // static 非基本类型字段被 final 修饰，会被编译器放到 <clinit> 方法中，该方法由 JVM 保证只执行一次
        static final NettyEchoServerHandler INSTANCE = new NettyEchoServerHandler();
    }

    public static NettyEchoServerHandler getInstance() {
        return LazyHolder.INSTANCE;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("收到消息");
        if (msg instanceof HttpRequest) {
            request = (HttpRequest) msg;
            String uri  = request.uri();
            System.out.println("Uri: " + uri);

            OkHttpClient client = new OkHttpClient();
            Request request =
                    new Request.Builder()
                            .url(uri)
                            .get()
                            .build();
            Call call = client.newCall(request);
            Response response = call.execute();

            FullHttpResponse resp = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                    HttpResponseStatus.OK,
                    Unpooled.wrappedBuffer(response.body().bytes()));

            resp.headers().set(HttpHeaderNames .CONTENT_TYPE, "text/plain");
            resp.headers().set(HttpHeaderNames.CONTENT_LENGTH, resp.content().readableBytes());

            ctx.writeAndFlush(resp);
        }

        if (msg instanceof HttpContent) {
            HttpContent content = (HttpContent) msg;
            ByteBuf buf = content.content();
            String requestUrl = buf.toString(CharsetUtil.UTF_8);
            System.out.println(requestUrl);
            buf.release();
        }
    }

}
