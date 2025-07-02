import java.util.Scanner;

/**
* Game.java
* @since November 8th, 2024 
* The Game class is the class which executes the entire game that the user plays.
* Using many factors like round number, score, and other classes + their methods, 
* the Game class is the head of entire game's program  
*/
public class Game 
{
	/** User's total score */
    private int totalScore; 
    /** Temporary score used to hold score */
    private int tempScore; 
    /** The number of points earned in a round */
    private int roundPoints; 
    /** Randomly generated password */
    private String pass; 
    /** Answer from user */
    private String answer; 
    /** The round number the user is on */
    private int round; 
    /** PassGen object which goes into pass */
    private PassGen gen; 
    /** Scanner object */
    private Scanner scan;
    /** The max score the user could potentially earn */
    private int maxScore;
    /** The potential penalty given if the answer is too long */
    private int lengthPenalty; 
    
    /** Boolean to see if the user continues to play */
    private boolean play = true;
    /** String to see if the game restarts */
    private String restart;

    /** 
     * Default Game constructor which creates a Game object and initializes instance variables
     */
    public Game() {
        totalScore = 0;
        tempScore = 0;
        maxScore = 0;
        lengthPenalty = 0;
        round = 1;
        pass = "";
        answer = ""; 
        gen = new PassGen();
        scan = new Scanner(System.in);
    }

    /** Begins basic gameplay loop and compares user input to track points
    */ 
    private void gameplay() {
        // Variables reset to make sure no data from any previous games carry over
        answer = "";
        round = 1;
        totalScore = 0;
        maxScore = 0;

        printInstructions();
        
        // While loop which continues the whole game until the user enters an exclamation mark
        while (!answer.equals("!")) {

            /**
            * Calls the generate method from the PassGen class and assigns it to the private
            * pass variable. Then, it adds the length of the pass to the maxScore variable to 
            * indicate what the max score could be. maxScore carries over rounds.
            */  
            pass = gen.generate(round);
            maxScore += pass.length();

            System.out.println("Round " + round + "\n");
            System.out.println("Password:\n" + pass);
            System.out.println("\nEnter \"next\" when ready");

            //Checks if user inputed "next" to continue until a next is entered
            answer = getUserInput("next", "next");
            
            clearConsole();
            
            tempScore = 0;
            System.out.println("Enter the password (" + pass.length() + " characters):");
            answer = scan.nextLine();

            /** 
            * For loop to check each character of the user's inputed answer and the random 
            * generated password. 
            * Increments tempScore by 1 if a character matches with each other.
            */  
            for (int i = 0; i < pass.length() && i < answer.length(); i++) {
                char ans = answer.charAt(i);
                char pas = pass.charAt(i);
                
                if (ans == pas) {
                    tempScore++;
                }
                
            }

            /**
            * Penalty distributed if the answer is longer than
            * the password and reflects in the 
            * player's score  
            */
            lengthPenalty = Math.abs(pass.length() - answer.length());

            if (lengthPenalty > tempScore) {
                lengthPenalty = tempScore;
            }

            roundPoints = tempScore - lengthPenalty;
            totalScore += roundPoints;

            printRoundResults();
            
            /**
            * Takes user input to determine whether to end the game or continue another round 
            * OR ends the game automatically if 5 rounds are played or if 20 points have been
            * earned 
            */
            if (totalScore >= 20 || round == 5) {
                answer = "!";
            } else {
                System.out.println("Enter \"!\" to end game or \"next\" to continue:");
                answer = getUserInput("next", "!");
            }

            /** 
            * Prints out new line statements and increases the round number by 1 if player wants * to continue 
            */
            if (answer.equals("next")) {
                clearConsole();
                System.out.println("Continuing game...");
                round++; 
            }


        }

        //New line statements to hide game 
        clearConsole();
        printEndMessage();
    }
    
    /** 200 new lines printed to conceal the password.
    * We couldn't find a way to hide a printed statement in the console,
    * so this was an alternate solution Mrs. Bhatgnar allowed us to perform
    */
    private void clearConsole(){
        for (int i = 0; i < 200; i++) {
            System.out.println();
        }
    }

    /**
    * Game instructions for the user. 
    * Explains how the game works and some rules within the game program.
    */ 
    private void printInstructions() {
        System.out.println("Memorize the password and enter next when you're ready!");
        System.out.println("The goal is to get 20 points within 5 rounds. The more accurate you are, the better!\n");
        System.out.println("Be sure to match the exact length or you'll be penalized. The case and position of each character matters too!");
        System.out.println("Don't worry though, you can't earn negative points.\n");
    }

    /**
    * Shows player the password and tells them how many characters were correct 
    * Tells player how many points they gained in a round relative to the penalty given
    */  
    private void printRoundResults() {
        System.out.println("\n" + pass + " (" + tempScore + " correct out of " + pass.length() + ")");
        if (roundPoints != 1) {
            System.out.println((roundPoints) + " points gained this round (length mismatch penalty of " + lengthPenalty + ")\n");
        } else {
            System.out.println("1 point gained this round (length mismatch penalty of " + lengthPenalty + "\n)");
        }
        System.out.println("Current Score: " + totalScore + " points out of " + maxScore + " possible\n");
    }

    /**
    * Tells the user whether they won or lost in addition to their final score and accuracy 
    * (in percent) 
    */ 
    private void printEndMessage() {
        if (totalScore >= 20) {
            System.out.println("You won! You got to 20 points in " + round + " rounds!");
        } else if (round == 5) {
            System.out.println("You used all 5 rounds. Try again next time!");
        } else {
            System.out.println("Incomplete game!");
        }
        System.out.println("Final Score: " + totalScore + " (" + ((int) ((totalScore + 0.0) / maxScore * 1000) / 10.0) + "% accurate)\n");
    }

    /** 
    * Filters user input until receiving acceptable strings and reprints instructions if 
    * incorrect inputs are entered
    * @param validInput1 the first acceptable String as user input
    * @param validInput2 the second acceptable String as user input
    * @return proper valid user input 
    */
    private String getUserInput(String validInput1, String validInput2) {
        String input = scan.nextLine();
        while (!input.equals(validInput1) && !input.equals(validInput2)) {
            if (!validInput1.equals(validInput2)) {
                System.out.println("Enter \"" + validInput1 + "\" or \"" + validInput2 + "\":");
            } else {
                System.out.println("Enter \"" + validInput1 + "\" to continue:");
            }
            input = scan.nextLine();
        }
        return input;
    }

    /**
    * The startGame method asks the user if they want to play a new game after the previous one
    * ended. 
    * The game will continue if the player enters "yes", but will stop if otherwise.
    */ 
    public void startGame()
    {

        // This flag controlled while loop makes sure that the game continues to play
        while (play) {
            gameplay();
    
            System.out.println("New Game? Enter \"yes\" to continue:");

            restart = scan.nextLine();
            if (!restart.equals("yes")) {
                play = false;
            } else {
                // For loop to clear the console for the new game
                clearConsole();
            }
        }
    }

}
