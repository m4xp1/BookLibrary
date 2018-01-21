package one.xcorp.booklibrary.core.data.book;

import android.support.annotation.NonNull;

@SuppressWarnings({"WeakerAccess", "unused"})
public class Book {

    private @NonNull String author;
    private @NonNull String name;
    private int year;
    private int pages;

    public Book(@NonNull String author, @NonNull String name, int year, int pages) {
        this.author = author;
        this.name = name;
        this.year = year;
        this.pages = pages;
    }

    @NonNull public String getAuthor() {
        return author;
    }

    public void setAuthor(@NonNull String author) {
        this.author = author;
    }

    @NonNull public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }
}