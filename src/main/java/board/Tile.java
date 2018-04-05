package board;

import app.App;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Tile extends Rectangle
{
    private Piece piece;
    private Color color = Color.BLUE;



    public Tile(int x, int y){
        setWidth(App.TILE_SIZE);
        setHeight(App.TILE_SIZE);

        relocate(x*(App.TILE_SIZE+ App.HOLE_SIZE), y*(App.TILE_SIZE+ App.HOLE_SIZE));

        setFill(color);
        setStroke(Color.BLACK);
        setStrokeWidth(3);

    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

}
