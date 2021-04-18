package com.mercadolibre.desafio_quality.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;

public class ValidatorUtil {

    public static boolean isEmail(String email)
    {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                            "[a-zA-Z0-9_+&*-]+)*@" +
                            "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                            "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);

        return email != null && pat.matcher(email).matches();
    }

    public static boolean isDNI(String dni){
        String emailRegex = "[0-9]+";

        Pattern pat = Pattern.compile(emailRegex);

        return dni != null && dni.length() == 8 && pat.matcher(dni).matches();
    }
}
