package org.sopt.utils;

public class IdGenrator {

    private static int id = 1;

    public static int generateId() {
        return id++;
    }

}
