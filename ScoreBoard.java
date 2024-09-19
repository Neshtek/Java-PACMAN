public class ScoreBoard {
    private boolean isPacmanDead;
    private double finalScore;
    private int hits;
    private int foodEaten;
    private int moves;
    private int monstersKilled;
    private int gameNumber;
    private String player;

    public ScoreBoard() {}

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

    public String getPlayer() {
        return this.player;
    }

    public double getFinalScore() {
        return this.finalScore;
    }

    public int getFoodEated() {
        return this.foodEaten;
    }

    public int getMonstersKilled() {
        return this.monstersKilled;
    }

    public int getHits() {
        return this.hits;
    }

    public int getMoves() {
        return this.moves;
    }

    public int getGameNumber() {
        return this.gameNumber;
    }
    
    public void setPlayer(String playerValue) {
        this.player = playerValue;
    }

    public void setHits() {
        this.hits++;
    }

    public void eatFood() {
        this.foodEaten++;
    }

    public void killMonster() {
        this.monstersKilled++;
    }

    public void setMoves() {
        this.moves++;
    }

    public void killPacman() {
        this.isPacmanDead = true;
    }

    public void calculateScore(Maze maze) {
        int isPacmanDeadInt = this.isPacmanDead ? 0 : 1;
        this.finalScore += (20 * isPacmanDeadInt);
        this.finalScore += (10 * this.monstersKilled);
        this.finalScore += (5 * this.foodEaten);
        this.finalScore -= (0.5 * this.hits);
        this.finalScore -= (0.25 * this.moves);
    }

}
