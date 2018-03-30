package com.seriatornet.yhondri.seriatornet.Module.Login;

import android.content.Context;

import com.seriatornet.yhondri.seriatornet.Model.DataBase.User;
import com.seriatornet.yhondri.seriatornet.Util.SharedPreferenceKey;
import com.seriatornet.yhondri.seriatornet.Util.SharedPreferenceUtils;

/**
 * Created by yhondri on 30/3/18.
 */

public class LoginInteractor implements LoginInteractorInput {

    private Context context;

    public LoginInteractor(Context context) {
        this.context = context;
    }

    @Override
    public void userDidLogin(User newUser) {
        String userName = newUser.getUserName();
        if (userName == null) {
            userName = newUser.getEmail();
        }

        SharedPreferenceUtils.getInstance(context).setValue(SharedPreferenceKey.USER_NAME, userName);
        SharedPreferenceUtils.getInstance(context).setValue(SharedPreferenceKey.IS_USER_LOGGED_IN, true);
    }
}
