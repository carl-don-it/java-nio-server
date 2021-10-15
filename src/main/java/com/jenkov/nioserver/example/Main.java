package com.jenkov.nioserver.example;

import com.jenkov.nioserver.IMessageProcessor;
import com.jenkov.nioserver.Message;
import com.jenkov.nioserver.Server;
import com.jenkov.nioserver.http.HttpMessageReaderFactory;

import java.io.IOException;

/**
 * Created by jjenkov on 19-10-2015.
 */
public class Main {

    public static void main(String[] args) throws IOException {

        String httpResponse = "HTTP/1.1 200 OK\r\n" +
                "Content-Length: 38\r\n" +
                "Content-Type: text/html\r\n" +
                "Set-Cookie: __jdu=16342036184941014939424;path=/;domain=localhost;expires=Tue, 12 Apr 2022 09:28:15 GMT\r\n" +
                "\r\n" +
                "<html><body>Hello World!</body></html>";

        byte[] httpResponseBytes = httpResponse.getBytes("UTF-8");

        IMessageProcessor messageProcessor = (request, writeProxy) -> {
            System.out.println("Message Received from socket: " + request.socketId + "\n" + new String(request.sharedArray, request.offset, request.length));

            Message response = writeProxy.getMessage();
            response.socketId = request.socketId;
            response.writeToMessage(httpResponseBytes);

            writeProxy.enqueue(response);
        };

        int tcpPort = 9999;
        Server server = new Server(tcpPort, new HttpMessageReaderFactory(), messageProcessor);

        server.start();
        System.out.println("server start at "+tcpPort+" !!!!");
    }


}
