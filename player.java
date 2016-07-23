/**
* @author Ankit Gyawali
* File Name: player.java
* Abstract class player contains methods that can be inherited and
* also containt the required data types to be used in the game.
*/
import java.util.*;
import javax.swing.Timer;
/**
* Abstract class player is implementation of a player in the Rack-O game.
*/
public abstract class player
{
    /**
    * A rack constructed to be held by each player.
    */
    public rack aRack = new rack();
    
    /**
    * Boolean that determines if the player is AI or human.
    */
    public boolean humanOrNot;
    
    /**
    * String of player name.
    */
    public String playerName;
    
    /**
    * This int holds player's score.
    */
    public int playerScore;
    
    /**
    * Boolean to determine end of game.
    */
    public boolean isitNextRoundYet = false;
    
    /**
    * Constructor for player object.
    * @param newRack Rack to be helf by the player.
    * @param isHuman Boolean value that determines if the player is human.
    * @param playaName Name of the player.
    * @param playaScore Score of the player, instantiated with 0.
    */
    public player(rack newRack, boolean isHuman, String playaName, int playaScore)
    {
        aRack = newRack;
        humanOrNot = isHuman;
        playerName = playaName;
        playerScore = playaScore;
    }
    
    
    /**
    * Adds card to the rack object inside player class.
    * @param cardToAdd Card to be added to the player's rack.
    */
    public void setToRack(card cardToAdd)	{
        aRack.setToRack(cardToAdd);
    }
    
    /**
    * Adds card to the rack object inside player class, with specified index.
    * @param cardToAdd Card to be added to the player's rack.
    * @param index Index where the card should be added at.
    */
    public void setToRack(int index,card cardToAdd)	{
        aRack.setToRack(index,cardToAdd);
    }
    
    
    /**
    * Sets name of the player in player object.
    * @param nameToSet String containing the player name to be set.
	* @see player#setScore
    */
    public void setName(String nameToSet)	{
        playerName = nameToSet;
    }
    
    
    /**
    * Sets score of the player in the player object
    * @param scoreToSet integer containing the player score to be set.
	* @see player#getScore
	* @see player#setName
    */
    public void setScore(int scoreToSet)	{
        playerScore = scoreToSet;
        
    }
    
    
    /**
    * Returns card from the player's rack from specified index.
    * @param index Index from where card should be retrieved.
    * @return Card fetched from the player's rack.
    */
    public card getRack(int index) {
        return aRack.getRack(index);
    }
    
    /**
    * Returns player's name.
    * @return Returns the string containing player's name.
	* @see player#getScore
    */
    public String getName() 	{
        return playerName;
    }
    
    /**
    * Returns player's score.
    * @return Returns the integer containing player's score.
	* @see player#getName
	* @see player#setScore
    */
    public int getScore() 	{
        return playerScore;
    }
    
    /**
    * Clears the current player's rack.
    */
    public void clearRack() {
        aRack.clearRack();
    }
    
    /**
    * Checks if the deck is empty when player is playing the game.
    * Refills deck if empty
    */
    public void isDeckEmpty()
    {
        if (rackoapp.newDeck.aDeck.isEmpty()) //If deck is empty, refill
        {
            System.out.println(
            "Deck is empty, using discard pile as new deck and reshuffling it.");
            int temp = rackoapp.newDeck.discardDeck.size()-1;
            //Shuffles the deck
            Collections.shuffle(rackoapp.newDeck.discardDeck);
            for (int i=0; i < temp; i++)
            {
                rackoapp.newDeck.aDeck.push(rackoapp.newDeck.discardDeck.peek());
                rackoapp.newDeck.discardDeck.pop();
            }
            rackoapp.newDeck.shuffleDeck();
        }
    }
    
    
    /**
    * Calculates and set the current player's score using the rack.
    */
    public void calculateScore()
    {
        int score = 0; //Increases score if card from bottom are in order
        if ((aRack.getRack(9).getCard()) < (aRack.getRack(8)).getCard())
        {
            score++;
        }
        outerloop:
        for (int i=9;i>0;i--)
        {
            if ((aRack.getRack(i).getCard()) < (aRack.getRack(i-1)).getCard())
            {
                score++;
            }
            else
            {
                break outerloop;
            }
        }
        
        //If all cards are in order give extra 25 points and next round
        if (score==10 && ((aRack.getRack(1).getCard()) < (aRack.getRack(0)).getCard()))
        {
            // Player is Rack-O! Go to next round
            score = score+5;
            isitNextRoundYet = true;
        }
        setScore(score*5);
        
    }
    
    
    /**
    * This is the abstract method which is implemented in human.java and ai.java
	* @see ai#takeATurn
	* @see human#takeATurn
	* @see rackoapp#takeAnotherTurn
    */
    abstract void takeATurn();
    
}