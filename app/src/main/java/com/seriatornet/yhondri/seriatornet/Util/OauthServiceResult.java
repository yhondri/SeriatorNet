package com.seriatornet.yhondri.seriatornet.Util;

import com.seriatornet.yhondri.seriatornet.Model.DataBase.User;

public interface OauthServiceResult {
   void onComplete(boolean success, User user);
}
