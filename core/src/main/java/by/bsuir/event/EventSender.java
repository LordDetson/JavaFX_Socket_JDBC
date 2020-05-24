package by.bsuir.event;

import by.bsuir.exception.ExceptionHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;

public class EventSender {
    private static final Logger log = LogManager.getLogger(EventSender.class);
    private final ObjectOutputStream out;

    public EventSender(ObjectOutputStream out) {
        this.out = out;
        log.info("Sender initialized");
    }

    public void stop() throws IOException {
        log.info("Sender stopped");
        out.close();
    }

    public EventBuilder create() {
        return new EventBuilder();
    }

    public class EventBuilder {
        private Event event;

        private EventBuilder() {
            event = new Event();
        }

        public EventBuilder component(Serializable component) {
            event.setComponent(component);
            return this;
        }

        public EventBuilder components(Collection<? extends Serializable> component) {
            event.setComponents(component);
            return this;
        }

        public EventBuilder componentType(ComponentType componentType) {
            event.setComponentType(componentType);
            return this;
        }

        public EventBuilder crudType(Event.EventType crudType) {
            event.setEventType(crudType);
            return this;
        }

        public void send() {
            try {
                out.writeObject(event);
                out.flush();
                log.info("Send " + event);
            } catch (IOException e) {
                ExceptionHandler.handle(Thread.currentThread(), e);
            }
        }
    }
}
