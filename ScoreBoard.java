/**
 * This class contains the details of the current game's score.
 * @version ver 1.0
 * @author Neelaksh Tayal 1627659
 */
public class ScoreBoard {
    private boolean isPacmanDead;
    private double finalScore;
    private int hits;
    private int foodEaten;
    private int moves;
    private int monstersKilled;
    private int gameNumber;
    private String player;

    /**
     * The default constructor of the ScoreBoard class.
     */
    public ScoreBoard() {}

    /**
     * The parameterized constructor of the ScoreBoard class.
     * @param gameCounter An integer value that keeps the count of games as the program runs.
     */
    public ScoreBoard(int gameCounter) {
        this.isPacmanDead = false;
        this.finalScore = 0;
        this.hits = 0;
        this.foodEaten = 0;
        this.moves = 0;
        this.monstersKilled = 0;
        this.gameNumber = gameCounter;
        this.player = Constants.PLAYER_ONE;
    }

    /**
     * A getter method for the player String value.
     * @return A String value that contains the current player.
     */
    public String getPlayer() {
        return this.player;
    }

    /**
     * A getter method for the finalScore double value.
     * @return A double value that contains the final score of the current player.
     */
    public double getFinalScore() {
        return this.finalScore;
    }

    /**
     * A getter method for the foodEaten integer value.
     * @return An integer value that contains the number of food eaten by pacman.
     */
    public int getFoodEated() {
        return this.foodEaten;
    }

    /**
     * A getter method for the monstersKilled integer value.
     * @return An integer value that contains the number of monsters killed by pacman.
     */
    public int getMonstersKilled() {
        return this.monstersKilled;
    }

    /**
     * A getter method for the hits integer value.
     * @return An integer value that contains the number of times pacman hit a wall/boundary.
     */
    public int getHits() {
        return this.hits;
    }

    /**
     * A getter method for the moves integer value.
     * @return An integer value that contains the number of moves taken by pacman.
     */
    public int getMoves() {
        return this.moves;
    }

    /**
     * A getter method for the gameNumber integer value.
     * @return An integer value that contains the game's number.
     */
    public int getGameNumber() {
        return this.gameNumber;
    }

    /**
     * A setter method for the player String value.
     * @param playerValue A String value that contains the current player.
     */
    public void setPlayer(String playerValue) {
        this.player = playerValue;
    }

    /**
     * A setter method that increments the hits integer value by one.
     */
    public void setHits() {
        this.hits++;
    }

    /**
     * A setter method that increments the foodEaten integer value by one.
     */
    public void eatFood() {
        this.foodEaten++;
    }

    /**
     * A setter method that increments the monstersKilled integer value by one.
     */
    public void killMonster() {
        this.monstersKilled++;
    }

    /**
     * A setter method that increments the moves integer value by one.
     */
    public void setMoves() {
        this.moves++;
    }

    /**
     * A setter method that changes the value of isPacmanDead boolean to true.
     */
    public void killPacman() {
        this.isPacmanDead = true;
    }

    /**
     * This method calculates the final score of the player for the game.
     */
    public void calculateScore() {
        int isPacmanDeadInt = this.isPacmanDead ? 0 : 1;
        this.finalScore = (5 * this.foodEaten) + (10 * this.monstersKilled) - (0.5 * this.hits) - (0.25 * this.moves) + (20 * isPacmanDeadInt);
    }
}
