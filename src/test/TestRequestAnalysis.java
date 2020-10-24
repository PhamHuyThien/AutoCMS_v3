/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import function.Function;
import java.io.IOException;
import object.cms.CMSAccount;
import object.cms.InfoAndressIP;
import request.HttpRequest;

/**
 *
 * @author Administrator
 */
public class TestRequestAnalysis {
    public static void main(String[] args) {    
        CMSAccount cMSAccount = new CMSAccount();
        cMSAccount.setUserName("thiendepzaii");
        followMe(cMSAccount, Function.getInfoAndressIP());
    }
    public static void followMe(CMSAccount cmsAccount, InfoAndressIP infoAndressIP) {
        final String url = "http://localhost/cmspoly/api/";
        final String param = "t=new-uses&user=%s&ip=%s&city=%s&region=%s&country=%s&timezone=%s";
        String parampost = String.format(param, 
                Function.URLEncoder(cmsAccount.getUserName()), 
                Function.URLEncoder(infoAndressIP.getIp()), 
                Function.URLEncoder(infoAndressIP.getCity()), 
                Function.URLEncoder(infoAndressIP.getRegion()), 
                Function.URLEncoder(infoAndressIP.getCountry()), 
                Function.URLEncoder(infoAndressIP.getTimezone())
        );
        System.out.println(parampost);
        HttpRequest httpRequest = new HttpRequest(url, parampost);
        try {
            httpRequest.connect();
            System.out.println(httpRequest.getResponseHTML());
        } catch (IOException ex) {
        }
    }
}
