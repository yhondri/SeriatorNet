package com.seriatornet.yhondri.seriatornet.Module.Login;

/**
 * Created by yhondri on 28/03/2018.
 */

public interface LoginPresentation {
    /**
     * Método que se llama para llevar a cabo el Login.
     * @param email correo de la cuenta del usuario.
     * @param password contraseña de la cuenta del usuario.
     */
    void onLogin(String email, String password);
}
