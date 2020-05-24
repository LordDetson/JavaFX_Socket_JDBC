package by.bsuir.event;

import by.bsuir.exception.ExceptionHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class EventReceiver implements Runnable, EventObservable {
    private static final Logger log = LogManager.getLogger(EventReceiver.class);
    private static final List<EventObserver> observers = new ArrayList<>();
    private final ObjectInputStream in;
    private final AtomicBoolean inProgress = new AtomicBoolean(false);

    public EventReceiver(ObjectInputStream in) {
        this.in = in;
        log.info("Receiver initialized");
    }

    @Override
    public void run() {
        log.info("Receiver listens to events...");
        try {
            inProgress.set(true);
            while (inProgress.get()) {
                Object object = in.readObject();
                if (object instanceof Event) {
                    log.info("Receive " + object);
                    notifyObservers((Event) object);
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            try {
                stop();
            } catch (IOException ex) {
                ExceptionHandler.handle(Thread.currentThread(), e);
            }
        }
    }

    @Override
    public void addObserver(EventObserver eventObserver) {
        observers.add(eventObserver);
    }

    @Override
    public void removeObserver(EventObserver eventObserver) {
        observers.remove(eventObserver);
    }

    @Override
    public void notifyObservers(Event event) {
        observers.forEach(eventObserver -> eventObserver.handleEvent(event));
    }

    public void stop() throws IOException {
        log.info("Receiver stopped");
        inProgress.set(false);
        in.close();
    }
}
