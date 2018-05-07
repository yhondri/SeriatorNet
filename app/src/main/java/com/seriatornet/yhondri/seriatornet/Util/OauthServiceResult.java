package com.seriatornet.yhondri.seriatornet.Util;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.seriatornet.yhondri.seriatornet.Model.DataBase.User;

public interface OauthServiceResult {
   /**
    * Call back que devuelve un booleano y un objeto de tipo User.
    * @param success Si la operación ha ido bien o no.
    * @param user Usuario.
    */
   void onComplete(boolean success, User user);
   void onRegisterDidFail(@NonNull Task<AuthResult> task);
}
