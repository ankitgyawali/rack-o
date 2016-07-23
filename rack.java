/**
* @author Ankit Gyawali
* File Name: rack.java
* This class contains necessary card objects and methods to manipulate
* card's held in a player's rack.
*/
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.BorderFactory;
import java.awt.Color;
import java.net.URL;
import java.net.MalformedURLException;

/**
* This is the class for cards held by a player in a Rack-O game.
* @author Ankit Gyawali
*/
public class rack extends JPanel {
    
    
    /**
    * Constructor for rack class. Set's null layout for the JPanel.
    */
    public rack() {
        setLayout(null);
    }
    
    /**
    * List of cards in an arraylist of size 10.
    */
    public ArrayList < card > rackToFill = new ArrayList < card > (10);
    
    /**
    * Sort's the current rack in order. Called if the cheat is activated.
    */
    public void sortRack() {
        ArrayList < Integer > sortCards = new ArrayList < Integer > ();
        for (int i = 0; i < 10; i++) {
            sortCards.add(rackToFill.get(i).getCard());
        }
        Collections.sort(sortCards);
        for (int i = 0; i < 10; i++) {
            rackToFill.get(i).setCard(sortCards.get(9 - i));
        }
    }
    
    
    /**
    * Searches the rackt to find index of the card passed.
    * @param cardToFind The card to look for.
    * @return Index of the card in the rack. Returns -1 if card is not found.
    * @see rack#getRack
    */
    public int getIDfromCard(card cardToFind) {
        for (int i = 0; i < 10; i++) {
            if (cardToFind.getCard() == rackToFill.get(i).getCard()) {
                
                return i;
                
            }
        }
        return -1;
    }
    
    /**
    * Locks all the card JButton of current player so that the
    * Action Listeners cant be activated.
    * @see rack#unlockRack
    */
    public void lockRack() {
        for (int i = 0; i < 10; i++) {
            rackToFill.get(i).setEnabled(false);
        }
    }
	
	
	  /**
    * Removes all the borders
    * @see rack#unlockRack
    */
    public void removeBorders() {
	
        for (int i = 0; i < 10; i++) {
		rackToFill.get(i).setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
        }
    }
    
    /**
    * UnLocks all the card JButton of current player so that the
    * Action Listeners can be activated.
    * @see rack#lockRack
    */
    public void unlockRack() {
        for (int i = 0; i < 10; i++) {
            rackToFill.get(i).setEnabled(true);
        }
    }
    
    /**
    * Updates JButton to display appropriate card values held in Rack.
    * @see rack#hideRackValues
    */
    public void showRackValues() {
        for (int i = 0; i < 10; i++) {
            rackToFill.get(i).setText(rackToFill.get(i).getCard() + "");
        }
    }
    
    /**
    * Updates JButton to hide card values held in Rack.
    * @see rack#showRackValues
    */
    public void hideRackValues() {
        for (int i = 0; i < 10; i++) {
            rackToFill.get(i).setText("");
            rackToFill.get(i).setIcon(new ImageIcon("hodor.jpg"));
        }
    }
    
    /**
    * Sets Icon of JButtons in the rack to use blank image file.
    */
    public void setIconBlank() {
        for (int i = 0; i < 10; i++) {
            rackToFill.get(i).setIcon(new ImageIcon("blank.jpg"));
        }
    }
    
    /**
    * Sets Icon of JButtons in the rack to use blank image file when disabled.
    */
    public void setDisabledIconBlank() {
        for (int i = 0; i < 10; i++) {
            rackToFill.get(i).setDisabledIcon(new ImageIcon("blank.jpg"));
        }
    }
    
    /**
    * Sets Icon of JButtons in the rack to use specified image  when disabled.
    */
    public void setDisabledX() {
        for (int i = 0; i < 10; i++) {
            rackToFill.get(i).setDisabledIcon(new ImageIcon("hodor.jpg"));
        }
    }
    
    /**
    * Sets card to rack according to specified index.
    * @param index Index in the rack where card has to be set.
    * @param cardToAdd Card to set in the rack.
    */
    public void setToRack(int index, card cardToAdd) {
        rackToFill.set(index, cardToAdd);
    }
    
    
    /**
    * Adds card after last element in the rack.
    * @param cardToAdd Card to set in the rack.
    */
    public void setToRack(card cardToAdd) {
        rackToFill.add(cardToAdd);
    }
    
    /**
    * Gets the card on specified rack index.
    * @param index Index where the card has to be added.
    * @return Returns the card from specified index.
    */
    public card getRack(int index) {
        return rackToFill.get(index);
    }
    
    /**
    * Returns an int of rack size.
    * @return Returns the size of rack.
    */
    public int getRackSize() {
        return rackToFill.size();
    }
    
    /**
    * Clears the rack of player.
    */
    public void clearRack() {
        rackToFill.clear();
    }
    
    
}