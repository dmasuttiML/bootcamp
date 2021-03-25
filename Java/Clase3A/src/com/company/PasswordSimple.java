package com.company;

import com.company.Password;

public class PasswordSimple extends Password {
    public PasswordSimple() {
        super("^(?=.*[A-Z])(?=.*[a-z]){8,16}");
    }
}
