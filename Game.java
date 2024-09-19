public class Game {
    private int pacmanRow;
    private int pacmanCol;
    private int pacmanPrevRow;
    private int pacmanPrevCol;

    private int powerUp;
    private boolean isGameCompleted;

    enum GhostType {R, B, G, Y}

    public Game() {
        this.powerUp = 0;
        this.isGameCompleted = true;
        this.pacmanCol = Constants.PACMAN_INITIAL_POS;
        this.pacmanRow = Constants.PACMAN_INITIAL_POS;
        this.pacmanPrevCol = Constants.PACMAN_INITIAL_POS;
        this.pacmanPrevRow = Constants.PACMAN_INITIAL_POS;
    }

    public boolean checkGameCompletion() {
        return this.isGameCompleted;
    }
    
    public void startGame(Maze maze, ScoreBoard score) {
        this.isGameCompleted = false;
        String message = null;  
        do {
            score.calculateScore(maze);
            if (message != null) {
                System.out.println(message);
                if (message.equals(Messages.DEATH)) {
                    this.endGame(score);
                    break;
                }
            }
            if (maze.areAllGhostsDead()) {
                this.endGame(score);
                break;
            }
            maze.setPacmanPos(this.pacmanRow, this.pacmanCol, this.pacmanPrevRow, this.pacmanPrevCol, message);
            maze.printGrid();
            this.printMovePacmanMenu();
            String movementChoice = GameEngine.keyboard.nextLine().toUpperCase();
            if (movementChoice.equals(Constants.QUIT)) {
                System.out.println(Messages.GAME_PAUSED);
                this.isGameCompleted = false;
                return;
            }
            this.movePacmanMenu(movementChoice);
            message = this.executeMove(maze, score, movementChoice);
        } while (true);
    }

    private void printMovePacmanMenu() {
        System.out.println("Press W to move up.");
        System.out.println("Press A to move left.");
        System.out.println("Press S to move down.");
        System.out.println("Press D to move right.");
        System.out.println("Press Q to exit.");
        System.out.print("> ");
    }

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

    private String executeMove(Maze maze, ScoreBoard score, String movementChoice) {
        char currentItem = maze.getGrid()[this.pacmanRow][this.pacmanCol];
        if (currentItem == Constants.MAZE_BOUNDARY || currentItem == Constants.MAZE_WALL) {
            score.setHits();
            this.correctPos(movementChoice);
            if (currentItem == Constants.MAZE_WALL)
                return Messages.WALL_HIT;
            else return Messages.BOUNDARY_HIT;
        } else if (currentItem == Constants.MAZE_FOOD) {
            this.powerUp++;
            score.setMoves();
            score.eatFood();
            return Messages.POWER_UP;
        } else {
            for (GhostType ghost : GhostType.values()) {
                if (currentItem == ghost.toString().charAt(0))
                    if (this.powerUp == 0) {
                        score.killPacman();
                        return Messages.DEATH;
                    }
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

    private void endGame(ScoreBoard score) {
        this.isGameCompleted = true;
        System.out.println(Messages.GAME_END + score.getFinalScore());
    }
}