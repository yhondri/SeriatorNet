package com.seriatornet.yhondri.seriatornet.Register;

import android.content.Context;
import android.content.Intent;

import com.seriatornet.yhondri.seriatornet.Main.MainActivity;

/**
 * Created by yhondri on 27/03/2018.
 */

public class RegisterRouter implements RegisterWireframe {

    private Context context;

    public RegisterRouter(Context context) {
        this.context = context;
    }

    @Override
    public void showMainActivity() {
        Intent mainActivityIntent = new Intent(context, MainActivity.class);
        context.startActivity(mainActivityIntent);
    }
}
