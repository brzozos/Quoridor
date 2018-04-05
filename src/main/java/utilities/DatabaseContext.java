package utilities;

import board.Board;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class DatabaseContext {
    private String connectionString;
    private static Logger logger;


    public DatabaseContext(){
        PropertiesReader properties = new PropertiesReader();
        connectionString = properties.getConnectionString();
        logger = LogManager.getLogger(DatabaseContext.class.getName());   //Trace level information, separately is to call you when you started in a method or program logic, and logger.trace ("entry") basic a meaning

    }

    public ObservableList<PlayerViewModel> getTop5Players(){
        ObservableList<PlayerViewModel> players = FXCollections.observableArrayList();
        try {
            Connection conn = DriverManager.getConnection(connectionString);
            String sqlStatement = "SELECT \"PlayerName\", \"PlayerCountry\", \"PlayerID\", \"PlayerScore\"" +
                    " FROM public.\"Player\"" +
                    " ORDER BY \"PlayerScore\" desc" +
                    " LIMIT 5";
            PreparedStatement statement =conn.prepareStatement(sqlStatement);
            ResultSet result = statement.executeQuery();

            while(result.next()){
                players.add(new PlayerViewModel(result.getInt("PlayerID"), result.getString("PlayerName"),
                                                result.getString("PlayerCountry"), result.getInt("PlayerScore")));
            }
            logger.info("Selected 5 players with top score form database");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return players;
    }

    public void enterResult(boolean firstWon, String whiteName, String whiteCountry,
                            String blackName, String blacCountry){
        Integer whiteID = null,whiteScore = null,blackID=null, blackScore=null;
        try {
            Connection conn = DriverManager.getConnection(connectionString);
            String sqlStatement = "SELECT \"PlayerID\", \"PlayerScore\"\n" +
                    "\tFROM public.\"Player\"\n" +
                    "    WHERE \"PlayerName\"=? and \"PlayerCountry\" = ?;";
            PreparedStatement statement =conn.prepareStatement(sqlStatement);
            statement.setString(1,whiteName);
            statement.setString(2,whiteCountry);
            ResultSet result = statement.executeQuery();

            while(result.next()){
                whiteID = result.getInt(1);
                whiteScore = result.getInt(2);
            }

            statement.setString(1,blackName);
            statement.setString(2,blacCountry);
            result = statement.executeQuery();

            while(result.next()){
                blackID = result.getInt(1);
                blackScore = result.getInt(2);
            }

            if(whiteID==null||whiteScore==null) {
                sqlStatement="INSERT INTO public.\"Player\"(\n" +
                        "\t\"PlayerName\", \"PlayerCountry\", \"PlayerScore\")\n" +
                        "\tVALUES (?, ?, ?);";
                statement =conn.prepareStatement(sqlStatement);
                statement.setString(1,whiteName);
                statement.setString(2,whiteCountry);
                statement.setInt(3,1000);
                whiteScore = 1000;
                statement.executeUpdate();
                sqlStatement = "SELECT \"PlayerID\"\n" +
                        "\tFROM public.\"Player\"\n" +
                        "    WHERE \"PlayerName\"=? and \"PlayerCountry\" = ?;";
                statement =conn.prepareStatement(sqlStatement);
                statement.setString(1,whiteName);
                statement.setString(2,whiteCountry);
                result = statement.executeQuery();

                while(result.next()){
                    whiteID = result.getInt(1);
                }
            }
            if(blackID==null||blackScore==null) {
                sqlStatement="INSERT INTO public.\"Player\"(\n" +
                        "\t\"PlayerName\", \"PlayerCountry\", \"PlayerScore\")\n" +
                        "\tVALUES (?, ?, ?);";
                statement =conn.prepareStatement(sqlStatement);
                statement.setString(1,blackName);
                statement.setString(2,blacCountry);
                statement.setInt(3,1000);
                blackScore = 1000;
                statement.executeUpdate();
                sqlStatement = "SELECT \"PlayerID\"\n" +
                        "\tFROM public.\"Player\"\n" +
                        "    WHERE \"PlayerName\"=? and \"PlayerCountry\" = ?;";
                statement =conn.prepareStatement(sqlStatement);
                statement.setString(1,blackName);
                statement.setString(2,blacCountry);
                result = statement.executeQuery();

                while(result.next()){
                    blackID = result.getInt(1);
                }
            }

            sqlStatement = "INSERT INTO public.\"Match\"(\n" +
                    "\t\"WhiteID\", \"BlackID\", \"Result\", \"Date\")\n" +
                    "\tVALUES (?, ?, ?, ?);";
            statement =conn.prepareStatement(sqlStatement);
            statement.setInt(1,whiteID);
            statement.setInt(2,blackID);
            statement.setBoolean(3,firstWon);
            Calendar calendar = Calendar.getInstance();
            java.sql.Date ourJavaDateObject = new java.sql.Date(calendar.getTime().getTime());
            statement.setDate(4, ourJavaDateObject);

            statement.executeUpdate();

            if(firstWon){
                whiteScore+=100;
                blackScore-=100;
            }
            else{
                blackScore+=100;
                whiteScore-=100;
            }

            sqlStatement = "UPDATE public.\"Player\"\n" +
                    "\tSET \"PlayerScore\"=?\n" +
                    "\tWHERE \"PlayerID\"=?;";
            statement =conn.prepareStatement(sqlStatement);
            statement.setInt(1,whiteScore);
            statement.setInt(2,whiteID);

            statement.executeUpdate();

            statement.setInt(1,blackScore);
            statement.setInt(2,blackID);

            statement.executeUpdate();
            logger.info("Inserted new match and updated players score "+ whiteName + " and " + blackName);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

