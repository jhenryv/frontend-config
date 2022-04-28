package vuce2.mfeconfig.configs;

public final class Strings {
    public static boolean isNullOrWhiteSpace(String value) {
        return value == null || value.trim().length() == 0;
    }
}
