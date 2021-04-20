package com.mercadolibre.desafio_quality.utils;

import java.util.regex.Pattern;

/*
* Utility class for generic validations.
* */
public class ValidatorUtil {
    /*
    * Validate if a string has an email form.
    * */
    public static boolean isEmail(String email)
    {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                            "[a-zA-Z0-9_+&*-]+)*@" +
                            "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                            "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);

        return email != null && pat.matcher(email).matches();
    }

    /*
    * Validate if a string has an DNI form.
    * */
    public static boolean isDNI(String dni){
        String emailRegex = "[0-9]+";
        Pattern pat = Pattern.compile(emailRegex);

        return dni != null && dni.length() == 8 && pat.matcher(dni).matches();
    }
}
