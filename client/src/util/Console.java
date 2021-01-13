/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.util.Arrays;

/**
 *
 * @author Administrator
 */
public class Console {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static final String CONSOLE_DEBUG_LINE = ANSI_PURPLE + "@~~~> " + ANSI_RESET;

    public static void debug(String content) {
        println(CONSOLE_DEBUG_LINE + ANSI_BLUE + content);
    }

    public static void debug(Exception e) {
        println(CONSOLE_DEBUG_LINE + ANSI_RED + e.toString());
    }

    public static void debug(Object object) {
        println(CONSOLE_DEBUG_LINE + ANSI_BLUE + object.toString());
    }

    public static void debug(Object object, String color) {
        println(CONSOLE_DEBUG_LINE + color + object.toString());
    }

    public static void debug(String content, String color) {
        println(CONSOLE_DEBUG_LINE + color + content);
    }

    public static void debug(String content, Class c) {
        printlnSperator("debug");
        println(CONSOLE_DEBUG_LINE + getClassInfo(c));
        println(CONSOLE_DEBUG_LINE + ANSI_BLUE + content);
    }

    public static void debug(String content, Class c, String color) {
        printlnSperator("debug");
        println(CONSOLE_DEBUG_LINE + getClassInfo(c));
        println(CONSOLE_DEBUG_LINE + color + content);
    }

    public static void debug(String content, Class c, Exception e) {
        printlnSperator("debug");
        println(CONSOLE_DEBUG_LINE + getClassInfo(c));
        println(CONSOLE_DEBUG_LINE + ANSI_BLUE + content);
        println(CONSOLE_DEBUG_LINE + ANSI_RED + e.toString());
    }

    public static void debug(String content, Class c, Exception e, String color) {
        printlnSperator("debug");
        println(CONSOLE_DEBUG_LINE + getClassInfo(c));
        println(CONSOLE_DEBUG_LINE + color + content);
        println(CONSOLE_DEBUG_LINE + ANSI_RED + e.toString());
    }

    public static void debug(Class c, Exception e) {
        printlnSperator("debug");
        println(CONSOLE_DEBUG_LINE + getClassInfo(c));
        println(CONSOLE_DEBUG_LINE + ANSI_RED + e.toString());
    }

    private static String getClassInfo(Class c) {
        String className = c.getSimpleName();
        String packageName = c.getPackage().toString().replace("package ", "");
        String superClass = c.getSuperclass().getSimpleName();
        String interfaceClass = Arrays.toString(c.getInterfaces());
        String formResult = ANSI_PURPLE + "[class:%s]-[package:%s]-[extends:%s]-[interface:%s]";
        return String.format(formResult, className, packageName, superClass, interfaceClass);
    }

    private static void printlnSperator(String name) {
        String sperator = "@~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~>[" + name + "]<~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~@";
        println(ANSI_PURPLE + sperator);
    }

    private static void printlnSperator() {
        String sperator = "@~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~@";
        println(ANSI_PURPLE + sperator);
    }

    private static void println(String text) {
        System.out.println(text + ANSI_RESET);
    }
}
