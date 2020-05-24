package by.bsuir.event;

import by.bsuir.exception.ExceptionHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static final Logger log = LogManager.getLogger(Server.class);
    private static final int DEFAULT_PORT = 5000;
    private static EventSender sender;
    private static EventReceiver receiver;
    private static Socket socket;
    private static ServerSocket serverSocket;

    private Server() { }

    public static void launch() {
        log.info("Server starting... Waiting for a connection...");
        try {
            serverSocket = new ServerSocket(DEFAULT_PORT);
            socket = serverSocket.accept();
            if (socket.isConnected()) {
                log.info("Got a connection");
                sender = new EventSender(new ObjectOutputStream(socket.getOutputStream()));
                receiver = new EventReceiver(new ObjectInputStream(socket.getInputStream()));
                Thread receiverThread = new Thread(receiver);
                receiverThread.setName("Receiver thread");
                receiverThread.start();
            } else {
                log.info("No connection");
            }
        } catch (Exception e) {
            ExceptionHandler.handle(Thread.currentThread(), e);
        } finally {
            stop();
        }
    }

    public static EventSender getSender() {
        return sender;
    }

    public static EventReceiver getReceiver() {
        return receiver;
    }

    public static void stop() {
        try {
            while (!socket.isConnected()) {
                sender.stop();
                receiver.stop();
                socket.close();
                serverSocket.close();
            }
        } catch (IOException e) {
            ExceptionHandler.handle(Thread.currentThread(), e);
        }
    }
}
