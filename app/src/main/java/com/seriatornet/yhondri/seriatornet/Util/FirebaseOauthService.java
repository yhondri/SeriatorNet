package com.seriatornet.yhondri.seriatornet.Util;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.seriatornet.yhondri.seriatornet.Model.DataBase.User;

/**
 * Implementación de un servicio OAUTH adaptado a Seriator.
 */
public class FirebaseOauthService implements OauthService, OnCompleteListener<AuthResult> {

    private FirebaseAuth firebaseAuth;
    private OauthServiceResult listener;

    public FirebaseOauthService() {
        this.firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void signInWithEmailAndPassword(String email, String password, OauthServiceResult listener) {
        this.listener = listener;
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this);
    }

    @Override
    public void createUserWithEmailAndPassword(String email, String password, OauthServiceResult listener) {
        this.listener = listener;
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this);
    }

    @Override
    public void logOut() {
        firebaseAuth.signOut();
    }

    @Override
    public void onComplete(@NonNull Task<AuthResult> task) {
        FirebaseUser user = task.getResult().getUser();
        User newUser = new User(user.getDisplayName(), user.getEmail());
        listener.onComplete(task.isSuccessful(), newUser);
    }
}
