package utilities;

public class PlayerViewModel {
    private int playerID;
    private String playerName;
    private String playerCountry;
    private int playerScore;

    public PlayerViewModel(int playerID, String playerName, String playerCountry, int score) {
        this.playerID = playerID;
        this.playerName = playerName;
        this.playerCountry = playerCountry;
        this.playerScore = score;
    }

    public int getPlayerID() {
        return playerID;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getPlayerCountry() {
        return playerCountry;
    }

    public void setPlayerCountry(String playerCountry) {
        this.playerCountry = playerCountry;
    }

    public int getPlayerScore() {
        return playerScore;
    }

    public void setPlayerScore(int playerScore) {
        this.playerScore = playerScore;
    }
}
