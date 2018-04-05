package menu;

import app.App;
import javafx.application.Application;
import utilities.PropertiesReader;
import utilities.XMLReader;
import javafx.scene.control.Button;
import utilities.exception.NoXMLResponseException;


public class MenuOption extends Button {
    private int x,y,width,height;
    private MenuOptionType type;
    private Menu menu;
    private String languageName;
    private int theme = 0;


    public MenuOption(Menu menu, int x, int y, int width, int height, MenuOptionType type){
        this.x=x;
        this.y=y;
        setHeight(height);
        setWidth(width);
        relocate(x,y);

        PropertiesReader properties = new PropertiesReader();
        languageName = properties.getLanguage();

        XMLReader language = new XMLReader();
        switch(type){
            case START:
                try {
                    this.setText(language.getStart(languageName));
                } catch (NoXMLResponseException e) {
                    this.setText("XML Error");
                }
                break;
            case EXIT:
                this.setText(language.getExit(languageName));
                break;
        }

        setOnMouseClicked(e->{
            switch(type){
                case START:
                    menu.getApp().showBoard();
                    break;
                case THEME_DARKORANGE:
                    menu.getApp().changeCSS("file:themes/darkorange.css");
                    break;
                case THEME_DEFAULT:
                    menu.getApp().changeCSS(Application.STYLESHEET_MODENA);
                    break;
                case THEME_GREENBLUE:
                    menu.getApp().changeCSS("file:themes/greenblue.css");
                    break;
                case EXIT:
                    System.exit(0);
                    break;

            }
        });


    }


}
