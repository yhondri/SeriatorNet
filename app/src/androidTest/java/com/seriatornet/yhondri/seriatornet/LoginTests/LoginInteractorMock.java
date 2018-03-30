package com.seriatornet.yhondri.seriatornet.LoginTests;

import com.seriatornet.yhondri.seriatornet.Model.DataBase.User;
import com.seriatornet.yhondri.seriatornet.Module.Login.LoginInteractorInput;

/**
 * Created by yhondri on 30/3/18.
 */

public class LoginInteractorMock implements LoginInteractorInput {

    private boolean userDidLoginCalled;

    @Override
    public void userDidLogin(User newUser) {
        this.userDidLoginCalled = true;
    }

    public boolean isUserDidLoginCalled() {
        return userDidLoginCalled;
    }
}
