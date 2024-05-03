package controllers;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.scene.paint.Color;
import org.w3c.dom.events.MouseEvent;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.URL;
import java.util.EventListener;
import java.util.ResourceBundle;

public class LoginController {

    @FXML
    private HBox accountPart;

    @FXML
    private ImageView attachBox;

    @FXML
    private HBox deadlinePart;

    @FXML
    private ImageView emojiBox;

    @FXML
    private HBox insightPart;

    @FXML
    private HBox messageBox;

    @FXML
    private Pane messagePane;

    @FXML
    private ScrollPane messageScrollPane;

    @FXML
    private TextField messageTextField;

    @FXML
    private ImageView moreBox;

    @FXML
    private ImageView moreInfoBox;

    @FXML
    private HBox overviewPart;

    @FXML
    private ImageView phoneBox;

    @FXML
    private TextField searchField;

    @FXML
    private TextField searchPartField;

    @FXML
    private ImageView sendMessage;

    @FXML
    private HBox settingPart;

    @FXML
    private HBox todoPart;

    @FXML
    private ImageView videoCallBox;

    private Server server;
    public void initialize(URL location, ResourceBundle resourceBundle){
        try {
            server = new Server(new ServerSocket(1234));

        }catch (IOException e){
            e.printStackTrace();
            System.out.println("Error creating server.");
        }

        messagePane.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                messageScrollPane.setVvalue((Double) newValue);
            }
        });

        server.receiveMessageFromClient(messagePane);

        sendMessage.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {

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
                    messagePane.getChildren().add(hBox);

                    server.sendMessageToClient(messageToSend);
                    messageTextField.clear();;
                }
            }
        });
    }
    public static void addLabel(String messageFromClient, VBox vBox){
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setPadding(new Insets(5,5,5,10));

        Text text = new Text(messageFromClient);
        TextFlow textFlow = new TextFlow(text);
        textFlow.setStyle("-fx-background-color: rgb(233, 233, 235);"+
                          "-fx-background-radius: 20px;");
        textFlow.setPadding(new Insets(5, 10, 5, 10));
        hBox.getChildren().add(textFlow);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                vBox.getChildren().add(hBox);
            }
        });
    }
}


