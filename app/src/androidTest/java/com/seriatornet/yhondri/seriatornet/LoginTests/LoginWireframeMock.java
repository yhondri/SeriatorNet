package com.seriatornet.yhondri.seriatornet.LoginTests;

import com.seriatornet.yhondri.seriatornet.Module.Login.LoginWireframe;

/**
 * Created by yhondri on 30/3/18.
 */

public class LoginWireframeMock implements LoginWireframe {

    private Boolean showMainActivityCalled;

    @Override
    public void showMainActivity() {
        showMainActivityCalled = true;
    }
}
