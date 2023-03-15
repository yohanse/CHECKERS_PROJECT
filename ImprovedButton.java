import javax.swing.*;
import java.awt.*;

public class ImprovedButton extends JButton{

    protected int row, column, type;
    protected String picture;

    ImprovedButton(int row, int column, int type, String picture){
        super();
        this.row = row;
        this.column = column;
        this.type = type;
        update_picture(picture);
        
    }
    void update_picture(String picture){
        this.picture = picture;
        if(picture.equals("")){
           
            super.setIcon(null);
            super.setBackground(Color.BLACK);
            if((this.row + this.column) % 2 == 1){
                super.setBackground(Color.WHITE);
            }
        }

        else{
            super.setIcon(new ImageIcon(picture));
        }
    }

    void update_type(int type){
        this.type = type;
    }
}