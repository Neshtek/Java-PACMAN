import java.util.Arrays;
import java.util.Scanner;

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

    public static void main(String[] args) {
        GameEngine engine = new GameEngine();

        if (engine.isInvalidArgs(args)) {
            System.out.println(Messages.INVALID_ARGS);
        } else {
            engine.displayMessage();
            do {
                engine.printPlayerMenu();
                int playerChoice = Integer.parseInt(keyboard.nextLine());
                if (playerChoice == Constants.EXIT_PLAYER) {
                    System.out.println(Messages.EXIT_PROGRAM);
                    break;
                }
                engine.playerMenu(playerChoice);
            } while (true);
        }
    }

    public GameEngine() {
        this.completedGames = new ScoreBoard[0];
        this.maze = null;
        this.mazeCopy = null;
        this.game = null;
        this.gameCounter = 0;
    }
     
    private void addCompletedGame(ScoreBoard score) {
        this.completedGames = Arrays.copyOf(this.completedGames, this.completedGames.length + 1);
        this.completedGames[this.completedGames.length - 1] = score;
    }

    private void printWinner() {
        if (this.noOfPlayers == Constants.MULTI_PLAYER && this.game.checkGameCompletion()) {
            ScoreBoard scoreOne = this.completedGames[this.completedGames.length - 2];
            ScoreBoard scoreTwo = this.completedGames[this.completedGames.length - 1];
            if (scoreOne.getFinalScore() >= scoreTwo.getFinalScore())
                System.out.printf(Messages.PLAYER_WINS, Constants.SINGLE_PLAYER);
            else System.out.printf(Messages.PLAYER_WINS, Constants.MULTI_PLAYER);
        }
    }
    
    private void setMaze() {
        do {
            this.printMazeMenu();
            int mazeType = Integer.parseInt(keyboard.nextLine());
            this.mazeMenu(mazeType);
        } while (this.maze == null);
    }

    private void printPlayerMenu() {
        System.out.println("Make player selection.");
        System.out.println("Press 1 for Single Player.");
        System.out.println("Press 2 for Multi Player.");
        System.out.println("Press 3 to exit.");
        System.out.print("> ");
    }

    private void playerMenu(int playerChoice) {
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
        do {
            this.printMainMenu();
            int mainChoice = Integer.parseInt(keyboard.nextLine());
            if (mainChoice == Constants.EXIT_MAIN) {
                System.out.println(Messages.EXIT_MENU);
                return;
            }
            this.mainMenu(mainChoice);
        } while (true);
    }

    private void printMainMenu() {
        System.out.println("Select an option to get started.");
        System.out.println("Press 1 to select a pacman maze type.");
        System.out.println("Press 2 to play the game.");
        System.out.println("Press 3 to resume the game.");
        System.out.println("Press 4 to view the scores.");
        System.out.println("Press 5 to exit.");
        System.out.print("> ");
    }

    private void mainMenu(int mainChoice) {
        switch (mainChoice) {

            case Constants.SELECT_MAZE:
                this.setMaze();
                System.out.println(Messages.MAZE_SET);
                break;


            case Constants.PLAY_GAME:
                this.game = new Game();
                if (this.maze == null) {
                    System.out.println(Messages.MAZE_UNSET);
                    break;
                } else if (!this.game.checkGameCompletion()) {
                    System.out.print(Messages.GAME_INCOMPLETE);
                    String discardChoice = keyboard.nextLine().toUpperCase();
                    if (discardChoice.equals(Constants.NO))
                        break;
                    else {
                        this.maze = null;
                        this.setMaze();
                        System.out.println(Messages.NEW_MAZE_SET);
                    }
                }
                if (this.noOfPlayers == Constants.MULTI_PLAYER)
                    this.mazeCopy = new Maze(maze);
                int multiPlayerCounter = 0;
                do {
                    System.out.println(Messages.START_GAME);
                    this.gameCounter++;
                    this.score = new ScoreBoard(this.gameCounter);
                    if (multiPlayerCounter > 0) {
                        this.game = new Game();
                        this.maze = this.mazeCopy;
                        this.score.setPlayer(Constants.PLAYER_TWO);
                        System.out.printf(Messages.PLAYER_GAME_START, Constants.MULTI_PLAYER);
                    } else {
                        this.score.setPlayer(Constants.PLAYER_ONE);
                        System.out.printf(Messages.PLAYER_GAME_START, Constants.SINGLE_PLAYER);
                    }

                    this.game.startGame(this.maze, this.score);

                    if (this.game.checkGameCompletion()) {
                        this.addCompletedGame(this.score);
                        this.maze = null;
                    } else break;

                    multiPlayerCounter++;
                } while (multiPlayerCounter < this.noOfPlayers);

                this.printWinner();
                break;


            case Constants.RESUME_GAME:
                if (this.maze == null) {
                    System.out.println(Messages.MAZE_UNSET);
                    break;
                } else if (!this.game.checkGameCompletion()) {
                    System.out.println(Messages.RESTART_GAME);
                    if (this.score.getPlayer() == Constants.PLAYER_ONE) {
                        multiPlayerCounter = 0;
                        System.out.printf(Messages.PLAYER_GAME_START, Constants.SINGLE_PLAYER);
                        do {
                            if (multiPlayerCounter > 0) {                                
                                System.out.println(Messages.START_GAME);
                                this.gameCounter++;
                                this.maze = this.mazeCopy;
                                this.game = new Game();
                                this.score = new ScoreBoard(this.gameCounter);
                                this.score.setPlayer(Constants.PLAYER_TWO);
                                System.out.printf(Messages.PLAYER_GAME_START, Constants.MULTI_PLAYER);
                            }

                            this.game.startGame(this.maze, this.score);

                            if (this.game.checkGameCompletion()) {
                                this.addCompletedGame(this.score);
                                this.maze = null;
                            } else break;

                            multiPlayerCounter++;
                        } while (multiPlayerCounter < this.noOfPlayers);
                    }
                    else { 
                        System.out.printf(Messages.PLAYER_GAME_START, Constants.MULTI_PLAYER);
                        this.game.startGame(this.maze, this.score);

                        if (this.game.checkGameCompletion()) {
                            this.addCompletedGame(this.score);
                            this.maze = null;
                        } else break;
                    }
                    this.printWinner();
                    break;
                }
                System.out.println(Messages.NO_PAUSED_GAME);
                break;


            case Constants.VIEW_SCORES:
                if (this.completedGames.length == 0) {
                    System.out.println(Messages.NO_COMPLETED_GAMES);
                    break;
                } else {
                    System.out.printf(Constants.LEADERBOARD_HEADER_FORMAT, Constants.GAME_NO, Constants.PLAYER_NAME, Constants.FOOD_EATEN, Constants.MONSTER_KILLED, Constants.HITS, Constants.MOVES, Constants.SCORE);
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

    private void printMazeMenu() {
        System.out.println("Please select a maze type.");
        System.out.println("Press 1 to select lower triangle maze.");
        System.out.println("Press 2 to select upper triangle maze.");
        System.out.println("Press 3 to select horizontal maze.");
        System.out.print("> ");
    }

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