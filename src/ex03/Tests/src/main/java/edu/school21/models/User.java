package edu.school21.models;

public class User {
    private Long Identifier;

    private String Login;

    private String Password;

    private  boolean Authentication;

    public User(Long identifier, String login, String password, boolean authentication) {
        Identifier = identifier;
        Login = login;
        Password = password;
        Authentication = authentication;
    }

    public Long getIdentifier() {
        return Identifier;
    }

    public void setIdentifier(Long identifier) {
        Identifier = identifier;
    }

    public String getLogin() {
        return Login;
    }

    public void setLogin(String login) {
        Login = login;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public boolean isAuthentication() {
        return Authentication;
    }

    public void setAuthentication(boolean authentication) {
        Authentication = authentication;
    }
}
