package board;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import utilities.DatabaseContext;
import utilities.PropertiesReader;
import utilities.SceneManager;
import utilities.XMLReader;

public class ResultScreen extends GridPane {
    private PlayerType winner;

    private double width, height;
    private Paint backgroungColor = Color.AZURE;
    private TextField whitePlayer;
    private Label whitePlayerLable;
    private TextField whitePlayerCountry;
    private TextField blackPlayer;
    private Label blackPlayerLable;
    private TextField blackPlayerCountry;
    private Label nameLable;
    private Label countryLable;
    private Button enterDataButton;
    private CheckBox whiteWonBox;
    private CheckBox blackWonBox;
    private Label errorLabel;

    public ResultScreen(PlayerType winner, double width, double height){
        this.width = width;
        this.height = height;
        this.winner = winner;
        createResultScreen();
    }

    private void createResultScreen() {
        setPrefSize(width, height);
        setBackground(new Background(new BackgroundFill(backgroungColor, CornerRadii.EMPTY, Insets.EMPTY)));
        setPadding(new Insets(5));
        setHgap(5);
        setVgap(5);
        ColumnConstraints col0 = new ColumnConstraints(50);
        ColumnConstraints col1 = new ColumnConstraints(200);
        ColumnConstraints col2 = new ColumnConstraints(200);
        col1.setHgrow(Priority.ALWAYS);
        col2.setHgrow(Priority.ALWAYS);
        getColumnConstraints().addAll(col0,col1,col2);

        XMLReader xml = new XMLReader();

        whitePlayer = new TextField("Unknown");
        whitePlayerLable = new Label(xml.getWord("player1"));
        whitePlayerCountry = new TextField("Unknown");

        blackPlayer = new TextField("Unknown");
        blackPlayerLable = new Label(xml.getWord("player2"));
        blackPlayerCountry = new TextField("Unknown");

        nameLable = new Label(xml.getWord("name"));
        countryLable = new Label(xml.getWord("country"));

        whiteWonBox = new CheckBox("Winner");
        blackWonBox = new CheckBox("Winner");

        whiteWonBox.setOnMouseClicked(e->{
            blackWonBox.setSelected(false);
        });
        blackWonBox.setOnMouseClicked(e->{
            whiteWonBox.setSelected(false);
        });

        errorLabel = new Label("");
        errorLabel.setTextFill(Color.RED);

        enterDataButton = new Button(xml.getWord("commit"));
        enterDataButton.setOnMouseClicked(e->{
            enterData();
        });

        add(nameLable,0,1);
        add(countryLable,0,2);
        add(whitePlayerLable,1,0);
        add(blackPlayerLable,2,0);
        add(whitePlayer,1,1);
        add(blackPlayer,2,1);
        add(whitePlayerCountry,1,2);
        add(blackPlayerCountry,2,2);
        //add(whiteWonBox,1,3);
        //add(blackWonBox,2,3);
        add(enterDataButton,1,4);
        //add(errorLabel,2,4);
    }

    private void enterData() {
        DatabaseContext db = new DatabaseContext();
        db.enterResult(this.winner==PlayerType.WHITE, whitePlayer.getText(), whitePlayerCountry.getText(),
                blackPlayer.getText(), blackPlayerCountry.getText());
        SceneManager.app.showMenu();
    }
}
