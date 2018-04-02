package com.seriatornet.yhondri.seriatornet.Module.Welcome;

import android.content.Context;

import com.seriatornet.yhondri.seriatornet.Util.SharedPreferenceKey;
import com.seriatornet.yhondri.seriatornet.Util.SharedPreferenceUtils;

/**
 * Created by yhondri on 27/03/2018.
 */

public class WelcomePresenter implements WelcomePresentation {

    private WelcomeWireframe router;
    private Context context;
    private WelcomeView view;

    public WelcomePresenter(WelcomeWireframe router, Context context, WelcomeView view) {
        this.router = router;
        this.context = context;
        this.view = view;
    }

    @Override
    public void onCreate() {
        if (SharedPreferenceUtils.getInstance(context).getBoolanValue(SharedPreferenceKey.IS_USER_LOGGED_IN, false)) {
            router.showMainActivity();
            view.finishActivity();
        }
    }

    @Override
    public void onStartClicked() {
        router.showLoginActivity();
    }

    @Override
    public void onRegisterClicked() {
        router.showRegisterActivity();
    }
}
