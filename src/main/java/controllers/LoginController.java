package controllers;

import Model.DatabaseConnection;
import com.example.member.Main;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
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
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

import javafx.fxml.FXMLLoader;

public class LoginController implements Initializable {

    @FXML
    private Button btnClose;


    @FXML
    private Label errorMessageLabel;

    @FXML
    private PasswordField pfPassword;

    @FXML
    private TextField tfUsername;

    private String errorMessage = "";
    private Stage stage;
    private Scene scene;
    private Parent root;

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


    public void loginButtonOnAction(ActionEvent event){
        if(tfUsername.getText().isBlank() == false && pfPassword.getText().isBlank() == false){
            validateLogin();
        }else{
            errorMessageLabel.setText("Please enter username and password");
        }
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

    private void switchChatPart(ActionEvent event){
        Parent root = FXMLLoader.load(getClass().getResource("ChatUI.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        btnClose.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                System.exit(0);
            }
        });

    }
    public void validateLogin(){
        DatabaseConnection connectionNow = new DatabaseConnection();
        Connection connectDB = connectionNow.getConnection();
        String verifyLogin = "SELECT count(1) FROM user_account WHERE username = '" + tfUsername.getText() + "' AND password ='" + pfPassword.getText() + "'";
        try{

            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin);

            while(queryResult.next()){
                if(queryResult.getInt(1) == 1){
                    errorMessageLabel.setText("Congratulations!");
                    startHomeWindow();
                }else{
                    errorMessageLabel.setText("Invalid Login. Please try again!");
                }
            }

        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }
    public void createAccountForm(){
        try{
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Register.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.show();
        }catch(Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }

}
