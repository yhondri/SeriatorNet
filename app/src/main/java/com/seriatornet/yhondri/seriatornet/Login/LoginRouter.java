package com.seriatornet.yhondri.seriatornet.Login;

import android.content.Context;
import android.content.Intent;

import com.seriatornet.yhondri.seriatornet.Main.Views.MainActivity;

/**
 * Created by yhondri on 28/03/2018.
 */

public class LoginRouter implements LoginWireframe {

    private Context context;

    public LoginRouter(Context context) {
        this.context = context;
    }

    @Override
    public void showMainActivity() {
        Intent mainActivityIntent = new Intent(context, MainActivity.class);
        context.startActivity(mainActivityIntent);
    }
}
