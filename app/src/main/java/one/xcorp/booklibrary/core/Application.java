package one.xcorp.booklibrary.core;

import one.xcorp.booklibrary.core.data.book.BookMemoryProvider;
import one.xcorp.booklibrary.core.data.book.BookProviderFactory;

public class Application extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();

        BookProviderFactory.setDefault(BookMemoryProvider.class);
    }
}
