/**
* @author Ankit Gyawali
* File Name: human.java
* Extends player class, implements method to take
* a turn human.
*/
import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.JButton;
/**
* Provides a method to take a turn for human by extending player class.
* The actual taking of turn will occurs in our GUI, rackoapp, since GUI is event
* driven.
*/
public class human extends player
{
    /**
    * Human constructor gets values from player class, to use it.
    * @param newRack Rack of the player.
    * @param isHuman Boolean value if player is human or computer
    * @param playaName Name of the player
    * @playaScore Score of the player
    */
    human(rack newRack, boolean isHuman, String playaName, int playaScore)
    {
        super (newRack, isHuman,playaName,playaScore);
    }
    
    /**
    * Method takeATurn for human. Enables hearing of Action Listeners in
    * the GUI, rackoapp.java.
    */
    public void takeATurn()
    {
		 rackoapp.newPlayers.get(rackoapp.currentPlayer).aRack.removeBorders();
        rackoapp.topDrawDeck.setEnabled(true);
        rackoapp.topDiscard.setEnabled(true);
		rackoapp.topDrawDeck.setText("");
        rackoapp.newPlayers.get(rackoapp.currentPlayer).aRack.showRackValues();
        rackoapp.newPlayers.get(rackoapp.currentPlayer).aRack.setIconBlank();
        rackoapp.terminalText.append("> Current Player: "
        +rackoapp.newPlayers.get(rackoapp.currentPlayer).getName()+"\n");
    }
}