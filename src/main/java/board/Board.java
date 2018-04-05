package board;


import javafx.scene.Group;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utilities.exception.OccupiedHoleException;

import static app.App.TILES_AMOUNT;
import static app.App.keyHandler;

public class Board {

    private boolean[][] tiles = new boolean[9][9];
    private Tile[][] tilesObjects = new Tile[9][9];
    private Piece[] pieces = new Piece[2];
    private boolean[][] verticalWalls = new boolean[9][9];
    private boolean[][] horizontalWalls = new boolean[9][9];
    private boolean[][] crossSpots = new boolean[9][9];


    private PlayerType turn = PlayerType.WHITE;

    private Group boardGroup = new Group();
    private Group hudGroup = new Group();
    private HUD hud;
    private static Logger logger;

    public Board(Group boardGroup, Group hudGroup){

        logger = LogManager.getLogger(Board.class.getName());   //Trace level information, separately is to call you when you started in a method or program logic, and logger.trace ("entry") basic a meaning
        this.boardGroup=boardGroup;
        this.hudGroup=hudGroup;
        createTiles();
        createWalls();
        createPieces();
        this.hud = new HUD(PlayerType.WHITE);;
        hudGroup.getChildren().add(this.hud);
        logger.log(Level.INFO, "Board created");
    }

    private void createTiles(){
        for(int y = 0; y < TILES_AMOUNT;y++){
            for(int x = 0; x < TILES_AMOUNT; x++){
                Tile tile = new Tile(x,y);
                tilesObjects[x][y] = tile;
                boardGroup.getChildren().add(tile);
            }
        }
    }

    private void createPieces(){
        Piece piece = new Piece(this, PlayerType.WHITE, 4, 8);
        boardGroup.getChildren().add(piece);
        pieces[0]=piece;
        piece = new Piece(this,PlayerType.BLACK, 4, 0);
        boardGroup.getChildren().add(piece);
        pieces[1]=piece;

    }

    private void createWalls(){
        for(int i = 0; i < app.App.WALL_AMOUNT; i++){
            Wall wall = new Wall(this,PlayerType.WHITE,i);
            boardGroup.getChildren().add(wall);
            keyHandler.addListener(wall);
        }
        for(int i = 0; i < app.App.WALL_AMOUNT; i++){
            Wall wall = new Wall(this,PlayerType.BLACK,i);
            boardGroup.getChildren().add(wall);
            keyHandler.addListener(wall);
        }
    }

    public void endTurn(){
        if(turn == PlayerType.WHITE){
            turn = PlayerType.BLACK;
        }
        else turn = PlayerType.WHITE;
        hud.endTurn(turn);
    }
    public void endGame(){
        PlayerType winner = turn;
        turn = PlayerType.NONE;

        hud.endGame(winner);

    }

    public void takeHorizontalWall(int x, int y){
        horizontalWalls[x][y]=true;
        horizontalWalls[x+1][y]=true;
    }

    public void takeVerticalWall(int x, int y){
        verticalWalls[x][y]=true;
        verticalWalls[x][y+1]=true;
    }

    public void takeCrossSpot(int x, int y){
        crossSpots[x][y]=true;
    }

    public boolean checkHorizontalWall(int x, int y){
        try{
            if(!horizontalWalls[x][y]&&!horizontalWalls[x+1][y]&&!crossSpots[x+1][y]) return true;
        }catch (ArrayIndexOutOfBoundsException e){

        }
        return false;
    }

    public void checkVerticalWall(int x, int y) throws OccupiedHoleException {
        if(!verticalWalls[x][y]&&!verticalWalls[x][y+1]&&!crossSpots[x][y+1]) return;
        throw  new OccupiedHoleException();
    }

    public PlayerType getTurn(){
        return turn;
    }

    public Piece[] getPieces() {
        return pieces;
    }

    public boolean[][] getTiles() {
        return tiles;
    }

    public Tile[][] getTilesObjects() {
        return tilesObjects;
    }

    public boolean[][] getVerticalWalls() {
        return verticalWalls;
    }

    public boolean[][] getHorizontalWalls() {
        return horizontalWalls;
    }


}
