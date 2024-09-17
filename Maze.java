

public class Maze {

    /* maze details */
    private int mazeType;
    private int mazeLength;
    private int mazeWidth;
    private char[][] maze;

    /* define other data variables here */


    public Maze(int mazeType, int mazeLength, int mazeWidth, long seed) {
        // intialise the maze here 
        //add the position of all the entities
        LocationGenerator generator = new LocationGenerator(seed);
    }

    

    

   
    private void generatePosition(LocationGenerator generator /* you can add other parameters here*/ ) {
        while(true) {
            int colPos = generator.generatePosition(1, this.mazeLength-2);
            int rowPos = generator.generatePosition(2, this.mazeWidth-2);
            if (this.maze[rowPos][colPos] == '.'){
                // set the Monster or foods location 
                // set the position of monster or food in maze, set it with correct symbol
                break;
            }
        }
    }


    private void movePacmanMenu(){
        System.out.println("Press W to move up.");
        System.out.println("Press A to move left.");
        System.out.println("Press S to move down.");
        System.out.println("Press D to move right.");
        System.out.println("Press Q to exit.");
        System.out.print("> ");
    }

    
}

