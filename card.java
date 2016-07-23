/**
* @author Ankit Gyawali
* File Name: card.java
* This class contains implementation of card object.
*/
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.Insets;
import java.awt.Color;
import javax.swing.UIManager.*;
import java.net.URL;
import java.net.MalformedURLException;
import java.applet.Applet;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.applet.*;

/**
* Card class is representation of card held by the player in Rack-O.
*/
public class card extends JButton{
    
    /**
    * Cardvalue is the integer held by the card.
    */
    private int cardvalue;
    
    /**
    * Constructor for card class.
    * @param cardValtoSet Card initialized with its value.
    */
    public card(int cardValtoSet) //Constructor
    {
        cardvalue = cardValtoSet;
        setText(Integer.toString(cardvalue));
        setIcon(new ImageIcon(getClass().getResource("blank.jpg")));
        setEnabled(false);
		setDisabledIcon(new ImageIcon(getClass().getResource("hodor.jpg")));
        setVerticalTextPosition(JButton.CENTER);
        setHorizontalTextPosition(JButton.CENTER);
        setMargin(new Insets(1,1,1,1));
        //setBorder(null);
		
		
		setRolloverSelectedIcon(new ImageIcon(getClass().getResource("blank.jpg")));
		
		
	
    }
    
    /**
    * Returns reference to the card JButton.
    * @return Reference to the card JButton.
    */
    public JButton getButtonref()
    {
        return this;
    }
    
    /**
    * @return Returns the cardValue.
    * @see card#setCard
    */
    public int getCard() {
        return cardvalue;
    }
    
    /**
    * Modifies the card's value.
    * @param cardtoSet Sets the current card's value.
    * @see card#getCard
    */
    public void setCard(int cardtoSet) {
        cardvalue = cardtoSet;
    }
    
    /**
    * Overloads toString for easier card output.
    */
    public String toString() {
        return this.getCard()+"";
    }
	
	
}