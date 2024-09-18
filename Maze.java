import java.util.Arrays;

public class Maze {
    /* maze details */
    private int mazeType;
    private int mazeLength;
    private int mazeWidth;
    private char[][] grid;

    /* define other data variables here */
    private LocationGenerator generator;
    private boolean[] isGhostKilled;
    
    enum GhostType {R, B, G, Y}

    public Maze() {}

    public Maze(Maze maze) {
        this.mazeType = maze.mazeType;
        this.mazeLength = maze.mazeLength;
        this.mazeWidth = maze.mazeWidth;
        this.grid = new char[mazeWidth][mazeLength];
        this.isGhostKilled = new boolean[4];
        Arrays.fill(isGhostKilled, false);
        this.generator = maze.generator;
        
    }

    public Maze(int mazeType, int mazeLength, int mazeWidth, long seed) {
        this.mazeType = mazeType;
        this.mazeLength = mazeLength;
        this.mazeWidth = mazeWidth;
        this.grid = new char[mazeWidth][mazeLength];
        this.isGhostKilled = new boolean[4];
        Arrays.fill(isGhostKilled, false);
        this.generator = new LocationGenerator(seed);

        this.createGrid();
        this.generatePosition(generator);
    }

    public char[][] getGrid() {
        return this.grid;
    }

    public boolean[] getGhostKilled() {
        return this.isGhostKilled;
    }

    public boolean areAllGhostsDead() {
        for (boolean deadGhost : this.isGhostKilled)
            if (!deadGhost)
                return false;
        return true;
    }

    public void printGrid() {
        for (char[] mazeRow : this.grid) {
            for (char mazeElement : mazeRow)
                System.out.print(mazeElement);
            System.out.println();
        }
    }

    public void setGhostKilled(int index) {
        this.isGhostKilled[index] = true;
    }

    public void setPacmanPos(int pacmanRow, int pacmanCol, int pacmanPrevRow, int pacmanPrevCol) {
        this.grid[pacmanPrevRow][pacmanPrevCol] = Constants.MAZE_DOT;
        this.grid[pacmanRow][pacmanCol] = Constants.PACMAN;
    }

    private void createGrid() {
        for (int colCounter = 0; colCounter < this.mazeLength; colCounter++) {
            this.grid[0][colCounter] = Constants.MAZE_BOUNDARY;
            this.grid[this.mazeWidth - 1][colCounter] = Constants.MAZE_BOUNDARY;
        }
        for (int rowCounter = 0; rowCounter < this.mazeWidth; rowCounter++) {
            this.grid[rowCounter][0] = Constants.MAZE_BOUNDARY;
            this.grid[rowCounter][this.mazeLength - 1] = Constants.MAZE_BOUNDARY;
        }
        for (int rowCounter = 1; rowCounter < this.mazeWidth - 1; rowCounter++)
            for (int colCounter = 1; colCounter < this.mazeLength - 1; colCounter++) {
                this.grid[rowCounter][colCounter] = Constants.MAZE_WALL;
                switch (this.mazeType) {
                    case 1:
                        if (colCounter <= rowCounter)
                            this.grid[rowCounter][colCounter] = Constants.MAZE_DOT;
                        break;
                    case 2:
                        if (colCounter > rowCounter)
                            this.grid[rowCounter][colCounter] = Constants.MAZE_DOT;
                        break;
                    case 3:
                        if (rowCounter % 2 == 1 || colCounter == 1 || colCounter == this.mazeLength - 2)
                            this.grid[rowCounter][colCounter] = Constants.MAZE_DOT;
                        break;
                }
            }
        this.generatePosition(this.generator);
    }

    private void generatePosition(LocationGenerator generator) {
        for (int ghostIndex = 0; ghostIndex < this.isGhostKilled.length; ghostIndex++)
            if (this.isGhostKilled[ghostIndex] == false) {
                int colPos = generator.generatePosition(1, this.mazeLength-2);
                int rowPos = generator.generatePosition(2, this.mazeWidth-2);
                if (this.grid[rowPos][colPos] == Constants.MAZE_DOT)
                    this.grid[rowPos][colPos] = GhostType.values()[ghostIndex].name().charAt(0);
            }

        int foodCounter = 0;
        while (foodCounter < Constants.FOOD_NUM) {
            int colPos = generator.generatePosition(1, this.mazeLength-2);
            int rowPos = generator.generatePosition(2, this.mazeWidth-2);
            if (this.grid[rowPos][colPos] == Constants.MAZE_DOT) {
                this.grid[rowPos][colPos] = Constants.MAZE_FOOD;
            }
            foodCounter++;
        }
    }
}

