module com.example.enigma {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires org.apache.commons.csv;

    opens com.example.enigma to javafx.fxml;
    exports com.example.enigma;
    exports com.example.enigma.Model;
    opens com.example.enigma.Model to javafx.fxml;
}