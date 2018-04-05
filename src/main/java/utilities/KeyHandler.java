package utilities;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

import java.util.ArrayList;
import java.util.List;

public class KeyHandler {
    private List<KeyListener> listeners = new ArrayList<KeyListener>();
    private Scene scene;

    public void addListener(KeyListener l){
        listeners.add(l);
    }

    public void signal(){
        for(KeyListener l : listeners){
            l.keyPressed();
        }
    }


    public void setScene(Scene scene){
        this.scene = scene;
        scene.setOnKeyReleased(e->{
           if(e.getCode()== KeyCode.SPACE) signal();
        });
    }

}
