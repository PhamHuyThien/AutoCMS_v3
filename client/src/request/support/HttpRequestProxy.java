package request.support;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Administrator
 */
public class HttpRequestProxy {

    private String proxyHost;
    private int proxyPort;
    private String proxyUserName = null;
    private String proxyPassword = null;

    public HttpRequestProxy(String proxyHost, int proxyPort) {
        this.proxyHost = proxyHost;
        this.proxyPort = proxyPort;
    }

    public HttpRequestProxy(String proxyHost, int proxyPort, String proxyUserName, String proxyPassword) {
        this.proxyHost = proxyHost;
        this.proxyPort = proxyPort;
        this.proxyUserName = proxyUserName;
        this.proxyPassword = proxyPassword;
    }

    public Proxy buildJavaNetProxy() {
        InetSocketAddress proxyLocation = new InetSocketAddress(getProxyHost(), getProxyPort());
        return new Proxy(Proxy.Type.HTTP, proxyLocation);
    }

    public String getProxyHost() {
        return proxyHost;
    }

    public int getProxyPort() {
        return proxyPort;
    }

    public String getProxyUserName() {
        return proxyUserName;
    }

    public String getProxyPassword() {
        return proxyPassword;
    }

    @Override
    public String toString() {
        return "Proxy{" + "proxyHost=" + proxyHost + ", proxyPort=" + proxyPort + ", proxyUserName=" + proxyUserName + ", proxyPassword=" + proxyPassword + '}';
    }

 
}
