package com.seriatornet.yhondri.seriatornet.Module.Main.Views;

public interface OnBackPressedListener {

    /**
     * Callback, which is called if the Back Button is pressed.
     * Fragments that extend MainFragment can/should override this Method.
     *
     * @return true if the App can be closed, false otherwise
     */
    void onBackPressed();
}