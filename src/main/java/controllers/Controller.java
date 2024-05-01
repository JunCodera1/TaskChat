package controllers;

import com.example.member.Main;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXMLLoader;

public class Controller implements Initializable {

    @FXML
    private Button btnClose;


    @FXML
    private Button btnLogin;

    @FXML
    private Label errorMessageLabel;

    @FXML
    private PasswordField pfPassword;

    @FXML
    private TextField tfUsername;

    private String errorMessage = "";

    private boolean isFieldsFilled() {
        errorMessage = "";
        boolean isFilled = true;

        if (tfUsername.getText().isEmpty()) {
            errorMessage = "Username is empty!";
            isFilled = false;
        }
        if (pfPassword.getText().isEmpty()) {
            if (!errorMessage.isEmpty()) {
                errorMessage += "\n";
            }
            errorMessage += "Password is empty!";
            isFilled = false;
        }

        errorMessageLabel.setText(errorMessage);
        return isFilled;
    }

    private boolean isValid() {
        errorMessage = "";
        boolean isValid = true;
        if (!tfUsername.getText().equals(Main.USERNAME)) {
            errorMessage = "Invalid Username!";
            isValid = false;
        }
        if (!pfPassword.getText().equals(Main.PASSWORD)) {
            if (!errorMessage.isEmpty()) {
                errorMessage += "\n";
            }
            errorMessage += "Invalid Password!";
            isValid = false;
        }

        errorMessageLabel.setText(errorMessage);
        return isValid;
    }

    private void startHomeWindow() {
        try {
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Dashboard.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnClose.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                System.exit(0);
            }
        });

        btnLogin.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (isFieldsFilled() && isValid()) {
                    startHomeWindow();
                }
            }
        });
    }
}
