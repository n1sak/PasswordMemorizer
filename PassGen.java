/**
* PassGen.java
* @since November 4th, 2024
* PassGen is a class that generates a password which varies in length depending on the round number
*/ 
public class PassGen
{
    /** String which holds the password generated */ 
    private String password;
    /** Integer variable which uses ASCII to generate random letters */
    private int ASCII;

    /**
    * Default Constructor to initialize the class variables
    * Password Initialized as an empty string
    * ASCII Initialized as 0 and acts as the ASCII values of each letter of the alphabet
    */ 
    public PassGen()
    {
        password = "";
        ASCII = 0;
    }

    /** 
    * Generates the random password for the game
    * @param round - Round Number 
    * @return - The complete randomized password
    */ 
    public String generate(int round) {
        password = ""; 
        for (int i = 1; i <= 4 + round; i++) {
            ASCII = (int)(Math.random()*26 + 97);
            password += (char)ASCII;
        }
        return password; 
    }
    
    
}
