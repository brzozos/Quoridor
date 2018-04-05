package menu;

import app.App;
import javafx.beans.binding.Bindings;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import utilities.DatabaseContext;
import utilities.PlayerViewModel;
import utilities.XMLReader;
import utilities.webapi.CountryModel;
import utilities.webapi.WebApiResponse;
import utilities.exception.WrongNumberOfResultsException;

import java.io.IOException;


public class Menu extends GridPane {
    //private Group menuGroup;
    private App app;
    private Button startButton;
    private Button exitButton;
    private Button defaultThemeButton;
    private Button darkThemeButton;
    private Button greenThemeButton;
    private TableView<PlayerViewModel> playersTable;
    private Image flag;
    private ImageView flagView = new ImageView();
    private XMLReader xml;
    private double width, height;
    public static final Color backgroungColor = Color.AZURE;
    public static final Color buttonColor = Color.LIGHTBLUE;
    public static final Color buttonFontColor = Color.WHITE;


    public Menu(App app, double width, double height){
        //this.menuGroup = new Group();
        //getChildren().add(this.menuGroup);
        this.width = width;
        this.height = height;
        this.app = app;
        this.xml = new XMLReader();
        createMenu();
    }

    public void createMenu() {
        setPrefSize(width, height);
        setBackground(new Background(new BackgroundFill(backgroungColor, CornerRadii.EMPTY, Insets.EMPTY)));
        setPadding(new Insets(5));
        setHgap(5);
        setVgap(5);
        ColumnConstraints col1 = new ColumnConstraints(200);
        ColumnConstraints col2 = new ColumnConstraints(200);
        col2.setHgrow(Priority.ALWAYS);
        getColumnConstraints().addAll(col1,col2);


        startButton = new MenuOption(this, 100,100,100,50,MenuOptionType.START);
        exitButton = new MenuOption(this, 100,150,100,50,MenuOptionType.EXIT);
        defaultThemeButton = new MenuOption(this, 100,150,100,50,MenuOptionType.THEME_DEFAULT);
        darkThemeButton = new MenuOption(this, 100,150,100,50,MenuOptionType.THEME_DARKORANGE);
        greenThemeButton = new MenuOption(this, 100,150,100,50,MenuOptionType.THEME_GREENBLUE);

        //ELO ELO TESTY

//
//        Button testResultButton = new Button();
//        testResultButton.setOnAction(new EventHandler<ActionEvent>() {
//            @Override public void handle(ActionEvent e) {
//                SceneManager.app.showResultScreen(PlayerType.WHITE);
//            }
//        });


         //ELOELO TESTY

        TableColumn<PlayerViewModel, String> nameColumn = new TableColumn<>(xml.getWord("player"));
        //nameColumn.setMinWidth();
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("playerName"));

        TableColumn<PlayerViewModel, String> countryColumn = new TableColumn<>(xml.getWord("country"));
        //nameColumn.setMinWidth();
        countryColumn.setCellValueFactory(new PropertyValueFactory<>("playerCountry"));

        TableColumn<PlayerViewModel, Integer> scoreColumn = new TableColumn<>(xml.getWord("score"));
        //nameColumn.setMinWidth();
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("playerScore"));
        DatabaseContext dbContext = new DatabaseContext();
        playersTable = new TableView<>();

        Task<Void> databaseReadTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                playersTable.setItems(dbContext.getTop5Players());

                return null;
            }
        };

        databaseReadTask.run();



        playersTable.getColumns().addAll(nameColumn, countryColumn,scoreColumn);
        playersTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        playersTable.setFixedCellSize(25);
        playersTable.prefHeightProperty().bind(Bindings.size(playersTable.getItems()).multiply(playersTable.getFixedCellSize()).add(30));



        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try{
                    WebApiResponse response = new WebApiResponse();
                    CountryModel country = response.getResponse(playersTable.getItems().get(0).getPlayerCountry());
                    flag = new Image("file:" + response.downloadImg(country));
                    flagView = new ImageView(flag);
                    flagView.setFitHeight(50);
                    flagView.setFitWidth(50);
                } catch (WrongNumberOfResultsException e) {
                    flag = new Image("file:cache/Pirate.png");
                    flagView = new ImageView(flag);
                    flagView.setFitHeight(50);
                    flagView.setFitWidth(50);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };

        task.run();

        GridPane left = new GridPane();
        setPadding(new Insets(5));
        setHgap(5);
        setVgap(5);
        ColumnConstraints colLeft1 = new ColumnConstraints(100);
        colLeft1.setHgrow(Priority.ALWAYS);
        left.getColumnConstraints().add(colLeft1);
        ColumnConstraints colLeft2 = new ColumnConstraints(100);
        colLeft2.setHgrow(Priority.ALWAYS);
        left.getColumnConstraints().add(colLeft2);
        GridPane right = new GridPane();
        setPadding(new Insets(5));
        setHgap(5);
        setVgap(5);
        ColumnConstraints colRight = new ColumnConstraints(200);
        colRight.setHgrow(Priority.ALWAYS);
        right.getColumnConstraints().addAll(colRight);

        //left.add(quoridorLabel, 0, 2);
        left.add(startButton, 0, 0);
        left.add(exitButton,1,0);
        left.add(flagView,0,1);
        left.add(defaultThemeButton,0,2);
        left.add(darkThemeButton,0,3);
        left.add(greenThemeButton,0,4);
        //left.add(testResultButton,0,1);

        right.add(playersTable,0,0);

        add(left,0,0);
        add(right,1,0);

//        add(quoridorLabel, 0, 0);
//        add(startButton,1,0);
//        add(exitButton,1,1);
//        add(playersTable,2,0);


    }



    public App getApp() {
        return app;
    }

    public void setApp(App app) {
        this.app = app;
    }
}
