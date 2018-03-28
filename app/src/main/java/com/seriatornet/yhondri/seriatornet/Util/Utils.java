package com.seriatornet.yhondri.seriatornet.Util;

import java.util.regex.Pattern;

/**
 * Created by yhondri on 28/03/2018.
 */

public class Utils {

    private static final Pattern passwordPattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$");

    public static boolean isEmailValid(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean isPasswordValid(String password) {
        return passwordPattern.matcher(password).matches();
    }
}
