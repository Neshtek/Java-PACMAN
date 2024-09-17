import java.util.Scanner;

public class GameEngine {
    public static Scanner keyboard = new Scanner(System.in);

    public GameEngine() {}

    public  static void main(String[] args){
        GameEngine engine = new GameEngine();

        if (engine.isInvalidArgs(args)) {
            System.out.println(Messages.INVALID_ARGS);
        } else {
            engine.displayMessage();
            do {
                int playerChoice = engine.playerMenu();
                if (playerChoice == Constants.EXIT_PLAYER_MENU) {
                    System.out.println(Messages.EXIT_PROGRAM);
                    break;
                }
                engine.executePlayerMenu(playerChoice);
            } while (true);
        }
    }

    private void executePlayerMenu(int playerChoice) {
        int noOfPlayers;
        switch (playerChoice) {
            case Constants.ONE_PLAYER:
                noOfPlayers = Constants.ONE_PLAYER;
                break;
            case Constants.TWO_PLAYER:
                noOfPlayers = Constants.TWO_PLAYER;
                break;
            default:
                System.out.println(Messages.INVALID_INPUT);
        }
        do {
            int menuChoice = this.mainMenu();
            if (menuChoice == Constants.EXIT_MAIN_MENU) {
                System.out.println(Messages.EXIT_MAIN_MENU);
                return;
            }
            this.executeMainMenu(menuChoice);
        } while (true);
    }

    private void executeMainMenu(int menuChoice) {
        Maze maze = new Maze();
        switch (menuChoice) {
            case Constants.SELECT_MAZE:
                do {
                    maze.mazeMenu();
                    
                }
                break;
            case Constants.PLAY_GAME:
                break;
            case Constants.RESUME_GAME:
                break;
            case Constants.VIEW_SCORES:
                break;
        }
    }

    private int playerMenu() {
        System.out.println("Make player selection.");
        System.out.println("Press 1 for Single Player.");
        System.out.println("Press 2 for Multi Player.");
        System.out.println("Press 3 to exit.");
        System.out.print("> ");
        int playerChoice = Integer.parseInt(keyboard.nextLine());
        return playerChoice;
    }

    private int mainMenu() {
        System.out.println("Select an option to get started.");
        System.out.println("Press 1 to select a pacman maze type.");
        System.out.println("Press 2 to play the game.");
        System.out.println("Press 3 to resume the game.");
        System.out.println("Press 4 to view the scores.");
        System.out.println("Press 5 to exit.");
        System.out.print("> ");
        int menuChoice = Integer.parseInt(keyboard.nextLine());
        return menuChoice;
    }

    private boolean isInvalidArgs(String[] args) {
        boolean invalidInput = false;
        if(args.length != 3) {
            invalidInput = true;
        }
        for (int i = 0; i < args.length; i++) {
            if (Integer.parseInt(args[i]) <= 0) {
                invalidInput = true;
            }
        }
        return invalidInput;
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