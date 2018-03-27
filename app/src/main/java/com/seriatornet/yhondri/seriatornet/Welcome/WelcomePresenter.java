package com.seriatornet.yhondri.seriatornet.Welcome;

/**
 * Created by yhondri on 27/03/2018.
 */

public class WelcomePresenter implements WelcomePresentation {

    private WelcomeWireframe router;

    public WelcomePresenter(WelcomeWireframe router) {
        this.router = router;
    }

    @Override
    public void onStartClicked() {
        router.goToLoginActivity();
    }

    @Override
    public void onRegisterClicked() {
        router.goToRegisterActivity();
    }
}
