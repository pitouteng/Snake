
// File name:  SnakeGUI_Test.java
// Written by: Pitou Teng   
// Description: JFrame to hold the SnakeGUI Panel
        
// Challenges: none
// Time Spent: 120 minutes
// Revision History:
// Date:         		By:      Action:
// ---------------------------------------------------
/* 11/11/2017   (Pitou Teng)            Created   - finished            
                  
 */

import javax.swing.*;

public class SnakeGUI_Test {

    public static void main(String args[]) {

        JFrame frame = new JFrame("Snake");
        SnakeGUI game = new SnakeGUI();
        frame.add(game);
        frame.pack();
        frame.setMinimumSize(frame.getSize());
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
}
