package com.company;

import com.company.Password;

public class PasswordIntermedia extends Password {
    public PasswordIntermedia() {
        super("^(?=.*\\d)(?=.*[A-Z])(?=.*[a-z]){8,16}");
    }
}
