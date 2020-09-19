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
        String html = "<pre class=\"poly-body\"><span class=\"poly-file\"><a href=\"https://cms.poly.edu.vn/asset-v1:FPOLY+WEB3023+26.02.2020+type@asset+block@lab1-3.zip\">Tải về</a></span>, thực hiện lab1-3.html và điền vào ô trống Thuộc tính <input placeholder=\"?\"> của thẻ video định nghĩa chiều rộng và thuộc tính <input placeholder=\"?\"> hiển thị bảng điều khiển?</pre>";
        Document document = Jsoup.parse(html);
        Element elms = document.selectFirst("pre");
        System.out.println(elms.text());
    }
}
