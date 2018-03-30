package com.seriatornet.yhondri.seriatornet.Module.Register;

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
 * Created by yhondri on 27/03/2018.
 */

public class RegisterPresenter implements RegisterPresentation, OauthServiceResult {

    private Context context;
    private RegisterWireframe router;
    private RegisterView view;
    private OauthService oauthService;

    public RegisterPresenter(RegisterView view, RegisterWireframe router, OauthService oauthService, Context context) {
        this.view = view;
        this.router = router;
        this.oauthService = oauthService;
        this.context = context;
    }

    @Override
    public void onRegisterUser(String email, String password) {

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

        if (password.length() < 5) {
            view.showInvalidPasswordError(context.getString(R.string.error_password_is_too_short));
            return;
        }

        if (!Utils.isPasswordValid(password)) {
            view.showInvalidPasswordError(context.getString(R.string.error_invalid_password));
            return;
        }

        view.onProgressBar(false);
        view.onRegisterUser();

        oauthService.createUserWithEmailAndPassword(email, password, this);
    }

    @Override
    public void onComplete(boolean success, User user) {
        view.registerDidEnd();

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
