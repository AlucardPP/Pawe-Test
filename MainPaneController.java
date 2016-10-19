package NortonCommander;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Alucard on 2016-10-18.
 */
public class MainPaneController implements Initializable {
    @FXML
    private MenuPaneController menuPaneController;

    @FXML
    private LeftPaneController leftPaneController;

    @FXML
    private RightPaneController rightPaneController;

    @FXML
    private Button copyButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
