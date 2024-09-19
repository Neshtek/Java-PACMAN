public class Messages {
    public static final String INVALID_ARGS = "Invalid Inputs to set layout. Exiting the program now.";
    public static final String INVALID_INPUT = "Invalid Input.";
    public static final String EXIT_PROGRAM = "Pacman says - Bye Bye Player.";
    public static final String EXIT_MENU = "Exiting main menu, return to Player Selection.";
    public static final String MAZE_UNSET = "Maze not created. Select option 1 from main menu.";
    public static final String MAZE_SET = "Maze created. Proceed to play the game.";
    public static final String NEW_MAZE_SET = "New maze created. Proceed to play the game.";
    public static final String PLAYER_GAME_START = "Player %d game begins.\n";
    public static final String GAME_PAUSED = "Your game is paused and saved.";
    public static final String WALL_HIT = "You have hit a wall.";
    public static final String BOUNDARY_HIT = "You have hit the boundary.";
    public static final String POWER_UP = "Power up!";
    public static final String DEATH = "Boo! Monster killed Pacman.";
    public static final String MONSTER_KILLED = "Hurray! A monster is killed.";
    public static final String GAME_END = "Game has ended! Your score for this game is ";
    public static final String NO_PAUSED_GAME = "No paused game found. Select option 2 from main menu to start a new game.";
    public static final String RESTART_GAME = "Restart your game from the last position you saved.";
    public static final String NO_COMPLETED_GAMES = "No completed games found.";
    public static final String PLAYER_WINS = "Player %d wins. Returning to main menu.\n";
    public static final String GAME_INCOMPLETE = "Previous game hasn't ended yet. Do you want to discard previous game?\n" + //
                                                "Press N to go back to main menu to resume the game or else press any key to discard.\n" + //
                                                "> ";

    public static final String START_GAME = "Move the Pacman towards the food pellet and gain super power to kill monsters.\n" + //
                "  > You gain 20 points if Pacman finishes the game without dying.\n" + //
                "  > You gain 10 more points for every monster you killed.\n" + //
                "  > You gain 5 points for every special food that you have eaten.\n" + //
                "  > You lose 0.5 point when you hit the wall/boundary.\n" + //
                "  > You lose 0.25 points for every move.\n" + //
                "  > Score = 5 * foodEaten + 10 * monsterKilled - 0.5 * numOfHits - 0.25 * numOfMoves  +  20 if not dead.";
}