/**
 * This class handles the functioning of the current game and contains its details.
 * @version ver 1.0
 * @author Neelaksh Tayal 1627659
 */
public class Game {
    private int pacmanRow;
    private int pacmanCol;
    private int pacmanPrevRow;
    private int pacmanPrevCol;

    private int powerUp;
    private boolean isGameCompleted;

    // enum for all ghosts.
    enum GhostType {R, B, G, Y}

    /**
     * Default constructor for the class Game.
     */
    public Game() {
        this.powerUp = 0;
        this.isGameCompleted = true;
        this.pacmanCol = Constants.PACMAN_INITIAL_POS;
        this.pacmanRow = Constants.PACMAN_INITIAL_POS;
        this.pacmanPrevCol = Constants.PACMAN_INITIAL_POS;
        this.pacmanPrevRow = Constants.PACMAN_INITIAL_POS;
    }

    /**
     * This method is a getter for the boolean isGameCompleted.
     * @return A boolean value that details if the current game has completed or not.
     */
    public boolean checkGameCompletion() {
        return this.isGameCompleted;
    }
    
    /**
     * This method handles the main functioning of a game for any player.
     * @param maze A Maze object that contains the details of the maze and the positions of all entities.
     * @param score A ScoreBoard object that contains the details required to calculate the score of the current game.
     */
    public void startGame(Maze maze, ScoreBoard score) {
        this.isGameCompleted = false;
        String message = null;  
        do {
            score.calculateScore();
            if (message != null) {
                System.out.println(message);
                
                // if pacman is dead.
                if (message.equals(Messages.DEATH)) {
                    this.endGame(score);
                    break;
                }
            }
            
            // if all ghosts are dead.
            if (maze.areAllGhostsDead()) {
                this.endGame(score);
                break;
            }
            maze.setPacmanPos(this.pacmanRow, this.pacmanCol, this.pacmanPrevRow, this.pacmanPrevCol, message);
            maze.printGrid();
            this.printMovePacmanMenu();
            String movementChoice = GameEngine.keyboard.nextLine().toUpperCase();
            
            // if player chooses to pause the game.
            if (movementChoice.equals(Constants.QUIT)) {
                System.out.println(Messages.GAME_PAUSED);
                this.isGameCompleted = false;
                return;
            }
            this.movePacmanMenu(movementChoice);
            message = this.executeMove(maze, score, movementChoice);
        } while (true);
    }

    /**
     * This method prints the movement menu for pacman.
     */
    private void printMovePacmanMenu() {
        System.out.println("Press W to move up.");
        System.out.println("Press A to move left.");
        System.out.println("Press S to move down.");
        System.out.println("Press D to move right.");
        System.out.println("Press Q to exit.");
        System.out.print("> ");
    }

    /**
     * This method changes the coordinates of pacman and stores the previous coordinates pacman was at.
     * @param movementChoice A String value that contains the user choice for pacman's movement.
     */
    private void movePacmanMenu(String movementChoice) {
        switch (movementChoice) {
            case Constants.UP:
                this.pacmanRow--;
                this.pacmanPrevCol = this.pacmanCol;
                this.pacmanPrevRow = this.pacmanRow + 1;
                break;
            case Constants.DOWN:
                this.pacmanRow++;
                this.pacmanPrevCol = this.pacmanCol;
                this.pacmanPrevRow = this.pacmanRow - 1;
                break;
            case Constants.LEFT:
                this.pacmanCol--;
                this.pacmanPrevRow = this.pacmanRow;
                this.pacmanPrevCol = this.pacmanCol + 1;
                break;
            case Constants.RIGHT:
                this.pacmanCol++;
                this.pacmanPrevRow = this.pacmanRow;
                this.pacmanPrevCol = this.pacmanCol - 1;
                break;
            default:
                System.out.println(Messages.INVALID_INPUT);
        }
    }

    /**
     * This method corrects the position of pacman if he hits a wall/boundary.
     * @param movementChoice A String value that contains the user choice for pacman's movement.
     */
    private void correctPos(String movementChoice) {
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

    /**
     * This method checks where pacman has gone after movement choice and if there are any other entities present on those coordinates.
     * @param maze A Maze object that contains the details of the maze and the positions of all entities.
     * @param score A ScoreBoard object that contains the details required to calculate the score of the current game.
     * @param movementChoice A String value that contains the user choice for pacman's movement.
     * @return A String value that contains the message to be printed based on pacman's position.
     */
    private String executeMove(Maze maze, ScoreBoard score, String movementChoice) {
        char currentItem = maze.getGrid()[this.pacmanRow][this.pacmanCol];
        
        // if pacman has hit a wall/boundary.
        if (currentItem == Constants.MAZE_BOUNDARY || currentItem == Constants.MAZE_WALL) {
            score.setHits();
            this.correctPos(movementChoice);
            if (currentItem == Constants.MAZE_WALL)
                return Messages.WALL_HIT;
            else return Messages.BOUNDARY_HIT;
        }

        // if pacman has landed on a food pellet.
        else if (currentItem == Constants.MAZE_FOOD) {
            this.powerUp++;
            score.setMoves();
            score.eatFood();
            return Messages.POWER_UP;
        } 
        
        // if pacman has landed on a ghost.
        else {
            for (GhostType ghost : GhostType.values()) {
                if (currentItem == ghost.toString().charAt(0))
                    
                    // if pacman has not consumed any food pellets.
                    if (this.powerUp == 0) {
                        score.killPacman();
                        return Messages.DEATH;
                    }

                    // if pacman has eaten some food.
                    else if (this.powerUp > 0) {
                        this.powerUp--;
                        score.setMoves();
                        score.killMonster();
                        int ghostIndex = GhostType.valueOf(Character.toString(currentItem)).ordinal();
                        maze.setGhostKilled(ghostIndex);
                        return Messages.MONSTER_KILLED;
                    }
            }
        }
        score.setMoves();
        return null;
    }

    /**
     * This method ends the game if pacman dies or all ghosts are killed.
     * @param score A ScoreBoard object that contains the details required to calculate the score of the current game.
     */
    private void endGame(ScoreBoard score) {
        this.isGameCompleted = true;
        System.out.println(Messages.GAME_END + score.getFinalScore());
    }
}