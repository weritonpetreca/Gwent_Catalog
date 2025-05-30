package com.petreca.Gwent_Catalog.util;

public class ConsoleColors {

    //Reset
    public static final String RESET = "\033[0m";

    //Cores regulares
    public static final String BLACK = "\033[0;30m";
    public static final String RED = "\033[0;31m";
    public static final String GREEN = "\033[0;32m";
    public static final String YELLOW = "\033[0;33m";
    public static final String BLUE = "\033[0;34m";
    public static final String PURPLE = "\033[0;35m";
    public static final String CYAN = "\033[0;36m";
    public static final String WHITE = "\033[0;37m";

    //Cores em negrito
    public static final String BOLD_BLACK = "\033[1;30m";
    public static final String BOLD_RED = "\033[1;31m";
    public static final String BOLD_GREEN = "\033[1;32m";
    public static final String BOLD_YELLOW = "\033[1;33m";
    public static final String BOLD_BLUE = "\033[1;34m";
    public static final String BOLD_PURPLE = "\033[1;35m";
    public static final String BOLD_CYAN = "\033[1;36m";
    public static final String BOLD_WHITE = "\033[1;37m";;

    //Metodos utilitários
    public static String colorize(String text, String color) {
        return color + text + RESET;
    }

    public static String success(String text) {
        return colorize(text, BOLD_GREEN);
    }

    public static String error(String text) {
        return colorize(text, BOLD_RED);
    }

    public static String info(String text) {
        return colorize(text, BOLD_BLUE);
    }

    public static String warning(String text) {
        return colorize(text, BOLD_YELLOW);
    }

    public static String header(String text) {
        return colorize(text, BOLD_CYAN);
    }
}
