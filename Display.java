import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.awt.*;
import java.nio.file.*;
import java.io.IOException;


public class Display {
    public static void main(String[] args){
        new Display();
    }
    JFrame main_window;
    JPanel window1;

    JButton newGame, about;

    JFrame aboutWindow;

    JLabel label;

    Display(){
        Font font = new Font("Nyala", Font.PLAIN, 20);
        UIManager.put("Label.font",font);
        UIManager.put("Button.font",font);
        Locale.setDefault(new Locale("am", "ET"));

        window1 = new JPanel();
        window1.setBackground(new Color(250,250,100));
        window1.setLayout(null);

        main_window = new JFrame("ዳማ");
        main_window.add(window1);
        
        main_window.setSize(250,200);

        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        main_window.setLocation( (screensize.width - main_window.getWidth())/2,
                (screensize.height - main_window.getHeight())/2);


        newGame = new JButton("ወደ ጨዋታ");
        about = new JButton("ስለ ጨዋታው");;
        window1.add(newGame);
        window1.add(about);
        
        main_window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        newGame.setBounds(45,30,150,30);
        about.setBounds(45,70,150,30);
        main_window.setVisible(true);
        main_window.setResizable(false);
        


        label = new JLabel();
        Path path = Paths.get("About.txt");
        try {
            String text = new String(Files.readAllBytes(path));
            text = "<html>" + text.replace("\n", "<br>") + "</html>";
            label.setText(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        label.setBounds(20,0,900,500);
        aboutWindow = new JFrame();
        aboutWindow.setTitle("ስለ ጨዋታው");
        aboutWindow.setLayout(null);
        aboutWindow.setSize(500, 500);
        aboutWindow.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        aboutWindow.add(label);
        aboutWindow.setResizable(false);



        class innner implements ActionListener{
            public void actionPerformed(ActionEvent src){
                Object touched = src.getSource();
                if(touched == newGame){
                    window1.setVisible(false);
                    PlayGame.method();
                    
                }
                else{
                    aboutWindow.setVisible(true);
                    
                }
            }
        }
        newGame.addActionListener(new innner());
        about.addActionListener(new innner());
    }
}