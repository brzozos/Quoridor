package board;

import app.App;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import utilities.exception.OccupiedFieldException;
import utilities.exception.OutOfBoardException;

public class Piece extends StackPane{
    private PlayerType playerType;

    private double mouseX, mouseY;

    private double oldX, oldY, newPosX, newPosY, oldPosX, oldPosY;
    //int z 0>x>-1 tak jak z 1>x>0 robi 0 i muszę wiedzieć czy jest ujemny y żeby grę zakończyć
    private double negativeY;

    private boolean[][] alailableMoves = new boolean[9][9];

    private Board board;


    public Piece(Board board,PlayerType playerType,int x, int y){
        this.playerType=playerType;
        this.board=board;

        move(x, y);

        Ellipse piece = new Ellipse(App.TILE_SIZE* 0.3, App.TILE_SIZE* 0.3);
        piece.setFill(playerType== PlayerType.WHITE ? Color.WHITE : Color.BLACK);
        piece.setStroke(playerType!= PlayerType.WHITE ? Color.WHITE : Color.BLACK);
        piece.setStrokeWidth(App.TILE_SIZE*0.03);

        piece.setTranslateX((App.TILE_SIZE+ App.HOLE_SIZE*2)/2 - App.TILE_SIZE* 0.3);
        piece.setTranslateY((App.TILE_SIZE+ App.HOLE_SIZE*2)/2 - App.TILE_SIZE* 0.3);

        getChildren().add(piece);

        setOnMousePressed(e -> {
            if(checkTurn()){
                mouseX = e.getSceneX() - App.SIDE_MARGIN;
                mouseY = e.getSceneY();
                setAvailablePaths();
            }


        });

        setOnMouseDragged(e -> {
            if(checkTurn()){
                relocate(e.getSceneX()- App.SIDE_MARGIN - mouseX + oldX, e.getSceneY() - mouseY + oldY);
                newPosX = (int)(e.getSceneX()- App.SIDE_MARGIN)/(App.TILE_SIZE+ App.HOLE_SIZE);
                newPosY = (int)(e.getSceneY())/(App.TILE_SIZE+ App.HOLE_SIZE);
                negativeY = (e.getSceneY())/(App.TILE_SIZE+ App.HOLE_SIZE);
            }
        });

        setOnMouseReleased(e ->{
            if(checkTurn()){
                try {
                    checkNewPosition((int) newPosX,(int) newPosY);
                    move((int) newPosX, (int) newPosY);
                    this.board.endTurn();
                } catch (OutOfBoardException e1) {
                    move((int)oldPosX, (int)oldPosY);

                } catch (OccupiedFieldException e1) {
                    move((int)oldPosX, (int)oldPosY);

                } finally {
                    removeAvailablePaths();
                }

            }

        });
    }

    public void move(int x, int y){
        board.getTiles()[(int)oldPosX][(int)oldPosY] = false;
        oldX = x*(App.TILE_SIZE+ App.HOLE_SIZE) - App.HOLE_SIZE;
        oldY = y*(App.TILE_SIZE+ App.HOLE_SIZE) - App.HOLE_SIZE;
        oldPosX = x;
        oldPosY = y;
        relocate(oldX, oldY);
        board.getTiles()[x][y] = true;
    }

    public void checkNewPosition(Integer x, Integer y) throws OutOfBoardException, OccupiedFieldException {
        if(x==null || y == null){
            x=(int)newPosX;
            y=(int)newPosY;
        }
        if((playerType==PlayerType.WHITE&&negativeY<0&&oldPosY==0)|| (playerType==PlayerType.BLACK&&y>8&&oldPosY==8)) board.endGame();
        if(x>8||x<0||y>8||y<0) throw new OutOfBoardException();
        if(!board.getTiles()[x][y]) {
            //RIGHT
            if (x - oldPosX == 1 && y - oldPosY == 0 && !board.getVerticalWalls()[x][y]){
                return;
            }
            //LEFT
            else if (x - oldPosX == -1 && y - oldPosY == 0 && !board.getVerticalWalls()[x + 1][y]){
                return;
            }
            //DOWN
            else if (y - oldPosY == 1 && x - oldPosX == 0 && !board.getHorizontalWalls()[x][y]){
                return;
            }
            //UP
            else if (y - oldPosY == -1 && x - oldPosX == 0 && !board.getHorizontalWalls()[x][y + 1]){
                return;
            }


            for(int i = 0; i < App.TILES_AMOUNT; i ++) {
                for (int j = 0; j < App.TILES_AMOUNT; j++) {
                    if (board.getTiles()[i][j]) {
                        if (oldPosX == i + 1 && oldPosY == j && !board.getVerticalWalls()[i+1][j] || oldPosX == i - 1 && oldPosY == j && !board.getVerticalWalls()[i][j] || oldPosX == i && oldPosY == j + 1 && !board.getHorizontalWalls()[i][j+1] || oldPosX == i && oldPosY == j - 1 && !board.getHorizontalWalls()[i][j]) {
                            if (x == i + 1 && y == j && !board.getVerticalWalls()[i+1][j]) {
                                return;
                            } else if (x == i - 1 && y == j && !board.getVerticalWalls()[i][j]) {
                                return;
                            } else if (x == i && y == j + 1 && !board.getHorizontalWalls()[i][j+1]) {
                                return;
                            } else if (x == i && y == j - 1 && !board.getHorizontalWalls()[i][j]) {
                                return;
                            }
                        }
                    }
                }
            }
        }
        throw new OccupiedFieldException();
    }

    public void setAvailablePaths(){
        int x=(int)oldPosX;
        int y=(int)oldPosY;
        for(int j = 0; j < App.TILES_AMOUNT;j++){
            for(int i = 0; i < App.TILES_AMOUNT; i++){
                try {
                    checkNewPosition(i,j);
                    board.getTilesObjects()[i][j].setFill(Color.rgb(0,255,0));
                    alailableMoves[i][j]=true;
                } catch (OutOfBoardException e) {

                } catch (OccupiedFieldException e) {

                }
            }
        }
    }

    public void removeAvailablePaths(){
        int x=(int)oldPosX;
        int y=(int)oldPosY;
        for(int j = 0; j < App.TILES_AMOUNT;j++){
            for(int i = 0; i < App.TILES_AMOUNT; i++){
                board.getTilesObjects()[i][j].setFill(board.getTilesObjects()[i][j].getColor());
                alailableMoves[i][j]=false;
            }
        }
    }


    public boolean checkTurn(){
        return playerType == board.getTurn();
    }

    public double getOldPosX() {
        return oldPosX;
    }

    public void setOldPosX(double oldPosX) {
        this.oldPosX = oldPosX;
    }

    public double getOldPosY() {
        return oldPosY;
    }

    public void setOldPosY(double oldPosY) {
        this.oldPosY = oldPosY;
    }
}
