/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author Administrator
 */
public class TestParseJsoup {
    public static void main(String[] args) {
        String html = "ssDgssbdfhbvzbzxbxc";
        Document document = Jsoup.parse(html);
        Element elms = document.selectFirst("a[class='b']");
        System.out.println(elms);
    }
}
