package com.seriatornet.yhondri.seriatornet.Welcome;

import android.content.Context;
import android.content.Intent;

import com.seriatornet.yhondri.seriatornet.Login.LoginActivity;
import com.seriatornet.yhondri.seriatornet.Main.Views.MainActivity;
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
    public void showMainActivity() {
        Intent mainActivityIntent = new Intent(context, MainActivity.class);
        context.startActivity(mainActivityIntent);
    }

    @Override
    public void showLoginActivity() {
        Intent loginIntent = new Intent(context, LoginActivity.class);
        context.startActivity(loginIntent);
    }

    @Override
    public void showRegisterActivity() {
        Intent registerIntent = new Intent(context, RegisterActivity.class);
        context.startActivity(registerIntent);
    }
}
