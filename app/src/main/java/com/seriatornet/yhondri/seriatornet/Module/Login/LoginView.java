package com.seriatornet.yhondri.seriatornet.Module.Login;

/**
 * Created by yhondri on 28/03/2018.
 */

public interface LoginView {

    void resetFieldsErrors();

    void showInvalidPasswordError(String message);

    void showInvalidEmailError(String message);

    void onProgressBar(final Boolean isHidden);

    void onLoginUser();

    void loginDidEnd();

    void showAuthenticationDidFailError(String message);

    void showAuthenticationDidSuccessMessage(String userName);

    void closeActivity();
}
