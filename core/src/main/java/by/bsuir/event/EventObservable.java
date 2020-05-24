package by.bsuir.event;

public interface EventObservable {
    void addObserver(EventObserver eventObserver);

    void removeObserver(EventObserver eventObserver);

    void notifyObservers(Event event);
}
