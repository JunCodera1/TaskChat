package controllers;

import com.example.member.Main;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;


import  javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.URL;
import java.util.ResourceBundle;

public class ChatController{



    @FXML
    private VBox vBoxMessages;

    @FXML
    private ScrollPane messageScrollPane;

    @FXML
    private TextField messageTextField;

    @FXML
    private Button sendMessage;

    private Server server;

    public void initialize(URL location, ResourceBundle resourceBundle){
        try {
            server = new Server(new ServerSocket(1234));
            System.out.println("Connected!");

        }catch (IOException e){
            e.printStackTrace();
            System.out.println("Error creating server.");
        }

        vBoxMessages.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                messageScrollPane.setVvalue((Double) newValue);
            }
        });

        server.receiveMessageFromClient(vBoxMessages);

        sendMessage.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String messageToSend = messageTextField.getText();
                if(!messageToSend.isEmpty()){
                    HBox hBox = new HBox();
                    hBox.setAlignment(Pos.CENTER_RIGHT);
                    hBox.setPadding(new Insets(5,5,5,10));

                    Text text = new Text(messageToSend);
                    TextFlow textFlow = new TextFlow(text);

                    textFlow.setStyle("-fx-color: rgb(239, 242, 255);" +
                                      "-fx-background-color: rgb(15,125,142);"+
                                      "-fx-background-radius: 20px;");

                    textFlow.setPadding(new Insets(5,10,5,10));
                    text.setFill(Color.color(0.934, 0.945, 0.996));

                    hBox.getChildren().add(textFlow);
                    vBoxMessages.getChildren().add(hBox);

                    server.sendMessageToClient(messageToSend);
                    messageTextField.clear();;
                }
            }
        });
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


