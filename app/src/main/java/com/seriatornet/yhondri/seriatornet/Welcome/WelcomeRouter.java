package com.seriatornet.yhondri.seriatornet.Welcome;

import android.content.Context;
import android.content.Intent;

import com.seriatornet.yhondri.seriatornet.Login.LoginActivity;
import com.seriatornet.yhondri.seriatornet.Register.RegisterActivity;

/**
 * Created by yhondri on 27/03/2018.
 */

public class WelcomeRouter implements WelcomeWireframe {

    private Context context;

    public WelcomeRouter(Context context) {
        this.context = context;
    }

    @Override
    public void goToLoginActivity() {
        Intent loginIntent = new Intent(context, LoginActivity.class);
        context.startActivity(loginIntent);
    }

    @Override
    public void goToRegisterActivity() {
        Intent registerIntent = new Intent(context, RegisterActivity.class);
        context.startActivity(registerIntent);
    }
}
