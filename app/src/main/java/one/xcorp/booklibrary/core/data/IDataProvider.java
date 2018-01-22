package one.xcorp.booklibrary.core.data;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface IDataProvider<T> {

    Single<List<T>> list();

    Completable add(T item);

    Completable delete(T item);

    Completable update(T item);
}