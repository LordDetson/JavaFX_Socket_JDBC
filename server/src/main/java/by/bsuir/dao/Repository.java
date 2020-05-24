package by.bsuir.dao;

import java.util.Collection;
import java.util.List;

public interface Repository<T> {
    List<T> getAll();

    void create(T domain);

    void update(T domain);

    void deleteAll(Collection<? extends T> domains);
}
