package ru.solomka.guard.utils;

import java.util.Arrays;

public class GLogger {

    public static void info(Object ...values) {
        Arrays.stream(values).forEach(System.out::println);
    }



}
