package com.seriatornet.yhondri.seriatornet.Module.Welcome;

/**
 * Created by yhondri on 27/03/2018.
 */

public interface WelcomePresentation {

    /**
     * Se llama cuando se ha creado la actividad.
     */
    void onCreate();

    /**
     * Se llama cuando el usuario quiere entrar en la aplicación.
     */
    void onStartClicked();

    /**
     * Método que procesa la acción cuando el usuario quiere registrarse.
     */
    void onRegisterClicked();

}
