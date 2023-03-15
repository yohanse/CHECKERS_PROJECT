import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.util.Timer;
import java.util.TimerTask;

/**
 This class does
 * the work of letting the users play checkers, and it displays
 * the checkerboard.
 */
public class UIBoard extends JPanel implements ActionListener{

    ImprovedButton[][] GUIboard = new ImprovedButton[8][8];

    Stack<int[][]> undoList = new Stack<int[][]>();

    JButton undo;

    JButton resignButton,newGameButton;

    JLabel message;

    CheckerTimer time = new CheckerTimer(PlayGame.playTime);

    JLabel label2 = time.timer1Label;

    JLabel label1 = time.timer2Label;

    CheckersData board; 

    boolean gameInProgress; 

    int currentPlayer;

    int selectedRow, selectedCol;  

    CheckersMove[] legalMoves;  

    UIBoard() {
        Font font = new Font("Nyala", Font.PLAIN, 14);
        UIManager.put("Button.font",font);
        UIManager.put("Label.font",font);
        Locale.setDefault(new Locale("am", "ET"));
        undo = new JButton("ወድ ቀድሞ");
        undo.addActionListener(this);
        resignButton = new JButton("ተሸንፍያለሁ");
        resignButton.addActionListener(this);


        newGameButton = new JButton("አዲስ ጨዋታ");
        newGameButton.addActionListener(this);
        message = new JLabel("",JLabel.CENTER);
        message.setForeground(Color.BLACK);
        board = new CheckersData();
 
        startGame();
    }

    public void actionPerformed(ActionEvent evt) {

        Object src = evt.getSource();
        if (src == newGameButton)
            startGame();
        else if (src == resignButton)
            resign();

        else if (src == undo)
            undo();
    
        else{
            ImprovedButton src1 = (ImprovedButton) src;
            if (gameInProgress == false)
                message.setText("ጨዋታው ስላለቀ እባክዎን \"አዲስ ጨዋታን\" ይጫኑ።");
            else {
                pressSquare(src1.row,src1.column);
                }
        }
    }

    void undo(){
        this.time.changeTimer();
        board.board = undoList.pop();
        if (currentPlayer == 1){
            currentPlayer = 3;}
        
        else{
            currentPlayer = 1;}

        int lastIndex1 = PlayGame.captured1.getComponentCount() - 1;
        int lastIndex2= PlayGame.captured2.getComponentCount() - 1;
        CheckersMove[] popped = board.getLegalMoves(currentPlayer);
        if (popped[0].toRow - popped[0].fromRow == 2 || popped[0].toRow - popped[0].fromRow == -2 ){
            if (currentPlayer == 3){
                PlayGame.captured1.remove(lastIndex1);
            }
            else{
                PlayGame.captured2.remove(lastIndex2);
            }
        }

        if (undoList.size() == 0){
            undo.setEnabled(false);
        }
        update();
        legalMoves = board.getLegalMoves(currentPlayer); 

        PlayGame.frame.setSize(800, 641);
        PlayGame.frame.setSize(800, 640);
    }

    void startGame() {
        
        PlayGame.frame.setSize(800, 641);
        PlayGame.captured1.removeAll();
        PlayGame.captured2.removeAll();
        PlayGame.frame.setSize(800, 640);
       
        board.setUpGame();   
        currentPlayer = 1; 
        legalMoves = board.getLegalMoves(1);  
        selectedRow = -1; 
        message.setText("ሀበሻ፤ ተንቀሳቀስ።");
        gameInProgress = true;
        newGameButton.setEnabled(false);
        resignButton.setEnabled(true);
        String input = JOptionPane.showInputDialog("ደቂቃ ሙላ ");

        if (input == null){
            System.out.print("Please Enter a value when you enter next time!");
            System.exit(1);
        }
        int newTime = 5;
        try{newTime = Integer.parseInt(input);}

        catch( Exception e ){
            System.out.print("Please Enter an integer when you enter next time!");
            System.exit(1);

        }
        
        time.setTimer(newTime*60);
        update();
        undo.setEnabled(false);
    }

    void loseOnTime1(){
        gameOver("የሀበሻ ደቂቃ አልቋል። ዋልያ አሽንፏል!");
    }
    void loseOnTime2(){
        gameOver("የዋልያ ደቂቃ አልቋል። ሀበሻ አሽንፏል!");
    }

    void resign() {
        if (currentPlayer == 1)
            gameOver("ሀበሻ እጅ ሰቷል። ዋልያ አሽንፏል!");
        else
            gameOver("ዋልያ እጅ ሰቷል። ሀበሻ አሽንፏል!");

        time.stopTimer();
    }

    void gameOver(String str) {
        message.setText(str);
        newGameButton.setEnabled(true);
        resignButton.setEnabled(false);
        gameInProgress = false;
    }


    void pressSquare(int row, int col) {

        for (int i = 0; i < legalMoves.length; i++)
            if (legalMoves[i].fromRow == row && legalMoves[i].fromCol == col) {
                selectedRow = row;
                selectedCol = col;
                if(board.pieceAt(selectedRow, selectedCol) == 1){
                    GUIboard[selectedRow][selectedCol].update_picture("HABESHA_SELECTED.png");
                }
                
                else if(board.pieceAt(selectedRow, selectedCol) == 2){
                    GUIboard[selectedRow][selectedCol].update_picture("HABESHA_KING_SELECTED.png");
                }

                else if(board.pieceAt(selectedRow, selectedCol) == 3){
                    GUIboard[selectedRow][selectedCol].update_picture("WALIYA_SELECTED.png");
                }

                else if(board.pieceAt(selectedRow, selectedCol) == 4){
                    GUIboard[selectedRow][selectedCol].update_picture("WALIYA_KING_SELECTED.png");
                }
                if (currentPlayer == 1)
                    message.setText("ሀበሻ፤ ተንቀሳቀስ።.");
                else
                    message.setText("ዋልያ፤ ተንቀሳቀስ።");
                
                return;
            }

        if (selectedRow < 0) {
            message.setText("ለማንቀሳቀስ የፈልግከውን ጠጠር ተጫን።");
            return;
        }

        for (int i = 0; i < legalMoves.length; i++)
            if (legalMoves[i].fromRow == selectedRow && legalMoves[i].fromCol == selectedCol
            && legalMoves[i].toRow == row && legalMoves[i].toCol == col) {

                int[][] temp = new int[8][8];
                for (int k = 0; k < 8; k++){
                    for (int l = 0; l < 8; l++){

                        temp[k][l] = board.board[k][l];
                    }
                }

                undoList.add(temp);
                makeMove(legalMoves[i]);
                undo.setEnabled(true);

                return;
            }

        message.setText("መዳረሻ ቦታህን ተጫን።");

    }  

    void makeMove(CheckersMove move) {

        board.makeMove(move);
        if (move.isJump()) {
            legalMoves = board.getLegalJumpsFrom(currentPlayer,move.toRow,move.toCol);
            if (legalMoves != null) {
                if (currentPlayer == 1)
                    message.setText("ሀበሻ፤ መዝለል መቀጠል አልብህ!");
                else
                    message.setText("ዋልያ፤ መዝለል መቀጠል አልብህ!.");
                selectedRow = move.toRow; 
                selectedCol = move.toCol;
                update();
                return;
            }
        }

        if (currentPlayer == 1) {
            currentPlayer = 3;
            legalMoves = board.getLegalMoves(currentPlayer);
            if (legalMoves == null)
                gameOver("ዋልያ መንቀሳቀስ አልቻልም። ሀበሻ አሽንፏል!!");
            else if (legalMoves[0].isJump())
                message.setText("ዋልያ፤ መዝለል አለብህ!");
            else
                message.setText("ዋልያ: ተንቀሳቀስ።");
        }
        else {
            currentPlayer = 1;
            legalMoves = board.getLegalMoves(currentPlayer);
            if (legalMoves == null)
                gameOver("ሀበሻ መንቀሳቀስ አልቻልም። ዋልያ አሽንፏል!!");
            else if (legalMoves[0].isJump())
                message.setText("ሀበሻ፤ መዝለል አለብህ!");
            else
                message.setText("ሀበሻ: ተንቀሳቀስ።");
        }
        this.time.changeTimer();
        selectedRow = -1;
        update();
    }  

    void update(){
        this.removeAll();
        this.setLayout(new GridLayout(8, 8));
        for(int j = 0; j < 8; j++){
            for(int i = 0; i < 8; i++){
                String picture = "";
                if (board.pieceAt(j, i) == 1){
                    picture = "HABESHA.png";
                }
                else if (board.pieceAt(j, i)  == 2){
                    picture = "HABESHA_KING.png"; 
                }
                else if (board.pieceAt(j, i)  == 3){
                    picture = "WALIYA.png";
                }
                else if (board.pieceAt(j, i)  == 4){
                    picture = "WALIYA_KING.png"; 
                }
                ImprovedButton button =  new ImprovedButton(j, i, board.pieceAt(j, i), picture);
                GUIboard[j][i] = button;
                button.addActionListener(this);
                this.add(button);
            }
        }
    }





public class CheckerTimer{
     JLabel timer2Label = new JLabel("00:00");  
     JLabel timer1Label = new JLabel("00:00");  
     
     Timer timer;
     TimerTask timer1Task, timer2Task;
     int timeLeft1, timeLeft2;
     boolean isTimer1Active;

    public CheckerTimer(int time) {
        setTimer(60*time);
    }

    private void startTimer1() {
        timer1Task = new TimerTask() {
            public void run() {
                timeLeft1--;
                if (timeLeft1 < 0) {
                    loseOnTime1();
                    stopTimer();
                } else {
                    updateTimer1Label();
                }
            }
        };
        timer.scheduleAtFixedRate(timer1Task, 0, 1000);
    }

    private void startTimer2() {
        timer2Task = new TimerTask() {
            public void run() {
                timeLeft2--;
                if (timeLeft2 < 0) {
                    loseOnTime2();
                    stopTimer();
                } else {
                    updateTimer2Label();
                }
            }
        };
        timer.scheduleAtFixedRate(timer2Task, 0, 1000);
    }

     void stopTimer() {
        if (timer2Task != null){timer2Task.cancel();}
        timer1Task.cancel();
        
        
    }

     void updateTimer1Label() {
        int minutes = timeLeft1 / 60;
        int seconds = timeLeft1 % 60;
        String minutesStr = String.format("%02d", minutes);
        String secondsStr = String.format("%02d", seconds);
        String timeStr = minutesStr + ":" + secondsStr;
        timer1Label.setText(timeStr);
    }

     void updateTimer2Label() {
        int minutes = timeLeft2 / 60;
        int seconds = timeLeft2 % 60;
        String minutesStr = String.format("%02d", minutes);
        String secondsStr = String.format("%02d", seconds);
        String timeStr = minutesStr + ":" + secondsStr;
        timer2Label.setText(timeStr);
    }

     void changeTimer() {
        if (isTimer1Active) {
            timer1Task.cancel();
            startTimer2();
            isTimer1Active = false;
        } else {
            timer2Task.cancel();
            startTimer1();
            isTimer1Active = true;
        }
    }

    public void setTimer(int minutes) {
        timeLeft1 = minutes;
        timeLeft2 = minutes;
        isTimer1Active = true;
        updateTimer1Label();
        updateTimer2Label();
        timer = new Timer();
        startTimer1();
    }
}


} 