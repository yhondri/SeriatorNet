package com.seriatornet.yhondri.seriatornet.Module.Login;

public interface LoginInteractorOutput {
    void onLoginDidFail(int errorMessage);
    void onLoadingDataDidFinish(String userName);
}
