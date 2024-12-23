    package ControladoresUi;

    import javafx.animation.FadeTransition;
    import javafx.animation.ScaleTransition;
    import javafx.application.Platform;
    import javafx.fxml.FXML;
    import javafx.fxml.FXMLLoader;
    import javafx.scene.Scene;
    import javafx.scene.control.*;
    import javafx.scene.control.Alert.AlertType;
    import javafx.scene.layout.Pane;
    import javafx.stage.Stage;
    import javafx.stage.StageStyle;
    import javafx.util.Duration;
    import javafx.geometry.Pos;
    import javafx.scene.layout.VBox;
    import javafx.scene.paint.Color;
    import javafx.animation.Timeline;
    import javafx.animation.KeyFrame;
    import javafx.scene.shape.SVGPath;
    import javafx.animation.ParallelTransition;
    import javafx.animation.Interpolator;
    import javafx.animation.FadeTransition;
    import javafx.application.Platform;
    import javafx.concurrent.Task;
    import javafx.fxml.FXMLLoader;
    import javafx.scene.Scene;
    import javafx.scene.control.Alert;
    import javafx.scene.control.Label;
    import javafx.scene.control.ProgressIndicator;
    import javafx.scene.layout.Pane;
    import javafx.scene.layout.VBox;
    import javafx.stage.Stage;
    import javafx.stage.StageStyle;
    import javafx.util.Duration;

    import java.io.File;
    import java.io.FileNotFoundException;
    import java.net.URL;
    import services.UserLogSig.userServices;

    import java.io.File;
    import java.io.FileNotFoundException;
    import java.net.URL;

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
                System.out.println("Iniciando carga del dashboard...");
                Stage loadingStage = new Stage(StageStyle.UNDECORATED);
                Pane loadingPane = new Pane();

                Stage currentStage = (Stage) login.getScene().getWindow();
                if (currentStage == null) {
                    throw new IllegalStateException("Current stage is null");
                }

                loadingPane.setPrefSize(currentStage.getWidth(), currentStage.getHeight());
                loadingPane.setStyle("-fx-background-color: #0E0E10;");

                VBox centerContainer = new VBox();
                centerContainer.setAlignment(Pos.CENTER);
                centerContainer.setSpacing(30);
                centerContainer.setLayoutY((currentStage.getHeight() - 200) / 2);
                centerContainer.setLayoutX(0);
                centerContainer.setPrefWidth(currentStage.getWidth());
                centerContainer.setOpacity(0);

                Label preparingLabel = new Label("PREPARING YOUR DASHBOARD");
                preparingLabel.setStyle("""
            -fx-font-family: 'Inter', 'Segoe UI', system-ui, sans-serif;
            -fx-font-size: 24px;
            -fx-text-fill: white;
            -fx-font-weight: bold;
        """);

                ProgressIndicator progressIndicator = new ProgressIndicator();
                progressIndicator.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
                progressIndicator.setPrefSize(50, 50);
                progressIndicator.setStyle("-fx-progress-color: #9147FF;");

                Label statusLabel = new Label("Loading resources...");
                statusLabel.setStyle("""
            -fx-font-family: 'Inter', 'Segoe UI', system-ui, sans-serif;
            -fx-font-size: 14px;
            -fx-text-fill: #ADADB8;
        """);

                centerContainer.getChildren().addAll(preparingLabel, progressIndicator, statusLabel);
                loadingPane.getChildren().add(centerContainer);

                Scene loadingScene = new Scene(loadingPane);
                loadingStage.setScene(loadingScene);
                loadingStage.setX(currentStage.getX());
                loadingStage.setY(currentStage.getY());
                loadingStage.setWidth(currentStage.getWidth());
                loadingStage.setHeight(currentStage.getHeight());

                // Animación de entrada para la pantalla de carga
                FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.3), centerContainer);
                fadeIn.setFromValue(0);
                fadeIn.setToValue(1);
                fadeIn.setInterpolator(Interpolator.EASE_IN);

                ScaleTransition scaleIn = new ScaleTransition(Duration.seconds(0.3), centerContainer);
                scaleIn.setFromX(0.8);
                scaleIn.setFromY(0.8);
                scaleIn.setToX(1);
                scaleIn.setToY(1);
                scaleIn.setInterpolator(Interpolator.EASE_OUT);

                ParallelTransition parallelIn = new ParallelTransition(fadeIn, scaleIn);

                Platform.runLater(() -> {
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/DashBoradml/dashboard.fxml"));
                        Pane dashboardPane = fxmlLoader.load();
                        dashboard dashboardController = fxmlLoader.getController();

                        if (dashboardController == null) {
                            throw new IllegalStateException("No se pudo obtener el controlador del dashboard");
                        }

                        Scene dashboardScene = new Scene(dashboardPane);
                        dashboardPane.setOpacity(0);

                        // Animación de salida para la pantalla de carga
                        FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.3), centerContainer);
                        fadeOut.setFromValue(1);
                        fadeOut.setToValue(0);
                        fadeOut.setInterpolator(Interpolator.EASE_OUT);

                        ScaleTransition scaleOut = new ScaleTransition(Duration.seconds(0.3), centerContainer);
                        scaleOut.setFromX(1);
                        scaleOut.setFromY(1);
                        scaleOut.setToX(0.8);
                        scaleOut.setToY(0.8);
                        scaleOut.setInterpolator(Interpolator.EASE_IN);

                        ParallelTransition parallelOut = new ParallelTransition(fadeOut, scaleOut);

                        parallelOut.setOnFinished(e -> {
                            currentStage.setScene(dashboardScene);

                            // Animación de entrada para el dashboard
                            FadeTransition dashboardFadeIn = new FadeTransition(Duration.seconds(0.3), dashboardPane);
                            dashboardFadeIn.setFromValue(0);
                            dashboardFadeIn.setToValue(1);
                            dashboardFadeIn.setInterpolator(Interpolator.EASE_OUT);

                            ScaleTransition dashboardScaleIn = new ScaleTransition(Duration.seconds(0.3), dashboardPane);
                            dashboardScaleIn.setFromX(1.1);
                            dashboardScaleIn.setFromY(1.1);
                            dashboardScaleIn.setToX(1);
                            dashboardScaleIn.setToY(1);
                            dashboardScaleIn.setInterpolator(Interpolator.EASE_OUT);

                            ParallelTransition dashboardParallelIn = new ParallelTransition(
                                    dashboardFadeIn,
                                    dashboardScaleIn
                            );

                            dashboardController.setUsername(username.getText());
                            loadingStage.close();
                            currentStage.show();
                            dashboardParallelIn.play();
                        });

                        parallelIn.play();
                        Timeline timeline = new Timeline(new KeyFrame(
                                Duration.seconds(1),
                                evt -> parallelOut.play()
                        ));
                        timeline.play();

                    } catch (Exception e) {
                        System.err.println("Error al cargar el dashboard: " + e.getMessage());
                        e.printStackTrace();
                        loadingStage.close();
                        showAlert(Alert.AlertType.ERROR,
                                "Error",
                                "Error al cargar el dashboard",
                                "Detalles: " + e.getMessage());
                    }
                });

                loadingStage.show();

            } catch (Exception e) {
                System.err.println("Error inicial: " + e.getMessage());
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR,
                        "Error",
                        "No se pudo iniciar la carga",
                        "Detalles: " + e.getMessage());
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
