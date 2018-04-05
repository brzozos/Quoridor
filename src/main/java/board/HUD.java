package board;

import app.App;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import utilities.SceneManager;

public class HUD extends StackPane{

    private Ellipse piece;


    public HUD(PlayerType playerType){
        relocate(App.MARGIN+(App.TILE_SIZE+ App.HOLE_SIZE)* App.TILES_AMOUNT, 0);
        piece = new Ellipse(App.TILE_SIZE* 0.3, App.TILE_SIZE* 0.3);
        piece.setFill(playerType== PlayerType.WHITE ? Color.WHITE : Color.BLACK);
        piece.setStroke(playerType!= PlayerType.WHITE ? Color.WHITE : Color.BLACK);
        piece.setStrokeWidth(App.TILE_SIZE*0.03);

        piece.setTranslateX((App.TILE_SIZE+ App.HOLE_SIZE*2)/2 - App.TILE_SIZE* 0.3);
        piece.setTranslateY((App.TILE_SIZE+ App.HOLE_SIZE*2)/2 - App.TILE_SIZE* 0.3);

        getChildren().add(piece);
    }

    public void endTurn(PlayerType playerType){
        piece.setFill(playerType== PlayerType.WHITE ? Color.WHITE : Color.BLACK);
        piece.setStroke(playerType!= PlayerType.WHITE ? Color.WHITE : Color.BLACK);
    }

    public void endGame(PlayerType winner){
        piece.setFill(Color.RED);
        SceneManager.app.showResultScreen(winner);
    }



}
