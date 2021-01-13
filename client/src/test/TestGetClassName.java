/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import util.Console;


/**
 *
 * @author Administrator
 */
public class TestGetClassName {
    public static void main(String[] args) {
        a(TestGetClassName.class);
    }
    
    public static void a(Class clazz){
        System.out.println(clazz.getSimpleName());
        System.out.println(clazz.getPackage());
        
        System.out.println(clazz.getMethods()[1].getName());
    }
}
