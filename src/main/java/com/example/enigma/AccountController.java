package com.example.enigma;

import com.example.enigma.Model.FileManager;
import com.example.enigma.Model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;

public class AccountController {

    @FXML TextField userName;
    @FXML TextField newPassword;
    @FXML TextField newConfirm;
    @FXML TextField newCode;
    @FXML Label informationLabel;

    private final PaneManager paneManager;
    private final FileManager csv;
    private final User user;

    public AccountController() {
        paneManager = PaneManager.getInstance();
        user = User.getInstance();
        csv = new FileManager("src/main/resources/com/example/users.csv");
    }

    public void setUserName(String name) {
        userName.setText(name);
    }

    public void changePassword() {
        boolean validPass = passWordValidator(newPassword.getText());

        if (!newPassword.getText().isEmpty() && !newConfirm.getText().isEmpty()) {
            if (newPassword.getText().equals(newConfirm.getText()) && validPass) {
                updateFile(newPassword.getText(), user.getSecretCode());
                informationLabel.setText("Password changed");
            } else if (!validPass) {
                informationLabel.setText("Not a valid password");
            } else {
                informationLabel.setText("Passwords do not match");
            }
        } else if (newPassword.getText().isEmpty()) {
            informationLabel.setText("Provide a password");
        } else {
            informationLabel.setText("Confirm the new password");
        }
    }

    public void changeCode() {
        if (!newCode.getText().isEmpty()) {
            updateFile(user.getPassword(), newCode.getText());
            informationLabel.setText("Secret code changed");
        } else {
            informationLabel.setText("Provide a new pokemon name");
        }
    }

    private boolean passWordValidator(String text) {
        return text.matches("[a-zA-Z0-9!@#$%^&*()-=_+\\\\[\\\\]{}|;':\\\",./<>?\\s]+");
    }

    private void updateFile(String passWord, String secretCode) {
        try {
            CSVParser csvParser = csv.readFromFile();

            List<CSVRecord> records = csvParser.getRecords();

            modifyRecord(records, String.valueOf(user.getUserId()), user.getName(),
                    passWord, secretCode);

            csvParser.close();

            File existingFile = new File("src/main/resources/com/example/users.csv");
            boolean deleted = existingFile.delete();

            CSVPrinter csvPrinter = csv.writeToFile();

            if (deleted){
                for (CSVRecord record : records) {
                    csvPrinter.printRecord(record);
                }
            } else {
                System.out.println("Not deleted!");
            }

            csvPrinter.flush();
            csvPrinter.close();
            csvParser.close();

        } catch (IOException e) {
            System.out.println("Error occurred while writing to the CSV file: " + e.getMessage());
        }
    }

    private void modifyRecord(List<CSVRecord> records, String id, String name, String password,
                              String secretCode) throws IOException {
        String[] newValues = {id, name, password, secretCode};

        for (int i = 0; i < records.size(); i++) {
            CSVRecord record = records.get(i);
            if (record.get(0).equals(id)) {
                CSVRecord newRecord = CSVFormat.DEFAULT
                        .parse(new StringReader(String.join(",", newValues)))
                        .iterator()
                        .next();

                records.set(i, newRecord);
            }
        }
    }
}
