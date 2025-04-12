package org.sopt.utils;

public class IdGenerator {

    private static int id = 0;

    public static long generateId() {
        return id++;
    }

}
