package homework.day05.gateway.backendserver;

import homework.day05.gateway.config.RouteConfig;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer01 {

    public void runServer() throws IOException {
        ServerSocket serverSocket = new ServerSocket(0, 1, InetAddress.getByAddress(new byte[]{127, 0, 0, 1}));
        RouteConfig.putRouteServer("server", RouteConfig.ROUTE_PREFIX + serverSocket.getLocalPort());
        while (true) {
            Socket socket = serverSocket.accept();
            service(socket);
        }
    }

    private static void service(Socket socket) {
        try {
            Thread.sleep(5);
            String body = "hello, server1";
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
            printWriter.println("HTTP/1.1 200 OK");
            printWriter.println("Content-Type:text/html;charset=utf-8");
            printWriter.println("Content-Length:" + body.getBytes().length);
            printWriter.println();
            printWriter.println(body);
            printWriter.close();
            socket.close();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        new HttpServer01().runServer();
    }
}
