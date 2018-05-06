package com.seriatornet.yhondri.seriatornet.Util;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;

/**
 * Created by yhondri on 30/3/18.
 */

public interface OauthService {

    /**
     * Método que conecta con el servicio de OAUTH y hace login según los parámetros.
     * @param email cuenta de correo del usuario.
     * @param password contraseña del usuario.
     * @param listener listener al que se avisará cuando se reciba la respuesta del servidor.
     */
    void signInWithEmailAndPassword(String email, String password, OauthServiceResult listener);
    /**
     * Método que conecta con el servicio de OAUTH y hace el registro de un usuarioo
     * según los parámetros.
     * @param email cuenta de correo del usuario.
     * @param password contraseña del usuario.
     * @param listener listener al que se avisará cuando se reciba la respuesta del servidor.
     */
    void createUserWithEmailAndPassword(String email, String password, OauthServiceResult listener);

    void logOut();
}
