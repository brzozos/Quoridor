package app;

import board.Board;
import board.HUD;
import board.PlayerType;
import board.ResultScreen;
import menu.Menu;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utilities.KeyHandler;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import utilities.SceneManager;


public class App extends Application {
    public static final int SIDE_MARGIN = 150;
    public static final int MARGIN = 10;
    public static final int TILE_SIZE = 60;
    public static final int HOLE_SIZE = 10;
    public static final int TILES_AMOUNT = 9;
    public static final int HOLE_AMOUNT = 8;
    public static final int WALL_AMOUNT = 12;

    private Stage primaryStage;
    private Group boardGroup = new Group();
    private Group hudGroup = new Group();

    public static KeyHandler keyHandler = new KeyHandler();
    public HUD hud;
    public Board board;
    public Menu menu;
    private static Logger logger;


    public void showBoard(){
        Scene scene = new Scene(createBoard());
        keyHandler.setScene(scene);
        primaryStage.setTitle("Quoridor");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Parent createBoard() {
        Pane root = new Pane();
        root.setPrefSize(TILE_SIZE*TILES_AMOUNT+ HOLE_SIZE * HOLE_AMOUNT+SIDE_MARGIN, TILE_SIZE*TILES_AMOUNT+ HOLE_SIZE * HOLE_AMOUNT+MARGIN);
        root.getChildren().addAll(boardGroup, hudGroup);
        root.setLayoutX(SIDE_MARGIN);
        root.setLayoutY(MARGIN);
        board = new Board(boardGroup, hudGroup);
        return root;
    }

    public void showMenu(){
        Scene scene = new Scene(createMenu());
        //keyHandler.setScene(scene);
        this.primaryStage.setTitle("Quoridor");
        this.primaryStage.setScene(scene);
        this.primaryStage.show();

        //this.primaryStage.getScene().getStylesheets().add("file:themes/darkorange.css");
    }
    private Parent createMenu(){
        GridPane root = new Menu(this, 200, 200);
        return root;
    }

    public void changeCSS(String url){
        this.primaryStage.getScene().getStylesheets().clear();
        if(url==Application.STYLESHEET_MODENA);
        else this.primaryStage.getScene().getStylesheets().add(url);
    }

    public void showResultScreen(PlayerType winner){
        Scene scene = new Scene(createResultScreen(winner));
        //keyHandler.setScene(scene);
        this.primaryStage.setTitle("Result");
        this.primaryStage.setScene(scene);
        this.primaryStage.show();
    }

    private Parent createResultScreen(PlayerType winner) {
        GridPane root = new ResultScreen(winner, 200,200);
        return root;
    }

    @Override
    public void start(Stage primaryStage){

        //setUserAgentStylesheet("file:themes/darkorange.css");
        this.primaryStage = primaryStage;
        SceneManager.app = this;
        showMenu();
        logger.log(Level.INFO, "App created");


    }

    public static void main(String[] args) {
        logger = LogManager.getLogger(App.class.getName());   //Trace level information, separately is to call you when you started in a method or program logic, and logger.trace ("entry") basic a meaning

        launch(args);
    }
}
