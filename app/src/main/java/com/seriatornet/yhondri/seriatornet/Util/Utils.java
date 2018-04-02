package com.seriatornet.yhondri.seriatornet.Util;

import android.support.annotation.Nullable;

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

    /**
     * Returns true if the string is null or 0-length.
     * @param str the string to be examined
     * @return true if str is null or zero length
     */
    public static boolean isEmpty(@Nullable CharSequence str) {
        return str == null || str.length() == 0;
    }
}
