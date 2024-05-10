package controllers;

import Model.ClientListener;
import Model.Server;
import com.example.member.Main;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
import java.util.ResourceBundle;

public class ClientController implements Initializable{
    @FXML
    private ScrollPane messageScrollPane;
    @FXML
    private VBox vBoxMessages;
    @FXML
    private Button sendMessage;
    @FXML
    private TextField messageTextField;
    private Server server;
    private static final int PORT = 7000;
    private static final String URL = "localhost";

    @Override
    public void initialize(java.net.URL url, ResourceBundle resourceBundle) {
        try{
            Socket socket = new Socket(URL, PORT);
            System.out.println("Connected to server");

            //Liên tục đọc dữ liệu từ server
            ClientListener clientListener = new ClientListener(socket);
            new Thread(clientListener).start();

            OutputStream outputStream = socket.getOutputStream();
            while (true){
                String message = messageTextField.getText();
                if(!message.isEmpty()){
                    HBox hBox = new HBox();
                    hBox.setAlignment(Pos.CENTER_RIGHT);
                    hBox.setPadding(new Insets(5,5,5,10));
                    Text text = new Text(message);
                    TextFlow textFlow = new TextFlow(text);
                    textFlow.setStyle("-fx-color: rgb(239, 242, 255);" +
                            "-fx-background-color: rgb(15,125,142);"+
                            "-fx-background-radius: 20px;");
                    textFlow.setPadding(new Insets(5,10,5,10));
                    text.setFill(Color.color(0.934, 0.945, 0.996));

                    hBox.getChildren().add(textFlow);
                    vBoxMessages.getChildren().add(hBox);
                    addLabel(message, vBoxMessages);

                    messageTextField.clear();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void addLabel(String messageFromClient, VBox vBoxMessages){
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setPadding(new Insets(5,5,5,10));

        Text text = new Text(messageFromClient);
        TextFlow textFlow = new TextFlow(text);
        textFlow.setStyle("-fx-background-color: rgb(233, 233, 235);"+
                "-fx-background-color: rgb(15,125,242);"+
                "-fx-background-radius: 20px;");
        textFlow.setPadding(new Insets(5, 10, 5, 10));
        hBox.getChildren().add(textFlow);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                vBoxMessages.getChildren().add(hBox);
            }
        });
    }
    @FXML
    private void switchScene(String fxmlPath, ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxmlPath));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private void switchDashPart(ActionEvent event) throws IOException {
        switchScene("Dashboard.fxml", event);
    }

    @FXML
    private void switchToDoPart(ActionEvent event) throws IOException {
        switchScene("todo.fxml", event);
    }
}
