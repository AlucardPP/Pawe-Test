package NortonCommander;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;

import java.net.URI;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.beans.EventHandler;
import java.io.File;
import java.net.URL;

import javafx.event.ActionEvent;

/**
 * Created by Alucard on 2016-10-18.
 */
public class RightPaneController implements Initializable {

    @FXML
    private ListView folderView2;

    @FXML
    private TextField searchArea2;

    @FXML
    private ComboBox<File> comboBox2;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        File[] root = File.listRoots();
        for (int i = 0; i < root.length; i++) {
            if (root[i].canRead() == true) {
                comboBox2.getItems().add(root[i]);
            }
        }
        searchArea2.setText("Search");
        searchArea2.addEventFilter(MouseEvent.MOUSE_CLICKED, x -> searchArea2.clear());


        comboBox2.setOnAction(new javafx.event.EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                folderView2.refresh();
                folderView2.getItems().clear();
                folderView2.getItems().add("...");
                folderView2.getItems().addAll(comboBox2.getValue().listFiles());


            }
        });
        folderView2.setOnMouseClicked(new javafx.event.EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.getClickCount()==2){
                    Object observableList=folderView2.getSelectionModel().getSelectedItem();
                    File file=new File(String.valueOf(observableList));

                   if (file.isDirectory()){
                       File[] folder =file.listFiles();
                       folderView2.refresh();
                       folderView2.getItems().clear();
                       folderView2.getItems().add("...");
                       folderView2.getItems().addAll(folder);
                   }




                }
            }
        });






    }
}
