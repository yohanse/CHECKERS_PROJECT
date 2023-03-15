import java.util.ArrayList;
import javax.swing.*;

/**
 * An object of this class holds data about a game of checkers.
 * It knows what kind of piece is on each square of the checkerboard.
 * Note that HABESHA moves "up" the board ,that is, row number decreases
 * while WALIYA moves "down" the board ,that is,  row number increases.
 * Methods are provided to return lists of available legal moves.
 */

public class CheckersData {
    final int
                EMPTY = 0,
                HABESHA = 1,
                HABESHA_KING = 2,
                WALIYA = 3,
                WALIYA_KING = 4;

    int[][] board; 
    ImprovedButton[][] GUIboard = new ImprovedButton[8][8]; 

    CheckersData() {
        board = new int[8][8];
        setUpGame(); 
    }

    void setUpGame() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {

                if ( row % 2 == col % 2 ) {
                    if (row < 3)
                        board[row][col] = WALIYA;
                    else if (row > 4)
                        board[row][col] = HABESHA;
                    else
                        board[row][col] = EMPTY;
                }
                else {
                    board[row][col] = EMPTY;
                } 
            }
        }
    }

    int pieceAt(int row, int col) {
        return board[row][col];
    }


    void makeMove(CheckersMove move) {
        makeMove(move.fromRow, move.fromCol, move.toRow, move.toCol);
    }

    void makeMove(int fromRow, int fromCol, int toRow, int toCol) {
        board[toRow][toCol] = board[fromRow][fromCol];
        board[fromRow][fromCol] = EMPTY;
        if (fromRow - toRow == 2 || fromRow - toRow == -2) {
            int jumpRow = (fromRow + toRow) / 2;  
            int jumpCol = (fromCol + toCol) / 2; 


            String picture = "";
            int player = HABESHA;
            JLabel capturedPiece = new JLabel();
            
            if(board[jumpRow][jumpCol] == HABESHA){
                picture = "HABESHA_CAPTURED.png";
                player = 2;
            }
            else if(board[jumpRow][jumpCol] == HABESHA_KING){
                picture = "HABESHA_KING_CAPTURED.png";
                player = 2;
            }
            else if(board[jumpRow][jumpCol] == WALIYA){
                picture = "WALIYA_CAPTURED.png";
                player = 1;
            }
            else if(board[jumpRow][jumpCol] == WALIYA_KING){
                picture = "WALIYA_KING_CAPTURED.png";
                player = 1;
            }
            ImageIcon icon = new ImageIcon(picture);
            capturedPiece.setIcon(icon);

            if(player == 1){
                PlayGame.captured2.add(capturedPiece);
            }
            else{
                PlayGame.captured1.add(capturedPiece);
            }

            board[jumpRow][jumpCol] = EMPTY;
            
        
        }
        if (toRow == 0 && board[toRow][toCol] == HABESHA)
            board[toRow][toCol] = HABESHA_KING;
        if (toRow == 7 && board[toRow][toCol] == WALIYA)
            board[toRow][toCol] = WALIYA_KING;
    }


   CheckersMove[] getLegalMoves(int player) {
    
        if (player != HABESHA && player != WALIYA)
            return null;

        int playerKing; 

        if (player == HABESHA)
            playerKing = HABESHA_KING;
        else
            playerKing = WALIYA_KING;

        ArrayList<CheckersMove> moves = new ArrayList<CheckersMove>(); 
        
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (board[row][col] == player || board[row][col] == playerKing) {
                    if (canJump(player, row, col, row+1, col+1, row+2, col+2))
                        moves.add(new CheckersMove(row, col, row+2, col+2));
                    if (canJump(player, row, col, row-1, col+1, row-2, col+2))
                        moves.add(new CheckersMove(row, col, row-2, col+2));
                    if (canJump(player, row, col, row+1, col-1, row+2, col-2))
                        moves.add(new CheckersMove(row, col, row+2, col-2));
                    if (canJump(player, row, col, row-1, col-1, row-2, col-2))
                        moves.add(new CheckersMove(row, col, row-2, col-2));
                }
            }
        }

        if (moves.size() == 0) {
            for (int row = 0; row < 8; row++) {
                for (int col = 0; col < 8; col++) {
                    if (board[row][col] == player || board[row][col] == playerKing) {
                        if (canMove(player,row,col,row+1,col+1))
                            moves.add(new CheckersMove(row,col,row+1,col+1));
                        if (canMove(player,row,col,row-1,col+1))
                            moves.add(new CheckersMove(row,col,row-1,col+1));
                        if (canMove(player,row,col,row+1,col-1))
                            moves.add(new CheckersMove(row,col,row+1,col-1));
                        if (canMove(player,row,col,row-1,col-1))
                            moves.add(new CheckersMove(row,col,row-1,col-1));
                    }
                }
            }
        }


        if (moves.size() == 0)
            return null;
        else {
            CheckersMove[] moveArray = new CheckersMove[moves.size()];
            for (int i = 0; i < moves.size(); i++)
                moveArray[i] = moves.get(i);
            return moveArray;
        }
    } 


    CheckersMove[] getLegalJumpsFrom(int player, int row, int col) {
        if (player != HABESHA && player != WALIYA)
            return null;
        int playerKing;  
        if (player == HABESHA)
            playerKing = HABESHA_KING;
        else
            playerKing = WALIYA_KING;

        ArrayList<CheckersMove> moves = new ArrayList<CheckersMove>(); 

        if (board[row][col] == player || board[row][col] == playerKing) {
            if (canJump(player, row, col, row+1, col+1, row+2, col+2))
                moves.add(new CheckersMove(row, col, row+2, col+2));
            if (canJump(player, row, col, row-1, col+1, row-2, col+2))
                moves.add(new CheckersMove(row, col, row-2, col+2));
            if (canJump(player, row, col, row+1, col-1, row+2, col-2))
                moves.add(new CheckersMove(row, col, row+2, col-2));
            if (canJump(player, row, col, row-1, col-1, row-2, col-2))
                moves.add(new CheckersMove(row, col, row-2, col-2));
        }
        if (moves.size() == 0)
            return null;
        else {
            CheckersMove[] moveArray = new CheckersMove[moves.size()];
            for (int i = 0; i < moves.size(); i++)
                moveArray[i] = moves.get(i);
            return moveArray;
        }
    } 
    private boolean canJump(int player, int r1, int c1, int r2, int c2, int r3, int c3) {

        if (r3 < 0 || r3 >= 8 || c3 < 0 || c3 >= 8)
            return false;  // (r3,c3) is off the board.

        if (board[r3][c3] != EMPTY)
            return false;  // (r3,c3) already contains a piece.

        if (player == HABESHA) {
            if (board[r1][c1] == HABESHA && r3 > r1)
                return false;  // Regular HABESHA piece can only move up.
            if (board[r2][c2] != WALIYA && board[r2][c2] != WALIYA_KING)
                return false;  // There is no WALIYA piece to jump.
            return true;  // The jump is legal.
        }
        else {
            if (board[r1][c1] == WALIYA && r3 < r1)
                return false;  // Regular WALIYA piece can only move downn.
            if (board[r2][c2] != HABESHA && board[r2][c2] != HABESHA_KING)
                return false;  // There is no HABESHA piece to jump.
            return true;  // The jump is legal.
        }

    }  


    private boolean canMove(int player, int r1, int c1, int r2, int c2) {

        if (r2 < 0 || r2 >= 8 || c2 < 0 || c2 >= 8)
            return false;  // (r2,c2) is off the board.

        if (board[r2][c2] != EMPTY)
            return false;  // (r2,c2) already contains a piece.

        if (player == HABESHA) {
            if (board[r1][c1] == HABESHA && r2 > r1)
                return false;  // Regular HABESHA piece can only move down.
            return true;  // The move is legal.
        }
        else {
            if (board[r1][c1] == WALIYA && r2 < r1)
                return false;  
            return true; 
        }
    } 
} 