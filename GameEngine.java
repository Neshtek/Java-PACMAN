import java.util.Arrays;
import java.util.Scanner;

/**
 * This class contains the main backbone code for the entire game.
 * @version ver 1.0
 * @author Neelaksh Tayal 1627659
 */
public class GameEngine {
    private String[] args;
    private ScoreBoard[] completedGames;
    private Maze maze;
    private Maze mazeCopy;
    private Game game;
    private ScoreBoard score;
    private int gameCounter;
    private int noOfPlayers;

    public static Scanner keyboard = new Scanner(System.in);

    /**
     * The main method that starts the execution of the game.
     * @param args An array of strings passed in as command line arguments (defines the maze dimensions and seed for random generator).
     */
    public static void main(String[] args) {
        GameEngine engine = new GameEngine();

        // check if the arguments entered through command line are valid.
        if (engine.isInvalidArgs(args)) {
            System.out.println(Messages.INVALID_ARGS);
        } else {
            engine.displayMessage();
            do {
                engine.printPlayerMenu();
                int playerChoice = Integer.parseInt(keyboard.nextLine());
                
                // if user chooses the exit option, the program ends.
                if (playerChoice == Constants.EXIT_PLAYER) {
                    System.out.println(Messages.EXIT_PROGRAM);
                    break;
                }
                engine.playerMenu(playerChoice);
            } while (true);
        }
    }

    /**
     * The default constructor for the GameEngine class.
     */
    public GameEngine() {
        this.completedGames = new ScoreBoard[0];
        this.maze = null;
        this.mazeCopy = null;
        this.game = null;
        this.gameCounter = 0;
    }
    
    /**
     * This method adds a completed game's details into the completedGames array.
     * @param score A ScoreBoard object that contains the details of a completed game.
     */
    private void addCompletedGame(ScoreBoard score) {

        // create an array that is one larger in size and copy all contents.
        this.completedGames = Arrays.copyOf(this.completedGames, this.completedGames.length + 1);
        
        // add the latest game details at the end.
        this.completedGames[this.completedGames.length - 1] = score;
    }

    /**
     * This method determines the winner of a multiplayer game and stores the winners details into the completedGames array.
     */
    private void printWinner() {
        if (this.noOfPlayers == Constants.MULTI_PLAYER && this.game.checkGameCompletion()) {
            ScoreBoard scoreOne = this.completedGames[this.completedGames.length - 2];
            ScoreBoard scoreTwo = this.completedGames[this.completedGames.length - 1];
            
            // if Player 1 wins, reduce array size by 1 and store only Player 1's game details.
            if (scoreOne.getFinalScore() >= scoreTwo.getFinalScore()) {
                System.out.printf(Messages.PLAYER_WINS, Constants.SINGLE_PLAYER);
                this.completedGames[this.completedGames.length - 2] = scoreOne;
                this.completedGames = Arrays.copyOf(this.completedGames, this.completedGames.length - 1);
            }
            
            // if Player 2 wins, reduce array size by 1 and store only Player 2's game details.
            else {
                System.out.printf(Messages.PLAYER_WINS, Constants.MULTI_PLAYER);
                this.completedGames[this.completedGames.length - 2] = scoreTwo;
                this.completedGames = Arrays.copyOf(this.completedGames, this.completedGames.length - 1);
            }
        }
    }
    
    /**
     * This method asks the user what type of maze they wanna create.
     */
    private void setMaze() {
        do {
            this.printMazeMenu();
            int mazeType = Integer.parseInt(keyboard.nextLine());
            this.mazeMenu(mazeType);
        } while (this.maze == null);
    }

    /**
     * This method prints the game mode selection menu.
     */
    private void printPlayerMenu() {
        System.out.println("Make player selection.");
        System.out.println("Press 1 for Single Player.");
        System.out.println("Press 2 for Multi Player.");
        System.out.println("Press 3 to exit.");
        System.out.print("> ");
    }

    /**
     * This method performs actions based on the selected game mode.
     * @param playerChoice An integer that stores the user choice for the game mode menu.
     */
    private void playerMenu(int playerChoice) {
        
        // store number of players based on user choice.
        switch (playerChoice) {
            case Constants.SINGLE_PLAYER:
                this.noOfPlayers = Constants.SINGLE_PLAYER;
                break;
            case Constants.MULTI_PLAYER:
                this.noOfPlayers = Constants.MULTI_PLAYER;
                break;
            default:
                System.out.println(Messages.INVALID_INPUT);
                return;
        }
        
        // take the user to the main menu once the game mode is selected.
        do {
            this.printMainMenu();
            int mainChoice = Integer.parseInt(keyboard.nextLine());
            
            // if user selects to exit main menu, they go back to the game mode menu.
            if (mainChoice == Constants.EXIT_MAIN) {
                System.out.println(Messages.EXIT_MENU);
                return;
            }
            this.mainMenu(mainChoice);
        } while (true);
    }

    /**
     * This method prints the main game menu.
     */
    private void printMainMenu() {
        System.out.println("Select an option to get started.");
        System.out.println("Press 1 to select a pacman maze type.");
        System.out.println("Press 2 to play the game.");
        System.out.println("Press 3 to resume the game.");
        System.out.println("Press 4 to view the scores.");
        System.out.println("Press 5 to exit.");
        System.out.print("> ");
    }

    /**
     * This method takes the necessary actions based on user choice in main menu.
     * @param mainChoice An integer that stores the user choice for the main menu.
     */
    private void mainMenu(int mainChoice) {
        switch (mainChoice) {
            
            // Option 1 Select maze type.
            case Constants.SELECT_MAZE:
                this.setMaze();
                System.out.println(Messages.MAZE_SET);
                this.game = new Game();
                break;

            // Option 2 Start a new game.
            case Constants.PLAY_GAME:
                // if maze not defined
                if (this.maze == null) {
                    System.out.println(Messages.MAZE_UNSET);
                    break;
                }

                // if a previous incomplete game is found.
                else if (this.game.checkGameCompletion() == false) {
                    System.out.print(Messages.GAME_INCOMPLETE);
                    String discardChoice = keyboard.nextLine().toUpperCase();

                    // if user chooses to preserve the previous game.
                    if (discardChoice.equals(Constants.NO))
                        break;

                    // if user chooses to discard the previous game.    
                    else {
                        this.maze = null;
                        this.setMaze();
                        System.out.println(Messages.NEW_MAZE_SET);
                        this.game = new Game();
                    }
                }

                // the normal functioning, where no incomplete games were found and a maze has been defined.
                if (this.noOfPlayers == Constants.MULTI_PLAYER)
                    this.mazeCopy = new Maze(maze);
                int multiPlayerCounter = 0;
                this.gameCounter++;
                do {
                    System.out.println(Messages.START_GAME);
                    this.score = new ScoreBoard(this.gameCounter);
                    
                    // if the game running is for Player 2.
                    if (multiPlayerCounter > 0) {
                        this.game = new Game();
                        this.maze = this.mazeCopy;
                        this.score.setPlayer(Constants.PLAYER_TWO);
                        System.out.printf(Messages.PLAYER_GAME_START, Constants.MULTI_PLAYER);
                    } 
                    
                    // if the game running is for Player 1.
                    else {
                        this.score.setPlayer(Constants.PLAYER_ONE);
                        System.out.printf(Messages.PLAYER_GAME_START, Constants.SINGLE_PLAYER);
                    }

                    this.game.startGame(this.maze, this.score);
                    
                    // if game completed by a player, add to completedGames array and reset the maze.
                    if (this.game.checkGameCompletion()) {
                        this.addCompletedGame(this.score);
                        this.maze = null;
                    } else break;

                    multiPlayerCounter++;
                } while (multiPlayerCounter < this.noOfPlayers);

                this.printWinner();
                break;

            // Option 3 Resume previous game.
            case Constants.RESUME_GAME:
                // if maze is not defined.
                if (this.maze == null) {
                    System.out.println(Messages.MAZE_UNSET);
                    break;
                } 
                
                // if an incomplete game is found.
                else if (!this.game.checkGameCompletion()) {
                    System.out.println(Messages.RESTART_GAME);

                    // if the incomplete game was for Player 1.
                    if (this.score.getPlayer() == Constants.PLAYER_ONE) {
                        multiPlayerCounter = 0;
                        System.out.printf(Messages.PLAYER_GAME_START, Constants.SINGLE_PLAYER);
                        do {
                            // reaching Player 2's game after Player 1 completes the incomplete game.
                            if (multiPlayerCounter > 0) {                                
                                System.out.println(Messages.START_GAME);
                                this.maze = this.mazeCopy;
                                this.game = new Game();
                                this.score = new ScoreBoard(this.gameCounter);
                                this.score.setPlayer(Constants.PLAYER_TWO);
                                System.out.printf(Messages.PLAYER_GAME_START, Constants.MULTI_PLAYER);
                            }

                            this.game.startGame(this.maze, this.score);

                            // if game completed by a player, add to completedGames array and reset the maze.
                            if (this.game.checkGameCompletion()) {
                                this.addCompletedGame(this.score);
                                this.maze = null;
                            } else break;

                            multiPlayerCounter++;
                        } while (multiPlayerCounter < this.noOfPlayers);
                    }

                    // if the incomplete game was for Player 2.
                    else { 
                        System.out.printf(Messages.PLAYER_GAME_START, Constants.MULTI_PLAYER);
                        this.game.startGame(this.maze, this.score);

                        // if game completed by a player, add to completedGames array and reset the maze.
                        if (this.game.checkGameCompletion()) {
                            this.addCompletedGame(this.score);
                            this.maze = null;
                        } else break;
                    }
                    this.printWinner();
                    break;
                }

                // if no incomplete games are found, but maze has been defined.
                System.out.println(Messages.NO_PAUSED_GAME);
                break;

            // Option 4 View scores.
            case Constants.VIEW_SCORES:
                // if no games have been completed.
                if (this.completedGames.length == 0) {
                    System.out.println(Messages.NO_COMPLETED_GAMES);
                    break;
                } 
                
                // if completed games exist.
                else {
                    System.out.printf(Constants.LEADERBOARD_HEADER_FORMAT, Constants.GAME_NO, Constants.PLAYER_NAME, Constants.FOOD_EATEN, Constants.MONSTER_KILLED, Constants.HITS, Constants.MOVES, Constants.SCORE);
                    System.out.println(Constants.LEADERBOARD_SEPERATOR);
                    for (int gameIndex = 0; gameIndex < this.completedGames.length; gameIndex++) {
                        System.out.printf(Constants.LEADERBOARD_FORMAT, 
                                        this.completedGames[gameIndex].getGameNumber(), 
                                        this.completedGames[gameIndex].getPlayer(), 
                                        this.completedGames[gameIndex].getFoodEated(),
                                        this.completedGames[gameIndex].getMonstersKilled(), 
                                        this.completedGames[gameIndex].getHits(), 
                                        this.completedGames[gameIndex].getMoves(), 
                                        this.completedGames[gameIndex].getFinalScore());
                    }
                }
                break;

            default:
                System.out.println(Messages.INVALID_INPUT);
        }
    }

    /**
     * This method prints the maze type selection menu.
     */
    private void printMazeMenu() {
        System.out.println("Please select a maze type.");
        System.out.println("Press 1 to select lower triangle maze.");
        System.out.println("Press 2 to select upper triangle maze.");
        System.out.println("Press 3 to select horizontal maze.");
        System.out.print("> ");
    }

    /**
     * This method creates a Maze object based on user choice of maze type.
     * @param mazeType An integer that stores the user choice for the maze selection menu.
     */
    private void mazeMenu(int mazeType) {
        int mazeLength = Integer.parseInt(this.args[0]);
        int mazeWidth = Integer.parseInt(this.args[1]);
        long seed = Long.parseLong(this.args[2]);

        if (mazeType < Constants.LOWER_TRIANGLE || mazeType > Constants.HORIZONTAL) {
            System.out.println(Messages.INVALID_INPUT);
            this.maze = null;
        }
        else this.maze = new Maze(mazeType, mazeLength, mazeWidth, seed);
    }

    /**
     * This method determines if the entered command line arguments are valid.
     * @param args An array of strings passed in as command line arguments.
     * @return A boolean value that is true when the arguments are invalid.
     */
    private boolean isInvalidArgs(String[] args) {
        this.args = args;
        
        boolean isInvalidInput = false;
        if (args.length != Constants.ARGS_LENGTH)
            isInvalidInput = true;

        for (String arg : args)
            if (Integer.parseInt(arg) <= 0) {
                isInvalidInput = true;
            }

        return isInvalidInput;
    }

    /**
     * This method prints the welcome message of the game.
     */
    private void displayMessage() {
        System.out.println(" ____         __          ___        _  _         __         __ _ \n" +
                "(  _ \\       / _\\        / __)      ( \\/ )       / _\\       (  ( \\\n" +
                " ) __/      /    \\      ( (__       / \\/ \\      /    \\      /    /\n" +
                "(__)        \\_/\\_/       \\___)      \\_)(_/      \\_/\\_/      \\_)__)");
        System.out.println("");
        System.out.println("Let the fun begin");
        System.out.println("(`<    ...   ...  ...  ..........  ...");
        System.out.println("");
    }
}