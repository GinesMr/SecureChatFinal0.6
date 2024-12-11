/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services.UserLogSig;

import model.user;
import repository.UserController.ControladorPersistenciaController;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author gines
 */
public class userServices {

        ControladorPersistenciaController controladorPersistenciaController = new ControladorPersistenciaController();

        public void crearusu(String Username ,String password){

            if(existe(Username,password)== false){
                user user = new user(Username,password);
                    controladorPersistenciaController.crear(user);
            }

        }


    public boolean existe(String username, String password) {
        List<user> usuarios = controladorPersistenciaController.buscarTodos();

        for (user usuario : usuarios) {
            if (usuario.getUsername().equals(username) && usuario.getPublicKey().equals(password)) {
                System.out.println("Usuario encontrado: " + usuario.getUsername());
                return true;
            }
        }

        System.out.println("Usuario no encontrado.");
        return false;
    }

    public boolean existeusername(String username) {
        List<user> usuarios = controladorPersistenciaController.buscarTodos();

        for (user usuario : usuarios) {
            if (usuario.getUsername().equals(username)) {
                System.out.println("Usuario con cuenta: " + usuario.getUsername());
                return true;
            }
        }

        System.out.println("Usuario sin cuenta.");
        return false;
    }

}
