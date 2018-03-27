package com.seriatornet.yhondri.seriatornet.Register;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.seriatornet.yhondri.seriatornet.R;
import com.seriatornet.yhondri.seriatornet.Util.SharedPreferenceKey;
import com.seriatornet.yhondri.seriatornet.Util.SharedPreferenceUtils;

import java.util.regex.Pattern;

/**
 * Created by yhondri on 27/03/2018.
 */

public class RegisterPresenter implements RegisterPresentation, OnCompleteListener<AuthResult> {

    private FirebaseAuth firebaseAuth;
    private Context context;
    public final Pattern passwordPattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$");
    private RegisterWireframe router;
    private RegisterView view;

    public RegisterPresenter(RegisterView view, RegisterWireframe router, Context context) {
        this.view = view;
        this.router = router;
        this.context = context;
    }

    @Override
    public void onCreate() {
        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onRegisterUser(String email, String password) {

        view.resetFieldsErrors();

        if (TextUtils.isEmpty(email)) {
            view.showInvalidEmailError(context.getString(R.string.error_field_required));
            return;
        }

        if (!isEmailValid(email)) {
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

        if (!isPasswordValid(password)) {
            view.showInvalidPasswordError(context.getString(R.string.error_invalid_password));
            return;
        }

        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this);
    }

    private boolean isEmailValid(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isPasswordValid(String password) {
        return passwordPattern.matcher(password).matches();
    }

    @Override
    public void onComplete(@NonNull Task<AuthResult> task) {
        if (task.isSuccessful()) {
            FirebaseUser user = task.getResult().getUser();

            String userName = user.getDisplayName();
            if (userName == null) {
                userName = user.getEmail();
            }

            SharedPreferenceUtils.getInstance(context).setValue(SharedPreferenceKey.USER_NAME, userName);
            SharedPreferenceUtils.getInstance(context).setValue(SharedPreferenceKey.IS_USER_LOGGED_IN, true);
            view.showAuthenticationDidSuccessMessage(userName);
            router.showMainActivity();
            view.closeActivity();
        } else {
            view.showAuthenticationDidFailError("Authentication failed." + task.getException());
        }

        view.onProgressBar(true);
    }
}
