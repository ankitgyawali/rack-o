/**
* @author Ankit Gyawali
* File Name: rackoapp.java
* Constructs GUI for the game Rack-O by using required classes.
*/
import java.io.*;
import java.awt.*;
import java.util.*;
import java.applet.*;
import java.util.List;
import javax.swing.*;
import java.awt.event.*; 
import javax.swing.event.*;
import javax.swing.Timer;
import java.util.*;
import java.io.*;
import java.lang.*;
import javax.swing.UIManager.*;
import java.awt.image.BufferedImage;
import javax.swing.border.EtchedBorder;
import java.net.URL;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.UnknownHostException;


/**
* Constructs a GUI for the game Rack-O and provides game play.
*/
public class rackoapp extends Applet implements Runnable
{ 
	/**
	* Int representing number of players in the game.
	*/
	public static int numberOfPlayers=4;
	/**
	* Int representing number of turns taken in the game.
	*/
	public static int numberofturns =1;
	/**
	* Int representing currently playing player index.
	*/
	public static int currentPlayer = 0;

	
	//Various global booleans, which should be self explanatory
	public static boolean isitDemo = true, cheatOrNot = false, haltGame = false;
	
	/**
	* Arraylist of players in the game.
	*/
	public static ArrayList<player> newPlayers 
	= new ArrayList<player>(numberOfPlayers);
	
	/**
	* Deck to be used for the game, instantiated with number of players.
	*/
	public static deck newDeck;
	
	/**
	* TextField used to send and receive message
	*/
	public static JTextField messageField;
	
	/**
	* Panels for the game's GUI.
	*/
	public static JPanel score, tableCards, gameOptions;
	public static JButton topDrawDeck, topDiscard, gotButtons[], sendMessage, resetGame;
	
	/**
	* TextArea, that provides instructions for the game.
	*/
	public static JTextArea terminalText  = new JTextArea(); 

	public static JLabel turnnum, playerScores[], playerrackName[];
	
	/**
	* List of player icons. Used by the function gotify().
	* @see rackoapp#gotify
	*/
	public static  List<String> gotImages = Arrays.asList
	("JonSnow.png", "LittleFinger.png", "KhalDrogo.png","Tyrion.png");
	
	/**
	* List of player information taken when java is deployed.
	*/
	public static List listToInject;
	
	public static int internetGame = 1;
	public static int bndX =  0;
	
	//For internet game play
    public static Socket socket = null;
    public static PrintWriter out = null;
    public static BufferedReader in = null;
	
	//ReackoApp reference
	public static rackoapp rackoreference;
	
	//Context menu
	public static  JPopupMenu menu = new JPopupMenu("RackoApp Popup");
	
	
	// Various global ints declared to use for game play.
	public static int turntoQuit=0, topDrawDeckCount = 0, 
	actionListenerState = -1, FaceCount = 0, aiDelay = 1;
	
/**
* Calls necessary functions in to initialize GUI applet before taking a turn.
* @see rackoapp#createPlayersGUI
* @see rackoapp#createScorePanel
* @see rackoapp#showTableCards
* @see rackoapp#creategameOptions
* @see rackoapp#sendMessageActionListener
* @see rackoapp#createTerminal
* @see rackoapp#tableCardsActionListener
* @see rackoapp#playerActionListener
* @see rackoapp#gotify
* @see rackoapp#updateScore
* @see rackoapp#takeAnotherTurn
*/
public void init()
	{
			
		
	rackoreference = this;
	//UIManager used to change the look and feel of applet.
	UIManager.put("Button.disabledText", Color.BLACK);
	UIManager.put("Button.disabledText", Color.BLACK);
	UIManager.put("Label.font",new Font("Serif", Font.PLAIN, 12));


	//GET STUFF FROM DEPLOY JAVA
	//{totalPlayers:numberPlayers, humansOrAI:playerKey} ;
	// numberOfPlayers = Integer.parseInt(this.getParameter("totalPlayers"));
	// String fromDeployment = ((String)this.getParameter("humansOrAI"));
	// listToInject = Arrays.asList(fromDeployment.split(" ")); 

	
	//Applet Uses null layout.	
	setLayout(null);
	//right click context menu
    addcontextMenu();

   
	//Animation stuff
	 beforeGame();
			

	
	
	
 
	}
	
public void run()
{
	
}	

public void addcontextMenu()
{
	
	 addMouseListener(new PopupTriggerListener());

    JMenuItem item = new JMenuItem("Replay Game");
    item.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        resetlocalGame();
      }
    });
    menu.add(item);
	
	
	item = new JMenuItem("Cheats");
    item.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        	JOptionPane.showMessageDialog(null, "Cheats can be activated via 'chat' button.\n You can use only one cheat at a time.\n /c  shows all players racks\n /n someInteger  will end the game after currentTurn + someInteger turns\n /s will sort and display current player's cards. Duplicate cards wont be used.\n	/o  1 2 3 4 will inject 4 cards, 1 2 3 & 4 in order. \nNo error catching for injecting more than 10 cards or injecting strings has been implemented.\n Duplicate cards will exist in the game if this cheat was used, unlike /s.");
      }
    });
    menu.add(item);
	
	
		item = new JMenuItem("Give me a Hint");
    item.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        int cardperSlot = numberOfPlayers + 2;
			
			
	    int counter = (newDeck.aDeck.peek().getCard()/cardperSlot);
        if (newDeck.aDeck.peek().getCard()%cardperSlot==0)
        { counter--;
        }
	   counter++;
	
		int counterxx = (newDeck.discardDeck.peek().getCard()/cardperSlot);
        if (newDeck.discardDeck.peek().getCard()%cardperSlot==0)
        { counterxx--;
        }
		counterxx++;
	
			JOptionPane.showMessageDialog(null, "You could try to place card with values between "+cardperSlot+" times (n-1) and "+ cardperSlot+ " times n slot for each nth slot in your rack \n"+ "Seems like top card from draw pile ["+newDeck.aDeck.peek().toString() +"] should be on slot#" + counter   +"! \n"+ "Seems like top card from discard pile ["+newDeck.discardDeck.peek().toString() +"] should be on slot# " + counterxx   +" ! \n");
			
			
	
			
      }
    });
    menu.add(item);
	
}
public void resetlocalGame()
{
	rackoreference.removeAll();
	currentPlayer=0;
	
    newPlayers.clear();
	terminalText.setText(null);
	terminalText.setText("> Game has been reset.\n \n ");
    localGame();

	JOptionPane.showMessageDialog(null, "Game has been reset");
	 
}

class PopupTriggerListener extends MouseAdapter {
      public void mousePressed(MouseEvent ev) {
        if (ev.isPopupTrigger()) {
          menu.show(ev.getComponent(), ev.getX(), ev.getY());
        }
      }

      public void mouseReleased(MouseEvent ev) {
        if (ev.isPopupTrigger()) {
          menu.show(ev.getComponent(), ev.getX(), ev.getY());
        }
      }

      public void mouseClicked(MouseEvent ev) {
      }
    }
  

public void beforeGame()
{
	
			
			try{
     URL openSeq = new URL(getCodeBase(), "gotIntro.wav");
	// URL openSeq = new URL(getCodeBase(), "gotFull.wav");
	AudioClip dealingCards = getAudioClip(openSeq);
	dealingCards.play();
    }catch (MalformedURLException e){
        System.err.println("New URL failed");
        System.err.println("exception thrown: " + e.getMessage());
		
	}
	
	
	
		JButton gameInit = new JButton (new ImageIcon(getClass().getResource("gifJLabel.gif")));
	
	add(gameInit);
	gameInit.setBounds(50, 350, 700,100);

	JLabel introText = new JLabel();
	introText.setIcon(new ImageIcon(getClass().getResource("gameOfRackO.png")));
	add(introText);
	
	gameInit.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.RED));
	gameInit.setDoubleBuffered(true);
	introText.setDoubleBuffered(true);
	
	Thread one = new Thread() {
    public void run() {
		
        Timer timerX = new Timer(20, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
				
					
				introText.setBounds(bndX, 100, 700, 150);
				bndX+=5;
				introText.repaint();
				if (bndX==800) { bndX=50;}
				
			
				
		   }
            });
    
            timerX.setCoalesce(true);
            timerX.start();
    }  
};

one.start();




gameInit.addActionListener(new ActionListener()
	{ 
	public void actionPerformed(ActionEvent event) {
	remove(gameInit);
	remove(introText);
		playDealCard();	
		localGame();
	}
	});
	

}

public void playDealCard()
{
	
	try{
     URL openSeq = new URL(getCodeBase(), "openCard.wav");
	AudioClip dealingCards = getAudioClip(openSeq);
	dealingCards.play();
	
	
	
    }catch (MalformedURLException e){
        System.err.println("New URL failed");
        System.err.println("exception thrown: " + e.getMessage());
    }  
}
	
public void instantiateSocket()
{


  try{
       socket = new Socket("localhost", 15008);
	   if(socket == null)
	   {
			 JOptionPane.showMessageDialog(null, "Cannot connect to server, try again","Server Error" ,JOptionPane.ERROR_MESSAGE);
	   }
       out = new PrintWriter(socket.getOutputStream(), true);
       in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
     } catch (UnknownHostException e) {
       System.out.println("Unknown host: acad");
       System.exit(1);
     } catch  (IOException e) {
       System.out.println("No I/O");
	   JOptionPane.showMessageDialog(null, "Cannot connect to server, try again","Server Error" ,JOptionPane.ERROR_MESSAGE);
       System.exit(1);
     }


		
}



public void localGame()
{
	haltGame = false; numberofturns=1;
	//Adds Player's panel, deck's panel to the GUI after being Instantiated
	createPlayersGUI();
	
	//Adds Score Panel to the GUI after calculating the Score
	createScorePanel();

	//Show Deck and Discard Deck cards
	showTableCards();
	
	//Create game option to send message
	creategameOptions();

	//Add action listener to send message button aka IMPLEMENT CHEAT
	sendMessageActionListener(); 
	
	//Creates terminal to provide game instructions before game starts
	createTerminal();

	// Prepares to start the game by adding action listener to table cards
	tableCardsActionListener();
	
	//Adds action listeners to player's card
	playerActionListener();

	
	//Lock and hide everything in preparation for game to start
	for (int i=0;i<numberOfPlayers;i++)
		{
		newPlayers.get(i).aRack.lockRack();
    	newPlayers.get(i).aRack.hideRackValues();
		}
		

	
	//Added Game of Thrones related "theme" to the GUI
	gotify();
		
	//Update everything before taking a turn	
	updateScore(currentPlayer); updateRackLabel(currentPlayer); updateTable(); 
	repaint(); validate();
	
	//Calls a RECURSIVE function that keeps taking turn until game ends
	newPlayers.get(currentPlayer).takeATurn();
		
	//Update everything before ending the game	
	updateScore(currentPlayer); updateRackLabel(currentPlayer); updateTable(); 
	repaint(); validate();	
}


/**
* Creates terminal and prints out instructions for the game. 
* This terimnal's Scroll Pane uses SmartScroller.java.
* @see SmartScroller#SmartScroller
*/
 public void createTerminal()
 {
	 
	 
	//Creates a JPanel for the terminal
	JPanel terminal = new JPanel();
	terminal.setLayout(new BorderLayout()); 
	//Adds the terminal
	add(terminal);
	terminal.setBounds(100, 85, 600, 110);

	//Add scroll pane to terminal.
	JScrollPane scroll = new JScrollPane(terminalText); 




	terminalText.setFont(new Font("Bookman Old Style", Font.PLAIN, 12));
	terminal.add(scroll, BorderLayout.CENTER);
	new SmartScroller(scroll);
	scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	scroll.getVerticalScrollBar().setPreferredSize(new Dimension(13, 0));

	//Sets terminal properties
	terminalText.setBackground(Color.BLACK);
	terminalText.setForeground(Color.YELLOW);
	terminalText.setEditable(false);
	terminalText.append
	("> Please refer to this terminal for game instructions and/or game information.\n");
	terminalText.append
	("> Cards must be ordered lowest to highest from LEFT to RIGHT or TOP to BOTTOM \n");
	terminalText.append
	("> Place the scroll bar at bottom to activate auto scroll in the terminal. \n ");
	terminalText.append
	("> This lets you see new messages automatically. \n");
	terminalText.setForeground(Color.WHITE);
	terminalText.append
	("_________________________________________________________________________\n");
	 	 
 }
 
/**
* Change State of the cards in the table to true if false and false if true.
*/
 public static void changeDeckState()
 {
	 topDrawDeck.setEnabled(!topDrawDeck.isEnabled());
	 topDiscard.setEnabled(!topDiscard.isEnabled());
 }
 
/**
* Updates the discard pile in the table.
*/
 public static void updateDiscard()
{
	topDiscard.setText(newDeck.discardDeck.peek().toString());
}

/**
* Updates the cards in the table with current value held inside the top cards.
*/
public static void updateTable()
{
	//topDrawDeck.setText(newDeck.aDeck.peek().toString());
	topDiscard.setText(newDeck.discardDeck.peek().toString());

}

/**
* Adds Panel and top cards from the deck to the GUI
*/
public void showTableCards()
{
	//Panel for the table Cards
	tableCards = new JPanel();
	add(tableCards);
	tableCards.setBounds(100, 325, 600, 100);
	tableCards.setLayout(null);

	//Label and JButton for top deck cards.
	JLabel deckLbl = new JLabel("Facedown Deck");
	tableCards.add(deckLbl);
	deckLbl.setBounds(15,0,180,20);
	
	//Show top deck card
	topDrawDeck = new JButton();
	tableCards.add(topDrawDeck);
	topDrawDeck.setBounds(15, 20, 50,77);
	
	//Label and JButton for top discard.
	JLabel discardLbl = new JLabel("Discard Pile");
	tableCards.add(discardLbl);
	discardLbl.setBounds(250,0,180,20);
	
	//Show top discard
	topDiscard = new JButton();
	topDiscard.setText(newDeck.discardDeck.peek().toString());
	tableCards.add(topDiscard);
	topDiscard.setBounds(250, 20, 50,77);
	
	//Finally disables the top cards.
	topDiscard.setEnabled(false);
	topDrawDeck.setEnabled(false);	
 }
	
/**
* Adds Panel and top cards from the deck to the GUI
* @param x Represents index of player whose score has to be updated.
* @see rackoapp#updateRackLabel
* @see rackoapp#updateDiscard
*/
public static void updateScore(int x)
{
		playerScores[x].setText
		(newPlayers.get(x).getName()+": "+newPlayers.get(x).getScore());
		
}

/**
* Creates a Panel where player's score are displayed and add to the GUI.
* @see rackoapp#createPlayersGUI
* @see rackoapp#createTerminal
* @see rackoapp#creategameOptions
*/	
public void createScorePanel()
{
		
		score = new JPanel();
		score.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
		 
		JLabel label  = new JLabel
		("<html><u>Current Game Information</u><html>", JLabel.CENTER); 
		//Creates Label for turn number.
		turnnum  = new JLabel("Current Turn# "+numberofturns, JLabel.CENTER); 
		
		score.setLayout(new GridLayout(3, 2));
		score.add(label);
		score.add(turnnum);
		playerScores = new JLabel[numberOfPlayers];
		
		//Populates players' score label
		for (int i=0; i<numberOfPlayers;i++) 		
		{
			if (newPlayers.get(i).humanOrNot) 
			{   
			isitDemo = isitDemo && false;     
			}
			newPlayers.get(i).calculateScore();
			playerScores[i] = new JLabel(newPlayers.get(i).getName()+": "
			+newPlayers.get(i).getScore(), JLabel.CENTER);  
			score.add(playerScores[i]);
		}
	add(score);
	score.setBounds(100, 5, 600, 75);
}

/**
* Updates rack label for current player
* @param x Represents index of player whose label has to be updated.
* @see rackoapp#updateScore
* @see rackoapp#updateDiscard
*/
public static void updateRackLabel(int x)
{ 	
	int xxScore = (newPlayers.get(x).getScore()/5);
	xxScore = (xxScore == 15) ? (xxScore-5) : xxScore;
	if (x==0 || x==1) 
	{
		playerrackName[x].setText(newPlayers.get(x).getName()
		+"'s Rack has ["+xxScore+"] cards sorted");
	}
	else 
	{
	playerrackName[x].setText("<html>"+newPlayers.get(x).getName()
	+" ["+xxScore+"]<br> cards sorted</html>");	
	}	
}

/**
* Adds players' racks to the applet.
* @see rackoapp#createScorePanel
* @see rackoapp#createTerminal
* @see rackoapp#creategameOptions
*/
public void createPlayersGUI()
{
		//Instantiates new deck and shuffles it.
		newDeck = new deck(numberOfPlayers);
		newDeck.shuffleDeck();
		Collections.shuffle(gotImages);
		newPlayers.clear();
		playerrackName = new JLabel[numberOfPlayers];

		int tempYaxis=200;
		
		
		// for (int i=0;i<numberOfPlayers;i++)
		// {	if (((String)listToInject.get(i)).equals("human"))
		// {newPlayers.add(new human(new rack(), true,(gotImages.get(i).substring(0, gotImages.get(i).length()-4)),0));	}else
		// {newPlayers.add(new ai(new rack(), false,(gotImages.get(i).substring(0, gotImages.get(i).length()-4)),0));}	}
	
	
		newPlayers.add(new ai(new rack(), false,(gotImages.get(0).substring(0, gotImages.get(0).length()-4)),0));
		newPlayers.add(new ai(new rack(), false,(gotImages.get(1).substring(0, gotImages.get(1).length()-4)),0));

		  newPlayers.add(new ai(new rack(), false,(gotImages.get(2).substring(0, gotImages.get(2).length()-4)),0));
		  newPlayers.add(new ai(new rack(), false,(gotImages.get(3).substring(0, gotImages.get(3).length()-4)),0));
		
		
		
		
		
		for (int i=0;i<numberOfPlayers;i++)
		{
		//For 2 players
		if (i==0 || i==1) {
		playerrackName[i] = new JLabel(newPlayers.get(i).getName()+"'s Rack: ");
		add(newPlayers.get(i).aRack);
		newPlayers.get(i).aRack.add(playerrackName[i]);
		newPlayers.get(i).aRack.setBounds(100,tempYaxis,600,120);
		playerrackName[i].setBounds(150,10,400,20);
		tempYaxis+=230;
		}
		else if  (i==2) {
		add(newPlayers.get(i).aRack);
		newPlayers.get(i).aRack.setBounds(5,5,90,550);
		playerrackName[i] = new JLabel("<HTML>"+newPlayers.get(i).getName()
		+"'s <BR>Rack:</HTML>");
		newPlayers.get(i).aRack.add(playerrackName[i]);
		playerrackName[i].setBounds(5,105,100,45);
		}
		else {
		add(newPlayers.get(i).aRack);
		newPlayers.get(i).aRack.setBounds(5,5,90,550);
	
		playerrackName[i-1].setText("<HTML>"+newPlayers.get(i-1).getName()+"'s <BR>Rack:</HTML>");
		newPlayers.get(i-1).aRack.add(playerrackName[i-1]);
		playerrackName[i-1].setBounds(5,105,100,45);	
		
		playerrackName[i] = new JLabel("<HTML>"+newPlayers.get(i).getName()+"'s <BR>Rack:</HTML>");
		newPlayers.get(i).aRack.add(playerrackName[i]);
		playerrackName[i].setBounds(5,105,100,45);	
		
		
		add(newPlayers.get(i).aRack);
		newPlayers.get(i).aRack.setBounds(705,5,90,550);	}
		}
		
		//Deals Cards
		dealCards();
		
		
		for (int i=0;i<numberOfPlayers;i++) {
		int pixel = 500;
		int pixelYaxis = 450;
		for (int j=0;j<10;j++) 
		{
			if (i==0 || i==1) {
			newPlayers.get(i).aRack.add(newPlayers.get(i).aRack.getRack(j));
			newPlayers.get(i).aRack.getRack(j).setBounds(pixel, 40, 50,77);
			
			pixel-=40;
			}	
			else 
			// else if (i==2 || i==3) 
			// HANDLES BOTH player 3 & 4 might need one else if later
			{
			newPlayers.get(i).aRack.add(newPlayers.get(i).aRack.getRack(j));
			newPlayers.get(i).aRack.getRack(j).setBounds(20, pixelYaxis, 50,40);
			pixelYaxis-=30;
			}
		}
	}
}	

/**
* Deal cards to the players.
*/
private void dealCards()
{
	int pidx =0;
	for (int j=0;j<numberOfPlayers;j++)
	{
		for (int i=0;i<10;i++)
		{
			newPlayers.get(pidx).aRack.setToRack(newDeck.aDeck.peek());
			newDeck.aDeck.pop();
		}
		pidx++;
	}
	//First Discard
	newDeck.discardDeck.push(newDeck.aDeck.peek());
	newDeck.aDeck.pop(); 

}

/**
* Create game options, mainly used for cheat.
* @see rackoapp#createScorePanel
* @see rackoapp#createTerminal
* @see rackoapp#createPlayersGUI
*/
public void creategameOptions()
{
	//Game Option JPanel instantiated and added here
	gameOptions = new JPanel();
	sendMessage = new JButton("Chat:");
	gameOptions.add(sendMessage);
	sendMessage.setEnabled(false);
	messageField = new JTextField("", 30);
	
	//MessageField TextField added to gameOptions, used to get text from user
	gameOptions.add(messageField);
	//DoumentListener added to TextField to enable sendMessage actionListener click
	DocumentListener documentListener = new DocumentListener() 
	{
		public void insertUpdate(DocumentEvent e)
		{  sendMessage.setEnabled(true);
		}
		 public void removeUpdate(DocumentEvent e) {
        sendMessage.setEnabled(true);
		}
		public void changedUpdate(DocumentEvent e) {
        sendMessage.setEnabled(true);
		}
		
	};
	messageField.getDocument().addDocumentListener(documentListener);
	
	//Panel added to applet
	add(gameOptions);
	gameOptions.setBounds(100, 555, 600, 35);
}

/**
* Part of recursion in takeATurn, checks if game should end, if not 
* player another turn.
* @see ai#takeATurn
* @see player#takeATurn
*/
public static void takeAnotherTurn()
{
	if (newPlayers.get(currentPlayer).getScore() > 70)
	{	
		// If game is over set haltGame
		haltGame = true; 
		newPlayers.get(currentPlayer).aRack.lockRack();
		terminalText.append("> "+newPlayers.get(currentPlayer).getName()
		+" won the game. Hope you enjoyed the game. Play again! \n");
		int isItBonus = 0;
		for (int i=1;i<10;i++)  //Check for bonus score, part of new assignment
		{
			if (newPlayers.get(currentPlayer).aRack.getRack(i-1).getCard() 
				!= (newPlayers.get(currentPlayer).aRack.getRack(i).getCard()+1))
			{
				isItBonus++;
			}
		}
		if (isItBonus==9)
		{
			terminalText.append("> "+newPlayers.get(currentPlayer).getName()+
			" won the game with no consecutive cards on the rack. He is awarded 25 points of bonus! \n");
			newPlayers.get(currentPlayer).setScore
			((newPlayers.get(currentPlayer).getScore()+25));
			updateScore(currentPlayer);
		}	
	} 
	// Since the game is not over take appropriate action before taking turn.
	else 
	{   
		//Check if deck is empty
		newPlayers.get(currentPlayer).isDeckEmpty();
		
		//Lock current players rack, calulcate score, update GUI
		newPlayers.get(currentPlayer).aRack.lockRack();
		newPlayers.get(currentPlayer).calculateScore();
		updateScore(currentPlayer);
		updateRackLabel(currentPlayer);
		
		//If game is Demo show all values before taking turns.
		if (isitDemo) 
		{
			newPlayers.get(currentPlayer).aRack.showRackValues();
			
		}
		else
		{
			newPlayers.get(currentPlayer).aRack.hideRackValues();
		}
		//Increament currentplayer's value by 1 before taking another turn
		currentPlayer = (currentPlayer+1)%numberOfPlayers;
		
		
		//If taking turn is back to Player 1, increase number of turns taken by 1 
		if(currentPlayer==0)
		{	
			numberofturns++;
			turnnum.setText("Current Turn# "+numberofturns);
		}
		//Check if cheat has to be used or not before taking turn
		execCheat();
	
		if(!haltGame)
		{
			
		if (!newPlayers.get(currentPlayer).humanOrNot)	 {
			Timer timer = new Timer(aiDelay, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
		
					
					newPlayers.get(currentPlayer).takeATurn(); 
					
		   }
            });
            timer.setRepeats(false);
            timer.setCoalesce(true);
            timer.start();
		}
		else
		{
			newPlayers.get(currentPlayer).takeATurn(); 
		}
		//If game is not over take a turn.
		
		}
	} 
}


/**
* Adds appropriate action listeners to table cards
* @see rackoapp#playerActionListener
*/
public void tableCardsActionListener()
{
	//Add Action Listener to top draw deck
	topDrawDeck.addActionListener(new ActionListener()
	{ public void actionPerformed(ActionEvent event)
	{
	if (FaceCount==0) //Button pressed once
		{	
		terminalText.append("> "+newPlayers.get(currentPlayer).getName()
		+" got ["+ newDeck.aDeck.peek().toString()+
		"], pick a card from your rack to replace, or click the card again to discard. \n");
		topDrawDeck.setText(newDeck.aDeck.peek().toString());
        //The same button was clicked two+ times in a row
		newPlayers.get(currentPlayer).aRack.unlockRack();
		newPlayers.get(currentPlayer).aRack.showRackValues();	
		newPlayers.get(currentPlayer).aRack.setIconBlank();
		newPlayers.get(currentPlayer).isDeckEmpty();
		rackoapp.topDiscard.setEnabled(false);
		actionListenerState = 0; 
		FaceCount++;
	
	} 
	//Discard the face card since button was pressed twice
    else
	{ 
		try{
		URL cardURL = new URL(getCodeBase(), "cardClick.wav");
		AudioClip cardClickedClip = getAudioClip(cardURL);
		cardClickedClip.play();
		}catch (MalformedURLException x){
        System.err.println("New URL failed");
        System.err.println("exception thrown: " + x.getMessage());
		}
		
		terminalText.append("> "+newPlayers.get(currentPlayer).getName()
		+" picked ["+topDrawDeck.getText()+ "] and discarded it.\n");
		terminalText.append
		("_________________________________________________________________________\n");
		newDeck.discardDeck.push(rackoapp.newDeck.aDeck.peek());
		
		// Update the Discard on GUI
		topDiscard.setText(newDeck.aDeck.peek().toString());
		newDeck.aDeck.pop();
		newPlayers.get(currentPlayer).isDeckEmpty();
		//Update the Draw Deck on GUI 
		topDrawDeck.setText(newDeck.aDeck.peek().toString());


		//Prepare for next Player
		topDrawDeck.setText("");
		topDrawDeck.setEnabled(false);
		actionListenerState = -1; FaceCount--;	
	
		//Take another turn
		takeAnotherTurn();
	
    } 
    //rackoapp.topDrawDeck = buttonPressed;
	} });
	
	topDiscard.addActionListener(new ActionListener()
	{ public void actionPerformed(ActionEvent event)
	{	
	terminalText.append("> "+newPlayers.get(currentPlayer).getName()+
	" chose ["+newDeck.discardDeck.peek().toString()+
	"] from discard deck. Choose a card from your rack to replace it with. \n");
	//Unlock player's rack to let player choose from rack
	newPlayers.get(currentPlayer).aRack.unlockRack();
	newPlayers.get(currentPlayer).aRack.showRackValues();	
	newPlayers.get(currentPlayer).aRack.setIconBlank();
	topDrawDeck.setEnabled(false);	
	topDiscard.setEnabled(false);
	actionListenerState = 1;
	} });	
}	

/**
* Adds appropriate action listeners to player's cards
* @see rackoapp#tableCardsActionListener
*/
public void playerActionListener()
{
	

	// topDrawDeckCount = 0, actionListenerState = -1;	
	for (player currentlyPlaying: newPlayers)
	{
		for	(card identifier: currentlyPlaying.aRack.rackToFill)
		{
		identifier.addActionListener(new ActionListener()
		{ public void actionPerformed(ActionEvent event)   {
			
			
		try{
		URL cardURL = new URL(getCodeBase(), "cardClick.wav");
		AudioClip cardClickedClip = getAudioClip(cardURL);
		cardClickedClip.play();
		}catch (MalformedURLException x){
        System.err.println("New URL failed");
        System.err.println("exception thrown: " + x.getMessage());
		}
		if (actionListenerState==0) //Swap Rack and Facedown card
		{
			identifier.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.red));
			
			
			FaceCount--;
			int idx = newPlayers.get(currentPlayer)
			.aRack.getIDfromCard(identifier);
			
			// First adds the discarded card from rack to arraylist of Discarded Deck
			newDeck.discardDeck.push(new card(identifier.getCard())); 
			
			//Update the discard on GUI
			topDiscard.setText(identifier.toString());
			newPlayers.get(currentPlayer).aRack.getRack(idx)
			.setText(newDeck.aDeck.peek().toString());
		
			// Second, replaces the discarded card in the rack 
			newPlayers.get(currentPlayer).aRack.getRack(idx)
			.setCard(newDeck.aDeck.peek().getCard()); 			
		
		
			// Lastly removes the used card from the facedown deck
			newDeck.aDeck.pop(); 
			newPlayers.get(currentPlayer).isDeckEmpty();
			//Update the DrawDeck on GUI
			topDrawDeck.setText(newDeck.aDeck.peek().toString());
	
			terminalText.append("> "+newPlayers.get(currentPlayer).getName()
			+" replaced ["+ newDeck.discardDeck.peek() 
			+"] with ["+ newPlayers.get(currentPlayer).aRack.getRack(idx)
			+"] from facedown deck.\n");
			terminalText.
			append("_________________________________________________________________________\n");
			actionListenerState = -1;
			topDrawDeck.setEnabled(false);
			identifier.setEnabled(false);
	
			newPlayers.get(currentPlayer).aRack.hideRackValues();			
			topDrawDeck.setText("");
			takeAnotherTurn();
			//More work here
		}
		else // SWAP RACK AND DISCARD
		{
			identifier.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.red));
			
			int idx2 = newPlayers.get(currentPlayer)
			.aRack.getIDfromCard(identifier); // ID OF CARD TO SWAP	
			card cardToSwap = new card(newPlayers.get(currentPlayer)
			.aRack.getRack(idx2).getCard());
			newPlayers.get(currentPlayer).aRack.getRack(idx2)
			.setCard(newDeck.discardDeck.peek().getCard()); 
		
			//GUI
			identifier.setText(newDeck.discardDeck.peek().toString());
			terminalText.append("> "+newPlayers.get(currentPlayer).getName()
			+" replaced ["+cardToSwap.toString()+"] with ["
			+newDeck.discardDeck.peek().toString()+"] from discard deck.\n");
			terminalText
			.append("_________________________________________________________________________\n");
			newDeck.discardDeck.pop(); 
			newDeck.discardDeck.push(cardToSwap);
			topDiscard.setText(newDeck.discardDeck.peek().toString());
			identifier.setEnabled(false);
			// identifier.setText("");
			// identifier.setIcon(new ImageIcon("fdown.jpg"));
	
			topDrawDeck.setText("");
			topDiscard.setEnabled(false);			
			actionListenerState = -1;
			takeAnotherTurn();
			} //If else brace
		} } );  //Action Listener and Action performed brace
	} //For loop inner brace
	} //For loop outer brace
} //playerActionListener


/**
* Adds action listener to send message button, and checks/implements cheat
* @see rackoapp#tableCardsActionListener
* @see rackoapp#playerActionListener
*/
public static void sendMessageActionListener()
{
	//CHEAT LIST
	// >>>>>>>> /c to Show Racks
	// >>>>>>>> /n disable after n turns
	// >>>>>>>> /s to sort cards
	// >>>>>>>> /o to inject cards
	sendMessage.addActionListener(new ActionListener()
	{ public void actionPerformed(ActionEvent event) {
		
		//ADD CODE TO SEND MESSAGE FROM SOCKET HERE
		
		terminalText.append("> "+ newPlayers.get(currentPlayer).getName().toUpperCase() +" says: "+messageField.getText()+" \n");
		
		if(messageField.getText().contains("/c"))
		{
			terminalText.append
			("> Cheat '/c' Detected! Be warned, using cheat can mess up the game! \n");
			for (player currentlyPlaying: newPlayers)
			{
				currentlyPlaying.aRack.showRackValues();
				terminalText.append
				("> "+currentlyPlaying.getName()+"'s rack has: ");
				for	(card identifier: currentlyPlaying.aRack.rackToFill)
				{ 
					terminalText.append("["+identifier.toString()+"] ");
				}
				terminalText.append("\n");
			}
			terminalText.append
			("_________________________________________________________________________\n");
		}
		if(messageField.getText().contains("/n"))
		{
			turntoQuit = Integer.parseInt
			(messageField.getText().replaceAll("[\\D]", ""))
			+ numberofturns;
			terminalText.append
			("> Cheat '/n' Detected! Be warned, using cheat can mess up the game! \n");
			terminalText.append("> Game will quit after "
			+Integer.parseInt(messageField.getText().replaceAll("[\\D]", ""))
			+" turns from now. \n");
			terminalText.append("> Quitting on Turn# "+turntoQuit+ " \n");
			terminalText.append
			("_________________________________________________________________________\n");
			cheatOrNot=true;
		}
		if(messageField.getText().contains("/s"))
		{
			terminalText.append
			("> Cheat '/s' Detected! Be warned, using cheat can mess up the game! \n");
			terminalText.append("> Current Player:"
			+newPlayers.get(currentPlayer).getName()
			+"'s cards have been sorted! \n");
			terminalText.append("> Play two more turns for the win prompt. \n");
			terminalText.append
			("_________________________________________________________________________\n");
			newPlayers.get(currentPlayer).aRack.sortRack();
			newPlayers.get(currentPlayer).aRack.showRackValues();
			updateScore(currentPlayer);
			updateRackLabel(currentPlayer);	
		}
		if(messageField.getText().contains("/o"))
		{
			terminalText.append
			("> Cheat '/o' Detected! Be warned, using cheat can mess up the game! \n");
	
			List listToInject = Arrays.asList(messageField.getText()
			.replace("/o", "").substring(1).split(" ")); 
			
			terminalText.append("> Cards to Inject: ");
			for(int i=0;i<listToInject.size();i++)
			{ 
				terminalText.append(" ["+listToInject.get(i) +"] ");
				newPlayers.get(currentPlayer).aRack.getRack(9-i).setCard
				(Integer.valueOf((String)listToInject.get(i)));
			}
			terminalText.append(" \n Cards have been sucessfully injected, the game might now contain duplicate cards. \n");
			terminalText.append("_________________________________________________________________________\n");
			newPlayers.get(currentPlayer).aRack.showRackValues();
		}
	} }); //For Action Listener and Action performed
 }
 
 
/**
* Uses game of thrones related theme while playing the game.
*/
public void gotify()
{
	
	gotButtons =  new JButton[numberOfPlayers];
	for (int i=0; i<numberOfPlayers;i++) 		
	{
		gotButtons[i] = new JButton();
		ImageIcon icon = new ImageIcon(gotImages.get(i));
		Image img = icon.getImage() ;  
		if (i==0 || i==1)
		{
			Image newimg = 
			img.getScaledInstance(100,100,java.awt.Image.SCALE_SMOOTH ) ;  
			icon = new ImageIcon(newimg);
			gotButtons[i].setIcon(icon);
			newPlayers.get(i).aRack.add(gotButtons[i]);
			gotButtons[i].setBounds(25,10,100,100);
		}
		else
		{
			Image newimg 
			= img.getScaledInstance(90,100,java.awt.Image.SCALE_SMOOTH ) ;  
			icon = new ImageIcon(newimg);
			gotButtons[i].setIcon(icon);
			newPlayers.get(i).aRack.add(gotButtons[i]);
			gotButtons[i].setBounds(5,5,80,100);
		}
		gotButtons[i].addActionListener(new ActionListener()
		{ public void actionPerformed(ActionEvent event) {
			
			URL url1 = null;
			try	{
			url1 = new URL(getCodeBase(), "gotlines.txt");
			} catch(MalformedURLException e){}
			try 
						
			{	
			
					InputStream in1 = url1.openStream();
					BufferedReader reader = new BufferedReader(new InputStreamReader(in1));
			
			
			
				// BufferedReader reader 
				// = new BufferedReader(new FileReader("gotlines.txt"));
				String line = reader.readLine();
				List<String> lines = new ArrayList<String>();
				while (line != null) 
				{
					lines.add(line);
					line = reader.readLine();
				}
				Random r = new Random();
				String randomLine = lines.get(r.nextInt(lines.size()));
				terminalText.append
				("_________________________________________________________________________\n");
				terminalText.append(randomLine+"  \n"); } 
			 catch (IOException e){ }
		}	});	
	
	}
	//Other action if number of players is two
	

	//Other action if number of players is two
	if(numberOfPlayers==2)
	{	
		gotify2Player();
	}
}


/**
* Adds banners for 2 players game
*/
public void gotify2Player()
{
		
		List<String> gotBanners = Arrays.asList
		("highgarden.png", "lannister.png", "winter.png","dany.png","fury.png");
		Collections.shuffle(gotBanners);
		
		ImageIcon iconx = new ImageIcon("gotimgs/"+gotBanners.get(0));
		Image imgx = iconx.getImage() ;
		Image newimgx = imgx.getScaledInstance(90,590,java.awt.Image.SCALE_SMOOTH);  
		iconx = new ImageIcon(newimgx);
						
		ImageIcon icony = new ImageIcon("gotimgs/"+gotBanners.get(1));
		Image imgy = icony.getImage() ;
		Image newimgy = imgy.getScaledInstance(90,590,java.awt.Image.SCALE_SMOOTH);  
		icony = new ImageIcon(newimgy);
		
		JPanel got1 = new JPanel();
		JPanel got2 = new JPanel();
		JLabel got1bg = new JLabel();
		JLabel got2bg = new JLabel();
		got1.setBounds(5,5,90,750);
		got2.setBounds(705,5,90,755);
		got1.add(got1bg);
		got2.add(got2bg);
		got1bg.setIcon(iconx);
		got2bg.setIcon(icony);
		add(got1); 
		add(got2);	
	
}


/**
* Causes the game to end if a cheat to end the game has been activated.
*/
public static void execCheat()
	{
		if(cheatOrNot)
		{
			if(turntoQuit==numberofturns)
			{
				terminalText.append("> Cheat '/n' has forced the game to end! \n");
				terminalText.append("> Thanks for playing! \n");
				terminalText.append
				("_________________________________________________________________________\n");
				haltGame = true;
			}
		}
	}	
}
