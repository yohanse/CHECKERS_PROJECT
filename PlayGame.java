import java.awt.*;
import javax.swing.*;
import java.util.*;

/**
Two players can compete in a game of checkers using this panel.
The game always kicks off with "Habesha" or Player1. A player must jump 
if they are able to do so with an opponent's piece. 
The game is over after a player has used all of their movements.
 */

public class PlayGame extends JPanel {

    protected static JFrame frame = new JFrame("ዳማ");

   protected static JPanel captured1 = new JPanel(), captured2 = new JPanel();

   JLabel Habesha, Waliya;

   static int playTime;

    public PlayGame() {
        Font font = new Font("Nyala", Font.PLAIN, 20);
        UIManager.put("Label.font",font);
        Locale.setDefault(new Locale("am", "ET"));
        Habesha = new JLabel("ሀበሻ:");
        Waliya = new JLabel("ዋልያ:");

        frame.setSize(800,640);
        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation( (screensize.width - frame.getWidth())/2,
                (screensize.height - frame.getHeight())/2);

        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        frame.setResizable(false);  
        frame.setVisible(true);

        captured1.setBackground(Color.WHITE);
        captured2.setBackground(Color.WHITE);
        
        setLayout(null);  
        setSize(1200, 1200);
        setBackground(new Color(125,125,200)); 


        UIBoard board = new UIBoard();  // Note: The constructor for the
                                    //   board also creates the buttons
                          //   and label.
        add(captured1);
        add(captured2);
        add(board);
        add(board.newGameButton);
        add(board.resignButton);
        add(board.message);
        add(board.undo);
        add(board.label1);
        add(board.label2);
        add(Habesha);
        add(Waliya);


        captured1.setBounds(20,20,500,50);
        captured2.setBounds(20,540,500,50);
        board.setBounds(20,80,450,450); 
        board.newGameButton.setBounds(500, 210, 100, 40);
        board.resignButton.setBounds(500, 260, 100,40);
        board.undo.setBounds(500,310,100,40);
        board.message.setBounds(400, 360, 400, 30);
        board.label1.setBounds(570, 5, 80,80);
        board.label2.setBounds(570, 525, 80,80);
        Waliya.setBounds(530,30,50,30);
        Habesha.setBounds(530,550,50,30);

    } 

    public static void method() {
        PlayGame content = new PlayGame();
        frame.setContentPane(content);
    }
} 