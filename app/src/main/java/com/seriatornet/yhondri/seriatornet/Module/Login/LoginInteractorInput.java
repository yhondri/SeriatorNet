package com.seriatornet.yhondri.seriatornet.Module.Login;

import com.seriatornet.yhondri.seriatornet.Model.DataBase.User;

/**
 * Created by yhondri on 30/3/18.
 */

public interface LoginInteractorInput {
    /**
     * Método que recibe un nuevo usuario y lo guarda.
     * @param newUser Nuevo usuario en la aplicación.
     */
    void userDidLogin(User newUser);
}
