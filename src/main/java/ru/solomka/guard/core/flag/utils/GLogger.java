package ru.solomka.guard.core.flag.utils;

import java.util.Arrays;

public class GLogger {

    public static void info(Object ...values) {
        Arrays.stream(values).forEach(System.out::println);
    }



}
