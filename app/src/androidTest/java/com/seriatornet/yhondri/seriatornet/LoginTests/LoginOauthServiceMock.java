package com.seriatornet.yhondri.seriatornet.LoginTests;

import com.seriatornet.yhondri.seriatornet.Util.OauthService;
import com.seriatornet.yhondri.seriatornet.Util.OauthServiceResult;

/**
 * Created by yhondri on 30/3/18.
 */

public class LoginOauthServiceMock implements OauthService {

    private boolean signInWithEmailAndPasswordCalled;
    private boolean createUserWithEmailAndPasswordCalled;

    private boolean succesAction;

    @Override
    public void signInWithEmailAndPassword(String email, String password, OauthServiceResult listener) {
        signInWithEmailAndPasswordCalled = true;
        listener.onComplete(succesAction, null);
    }

    @Override
    public void createUserWithEmailAndPassword(String email, String password, OauthServiceResult listener) {
        createUserWithEmailAndPasswordCalled = true;
        listener.onComplete(succesAction, null);
    }

    public boolean isSignInWithEmailAndPasswordCalled() {
        return signInWithEmailAndPasswordCalled;
    }

    public boolean isCreateUserWithEmailAndPasswordCalled() {
        return createUserWithEmailAndPasswordCalled;
    }

    public void setSuccesAction(boolean succesAction) {
        this.succesAction = succesAction;
    }
}
