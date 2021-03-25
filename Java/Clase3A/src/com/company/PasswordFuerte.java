package com.company;

import com.company.Password;

public class PasswordFuerte extends Password {
    public PasswordFuerte() {
        super("^(?=.*\\d)(?=.*[A-Z])(?=.*[a-z])(?=.*[^\\w\\d\\s:])([^\\s]){8,16}$\n");
    }
}
