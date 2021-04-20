package com.mercadolibre.desafio_quality.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests of ValidatorUtil")
class ValidatorUtilTest {

    @Test
    @DisplayName("Correct email")
    void isEmail() {
        Assertions.assertTrue(ValidatorUtil.isEmail("aa@aa.com.ar"));
    }

    @Test
    @DisplayName("Email with invalid format")
    void isEmailWithInvalidFormat() {
        Assertions.assertFalse(ValidatorUtil.isEmail("aaaa.com.ar"));
    }

    @Test
    @DisplayName("Correct DNI")
    void isDNI() {
        Assertions.assertTrue(ValidatorUtil.isDNI("12345678"));
    }

    @Test
    @DisplayName("DNI with fewer digits")
    void isDNIWhitFewerDigits() {
        Assertions.assertFalse(ValidatorUtil.isDNI("12345"));
    }

    @Test
    @DisplayName("DNI with alphanumeric characters")
    void isDNIWithAlphanumericCharacters() {
        Assertions.assertFalse(ValidatorUtil.isDNI("abcd"));
    }

    @Test
    @DisplayName("DNI null")
    void isDNINull() {
        Assertions.assertFalse(ValidatorUtil.isDNI(null));
    }


}