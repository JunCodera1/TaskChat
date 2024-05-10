package controllers;

import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;

import com.example.member.Main;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.annotation.JsonSerialize;
//import com.fasterxml.jackson.datatype.eclipsecollections.EclipseCollectionsModule;
//import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
//import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.impl.collector.Collectors2;

public class TodoController
{
    @FXML
    private TextField todoItem;

    @FXML
    public ComboBox<ToDoCategory> todoCategory;

    @FXML
    private DatePicker todoDate;

    @FXML
    private TableView<ToDoItem> todoList;
    
    @FXML
    private TableColumn<ToDoItem, String> categoryColumn;

    @FXML
    private Button btnMessagePart;
    @FXML
    private Button btnDashPart;

    @FXML
    protected void initialize()
    {
        MutableList<ToDoItem> items = this.readToDoListFromFile();
        ObservableList<ToDoItem> list = FXCollections.observableList(items);

        this.todoList.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        this.todoList.setItems(list);

        ObservableList<ToDoCategory> categories =
                FXCollections.observableList(
                        Lists.mutable.with(ToDoCategory.values()));

        this.todoCategory.setItems(categories);
    }

    @FXML
    public void onAddButtonClick()
    {
        String text = this.todoItem.getText();
        ToDoCategory category = this.todoCategory.getValue();
        LocalDate date = this.todoDate.getValue();
        if (text == null || category == null || date == null)
        {
            this.displayInvalidInputMessage();
        }
        else
        {
            this.createAndAddToDoItem(text, category, date);
        }
    }

    private void displayInvalidInputMessage()
    {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setHeaderText("Invalid input");
        errorAlert.setContentText("Text, category and date must all be specified.");
        errorAlert.showAndWait();
    }

    private void createAndAddToDoItem(String text, ToDoCategory category, LocalDate date)
    {
        ToDoItem item = new ToDoItem(text, category, date);
        this.todoList.getItems().add(item);
    }

//    @FXML
//    public void onRemoveButtonClick()
//    {
//        int indexToRemove = this.todoList.getSelectionModel().getSelectedIndex();
//        this.todoList.getItems().remove(indexToRemove);
//    }
    @FXML
    public void onRemoveButtonClick() {
        int indexToRemove = this.todoList.getSelectionModel().getSelectedIndex();
        if (indexToRemove >= 0 && indexToRemove < this.todoList.getItems().size()) {
            this.todoList.getItems().remove(indexToRemove);
        }
    }


    private MutableList<ToDoItem> readToDoListFromFile()
    {
//        ObjectMapper mapper = this.getObjectMapper();
        MutableList<ToDoItem> list = null;
//        try
//        {
//            list = mapper.readValue(
//                    Paths.get("todolist.json").toFile(),
//                    new TypeReference<MutableList<ToDoItem>>() {});
//            return list;
//        }
//        catch (IOException e)
//        {
//            System.out.println(e);
//        }
        return Lists.mutable.empty();
    }

    public void writeToDoListToFile()
    {
//        ObjectMapper mapper = this.getObjectMapper();
        MutableList<ToDoItem> list =
                this.todoList.getItems().stream()
                        .collect(Collectors2.toList());
//        try
//        {
//            mapper.writeValue(
//                    Paths.get("todolist.json").toFile(),
//                    list);
//        }
//        catch (IOException e)
//        {
//            throw new RuntimeException(e);
//        }
    }

//    private ObjectMapper getObjectMapper()
//    {
//        ObjectMapper mapper = new ObjectMapper()
//                .registerModule(new EclipseCollectionsModule())
//                .registerModule(new JavaTimeModule());
//        return mapper;
//    }

    public record ToDoItem(
            @JsonProperty String name,
            @JsonProperty ToDoCategory category,
            @JsonProperty LocalDate date)
    {
        @JsonIgnore
        public String getCategory()
        {
            return this.category.getEmoji();
        }

        @JsonIgnore
        public String getName()
        {
            return this.name;
        }

        @JsonIgnore
        public String getDate()
        {
            return this.date.toString();
        }
    }

    public enum ToDoCategory
    {
        EXERCISE("🚴"),
        WORK("📊"),
        RELAX("🧘"),
        TV("📺"),
        READ("📚"),
        EVENT("🎭"),
        CODE("💻"),
        COFFEE("☕️"),
        EAT("🍽"),
        SHOP("🛒"),
        SLEEP("😴");

        private String emoji;

        ToDoCategory(String emoji)
        {
            this.emoji = emoji;
        }

        public String getEmoji()
        {
            return this.emoji;
        }
    }
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
    private void switchChatPart(ActionEvent event) throws IOException {
        switchScene("ChatUI.fxml", event);
    }
}
