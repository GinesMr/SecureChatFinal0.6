package ControladoresUi;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import services.UserLogSig.userServices;

public class login {

    @FXML
    private TextField username; // Campo para el nombre de usuario

    @FXML
    private PasswordField password; // Campo para la contraseña

    @FXML
    private Button login; // Botón para iniciar sesión

    @FXML
    private Button sign; // Botón para registrarse

    @FXML
    private Label welcome; // Etiqueta para mostrar el mensaje de bienvenida
    @FXML
    private Label error;
    @FXML
    private Label labelname;
    @FXML
    private Label labelpass;
    @FXML
    private Label mensaje;
    @FXML
    private Label  empty;
    @FXML
    private Label exist;

    private userServices userService = new userServices();

    /**
     * Método llamado automáticamente después de que el FXML es cargado.
     */
    @FXML
    public void initialize() {
        // Configurar los eventos de los botones
        login.setOnAction(e -> handleLogin());
        sign.setOnAction(e -> handleSignUp());
    }

    /**
     * Lógica para manejar el botón de inicio de sesión.
     */
    private void handleLogin() {
        String enteredUsername = username.getText();
        String enteredPassword = password.getText();

        // Resetear estilos antes de cualquier validación
        resetFieldStyles();
        exist.setVisible(false);
        invisiblemessage();

        if (enteredUsername.isEmpty() || enteredPassword.isEmpty()) {
            // Aplicar estilo de error a los campos vacíos
            if (enteredUsername.isEmpty()) {
                username.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            }
            if (enteredPassword.isEmpty()) {
                password.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            }

            empty.setVisible(true);
        } else {
            // Usar userServices para validar el usuario
            if (userService.existe(enteredUsername, enteredPassword)) {
                loadDashboard();

            } else {
                // Aplicar estilo de error si la autenticación falla
                username.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
                password.setStyle("-fx-border-color: red; -fx-border-width: 2px;");

                mensaje.setVisible(true);
            }
        }
    }
    private void resetFieldStyles() {
        username.setStyle(null);
        password.setStyle(null);
    }
    private void invisiblemessage() {
       mensaje.setVisible(false);
       empty.setVisible(false);
    }

    /**
     * Lógica para manejar el botón de registro.
     */
    private void handleSignUp() {
        String enteredUsername = username.getText();
        String enteredPassword = password.getText();

        // Resetear estilos antes de cualquier validación
        resetFieldStyles();
        exist.setVisible(false);
        invisiblemessage();

        if (enteredUsername.isEmpty() || enteredPassword.isEmpty()) {
            // Aplicar estilo de error a los campos vacíos
            if (enteredUsername.isEmpty()) {
                username.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            }
            if (enteredPassword.isEmpty()) {
                password.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            }
            empty.setVisible(true);
        } else {
            // Validar si el nombre de usuario ya existe, ignorando la contraseña
            if (userService.existeusername(enteredUsername)) {
                // Usuario ya existe, marcar el campo de nombre de usuario con un borde rojo
                username.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
                password.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
                exist.setVisible(true);
            } else {
                // Crear nuevo usuario
                userService.crearusu(enteredUsername, enteredPassword);
                loadDashboard();
            }
        }
    }

    private void loadDashboard() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/DashBoradml/dashboard.fxml"));  // Cambiado a esta ruta relativa
            Pane dashboardPane = fxmlLoader.load();
            Scene dashboardScene = new Scene(dashboardPane);
            Stage currentStage = (Stage) login.getScene().getWindow();
            currentStage.setScene(dashboardScene);
            currentStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "No se pudo cargar el dashboard", e.getMessage());
        }
    }



    private void showAlert(AlertType alertType, String title, String header, String content) {
        // Crear la alerta
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

     // Aplica la clase 'alert' solo a la alerta

        // Mostrar la alerta
        alert.showAndWait();
    }




}
