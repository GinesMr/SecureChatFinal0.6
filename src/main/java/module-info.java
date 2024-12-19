module org.example.securechatfinal {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires jakarta.persistence;
    requires java.desktop;
    requires org.hibernate.orm.core;
    requires jdk.compiler;
    requires Java.WebSocket;
    requires org.json;

    // Exportar los paquetes necesarios
    exports ControladoresUi;

    // Permitir acceso de reflexión al paquete model para Hibernate
    opens model to org.hibernate.orm.core;

    // Permitir acceso de reflexión al paquete ControladoresUi para JavaFX FXML
    opens ControladoresUi to javafx.fxml;
}
