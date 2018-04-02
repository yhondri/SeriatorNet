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

public class LoginPresenter implements LoginPresentation, OauthServiceResult {

    private LoginView view;
    private LoginWireframe router;
    private LoginInteractorInput interactor;
    private OauthService oauthService;

    public LoginPresenter(LoginView view, LoginWireframe router, LoginInteractorInput interactor, OauthService oauthService) {
        this.view = view;
        this.router = router;
        this.interactor = interactor;
        this.oauthService = oauthService;
    }

    @Override
    public void onLogin(String email, String password) {
        view.resetFieldsErrors();

        if (Utils.isEmpty(email)) {
            view.showInvalidEmailError(R.string.error_field_required);
            return;
        }

        if (!Utils.isEmailValid(email)) {
            view.showInvalidEmailError(R.string.error_invalid_email);
            return;
        }

        if (Utils.isEmpty(password)) {
            view.showInvalidPasswordError(R.string.error_field_required);
            return;
        }

        view.onProgressBar(false);
        view.onLoginUser();

        oauthService.signInWithEmailAndPassword(email, password, this);
    }

    @Override
    public void onComplete(boolean success, User user) {
        view.loginDidEnd();

        if (success) {
            String userName = user.getUserName();
            if (userName == null) {
                userName = user.getEmail();
            }

            interactor.userDidLogin(user);

            router.showMainActivity();

            view.showAuthenticationDidSuccessMessage(userName);
            view.closeActivity();

        } else {
            view.showAuthenticationDidFailError("Authentication failed.");
        }

        view.onProgressBar(true);
    }
}
