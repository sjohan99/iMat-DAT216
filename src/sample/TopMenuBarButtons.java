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
     * Toggles the pressed button by removing all style-classes added to the button and then adding the wanted one.
     * @param pressedButtonId
     */
    void activate(String pressedButtonId) {
        // Otroligt ful kod men enda som jag fick det att fungera med haha / Johan
        for (Button button : siblings) {
            if (!(button.getId().equals(pressedButtonId))) {
                button.getStyleClass().removeAll("round_button_pressed");
                button.getStyleClass().removeAll("round_button");
                button.getStyleClass().add("round_button");
            }
            else {
                button.getStyleClass().removeAll("round_button");
                button.getStyleClass().removeAll("round_button_pressed");
                button.getStyleClass().add("round_button_pressed");
            }
            //System.out.println(button.getStyleClass());
        }
    }
    
    void activateSideBarButtons(String pressedButtonId) {
        for (Button button : siblings) {
            if (!(button.getId().equals(pressedButtonId))) {
                button.getStyleClass().removeAll("sidebar_button_pressed");
                button.getStyleClass().removeAll("sidebar_button");
                button.getStyleClass().add("sidebar_button");
            } else {
                button.getStyleClass().removeAll("sidebar_button");
                button.getStyleClass().removeAll("sidebar_button_pressed");
                button.getStyleClass().add("sidebar_button_pressed");
            }
        }
    }

    void activateCheckoutButtons(String pressedButtonId) {
        //TODO: Hjäääälp Johan!!!
        for (Button button : siblings) {
            if (!(button.getId().equals(pressedButtonId))) {
                button.getStyleClass().removeAll("sidebar_button_pressed");
                button.getStyleClass().removeAll("sidebar_button");
                button.getStyleClass().add("sidebar_button");
            } else {
                button.getStyleClass().removeAll("sidebar_button");
                button.getStyleClass().removeAll("sidebar_button_pressed");
                button.getStyleClass().add("sidebar_button_pressed");
            }
        }
    }
}
