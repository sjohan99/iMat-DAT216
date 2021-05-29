package sample;

import javafx.scene.control.Button;

import java.util.ArrayList;
import java.util.List;

/**
 * Groups the top menu buttons and make them relate to each other.
 */

public class ButtonGrouper {
    
    /**
     * Holds which buttons are in relation.
     */
    List<Button> siblings;
    
    public ButtonGrouper() {
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

    void activateCheckoutButtons(String pressedButtonId, int step) {

        for (Button button : siblings) {
            button.getStyleClass().removeAll("sidebar_button_pressed");
            button.getStyleClass().removeAll("sidebar_buttonNH");
            button.getStyleClass().add("sidebar_button_grey");
        }

        for (int i = 0; i < step; i++) {
            siblings.get(i).getStyleClass().removeAll("sidebar_button_pressed");
            siblings.get(i).getStyleClass().removeAll("sidebar_button_grey");
            siblings.get(i).getStyleClass().add("sidebar_buttonNH");
        }
        siblings.get(step).getStyleClass().removeAll("sidebar_button_grey");
        siblings.get(step).getStyleClass().add("sidebar_button_pressed");

    }

    void activateDateButtons(String pressedButtonId) {
        for (Button button : siblings) {
            if (button.getId().equals(pressedButtonId)) {
                button.getStyleClass().removeAll("");
                button.getStyleClass().add("delivery_date_button_pressed");
            }
        }
    }
}
