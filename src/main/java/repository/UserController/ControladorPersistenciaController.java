/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository.UserController;

import java.util.List;
import model.user;

/**
 *
 * @author gines
 */
    
public class ControladorPersistenciaController {

    private final userJpaController userJpaController;

    public ControladorPersistenciaController() {
        this.userJpaController = new userJpaController();
    }

    public void crear(user user) {
        try {
            userJpaController.create(user);
            System.out.println("Usuario creado correctamente: " + user.getUsername());
        } catch (Exception e) {
            System.err.println("Error al crear el usuario: " + e.getMessage());
        }
    }

    
    public List<user> buscarTodos() {
        return userJpaController.finduserEntities();
    }

    
    public user buscarPorUsername(String username) {
        return userJpaController.findByUsername(username);
    }

    
    public boolean usuarioExiste(String username) {
        return buscarPorUsername(username) != null;
    }
}