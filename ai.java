/**
* @author Ankit Gyawali
* File Name: ai.java
* Extends player class, implements method to take
* a turn by the ai. Inherits from player class.
*/
import java.util.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.lang.*;
import javax.swing.JButton;
import javax.swing.Timer;
import java.awt.Component;
import javax.swing.SwingUtilities;
import javax.swing.BorderFactory;
import java.awt.Color;

/**
* AI extends player class to implement methods to take a turn by AI player
* during the Rack-O game.
*/
public class ai extends player
{
    /**
    * AI constructor gets values from player class, to use it.
    * @param newRack Rack of the player.
    * @param isHuman Boolean value if player is human or computer
    * @param playaName Name of the player
    * @playaScore Score of the player
    */
    ai(rack newRack, boolean isHuman, String playaName, int playaScore)
    {
        super (newRack, isHuman,playaName,playaScore);
    }
    
	public static Timer timer;
    /**
    * Boolean arraylist to keep track of slot that have been sorted.
    */
    public static ArrayList<Boolean> isSlotSatisfied = new ArrayList<Boolean>(10);
    
    /**
    * Method takeATurn for AI implements simple boolean list to play a turn.
    * Turns are based on AI trying to fill all boolean values in list to true.
    */
    public void takeATurn()
    {
		

					
        //GUI Code
        aRack.setIconBlank();
        aRack.showRackValues();
        aRack.unlockRack();
        aRack.removeBorders();
        rackoapp.terminalText.append("> Current AI Player: " +
        rackoapp.newPlayers.get(rackoapp.currentPlayer).getName()+"\n");
        
        for (int i = 0; i <= 9; i++)
        {
            // This loop fills up the boolean arraylist after checking card values
            isSlotSatisfied.add(i,((aRack.getRack((i)).getCard() >=
            ((idxReverse(i)*cardperSlot())+1)) && (aRack.getRack((i)).getCard()
            <= ((idxReverse(i)+1)*cardperSlot()))));
        }
        
        // Checks if you can use first discard deck
        if(useOrNot(rackoapp.newDeck.discardDeck.peek()))
        {
            rackoapp.terminalText.append ("> "+getName()+" picked: ["+
            rackoapp.newDeck.discardDeck.peek()+"] from the [D]iscard pile!\n");
            rackoapp.terminalText.append ("> "+getName()+" replaced a card ["+
            
            aRack.getRack(calcCardsPlaceinSlot(rackoapp.newDeck.discardDeck.peek()))
            +"] in his rack with ["+rackoapp.newDeck.discardDeck.peek()+"] !\n");
            
			 aRack.getRack(calcCardsPlaceinSlot(rackoapp.newDeck.discardDeck
            .peek())).setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.red));
			
            //Discard Deck
            int tempCard = aRack.getRack(calcCardsPlaceinSlot
            (rackoapp.newDeck.discardDeck.peek())).getCard();
            
            //Sets card to rack
            aRack.getRack(calcCardsPlaceinSlot(rackoapp.newDeck.discardDeck
            .peek())).setCard(rackoapp.newDeck.discardDeck.peek().getCard());
			
			  aRack.getRack(calcCardsPlaceinSlot(rackoapp.newDeck.discardDeck
            .peek())).setText(rackoapp.newDeck.discardDeck.peek().toString());
			
			
            //Pushes new card from rack to discard pile
            rackoapp.newDeck.discardDeck.peek().setCard(tempCard);
            
            //Sets equivalent boolean arraylist index to true
            isSlotSatisfied.set
            (calcCardsPlaceinSlot(rackoapp.newDeck.discardDeck.peek()),true);
            
            //GUI
            rackoapp.topDiscard.setText
            (rackoapp.newDeck.discardDeck.peek().toString());
			
			 
			
            
        }
        //If discard card pile is not suitable, AI uses the facedown deck.
        else
        {
            //Checks if the first card from deck is suitable to use
            if(useOrNot(rackoapp.newDeck.aDeck.peek()))
            {
                rackoapp.terminalText.append ("> "+getName()+" replaced a card ["
                +aRack.getRack(calcCardsPlaceinSlot(rackoapp.newDeck.aDeck.peek()))
                +"] in his rack with ["+rackoapp.newDeck.aDeck.peek()+
                "] from [F]acedown deck !\n");
                
                aRack.getRack
				(calcCardsPlaceinSlot(rackoapp.newDeck.aDeck.peek())).
				setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.red));
				
                //Puts card from rack to discard pile
                rackoapp.newDeck.discardDeck.push(new card(aRack.getRack
                (calcCardsPlaceinSlot(rackoapp.newDeck.aDeck.peek())).getCard()));
                
                //Puts new card from deck into rack	 //new
                aRack.getRack
                (calcCardsPlaceinSlot(rackoapp.newDeck.aDeck.peek())).setCard
                (rackoapp.newDeck.aDeck.peek().getCard());
				
				 aRack.getRack
                (calcCardsPlaceinSlot(rackoapp.newDeck.aDeck.peek())).setText
                (rackoapp.newDeck.aDeck.peek().toString());
				
				 
				
				
                
                //Sets equivalent boolean arraylist index to true
                isSlotSatisfied.set(calcCardsPlaceinSlot
                (rackoapp.newDeck.aDeck.peek()),true);
                
                //Removes placed card from deck
                rackoapp.newDeck.aDeck.pop();
                rackoapp.updateTable();
                
            }
            else // Since no slots can be satisfied with the particular card
                //  AI just pick a card and puts it on discard
            {
                //Puts first card from facedown deck to discard pile
                rackoapp.newDeck.discardDeck.push
                (new card(rackoapp.newDeck.aDeck.peek().getCard()));
                
                rackoapp.terminalText.append("> "+getName()+" took: ["+
                rackoapp.newDeck.discardDeck.peek()+
                "] from facedownn deck and discarded the same card. \n");
                
                //GUI FOR DISCARD
                rackoapp.topDiscard.setText
                (rackoapp.newDeck.aDeck.peek().toString());
                
                //Removes the card from facedown deck.
                rackoapp.newDeck.aDeck.pop();
                
                //Check if deck is empty
                isDeckEmpty();
                
                // //GUI CODE
                rackoapp.topDrawDeck.setText
                (rackoapp.newDeck.aDeck.peek().toString());
                
            }
        }
        rackoapp.terminalText.append
        ("_________________________________________________________________________\n");
        aRack.lockRack();
        
        rackoapp.topDrawDeck.setEnabled(false);
        rackoapp.topDiscard.setEnabled(false);
        
	

		
        //Take another turn since this turn is over
        rackoapp.takeAnotherTurn();
        
        
    }
    
    /**
    * Returns index of slot from the back, needed because stack is being used.
    * @param i Value used to calculate new index.
	* @return New index value.
    */
    public static int idxReverse(int i)
    {
        return (((i+1)*9)%10);
    }
    
    
    /**
    * Calculates number of cards needed in each slot for ai.
    * @return Number of cards that can be places in each slot.
    */
    public static int cardperSlot()
    {
        switch(rackoapp.numberOfPlayers)
        { 	case 2: return 4;
            case 3: return 5;
            case 4: return 6;
        }
        return 4;
        
    }
    
    /**
    * Calculates the index of card to be placed in the arraylist
    * @param CardtoCheck Card which is to be places in arraylist.
    * @return Returns possible new index for that card to be placed.
    * @see ai#useOrNot
    */
    public int calcCardsPlaceinSlot(card CardtoCheck) {
        int counter = (CardtoCheck.getCard()/cardperSlot());
        if (CardtoCheck.getCard()%cardperSlot()==0)
        { counter--;
        }
        return idxReverse(counter);
    }
    
    /**
    * Calculates whether the passed card can be used
    * @param CardtoCheck Card which is used to check against arraylist.
    * @return Boolean value whether the card can be used.
    */
    public boolean useOrNot(card CardtoCheck) {
        return (!(isSlotSatisfied.get(calcCardsPlaceinSlot(CardtoCheck))));
    }
    
}