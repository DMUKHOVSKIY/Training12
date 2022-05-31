import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        HttpServer httpServer = HttpServer.create(new InetSocketAddress("localhost", 8080), 0);
        httpServer.createContext("/calc", new TestHandler());
        httpServer.start();
    }

    public static class TestHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String requestBody = exchange.getRequestURI().getQuery();
            String[] str = requestBody.split("&");
            int[] strInt = new int[3];
            strInt[0] = Integer.parseInt(str[0]);
            strInt[1] = Integer.parseInt(str[1]);
            int result = 0;
            if (str[2].equals("+"))
                result = strInt[0] + strInt[1];
            else if (str[2].equals("-"))
                result = strInt[0] - strInt[1];
            else if (str[2].equals("*"))
                result = strInt[0] * strInt[1];
            else if (str[2].equals("/"))
                result = strInt[0] / strInt[1];
            String r = "result = "+ result;
            exchange.sendResponseHeaders(200,0);
            exchange.getResponseBody().write(r.getBytes());
            exchange.getResponseBody().close();
        }
    }
}
