package com.seriatornet.yhondri.seriatornet.Module.Login;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.seriatornet.yhondri.seriatornet.Model.DataBase.User;
import com.seriatornet.yhondri.seriatornet.R;
import com.seriatornet.yhondri.seriatornet.Util.OauthService;
import com.seriatornet.yhondri.seriatornet.Util.OauthServiceResult;
import com.seriatornet.yhondri.seriatornet.Util.SharedPreferenceKey;
import com.seriatornet.yhondri.seriatornet.Util.SharedPreferenceUtils;
import com.seriatornet.yhondri.seriatornet.Util.Utils;

/**
 * Created by yhondri on 28/03/2018.
 */

public class LoginPresenter implements LoginPresentation, OauthServiceResult {

    private Context context;
    private LoginView view;
    private LoginWireframe router;
    private OauthService oauthService;

    public LoginPresenter(LoginView view, LoginWireframe router, OauthService oauthService, Context context) {
        this.view = view;
        this.oauthService = oauthService;
        this.context = context;
        this.router = router;
    }

    @Override
    public void onLogin(String email, String password) {
        view.resetFieldsErrors();

        if (TextUtils.isEmpty(email)) {
            view.showInvalidEmailError(context.getString(R.string.error_field_required));
            return;
        }

        if (!Utils.isEmailValid(email)) {
            view.showInvalidEmailError(context.getString(R.string.error_invalid_email));
            return;
        }

        if (TextUtils.isEmpty(password)) {
            view.showInvalidPasswordError(context.getString(R.string.error_field_required));
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

            SharedPreferenceUtils.getInstance(context).setValue(SharedPreferenceKey.USER_NAME, userName);
            SharedPreferenceUtils.getInstance(context).setValue(SharedPreferenceKey.IS_USER_LOGGED_IN, true);
            view.showAuthenticationDidSuccessMessage(userName);
            router.showMainActivity();
            view.closeActivity();
        } else {
            view.showAuthenticationDidFailError("Authentication failed.");
        }

        view.onProgressBar(true);
    }
}
