module com.example.enigma {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.enigma to javafx.fxml;
    exports com.example.enigma;
}