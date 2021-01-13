/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.io.IOException;
import request.HttpRequest;


public class TestUpdateCourse {
    public static void main(String[] args) throws IOException {
        HttpRequest httpRequest = new HttpRequest("http://localhost/server/api/index.php", "t=get-course&id_course=course04&total_quiz=10");
        System.out.println(httpRequest.getResponseHTML());
    }
}
