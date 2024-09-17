/*WARNING: This class shouldnt be moved to any package*/
import java.util.Scanner;

public class GameEngine {

    // a scanner is defined here but you can choose to move it to any other files.
    // there must be only one scanner shared amongst all the files 
    public static Scanner keyboard = new Scanner(System.in);

    public  static void main(String[] args){

        GameEngine engine = new GameEngine();

        if(engine.isInvalidArgs(args)){
            //print invalid args message
        }else {
            engine.displayMessage();
            // write rest of the code here.
        }


    }



    private void printMenu() {
        System.out.println("Select an option to get started.");
        System.out.println("Press 1 to select a pacman maze type.");
        System.out.println("Press 2 to play the game.");
        System.out.println("Press 3 to resume the game.");
        System.out.println("Press 4 to view the scores.");
        System.out.println("Press 5 to exit.");
        System.out.print("> ");
    }

    private boolean isInvalidArgs(String[] args) {
        boolean invalidInput = false;
        if(args.length != 3){
            invalidInput = true;
        }
        for(int i = 0; i< args.length; i++){
            if(Integer.parseInt(args[i]) <=0){
                invalidInput = true;
            }
        }
        return  invalidInput;
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

