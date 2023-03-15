/**
 * A CheckersMove object represents a move in the game of Checkers.
 * It holds the row and column of the piece that is to be moved
 * and the row and column of the square to which it is to be moved.
 * (This class makes no guarantee that the move is legal.)    
 */
public class CheckersMove {
    int fromRow, fromCol; 
    int toRow, toCol;      
    CheckersMove(int r1, int c1, int r2, int c2) {
         
        fromRow = r1;
        fromCol = c1;
        toRow = r2;
        toCol = c2;
    }
    boolean isJump() {

        return (fromRow - toRow == 2 || fromRow - toRow == -2);
    }
}