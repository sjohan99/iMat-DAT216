package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import java.net.URL;
import java.util.ResourceBundle;

public class HistoryController implements Initializable {

    private UIController parentController;
    public BackendController backend = new BackendController();

    @FXML AnchorPane historyViewParentAnchorPane, historyGuidePane, historyItemPane;
    @FXML Label totalprisLabel, timeLabel;
    public HistoryController(UIController parentController) { this.parentController = parentController; }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        historyItemPane.toFront();
        //historyGuidePane.toFront(); // uncomment to test other view
    }
}
