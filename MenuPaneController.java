package NortonCommander;

import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;

/**
 * Created by Alucard on 2016-10-18.
 */
public class MenuPaneController implements Initializable {

    @FXML
    private MenuBar menuBar;

    @FXML
    private Menu editMenu;

    @FXML
    private Menu toolsMenu;

    @FXML
    private Menu optionsMenu;

    @FXML
    private Menu windwoMenu;

    @FXML
    private Menu helpMenu;

    @FXML
    private Menu fileMenu;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
