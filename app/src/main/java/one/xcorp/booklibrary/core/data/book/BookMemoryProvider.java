package one.xcorp.booklibrary.core.data.book;


import java.util.ArrayList;
import java.util.List;

import one.xcorp.booklibrary.core.data.IDataProvider;

public class BookMemoryProvider implements IDataProvider<Book> {

    @Override public List<Book> list() {
        // TODO maXp: fix test impl
        List<Book> books = new ArrayList<>();

        books.add(new Book(
                "Михаил Зыгарь",
                "Империя должна умереть. История русских революций в лицах. 1900-1917",
                2017,
                910));
        books.add(new Book(
                "Дэн Браун",
                "Происхождение",
                2017,
                576));
        books.add(new Book(
                "Денис Савельев, Евгения Крюкова",
                "100+ хаков для интернет-маркетологов. Как получить трафик и конвертировать его в продажи",
                2018,
                304));
        books.add(new Book(
                "Олеся Куприн",
                "Вкусные истории. Душевные рецепты для теплой компании",
                2016,
                192));
        books.add(new Book(
                "Э. Лофтус",
                "Маленькая книга. Мечта гурмана",
                2017,
                288));

        ArrayList result = new ArrayList<>(books);
        result.addAll(books);
        result.addAll(books);
        result.addAll(books);
        result.addAll(books);
        result.addAll(books);
        result.addAll(books);
        return result;
    }

    @Override public void add(Book item) {

    }

    @Override public void remove(Book item) {

    }

    @Override public void update(Book item) {

    }
}