package com.company;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Password {
    private Pattern regex;
    private Matcher matcher;
    private String password;

    public Password(String regex) {
        this.regex = Pattern.compile(regex);
    }

    public void setValue(String pwd) {
        this.matcher = regex.matcher(pwd);

        if (matcher.find()) {
            setPassword(pwd);
        }
        else {
            throw new RuntimeException("El password no cumple con las condiciones");
        }
    }

    public String getPassword() {
        return password;
    }

    private void setPassword(String password) {
        this.password = password;
    }
}
