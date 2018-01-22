package one.xcorp.booklibrary.core;

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

    private Utils() { /* do nothing */ }
}
