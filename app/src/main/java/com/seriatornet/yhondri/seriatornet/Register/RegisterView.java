package com.seriatornet.yhondri.seriatornet.Register;

/**
 * Created by yhondri on 27/03/2018.
 */

public interface RegisterView {
    void showInvalidPasswordError(String message);

    void showInvalidEmailError(String message);

    void resetFieldsErrors();

    void onProgressBar(final Boolean isHidden);

    void onRegisterUser();

    void registerDidEnd();

    void showAuthenticationDidFailError(String message);

    void showAuthenticationDidSuccessMessage(String userName);

    void closeActivity();
}
