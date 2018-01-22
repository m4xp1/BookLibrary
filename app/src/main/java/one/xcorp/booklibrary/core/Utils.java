package one.xcorp.booklibrary.core;

import com.annimon.stream.function.Function;

import java.util.Collections;
import java.util.List;

public class Utils {

    @SuppressWarnings("SameParameterValue")
    public static String validString(String value, String defaultValue) {
        return value == null || value.isEmpty() ? defaultValue : value;
    }

    public static Integer castToInt(String value) {
        try {
            return Integer.valueOf(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static <T, R extends Comparable<? super R>> List<T> sort(
            List<T> list, Function<? super T, ? extends R> function) {
        Collections.sort(list, (t1, t2) -> {
            final R u1 = function.apply(t1);
            final R u2 = function.apply(t2);
            return u1.compareTo(u2);
        });

        return list;
    }

    private Utils() { /* do nothing */ }
}
