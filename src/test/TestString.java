/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

/**
 *
 * @author Administrator
 */
public class TestString {
    public static void main(String[] args) {
        System.out.println(makeUpValue("dsfsj%2c"));
    }
    private static String makeUpValue(String value) {
        int len = value.length();
        if (value.toCharArray()[len - 1] == '&') {
            return value.substring(0, len - 1);
        }
        if (value.indexOf("%2c") == len - 3) {
            return value.substring(0, len - 3);
        }
        return value;
    }
}
