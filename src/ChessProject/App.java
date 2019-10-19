package ChessProject;

import ChessProject.game.ChessGUI;
import javax.swing.*;

public class App extends JFrame{

    /*
     Main method that gets the ball moving.*/

    public static void main(String[] args) {
        JFrame frame = new ChessGUI();
        frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setResizable(true);
        frame.setLocationRelativeTo(null);
       frame.setVisible(true);
    }



}
