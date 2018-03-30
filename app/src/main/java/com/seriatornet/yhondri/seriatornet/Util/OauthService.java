package com.seriatornet.yhondri.seriatornet.Util;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;

/**
 * Created by yhondri on 30/3/18.
 */

public interface OauthService {
    void signInWithEmailAndPassword(String email, String password, OauthServiceResult listener);
    void createUserWithEmailAndPassword(String email, String password, OauthServiceResult listener);
}
