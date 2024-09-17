public class Maze {
    /* maze details */
    private int mazeType;
    private int mazeLength;
    private int mazeWidth;
    private char[][] maze;

    /* define other data variables here */


    public Maze() {}

    public Maze(int mazeType, int mazeLength, int mazeWidth, long seed) {
        // intialise the maze here 
        //add the position of all the entities
        LocationGenerator generator = new LocationGenerator(seed);
    }

    public void mazeMenu() {
        System.out.println("Please select a maze type.");
        System.out.println("Press 1 to select lower triangle maze.");
        System.out.println("Press 2 to select upper triangle maze.");
        System.out.println("Press 3 to select horizontal maze.");
        System.out.print("> ");
        this.mazeType = Integer.parseInt(GameEngine.keyboard.nextLine());
    }

    

   
    private void generatePosition(LocationGenerator generator) {
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

