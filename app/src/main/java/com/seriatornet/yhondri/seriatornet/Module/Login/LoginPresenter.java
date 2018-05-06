package com.seriatornet.yhondri.seriatornet.Module.Login;

import android.text.TextUtils;
import com.seriatornet.yhondri.seriatornet.Model.DataBase.User;
import com.seriatornet.yhondri.seriatornet.R;
import com.seriatornet.yhondri.seriatornet.Util.OauthService;
import com.seriatornet.yhondri.seriatornet.Util.OauthServiceResult;
import com.seriatornet.yhondri.seriatornet.Util.Utils;

/**
 * Created by yhondri on 28/03/2018.
 */

public class LoginPresenter implements LoginPresentation, LoginInteractorOutput {

    private LoginView view;
    private LoginWireframe router;
    private LoginInteractorInput interactor;

    public LoginPresenter(LoginView view, LoginWireframe router, LoginInteractorInput interactor, OauthService oauthService) {
        this.view = view;
        this.router = router;
        this.interactor = interactor;
    }

    @Override
    public void onLogin(String email, String password) {
        view.resetFieldsErrors();
        view.onProgressBar(false);
        view.onLoginUser();

        interactor.onLogin(email, password);
    }

    @Override
    public void onLoginDidFail(int errorMessage) {
        view.showInvalidPasswordError(errorMessage);
    }

    @Override
    public void onLoadingDataDidFinish(String userName) {
        view.loginDidEnd();
        view.showAuthenticationDidSuccessMessage(userName);
        router.showMainActivity();
        view.closeActivity();
    }
}
