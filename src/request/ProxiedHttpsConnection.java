/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package request;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.ProtocolException;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import request.header.HttpRequestProxy;

public class ProxiedHttpsConnection extends HttpURLConnection {

    private final String proxyHost;
    private final int proxyPort;
    private static final byte[] NEWLINE = "\r\n".getBytes();//should be "ASCII7"

    private Socket socket;
    private final Map<String, List<String>> headers = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    private final Map<String, List<String>> sendheaders = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    private final Map<String, List<String>> proxyheaders = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    private final Map<String, List<String>> proxyreturnheaders = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    private int statusCode;
    private String statusLine;
    private boolean isDoneWriting;
    private String replyStr;

    public ProxiedHttpsConnection(URL url, HttpRequestProxy httpRequestProxy) throws IOException {
        super(url);
        socket = new Socket();
        this.proxyHost = httpRequestProxy.getProxyHost();
        this.proxyPort = httpRequestProxy.getProxyPort();
        String encoded = new String(Base64.getEncoder().encode((httpRequestProxy.getProxyUserName() + ":" + httpRequestProxy.getProxyPassword()).getBytes())).replace("\r\n", "");
        proxyheaders.put("Proxy-Authorization", new ArrayList<>(Arrays.asList("Basic " + encoded)));
    }

    @Override
    public OutputStream getOutputStream() throws IOException {
        connect();
        afterWrite();
        return new FilterOutputStream(socket.getOutputStream()) {
            @Override
            public void write(byte[] b, int off, int len) throws IOException {
                out.write(String.valueOf(len).getBytes());
                out.write(NEWLINE);
                out.write(b, off, len);
                out.write(NEWLINE);
            }

            @Override
            public void write(byte[] b) throws IOException {
                out.write(String.valueOf(b.length).getBytes());
                out.write(NEWLINE);
                out.write(b);
                out.write(NEWLINE);
            }

            @Override
            public void write(int b) throws IOException {
                out.write(String.valueOf(1).getBytes());
                out.write(NEWLINE);
                out.write(b);
                out.write(NEWLINE);
            }

            @Override
            public void close() throws IOException {
                afterWrite();
            }

        };
    }

    private boolean afterwritten = false;

    @Override
    public InputStream getInputStream() throws IOException {
        connect();
        return socket.getInputStream();

    }

    @Override
    public void setRequestMethod(String method) throws ProtocolException {
        this.method = method;
    }

    @Override
    public void setRequestProperty(String key, String value) {
        sendheaders.put(key, new ArrayList<>(Arrays.asList(value)));
    }

    @Override
    public void addRequestProperty(String key, String value) {
        sendheaders.computeIfAbsent(key, l -> new ArrayList<>()).add(value);
    }

    @Override
    public Map<String, List<String>> getHeaderFields() {
        return headers;
    }

    @Override
    public void connect() throws IOException {
        if (connected) {
            return;
        }
        connected = true;
        socket.setSoTimeout(getReadTimeout());
        socket.connect(new InetSocketAddress(proxyHost, proxyPort), getConnectTimeout());
        StringBuilder msg = new StringBuilder();
        msg.append("CONNECT ");
        msg.append(url.getHost());
        msg.append(':');
        msg.append(url.getPort() == -1 ? 443 : url.getPort());
        msg.append(" HTTP/1.0\r\n");
        proxyheaders.entrySet().forEach((header) -> {
            header.getValue().stream().map((l) -> {
                msg.append(header.getKey()).append(": ").append(l);
                return l;
            }).forEachOrdered((_item) -> {
                msg.append("\r\n");
            });
        });

        msg.append("Connection: close\r\n");
        msg.append("\r\n");
        byte[] bytes;
        try {
            bytes = msg.toString().getBytes("ASCII7");
        } catch (UnsupportedEncodingException ignored) {
            bytes = msg.toString().getBytes();
        }
        socket.getOutputStream().write(bytes);
        socket.getOutputStream().flush();
        byte reply[] = new byte[200];
        byte header[] = new byte[200];
        int replyLen = 0;
        int headerLen = 0;
        int newlinesSeen = 0;
        boolean headerDone = false;
        /* Done on first newline */
        InputStream in = socket.getInputStream();
        while (newlinesSeen < 2) {
            int i = in.read();
            if (i < 0) {
                throw new IOException("Unexpected EOF from remote server");
            }
            if (i == '\n') {
                if (newlinesSeen != 0) {
                    String h = new String(header, 0, headerLen);
                    String[] split = h.split(": ");
                    if (split.length != 1) {
                        proxyreturnheaders.computeIfAbsent(split[0], l -> new ArrayList<>()).add(split[1]);
                    }
                }
                headerDone = true;
                ++newlinesSeen;
                headerLen = 0;
            } else if (i != '\r') {
                newlinesSeen = 0;
                if (!headerDone && replyLen < reply.length) {
                    reply[replyLen++] = (byte) i;
                } else if (headerLen < reply.length) {
                    header[headerLen++] = (byte) i;
                }
            }
        }

        String _replyStr;
        try {
            _replyStr = new String(reply, 0, replyLen, "ASCII7");
        } catch (UnsupportedEncodingException ignored) {
            _replyStr = new String(reply, 0, replyLen);
        }

        // Some proxies return http/1.1, some http/1.0 even we asked for 1.0
        if (!_replyStr.startsWith("HTTP/1.0 200") && !_replyStr.startsWith("HTTP/1.1 200")) {
            throw new IOException("Unable to tunnel. Proxy returns \"" + _replyStr + "\"");
        }
        SSLSocket s = (SSLSocket) ((SSLSocketFactory) SSLSocketFactory.getDefault()).createSocket(socket, url.getHost(), url.getPort(), true);
        s.startHandshake();
        socket = s;
        msg.setLength(0);
        msg.append(method);
        msg.append(" ");
        msg.append(url.toExternalForm().split(String.valueOf(url.getPort()), -2)[0]);
        msg.append(" HTTP/1.0\r\n");
        sendheaders.entrySet().forEach((h) -> {
            h.getValue().stream().map((l) -> {
                msg.append(h.getKey()).append(": ").append(l);
                return l;
            }).forEachOrdered((_item) -> {
                msg.append("\r\n");
            });
        });
        if (method.equals("POST") || method.equals("PUT")) {
            msg.append("Transfer-Encoding: Chunked\r\n");
        }
        msg.append("Host: ").append(url.getHost()).append("\r\n");
        msg.append("Connection: close\r\n");
        msg.append("\r\n");
        try {
            bytes = msg.toString().getBytes("ASCII7");
        } catch (UnsupportedEncodingException ignored) {
            bytes = msg.toString().getBytes();
        }
        socket.getOutputStream().write(bytes);
        socket.getOutputStream().flush();
    }

    private void afterWrite() throws IOException {
        if (afterwritten) {
            return;
        }
        afterwritten = true;
        socket.getOutputStream().write(String.valueOf(0).getBytes());
        socket.getOutputStream().write(NEWLINE);
        socket.getOutputStream().write(NEWLINE);
        byte reply[] = new byte[200];
        byte header[] = new byte[200];
        int replyLen = 0;
        int headerLen = 0;
        int newlinesSeen = 0;
        boolean headerDone = false;
        /* Done on first newline */
        InputStream in = socket.getInputStream();
        while (newlinesSeen < 2) {
            int i = in.read();
            if (i < 0) {
                throw new IOException("Unexpected EOF from remote server");
            }
            if (i == '\n') {
                if (headerDone) {
                    String h = new String(header, 0, headerLen);
                    String[] split = h.split(": ");
                    if (split.length != 1) {
                        headers.computeIfAbsent(split[0], l -> new ArrayList<>()).add(split[1]);
                    }
                }
                headerDone = true;
                ++newlinesSeen;
                headerLen = 0;
            } else if (i != '\r') {
                newlinesSeen = 0;
                if (!headerDone && replyLen < reply.length) {
                    reply[replyLen++] = (byte) i;
                } else if (headerLen < header.length) {
                    header[headerLen++] = (byte) i;
                }
            }
        }

        try {
            replyStr = new String(reply, 0, replyLen, "ASCII7");
        } catch (UnsupportedEncodingException ignored) {
            replyStr = new String(reply, 0, replyLen);
        }

        /* We asked for HTTP/1.0, so we should get that back */
//        if ((!replyStr.startsWith("HTTP/1.0 200")) && !replyStr.startsWith("HTTP/1.1 200")) {
//            throw new IOException("Server returns \"" + replyStr + "\"");
//        }
    }

    @Override
    public int getResponseCode() throws IOException {
        connect();
        if (replyStr != null && replyStr.length() > 0) {
            String regex = " ([0-9]{3}) ";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(replyStr);
            if (m.find()) {
                return Integer.parseInt(m.group().trim());
            }
        }
        throw new IOException("Response is NULL!");
    }

    @Override
    public String getResponseMessage() throws IOException {
        connect();
        if (replyStr!=null && replyStr.length() > 0) {
            String regex = " [0-9]{3} (.+?)$";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(replyStr);
            if (m.find()) {
                return m.group().substring(5);
            }
        }
        throw new IOException("Response is NULL!");
    }

    @Override
    public void disconnect() {
        try {
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(ProxiedHttpsConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public boolean usingProxy() {
        return true;
    }
}
