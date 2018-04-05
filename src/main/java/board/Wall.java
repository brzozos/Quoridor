package board;


import app.App;
import utilities.KeyListener;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import utilities.exception.OccupiedHoleException;

public class Wall extends Rectangle implements KeyListener {

    private Board board;

    private WallType wallType = WallType.HORIZONTAL;
    private PlayerType playerType;
    private int index;
    private double mouseX, mouseY, liveMouseX, liveMouseY;
    private double oldX, oldY, newX, newY, newPosX, newPosY, oldPosX, oldPosY;
    private boolean isMoving = false;
    private boolean placed = false;
    private PathChecker pc;

    public Wall(Board board, PlayerType playerType,int index){
        this.playerType = playerType;
        this.index = index;
        this.board = board;

        refreshSize();

        if(playerType==playerType.WHITE){
            oldX = (App.TILE_SIZE+ App.HOLE_SIZE)* App.TILES_AMOUNT;
            oldY = (App.TILE_SIZE+ App.HOLE_SIZE)* App.TILES_AMOUNT - (index+1)*20;
            relocate((App.TILE_SIZE+ App.HOLE_SIZE)* App.TILES_AMOUNT , (App.TILE_SIZE+ App.HOLE_SIZE)* App.TILES_AMOUNT - (index+1)*20);
            setFill(Color.GRAY);
            setStroke(Color.BLACK);
            setStrokeWidth(1);
        }else{
            oldX = -App.SIDE_MARGIN;
            oldY = (index+1)*20;
            relocate(-App.SIDE_MARGIN , (index+1)*20);
            setFill(Color.BLACK);
            setStroke(Color.GRAY);
            setStrokeWidth(1);
        }

        setOnMousePressed(e -> {
            if(chceckTurn()&&!placed&&!isMoving){
                mouseX = e.getSceneX() - App.SIDE_MARGIN;
                mouseY = e.getSceneY();
            }
        });

        setOnMouseDragged(e -> {
            if(wallType==WallType.HORIZONTAL){
                if(chceckTurn()&&!placed){
                    relocate(e.getSceneX()- App.SIDE_MARGIN - mouseX + oldX, e.getSceneY() - mouseY + oldY);
                    isMoving=true;
                    liveMouseX = e.getSceneX() - App.SIDE_MARGIN;
                    liveMouseY = e.getSceneY();
                    newPosX = (e.getSceneX() - App.SIDE_MARGIN - mouseX + oldX + App.TILE_SIZE/2)/(App.TILE_SIZE+ App.HOLE_SIZE);
                    newPosY = (e.getSceneY() - mouseY + oldY)/(App.TILE_SIZE+ App.HOLE_SIZE);
                }
            }else {
                if (chceckTurn()&&!placed) {
                    relocate(e.getSceneX() - App.SIDE_MARGIN, e.getSceneY() - mouseY + oldY);
                    isMoving = true;
                    liveMouseX = e.getSceneX() - App.SIDE_MARGIN;
                    liveMouseY = e.getSceneY();
                    newPosX = (e.getSceneX() - App.SIDE_MARGIN + App.TILE_SIZE/2)/(App.TILE_SIZE+ App.HOLE_SIZE);
                    newPosY = (e.getSceneY() - mouseY + oldY + App.TILE_SIZE/2)/(App.TILE_SIZE+ App.HOLE_SIZE);
                }
            }

        });

        setOnMouseReleased((MouseEvent e) ->{
            if(!placed){
                int x,y;

                if(newPosX<=9&&newPosX>=0&&newPosY<=8&&newPosY>=0){
                    newPosY = (int)newPosY;
                    newPosX = (int)newPosX;
                    if(wallType==WallType.HORIZONTAL){
                        x=(int) newPosX;
                        y=(int) newPosY+1;
                        pc = new PathChecker(this.board, x, y, WallType.HORIZONTAL);
                        if(pc.checkWallPlacement()){
                            if(isMoving&&board.checkHorizontalWall(x,y)){
                                moveHorizontal(x,y);
                                board.takeHorizontalWall(x,y);
                                board.takeCrossSpot(x+1,y);
                                board.endTurn();
                                this.placed=true;
                            }
                            else{
                                relocate(oldX,oldY);
                            }
                        }else{
                            relocate(oldX,oldY);
                        }

                    }else{
                        x=(int) newPosX;
                        y=(int) newPosY;
                        pc = new PathChecker(board, x, y, WallType.VERTICAL);
                        if(pc.checkWallPlacement()){
                            try{
                                board.checkVerticalWall(x,y);
                                if(isMoving){
                                    moveVertical(x,y);
                                    board.takeVerticalWall(x,y);
                                    board.takeCrossSpot(x, y+1);
                                    board.endTurn();
                                    this.placed=true;
                                }
                                else{
                                    changeOrientation();
                                    relocate(oldX,oldY);
                                }
                            } catch (OccupiedHoleException e1) {
                                changeOrientation();
                                relocate(oldX,oldY);
                            }
                        }
                        else{
                            changeOrientation();
                            relocate(oldX,oldY);
                        }

                    }
                }else{
                    if(wallType==WallType.HORIZONTAL) relocate(oldX,oldY);
                    else {
                        changeOrientation();
                        relocate(oldX,oldY);
                    }
                }
                isMoving=false;
            }

        });

    }



    public boolean chceckTurn(){
        return playerType == board.getTurn();
    }

    public void moveHorizontal(int x, int y){
        oldX = x*(App.TILE_SIZE+ App.HOLE_SIZE);
        oldY = y*(App.TILE_SIZE+ App.HOLE_SIZE) - App.HOLE_SIZE;
        oldPosX = x;
        oldPosY = y;
        relocate(oldX, oldY);
    }
    public void moveVertical(int x, int y){
        oldX = x*(App.TILE_SIZE+ App.HOLE_SIZE) - App.HOLE_SIZE;
        oldY = y*(App.TILE_SIZE+ App.HOLE_SIZE);
        oldPosX = x;
        oldPosY = y;
        relocate(oldX, oldY);
    }

    public void changeOrientation(){
        if(wallType==WallType.HORIZONTAL) wallType=WallType.VERTICAL;
        else wallType=WallType.HORIZONTAL;
        refreshSize();
    }

    public void refreshSize(){
        if(wallType==WallType.HORIZONTAL){
            setWidth(2* App.TILE_SIZE+ App.HOLE_SIZE);
            setHeight(App.HOLE_SIZE);
        }else{
            setHeight(2* App.TILE_SIZE+ App.HOLE_SIZE);
            setWidth(App.HOLE_SIZE);
        }
    }

    @Override
    public void keyPressed()
    {
        if(isMoving){
            changeOrientation();
            this.relocate(liveMouseX,liveMouseY);
        }
    }
}
