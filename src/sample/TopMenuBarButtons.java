package sample;

import javafx.scene.control.Button;

import java.util.ArrayList;
import java.util.List;

/**
 * Groups the top menu buttons and make them relate to each other.
 */

public class TopMenuBarButtons {
    
    /**
     * Holds which buttons are in relation.
     */
    List<Button> siblings;
    
    public TopMenuBarButtons() {
        this.siblings = new ArrayList<Button>();
    }
    
    void addButtonToList(Button button) {
        siblings.add(button);
    }
    
    List<Button> getButtons() {
        return siblings;
    }
    
    /**
     * Toggles the pressed button by changing its background color and makes every other button untoggled.
     * @param pressedButtonId
     */
    void activate(String pressedButtonId) {
        for (Button button : siblings) {
            if (!(button.getId() == pressedButtonId)) {
                button.setStyle("-fx-background-color: #F2F0E9");
            }
            else button.setStyle("-fx-background-color: #F2B84B");
        }
    }
}
