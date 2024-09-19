import java.util.Arrays;

/**
 * This class handles the functioning of the maze and contains all its details.
 * @version ver 1.0
 * @author Neelaksh Tayal 1627659
 */
public class Maze {
    private int mazeType;
    private int mazeLength;
    private int mazeWidth;
    private char[][] grid;
    private LocationGenerator generator;
    private boolean[] isGhostKilled;
    
    // enum for all ghost.
    enum GhostType {R, B, G, Y}

    /**
     * The default constructor for the Maze class.
     */
    public Maze() {}

    /**
     * The copy constructor for the Maze class.
     * @param maze
     */
    public Maze(Maze maze) {
        this.mazeType = maze.mazeType;
        this.mazeLength = maze.mazeLength;
        this.mazeWidth = maze.mazeWidth;
        this.grid = new char[this.mazeWidth][this.mazeLength];
        for (int i = 0; i < maze.mazeWidth; i++)
            for (int j = 0; j < maze.mazeLength; j++)
                this.grid[i][j] = maze.grid[i][j];
        this.isGhostKilled = new boolean[4];
        Arrays.fill(isGhostKilled, false);
        this.generator = maze.generator;
    }

    /**
     * The parameterized constructor for the Maze class.
     * @param mazeType An integer value that contains the user choice for maze type.
     * @param mazeLength An integer value that contains the length of the maze.
     * @param mazeWidth An integer value that contains the width of the maze.
     * @param seed A long integer value that contains the seed for the random number generator.
     */
    public Maze(int mazeType, int mazeLength, int mazeWidth, long seed) {
        this.mazeType = mazeType;
        this.mazeLength = mazeLength;
        this.mazeWidth = mazeWidth;
        this.grid = new char[mazeWidth][mazeLength];
        this.isGhostKilled = new boolean[4];
        Arrays.fill(isGhostKilled, false);
        this.generator = new LocationGenerator(seed);

        this.createGrid();
        this.placeGhostOrFoodBackbone(generator);
    }

    /**
     * A getter method for the 2-D grid character array.
     * @return
     */
    public char[][] getGrid() {
        return this.grid;
    }

    /**
     * A getter method for the isGhostKilled boolean array.
     * @return
     */
    public boolean[] getGhostKilled() {
        return this.isGhostKilled;
    }

    /**
     * This method determines if all the ghosts in the current game are dead.
     * @return A boolean value that is true if all ghosts are dead.
     */
    public boolean areAllGhostsDead() {
        for (boolean deadGhost : this.isGhostKilled)
            if (!deadGhost)
                return false;
        return true;
    }

    /**
     * This method prints the current state of the maze.
     */
    public void printGrid() {
        for (char[] mazeRow : this.grid) {
            for (char mazeElement : mazeRow)
                System.out.print(mazeElement);
            System.out.println();
        }
    }

    /**
     * A setter method to set the value at a particular index to true for the isGhostKilled boolean array.
     * @param index An integer value that contains the index that needs to be modified.
     */
    public void setGhostKilled(int index) {
        this.isGhostKilled[index] = true;
    }

    /**
     * This method updates the current position of pacman.
     * @param pacmanRow An integer value that contains the current row coordinate of pacman.
     * @param pacmanCol An integer value that contains the current column coordinate of pacman.
     * @param pacmanPrevRow An integer value that contains the last row coordinate of pacman.
     * @param pacmanPrevCol An integer value that contains the last column coordinate of pacman.
     * @param message A String value that contains the message to be printed based on pacman's position.
     */
    public void setPacmanPos(int pacmanRow, int pacmanCol, int pacmanPrevRow, int pacmanPrevCol, String message) {
        
        // if anything happened other than pacman hitting a wall/boundary, place a '.' in the last position of pacman.
        if (message == null || (!message.equals(Messages.BOUNDARY_HIT) && !message.equals(Messages.WALL_HIT)))
            this.grid[pacmanPrevRow][pacmanPrevCol] = Constants.MAZE_DOT;
        
        // update pacman's position.
        this.grid[pacmanRow][pacmanCol] = Constants.PACMAN;
    }

    /**
     * This method creates the barebones maze based on the user's choice of maze type.
     */
    private void createGrid() {
        for (int colCounter = 0; colCounter < this.mazeLength; colCounter++) {
            this.grid[0][colCounter] = Constants.MAZE_BOUNDARY;
            this.grid[this.mazeWidth - 1][colCounter] = Constants.MAZE_BOUNDARY;
        }
        for (int rowCounter = 0; rowCounter < this.mazeWidth; rowCounter++) {
            this.grid[rowCounter][0] = Constants.MAZE_BOUNDARY;
            this.grid[rowCounter][this.mazeLength - 1] = Constants.MAZE_BOUNDARY;
        }
        for (int rowCounter = 1; rowCounter < (this.mazeWidth - 1); rowCounter++)
            for (int colCounter = 1; colCounter < (this.mazeLength - 1); colCounter++) {
                this.grid[rowCounter][colCounter] = Constants.MAZE_WALL;
                switch (this.mazeType) {
                    case Constants.LOWER_TRIANGLE:
                        if (colCounter <= rowCounter)
                            this.grid[rowCounter][colCounter] = Constants.MAZE_DOT;
                        break;
                    case Constants.UPPER_TRIANGLE:
                        if (colCounter >= rowCounter)
                            this.grid[rowCounter][colCounter] = Constants.MAZE_DOT;
                        break;
                    case Constants.HORIZONTAL:
                        if (rowCounter % 2 == 1 || colCounter == 1 || colCounter == this.mazeLength - 2)
                            this.grid[rowCounter][colCounter] = Constants.MAZE_DOT;
                        break;
                }
            }
    }

    /**
     * This method places food or ghosts at a randomly generated position.
     * @param generator A LocationGenerator object that is the random number generator.
     * @param ghostIndex An integer value that details the index of the ghost to be placed.
     * @param foodFlag A boolean flag that is true if food is to be placed, and false for ghosts.
     */
    private void placeGhostOrFood(LocationGenerator generator, int ghostIndex, boolean foodFlag) {
        while (true) {
            int colPos = generator.generatePosition(1, this.mazeLength-2);
            int rowPos = generator.generatePosition(2, this.mazeWidth-2);
            if (this.grid[rowPos][colPos] == Constants.MAZE_DOT) {
                if (!foodFlag)
                    this.grid[rowPos][colPos] = GhostType.values()[ghostIndex].name().charAt(0);
                else
                    this.grid[rowPos][colPos] = Constants.MAZE_FOOD;
                return;
            }
        }
    }

    /**
     * This method is the backbone of the placeGhostOrFood method.
     * @param generator A LocationGenerator object that is the random number generator.
     */
    private void placeGhostOrFoodBackbone(LocationGenerator generator) {
        for (int ghostIndex = 0; ghostIndex < this.isGhostKilled.length; ghostIndex++) {
            if (this.isGhostKilled[ghostIndex] == false) {
                this.placeGhostOrFood(generator, ghostIndex, false);
            }
        }

        for (int foodCounter = 0; foodCounter < Constants.FOOD_NUM; foodCounter++) {
            // foodCounter is used as a placeholder parameter.
            this.placeGhostOrFood(generator, foodCounter, true);
        }
    }
}