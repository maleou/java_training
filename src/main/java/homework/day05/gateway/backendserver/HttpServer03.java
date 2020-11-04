package homework.day05.gateway.backendserver;

import homework.day05.gateway.config.RouteConfig;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServer03 {

    public void runServer() throws IOException{
        ExecutorService executorService = Executors.newFixedThreadPool(40);
        ServerSocket serverSocket = new ServerSocket(0, 1, InetAddress.getByAddress(new byte[]{127, 0, 0, 1}));
        RouteConfig.putRouteServer("server", RouteConfig.ROUTE_PREFIX + serverSocket.getLocalPort());
        while (true) {
            try {
                final Socket socket = serverSocket.accept();
                executorService.execute(() -> service(socket));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void service(Socket socket) {
        try {
            Thread.sleep(20);
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
            printWriter.println("HTTP/1.1 200 OK");
            printWriter.println("Content-Type:text/html;charset=utf-8");
            String body = "hello, server3";
            printWriter.println("Content-Length:" + body.getBytes().length);
            printWriter.println();
            printWriter.write(body);
            printWriter.close();
            socket.close();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        new HttpServer03().runServer();
    }
}
