package homework.day05.gateway.backendserver;

import homework.day05.gateway.config.RouteConfig;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer02 {

    public void runServer() throws IOException {
        ServerSocket serverSocket = new ServerSocket(0, 1, InetAddress.getByAddress(new byte[]{127, 0, 0, 1}));
        RouteConfig.putRouteServer("server", RouteConfig.ROUTE_PREFIX + serverSocket.getLocalPort());
        while (true) {
            Socket socket = serverSocket.accept();
            new Thread(() -> {
                service(socket);
            }).start();
        }
    }

    private static void service(Socket socket) {
        try {
            Thread.sleep(20);
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
            printWriter.println("HTTP/1.1 200 OK");
            printWriter.println("Content-Type:text/html;charset=utf-8");

            String body = "hello, server2";
            printWriter.println("Content-Length:" + body.getBytes().length);
            printWriter.println();
            printWriter.println(body);
            printWriter.close();
            socket.close();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }
}
