package model;

public class Account {

    private String cookie;
    private String csrfToken;
    private String userName;
    private String userId;
    private String email;

    public Account() {
    }

    public Account(String cookie, String csrfToken) {
        this.cookie = cookie;
        this.csrfToken = csrfToken;
    }

    public Account(String cookie, String csrfToken, String userName, String userId, String email) {
        this.cookie = cookie;
        this.csrfToken = csrfToken;
        this.userName = userName;
        this.userId = userId;
        this.email = email;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public String getCsrfToken() {
        return csrfToken;
    }

    public void setCsrfToken(String csrfToken) {
        this.csrfToken = csrfToken;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return userName + "@fpt.edu.vn";
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Account{" + "cookie=" + cookie + ", csrfToken=" + csrfToken + ", userName=" + userName + ", userId=" + userId + ", email=" + email + '}';
    }



}
