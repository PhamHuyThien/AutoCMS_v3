//code by ThienDepZaii
package request;

import java.io.BufferedReader;
import request.support.HttpRequestProxy;
import request.support.HttpRequestHeader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.util.HashMap;
import javax.net.ssl.HttpsURLConnection;

public class HttpRequest {

    String url = null;
    String paramPost = null;
    HttpRequestHeader httpRequestHeader = null;
    HttpRequestProxy httpRequestProxy = null;

    URL _url = null;
    HttpURLConnection httpUrlConnection = null;
    private InputStream is = null;
    OutputStream os = null;

    private static int HTTPREQUEST_TIMEOUT = 300000;
    private static int HTTPREQUEST_READTIMEOUT = 300000;

    private boolean isConnected = false;
    private int httpRequestResponseCode;
    private String httpRequestResponseMessage;

    public HttpRequest(String url) {
        this.url = url;
    }

    public HttpRequest(String url, String paramPost) {
        this.url = url;
        this.paramPost = paramPost;
    }

    public HttpRequest(String url, HttpRequestHeader httpRequestHeader) {
        this.url = url;
        this.httpRequestHeader = httpRequestHeader;
    }

    public HttpRequest(String url, HttpRequestProxy httpRequestProxy) {
        this.url = url;
        this.httpRequestProxy = httpRequestProxy;
    }

    public HttpRequest(String url, String paramPost, HttpRequestHeader httpRequestHeader) {
        this.url = url;
        this.paramPost = paramPost;
        this.httpRequestHeader = httpRequestHeader;
    }

    public HttpRequest(String url, String paramPost, HttpRequestProxy httpRequestProxy) {
        this.url = url;
        this.paramPost = paramPost;
        this.httpRequestProxy = httpRequestProxy;
    }

    public HttpRequest(String url, HttpRequestHeader httpRequestHeader, HttpRequestProxy httpRequestProxy) {
        this.url = url;
        this.httpRequestHeader = httpRequestHeader;
        this.httpRequestProxy = httpRequestProxy;
    }

    public HttpRequest(String url, String paramPost, HttpRequestHeader httpRequestHeader, HttpRequestProxy httpRequestProxy) {
        this.url = url;
        this.paramPost = paramPost;
        this.httpRequestHeader = httpRequestHeader;
        this.httpRequestProxy = httpRequestProxy;
    }

    public void connect() throws IOException {
        if (isConnected) {
            return;
        }
        isConnected = !isConnected;
        init();
        _url = new URL(url);
        if (getProtocol().equals("https")) {
            httpUrlConnection = (HttpsURLConnection) _url.openConnection(isProxy() ? httpRequestProxy.buildJavaNetProxy() : Proxy.NO_PROXY);
        } else {
            httpUrlConnection = (HttpURLConnection) _url.openConnection(isProxy() ? httpRequestProxy.buildJavaNetProxy() : Proxy.NO_PROXY);
        }

        httpUrlConnection.setRequestMethod(getMethod());
        httpUrlConnection.setConnectTimeout(HTTPREQUEST_TIMEOUT);
        httpUrlConnection.setReadTimeout(HTTPREQUEST_READTIMEOUT);

        String[][] requestHeader = httpRequestHeader.toArray();
        if (requestHeader != null) {
            for (String[] header : requestHeader) {
                httpUrlConnection.setRequestProperty(header[0], header[1]);
            }
        }

        if (isPOST()) {
            httpUrlConnection.setRequestProperty("Content-Length", paramPost.getBytes().length + "");
            httpUrlConnection.setDoOutput(true);
            os = httpUrlConnection.getOutputStream();
            os.write(paramPost.getBytes("UTF-8"));
        }

        httpUrlConnection.connect();
        httpRequestResponseCode = httpUrlConnection.getResponseCode();
        httpRequestResponseMessage = httpUrlConnection.getResponseMessage();
    }

    public int getResponseStatus() throws IOException {
        connect();
        return httpRequestResponseCode;
    }

    public String getResponseMessage() throws IOException {
        connect();
        return httpRequestResponseMessage;
    }

    public String getResponseHTML() throws IOException {
        connect();
        StringBuilder respHTML = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(httpUrlConnection.getInputStream(), "UTF-8"));
        String c;
        while ((c = br.readLine()) != null) {
            respHTML.append(c);
        }
        return respHTML.toString().equals("") ? null : respHTML.toString();
    }

    public String getResponseHeader(String key) throws IOException {
        connect();
        return httpUrlConnection.getHeaderField(key);
    }

    public HashMap<String, String> getResponseHeader() throws IOException {
        connect();
        HashMap<String, String> map = new HashMap<>();
        httpUrlConnection.getHeaderFields().entrySet().forEach((entries) -> {
            StringBuilder values = new StringBuilder();
            entries.getValue().forEach((value) -> {
                values.append(value).append(",");
            });
            map.put(entries.getKey(), values.toString());
        });
        return map;
    }

    private String getProtocol() {
        return url.contains("http://") ? "http" : "https";
    }

    private String getMethod() {
        return paramPost == null ? "GET" : "POST";
    }

    private boolean isPOST() {
        return getMethod().equals("POST");
    }

    private boolean isProxy() {
        return httpRequestProxy != null;
    }

    private boolean isProxyAuth() {
        if (isProxy()) {
            return httpRequestProxy.getProxyUserName() != null && httpRequestProxy.getProxyPassword() != null;
        }
        return false;
    }

    private void init() {
        if (httpRequestHeader == null) {
            httpRequestHeader = new HttpRequestHeader();
        }
    }

}
