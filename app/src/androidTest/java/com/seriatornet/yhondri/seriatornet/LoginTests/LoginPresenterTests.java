package com.seriatornet.yhondri.seriatornet.LoginTests;

import android.support.test.runner.AndroidJUnit4;

import com.seriatornet.yhondri.seriatornet.Module.Login.LoginPresenter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by yhondri on 30/3/18.
 */

@RunWith(AndroidJUnit4.class)
public class LoginPresenterTests {

    private LoginPresenter presenter;
    private LoginViewMock view;
    private LoginWireframeMock router;
    private LoginInteractorMock interactor;
    private LoginOauthServiceMock oauthServiceMock;

    @Before
    public void setUp() {
        view = new LoginViewMock();
        router = new LoginWireframeMock();
        interactor = new LoginInteractorMock();
        oauthServiceMock = new LoginOauthServiceMock();
        presenter = new LoginPresenter(view, router, interactor, oauthServiceMock);
    }

    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void loginFailWhenEmailFieldIsEmpty() {
        presenter.onLogin(null, "");
        assertTrue(view.getShowInvalidEmailErrorCalled());
    }

    @Test
    public void loginFailWhenPasswordFieldIsEmpty() {
        presenter.onLogin("email@prueba.com", "");
        assertTrue(view.getShowInvalidPasswordErrorCalled());
    }

    @Test
    public void loginFailWhenEmailIsInvalid() {
        presenter.onLogin("email", "");
        assertTrue(view.getShowInvalidEmailErrorCalled());
    }

    @Test
    public void loginSuccessAndPassworAreCorrects() {
        presenter.onLogin("email@prueba.com", "C123456siete");
        assertTrue(view.getOnLoginUserCalled());
    }
}
