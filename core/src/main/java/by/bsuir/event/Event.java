package by.bsuir.event;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.StringJoiner;

public class Event implements Serializable {
    public enum EventType {
        CREATED, UPDATED, DELETED, REFRESH, LOAD_DATA
    }

    private Serializable component;
    private Collection<Serializable> components = new ArrayList<>();
    private ComponentType componentType;
    private EventType eventType;

    public Serializable getComponent() {
        return component;
    }

    public void setComponent(Serializable component) {
        this.component = component;
    }

    public Collection<Serializable> getComponents() {
        return components;
    }

    public void setComponents(Collection<? extends Serializable> components) {
        this.components.addAll(components);
    }

    public ComponentType getComponentType() {
        return componentType;
    }

    public void setComponentType(ComponentType componentType) {
        this.componentType = componentType;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType crudType) {
        this.eventType = crudType;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Event.class.getSimpleName() + "[", "]")
                .add("component=" + component)
                .add("components=" + components)
                .add("componentType=" + componentType)
                .add("eventType=" + eventType)
                .toString();
    }
}
