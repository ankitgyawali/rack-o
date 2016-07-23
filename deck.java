/**
* @author Ankit Gyawali
* File Name: deck.java
* This class contains the deck and discard deck to  be used in the game
* and appropriate methods.
*/
import java.util.*;
import javax.swing.JButton;
import javax.swing.ImageIcon;
/**
* Deck class contains the two table decks to be used while playing Rack-o.
*/
public class deck {
    
    /**
    * Decksize determined according to number of players.
    */
    public int deckSize;
    /**
    * Number of players in game.
    */
    public int numPlayers;
    /**
    * Stack of card type, that contains facedown cards to be used in game.
    */
    public Stack<card> aDeck = new Stack<card>();
    /**
    * Stack of card type, that contains discard cards to be used in game.
    */
    public Stack<card> discardDeck = new Stack<card>();
    
    
    /**
    * Constructor for deck. Instantiate's deck with cards.
    * @param numbPlayers Number of players in the game.
    */
    public deck(int numbPlayers)
    {
        numPlayers = numbPlayers;
        int cardsinDeck;
        switch (numbPlayers)
        {
            case 2:deckSize = 40;break;
            case 3:deckSize = 50;break;
            case 4:deckSize = 60;break;
        }
        
        
        /**
        * Create a Deck with card objects of deckSize
        */
        for ( int deckCtr = 1; deckCtr <= deckSize; deckCtr++) {
            aDeck.add(new card(deckCtr));
        }
    }
    
    
    
    
    /**
    * Gets a card from specified index from main deck.
    * @param index Index to get card from.
    * @return returns Returns the retrieved card.
    */
    public card getFromDeck(int index) {
        return aDeck.get(index);
    }
    
    /**
    * Removes a card from specified index in the deck.
    * @param index Index to remove card from.
    * @see deck#getFromDeck
    */
    public void removeFromDeck(int index) {
        aDeck.remove(index);
    }
    
    /**
    * Gets the size of deck.
    * @return Returns the size of deck.
    */
    public int getDeckSize() {
        return aDeck.size();
    }
    
    /**
    * Looks for card in the deck and returns it's index.
	* @param cardToSearch Card to look for.
	* @return Returns the index of card.
    */
    public int getIndexOf(card cardToSearch) {
        return aDeck.search(cardToSearch);
    }
    
    /**
    * Adds a card to the discard pile.
    * @param cardToAdd Card to be added to discard deck.
    */
    public void addToDiscard(card cardToAdd) {
        discardDeck.add(cardToAdd);
    }
    
    
    
    
    /**
    * Returns card from specified index from the discard pile.
    * @param index Index to get card from.
    * @see deck#getFromDeck
    * @return Returns the retrieved card.
    */
    public card getFromDiscard(int index) {
        return discardDeck.get(index);
    }
    
    
    /**
    * Returns discard pile size.
    * @return Size of the discard deck stack.
    */
    public int getDiscardDeckSize() {
        return discardDeck.size();
    }
    
    
    /**
    * Find index of card inside facedown deck.
    * @param cardToFind Card to search for.
    * @return Returns the index of card.
    * @see deck#getIndexOf
    */
    public int findIndexOf(card cardToFind) {
        for (int i=0; i < getDeckSize(); i++)
        {
            if (cardToFind.getCard()==aDeck.get(i).getCard())
            {
                return i;
            }
        }
        return -1;
    }
    
    /**
    * Uses collection.shuffle() to shuffle the stack of card
    */
    public void shuffleDeck() {
        // Collections.shuffle(aDeck,new Random(007));
		Collections.shuffle(aDeck);
    }
    
    /**
    * Returns the frequently used top discard from discard pile.
	@return The top card from discard pile.
    */  
    public card topDiscard () {
        return discardDeck.get(discardDeck.size()-1);
    }
}