public class Game {
    private int pacmanRow;
    private int pacmanCol;

    private int noOfPlayers;
    private int multiPlayerCounter;
    private int powerUp;
    private ScoreBoard score;
    private static int gameCounter = 0;
    private boolean isGameCompleted;

    enum GhostType {R, B, G, Y};
    
    public Game() {
        this.noOfPlayers = Constants.SINGLE_PLAYER;
        this.powerUp = 0;
        this.isGameCompleted = true;
        this.pacmanCol = Constants.PACMAN_INITIAL_POS;
        this.pacmanRow = Constants.PACMAN_INITIAL_POS;
        this.multiPlayerCounter = 0;
    }

    public int getNoOfPlayers() {
        return this.noOfPlayers;
    }

    public boolean checkGameCompletion() {
        return this.isGameCompleted;
    }
    
    public void setNoOfPlayers(int noOfPlayers) {
        this.noOfPlayers = noOfPlayers;
    }

    public void startGame(Maze maze) {
        do {
            if (this.isGameCompleted != false) {
                Game.gameCounter++;
                this.score = new ScoreBoard(Game.gameCounter);
            }
            if (this.noOfPlayers == Constants.MULTI_PLAYER && multiPlayerCounter > 0) {
                maze = new Maze(maze);
                this.score.setPlayer(Constants.PLAYER_TWO);
                System.out.printf(Messages.PLAYER_GAME_START, Constants.MULTI_PLAYER);
            } else {
                this.score.setPlayer(Constants.PLAYER_ONE);
                System.out.printf(Messages.PLAYER_GAME_START, Constants.SINGLE_PLAYER);
            }

            String message = null;  
            do {
                this.score.calculateScore(maze);
                if (message != null) {
                    System.out.println(message);
                    if (message.equals(Messages.DEATH)) {
                        this.endGame();
                        break;
                    }
                }
                if (maze.areAllGhostsDead()) {
                    this.endGame();
                    break;
                }
                maze.setPacmanPos(this.pacmanRow, this.pacmanCol);
                maze.printGrid();
                this.printMovePacmanMenu();
                char movementChoice = Character.toUpperCase(GameEngine.keyboard.nextLine().charAt(0));
                if (movementChoice == Constants.QUIT) {
                    System.out.println(Messages.GAME_PAUSED);
                    this.isGameCompleted = false;
                    return;
                }
                this.movePacmanMenu(movementChoice);
                message = this.executeMove(maze, movementChoice);
            } while (true);

            multiPlayerCounter++;
        } while (multiPlayerCounter != this.noOfPlayers);
    }

    private void printMovePacmanMenu() {
        System.out.println("Press W to move up.");
        System.out.println("Press A to move left.");
        System.out.println("Press S to move down.");
        System.out.println("Press D to move right.");
        System.out.println("Press Q to exit.");
        System.out.print("> ");
    }

    private void movePacmanMenu(char movementChoice) {
        switch (movementChoice) {
            case Constants.UP:
                this.pacmanRow--;
                break;
            case Constants.DOWN:
                this.pacmanRow++;
                break;
            case Constants.LEFT:
                this.pacmanCol--;
                break;
            case Constants.RIGHT:
                this.pacmanCol++;
                break;
            default:
                System.out.println(Messages.INVALID_INPUT);
        }
    }

    private void correctPos(char movementChoice) {
        switch (movementChoice) {
            case Constants.UP:
                this.pacmanRow++;
                break;
            case Constants.DOWN:
                this.pacmanRow--;
                break;
            case Constants.LEFT:
                this.pacmanCol++;
                break;
            case Constants.RIGHT:
                this.pacmanCol--;
                break;
        }
    }

    private String executeMove(Maze maze, char movementChoice) {
        switch (maze.getGrid()[pacmanRow][pacmanCol]) {
            case Constants.MAZE_BOUNDARY:
                this.score.setHits();
                this.correctPos(movementChoice);
                return Messages.BOUNDARY_HIT;
            case Constants.MAZE_WALL:
                this.score.setHits();
                this.correctPos(movementChoice);
                return Messages.WALL_HIT;
            case Constants.MAZE_FOOD:
                this.powerUp++;
                this.score.setMoves();
                this.score.eatFood();
                return Messages.POWER_UP;
            case Constants.R:
            case Constants.B:
            case Constants.G:
            case Constants.Y:
                if (this.powerUp == 0) {
                    this.score.killPacman();
                    return Messages.DEATH;
                }
                else if (powerUp > 0) {
                    this.powerUp--;
                    this.score.setMoves();
                    this.score.killMonster();
                    int ghostIndex = GhostType.valueOf(Character.toString(maze.getGrid()[pacmanRow][pacmanCol])).ordinal();
                    maze.setGhostKilled(ghostIndex);
                    return Messages.MONSTER_KILLED;
                }
            default:
                this.score.setMoves();
                return null;
        }
    }

    private void endGame() {
        this.isGameCompleted = true;
        System.out.println(Messages.GAME_END + this.score.getFinalScore());
        GameEngine.addCompletedGame(this.score);
    }
}