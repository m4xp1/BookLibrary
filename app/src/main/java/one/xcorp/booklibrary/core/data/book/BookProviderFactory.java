package one.xcorp.booklibrary.core.data.book;

import one.xcorp.booklibrary.core.data.IDataProvider;

public class BookProviderFactory {

    private static String providerClass;

    public static void setDefault(Class<? extends IDataProvider<Book>> providerClass) {
        BookProviderFactory.providerClass = providerClass.getName();
    }

    public static IDataProvider<Book> getDefault() {
        if (BookMemoryProvider.class.getName().equals(providerClass)) {
            return BookMemoryProvider.getInstance();
        }

        throw new IllegalStateException("Unknown provider.");
    }

    private BookProviderFactory() { /* do nothing */ }
}
