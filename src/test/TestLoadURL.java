/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import org.http.simple.JHttp;

/**
 *
 * @author Administrator
 */
public class TestLoadURL {
    public static void main(String[] args) {
        String body = JHttp.get("https://cms.poly.edu.vn/").userAgent().body();
        System.out.println(body);
    }
}
