/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.io.IOException;
import request.HttpRequest;
import request.support.HttpRequestHeader;

/**
 *
 * @author Administrator
 */
public class TestPostSolution {
    public static void main(String[] args) throws IOException {
        String cookie = "Cookie: _ga=GA1.3.947349041.1600572821; _gid=GA1.3.632357214.1600572821; experiments_is_enterprise=false; edxloggedin=true; csrftoken=hIcWSdFZYRDTgjD8OQQpG4IVu4kKq4Y4pUbQ1qzptvBxfKlrOE8IOaE4ahT15ZyC; sessionid=\"1|klbo43gu9lbwx1agh3brvldzwdl6554h|YayltJMNWv31|IjUzYWYyOGUxNzJlOTAwN2YwYzM3MzdjNzczZjEwM2Y2MmJhNTAzZDM4NjZhMGQxYjA5YjIyMzYzNTliZTBkOWIi:1kJq77:pHomqq9NyJxW-_2Z3qwZe1CUCQQ\"; edx-user-info=\"{\\\"username\\\": \\\"thienttps08209\\\"\\054 \\\"version\\\": 1\\054 \\\"enrollmentStatusHash\\\": \\\"549b9eb6a57db2cafb39828d02a31366\\\"\\054 \\\"header_urls\\\": {\\\"learner_profile\\\": \\\"https://cms.poly.edu.vn/u/thienttps08209\\\"\\054 \\\"resume_block\\\": \\\"https://cms.poly.edu.vn/dashboard\\\"\\054 \\\"logout\\\": \\\"https://cms.poly.edu.vn/logout\\\"\\054 \\\"account_settings\\\": \\\"https://cms.poly.edu.vn/account/settings\\\"}}\"";
        String crfsToken = "hIcWSdFZYRDTgjD8OQQpG4IVu4kKq4Y4pUbQ1qzptvBxfKlrOE8IOaE4ahT15ZyC";

        String url = "https://cms.poly.edu.vn/courses/course-v1:FPOLY+WEB3023+26.02.2020/xblock/block-v1:FPOLY+WEB3023+26.02.2020+type@problem+block@a5ca656461974dd48593d43fee9ef3af/handler/xmodule_handler/problem_check";
        String paramPost = "input_a5ca656461974dd48593d43fee9ef3af_4_1%5B%5D=choice_0&input_a5ca656461974dd48593d43fee9ef3af_4_1%5B%5D=choice_2&input_a5ca656461974dd48593d43fee9ef3af_6_1=choice_1&input_a5ca656461974dd48593d43fee9ef3af_7_1=choice_2&input_a5ca656461974dd48593d43fee9ef3af_8_1=choice_1&input_a5ca656461974dd48593d43fee9ef3af_9_1=choice_3&input_a5ca656461974dd48593d43fee9ef3af_10_1=choice_1&input_a5ca656461974dd48593d43fee9ef3af_11_1=choice_1&input_a5ca656461974dd48593d43fee9ef3af_15_1=choice_2&input_a5ca656461974dd48593d43fee9ef3af_16_1=choice_1&input_a5ca656461974dd48593d43fee9ef3af_2_1=vien%2Cdiem&input_a5ca656461974dd48593d43fee9ef3af_13_1=box-shadow%2Cduong-vien&input_a5ca656461974dd48593d43fee9ef3af_14_1=inset%2Ccolor%2Cv-shadow&input_a5ca656461974dd48593d43fee9ef3af_3_1=&input_a5ca656461974dd48593d43fee9ef3af_5_1=&input_a5ca656461974dd48593d43fee9ef3af_12_1=";
//        paramPost = "";
        
        HttpRequestHeader httpRequestHeader = new HttpRequestHeader();
        httpRequestHeader.add("cookie", cookie);
        httpRequestHeader.add("X-CSRFToken", crfsToken);
        httpRequestHeader.add("Referer", url);
        
        HttpRequest httpRequest = new HttpRequest(url, paramPost, httpRequestHeader);
        System.out.println(httpRequest.getResponseHTML());
        
    }
}
