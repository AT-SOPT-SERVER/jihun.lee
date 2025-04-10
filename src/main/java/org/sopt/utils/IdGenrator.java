package org.sopt.utils;

public class IdGenrator {

    private static int id = 0;

    public static long generateId() {
        return id++;
    }

}
