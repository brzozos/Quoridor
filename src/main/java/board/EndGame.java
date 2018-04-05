package board;

import javafx.event.ActionEvent;
import javafx.event.EventTarget;

public class EndGame extends ActionEvent {
    public EndGame(Object source, EventTarget target) {
        super(source, target);
    }
}
