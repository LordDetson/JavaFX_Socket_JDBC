package by.bsuir.event;

import by.bsuir.exception.ExceptionHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {
    private static final Logger log = LogManager.getLogger(Client.class);
    private static final int DEFAULT_PORT = 5000;
    private static EventSender sender;
    private static EventReceiver receiver;
    private static Socket socket;

    private Client() { }

    public static void launch() {
        log.info("Client starting... Create a connection...");
        try {
            socket = new Socket("localhost", DEFAULT_PORT);
            if (socket.isConnected()) {
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
            sender.stop();
            receiver.stop();
            socket.close();
        } catch (IOException e) {
            ExceptionHandler.handle(Thread.currentThread(), e);
        }
    }
}
