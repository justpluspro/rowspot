package org.qwli.rowspot.util;

/**
 * @author qwli7
 * @date 2021/1/4 17:17
 * 功能：Context
 **/
public class EnvironmentContext {

    private static final ThreadLocal<Boolean> AUTH_TOKEN = new ThreadLocal<>();

    public static boolean isAuthenticated() {
        return AUTH_TOKEN.get() != null && AUTH_TOKEN.get();
    }

    public static void setAuthenticated(Boolean isAuthenticated) {
        AUTH_TOKEN.set(isAuthenticated);
    }

    public static void removeAll() {
        AUTH_TOKEN.remove();
    }
}
