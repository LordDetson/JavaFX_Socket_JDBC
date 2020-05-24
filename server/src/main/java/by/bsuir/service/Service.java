package by.bsuir.service;

import by.bsuir.event.EventObserver;

import java.util.Collection;
import java.util.List;

public interface Service<T> extends EventObserver {
    List<T> getAll();

    void add(T domain);

    void update(T domain);

    void deleteAll(Collection<? extends T> domains);
}
