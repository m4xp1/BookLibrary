package one.xcorp.booklibrary.core.data;

import java.util.Collection;

public interface IDataProvider<T> {

    Collection<? extends T> list();

    void add(T item);

    void remove(T item);

    void update(T item);
}