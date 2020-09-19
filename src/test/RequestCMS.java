/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import request.HttpRequest;

/**
 *
 * @author Administrator
 */
public class RequestCMS {
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, KeyManagementException {
        TrustManager[] trustAllCerts = new TrustManager[]{
           new X509TrustManager() {
               @Override
               public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                   return new X509Certificate[0];
               }

               @Override
               public void checkClientTrusted(
                       java.security.cert.X509Certificate[] certs, String authType) {
               }

               @Override
               public void checkServerTrusted(
                       java.security.cert.X509Certificate[] certs, String authType) {
               }
           }
       };

       try {
           SSLContext sc = SSLContext.getInstance("SSL");
           sc.init(null, trustAllCerts, new java.security.SecureRandom());
           HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
       } catch (GeneralSecurityException e) {
       }
       
        HttpRequest get = new HttpRequest("https://cms.poly.edu.vn");
        get.connect();
        System.out.println(get.getResponseStatus());
    }
}
