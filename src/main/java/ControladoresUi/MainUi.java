import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import services.UserLogSig.userServices;

public class MainUi extends Application {

    userServices userService = new userServices();

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Carga el archivo FXML de la vista de Login
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1024, 577);

        // Obtiene el controlador de la vista cargada
        loginController loginController = fxmlLoader.getController();

        // Establecer el título de la ventana principal
        primaryStage.setTitle("SecureChat");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Configurar el evento de iniciar sesión
        loginController.setMainUi(this);
    }


    public void createUser(String username, String password) {
        userService.crearusu(username, password);
    }
}
