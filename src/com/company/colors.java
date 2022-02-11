package com.company;

public class colors {

    public static final String reset = "\u001b[0m";

    public static final String black (String input) {
        return "\u001b[30m" + input + reset;
    }

    public static final String red (String input) {
        return "\u001b[31m" + input + reset;
    }

    public static final String green (String input) {
        return "\u001b[32m" + input + reset;
    }

    public static final String yellow (String input) {
        return "\u001b[33m" + input + reset;
    }

    public static final String blue (String input) {
        return "\u001b[34m" + input + reset;
    }

    public static final String magenta (String input) {
        return "\u001b[35m" + input + reset;
    }

    public static final String cyan (String input) {
        return "\u001b[36m" + input + reset;
    }

    public static final String white (String input) {
        return "\u001b[37m" + input + reset;
    }
}
