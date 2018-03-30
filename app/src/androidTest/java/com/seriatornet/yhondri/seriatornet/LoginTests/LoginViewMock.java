package com.seriatornet.yhondri.seriatornet.LoginTests;

import com.seriatornet.yhondri.seriatornet.Module.Login.LoginView;

/**
 * Created by yhondri on 30/3/18.
 */

public class LoginViewMock implements LoginView {

    private Boolean resetFieldsErrorsCalled = false;
    private Boolean showInvalidPasswordErrorCalled = false;
    private Boolean showInvalidEmailErrorCalled = false;
    private Boolean onProgressBarCalled = false;
    private Boolean onLoginUserCalled = false;
    private Boolean loginDidEndCalled = false;
    private Boolean showAuthenticationDidFailErrorCalled = false;
    private Boolean showAuthenticationDidSuccessMessageCalled = false;
    private Boolean closeActivityCalled = false;

    @Override
    public void resetFieldsErrors() {
        resetFieldsErrorsCalled = true;
    }

    @Override
    public void showInvalidPasswordError(int message) {
        showInvalidPasswordErrorCalled = true;
    }

    @Override
    public void showInvalidEmailError(int message) {
        showInvalidEmailErrorCalled = true;
    }

    @Override
    public void onProgressBar(Boolean isHidden) {
        onProgressBarCalled = true;
    }

    @Override
    public void onLoginUser() {
        onLoginUserCalled = true;
    }

    @Override
    public void loginDidEnd() {
        loginDidEndCalled = true;
    }

    @Override
    public void showAuthenticationDidFailError(String message) {
        showAuthenticationDidFailErrorCalled = true;
    }

    @Override
    public void showAuthenticationDidSuccessMessage(String userName) {
        showAuthenticationDidSuccessMessageCalled = true;
    }

    @Override
    public void closeActivity() {
        closeActivityCalled = true;
    }

    public Boolean getResetFieldsErrorsCalled() {
        return resetFieldsErrorsCalled;
    }

    public Boolean getShowInvalidPasswordErrorCalled() {
        return showInvalidPasswordErrorCalled;
    }

    public Boolean getShowInvalidEmailErrorCalled() {
        return showInvalidEmailErrorCalled;
    }

    public Boolean getOnProgressBarCalled() {
        return onProgressBarCalled;
    }

    public Boolean getOnLoginUserCalled() {
        return onLoginUserCalled;
    }

    public Boolean getLoginDidEndCalled() {
        return loginDidEndCalled;
    }

    public Boolean getShowAuthenticationDidFailErrorCalled() {
        return showAuthenticationDidFailErrorCalled;
    }

    public Boolean getShowAuthenticationDidSuccessMessageCalled() {
        return showAuthenticationDidSuccessMessageCalled;
    }

    public Boolean getCloseActivityCalled() {
        return closeActivityCalled;
    }
}
