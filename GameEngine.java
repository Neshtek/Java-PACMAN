import java.util.Arrays;
import java.util.Scanner;

public class GameEngine {
    private String[] args;
    private static ScoreBoard[] completedGames;
    private Maze maze;
    private Game game;

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
        GameEngine.completedGames = new ScoreBoard[0];
        this.maze = null;
        this.game = null;
    }
     
    public static void addCompletedGame(ScoreBoard score) {
        GameEngine.completedGames = Arrays.copyOf(GameEngine.completedGames, GameEngine.completedGames.length + 1);
        GameEngine.completedGames[GameEngine.completedGames.length - 1] = score;
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
        this.game = new Game();
        switch (playerChoice) {
            case Constants.SINGLE_PLAYER:
                this.game.setNoOfPlayers(Constants.SINGLE_PLAYER);
                break;
            case Constants.MULTI_PLAYER:
                this.game.setNoOfPlayers(Constants.MULTI_PLAYER);
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
                if (this.maze == null) {
                    System.out.println(Messages.MAZE_UNSET);
                    break;
                } else if (!this.game.checkGameCompletion()) {
                    System.out.print(Messages.GAME_INCOMPLETE);
                    String discardChoice = keyboard.nextLine();
                    if (discardChoice == "N")
                        break;
                    else {
                        this.maze = null;
                        this.setMaze();
                        System.out.println(Messages.NEW_MAZE_SET);
                    }
                }
                System.out.println(Messages.START_GAME);
                this.game.startGame(this.maze);
                break;
            case Constants.RESUME_GAME:
                if (this.maze == null) {
                    System.out.println(Messages.MAZE_UNSET);
                    break;
                } else if (!this.game.checkGameCompletion()) {
                    System.out.println(Messages.RESTART_GAME);
                    this.game.startGame(this.maze);
                    break;
                }
                System.out.println(Messages.NO_PAUSED_GAME);
                break;
            case Constants.VIEW_SCORES:
                if (GameEngine.completedGames.length == 0) {
                    System.out.println(Messages.NO_COMPLETED_GAMES);
                    break;
                } else {
                    System.out.printf(Constants.LEADERBOARD_HEADER_FORMAT, Constants.GAME_NO, Constants.PLAYER_NAME, Constants.FOOD_EATEN, Constants.MONSTER_KILLED, Constants.HITS, Constants.MOVES, Constants.SCORE);
                    for (int gameIndex = 0; gameIndex < GameEngine.completedGames.length; gameIndex++) {
                        System.out.printf(Constants.LEADERBOARD_FORMAT, 
                                        GameEngine.completedGames[gameIndex].getGameNumber(), 
                                        GameEngine.completedGames[gameIndex].getPlayer(), 
                                        GameEngine.completedGames[gameIndex].getMonstersKilled(), 
                                        GameEngine.completedGames[gameIndex].getHits(), 
                                        GameEngine.completedGames[gameIndex].getMoves(), 
                                        GameEngine.completedGames[gameIndex].getFinalScore());
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