package com.seriatornet.yhondri.seriatornet.Module.Register;

/**
 * Created by yhondri on 27/03/2018.
 */

public interface RegisterPresentation {
    /**
     * Método que se llama para realizar el regístro.
     * @param email cuenta de correo del usuario.
     * @param password contraseña del usuario.
     */
    void onRegisterUser(String email, String password);
}
