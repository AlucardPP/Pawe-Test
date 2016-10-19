package NortonCommander;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import java.beans.EventHandler;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

/**
 * Created by Alucard on 2016-10-18.
 */
public class LeftPaneController implements Initializable {

    @FXML
    private ListView folderView1;

    @FXML
    private TextField searchArea1;

    @FXML
    private ComboBox<File> comboBox1;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        File[] root = File.listRoots();
        for (int i = 0; i < root.length; i++) {
            if (root[i].canRead() == true) {
                comboBox1.getItems().add(root[i]);
            }
        }
        searchArea1.setText("Search");
        searchArea1.addEventFilter(MouseEvent.MOUSE_CLICKED, x -> searchArea1.clear());


        comboBox1.setOnAction(new javafx.event.EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                folderView1.refresh();
                folderView1.getItems().clear();
                folderView1.getItems().add("...");
                folderView1.getItems().addAll(comboBox1.getValue().listFiles());


            }
        });

        folderView1.setOnMouseClicked(new javafx.event.EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.getClickCount()==2){
                    Object observableList=folderView1.getSelectionModel().getSelectedItem();
                    File file=new File(String.valueOf(observableList));
                    String parent=file.getParent();
                    if (file.isDirectory()){
                        File[] folder =file.listFiles();
                        folderView1.refresh();
                        folderView1.getItems().clear();
                        folderView1.getItems().add("...");
                        folderView1.getItems().addAll(folder);

                    }

                }
            }
        });


    }


}
