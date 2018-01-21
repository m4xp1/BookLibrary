package one.xcorp.booklibrary.core.data.book;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.Serializable;
import java.util.UUID;

@SuppressWarnings({"WeakerAccess", "unused", "UnusedReturnValue"})
public class Book implements Serializable {

    private @NonNull String uuid;
    private @Nullable String illustration;
    private @NonNull String author;
    private @NonNull String name;
    private int year;
    private int pages;
    private @Nullable String excerpt;

    public Book(@NonNull String author, @NonNull String name, int year, int pages) {
        this.uuid = UUID.randomUUID().toString();
        this.author = author;
        this.name = name;
        this.year = year;
        this.pages = pages;
    }

    @NonNull public String getUuid() {
        return uuid;
    }

    @Nullable public String getIllustration() {
        return illustration;
    }

    public Book setIllustration(@Nullable String illustration) {
        this.illustration = illustration;
        return this;
    }

    @NonNull public String getAuthor() {
        return author;
    }

    public Book setAuthor(@NonNull String author) {
        this.author = author;
        return this;
    }

    @NonNull public String getName() {
        return name;
    }

    public Book setName(@NonNull String name) {
        this.name = name;
        return this;
    }

    public int getYear() {
        return year;
    }

    public Book setYear(int year) {
        this.year = year;
        return this;
    }

    public int getPages() {
        return pages;
    }

    public Book setPages(int pages) {
        this.pages = pages;
        return this;
    }

    @Nullable public String getExcerpt() {
        return excerpt;
    }

    public Book setExcerpt(@Nullable String excerpt) {
        this.excerpt = excerpt;
        return this;
    }

    @Override public boolean equals(Object object) {
        return this == object ||
                !(object == null || getClass() != object.getClass()) &&
                        uuid.equals(((Book) object).uuid);
    }

    @Override public int hashCode() {
        return uuid.hashCode();
    }
}