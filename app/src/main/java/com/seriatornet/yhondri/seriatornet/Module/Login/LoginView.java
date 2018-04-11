package com.seriatornet.yhondri.seriatornet.Module.Login;

/**
 * Created by yhondri on 28/03/2018.
 */

public interface LoginView {

    /**
     * Resetea los campos de error.
     */
    void resetFieldsErrors();

    /**
     * Muestra un mensaje de contraseña inválida.
     * @param message id del Mensaje a mostrar.
     */
    void showInvalidPasswordError(int message);

    /**
     * Muestra un mensaje de email inválido.
     * @param message id del Mensaje a mostrar.
     */
    void showInvalidEmailError(int message);

    /**
     * Se llama para mostrar u ocultar el progressBar.
     * @param isHidden Booleano que define si hay que ocultar o mostrar el progressBar.
     */
    void onProgressBar(final Boolean isHidden);

    /**
     * Se llama cuando se inicia el Login.
     */
    void onLoginUser();

    /**
     * Se llama una vez ha terminado el proceso login.
     */
    void loginDidEnd();

    /**
     * Muestra un mensaje de error cuando el login ha fallado.
     * @param message mensaje a mostrar.
     */
    void showAuthenticationDidFailError(String message);

    /**
     * Muestra un mensaje cuando la autenticación se ha llevado a cabo correctamente.
      * @param userName Nombre del usuario.
     */
    void showAuthenticationDidSuccessMessage(String userName);

    /**
     * Cierra la actividad.
     */
    void closeActivity();
}
