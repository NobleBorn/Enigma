module com.example.enigma {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires org.apache.commons.csv;

    exports com.example.enigma;
    opens com.example.enigma to javafx.fxml;
    exports com.example.enigma.Model;
    opens com.example.enigma.Model to javafx.fxml;
    exports com.example.enigma.Model.Encryption;
    opens com.example.enigma.Model.Encryption to javafx.fxml;
    exports com.example.enigma.Model.Interfaces;
    opens com.example.enigma.Model.Interfaces to javafx.fxml;
    exports com.example.enigma.Model.Client;
    opens com.example.enigma.Model.Client to javafx.fxml;
    exports com.example.enigma.Model.Encryption.Ascii;
    opens com.example.enigma.Model.Encryption.Ascii to javafx.fxml;
    exports com.example.enigma.Model.Encryption.Substitute;
    opens com.example.enigma.Model.Encryption.Substitute to javafx.fxml;
    exports com.example.enigma.Model.Encryption.Shift;
    opens com.example.enigma.Model.Encryption.Shift to javafx.fxml;
    exports com.example.enigma.Controller;
    opens com.example.enigma.Controller to javafx.fxml;
}