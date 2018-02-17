
// File name:  SnakeGUI.java
// Written by: Pitou Teng   
// Description: JPanel that gold the SnakeGameGUI Panel and control settings and keep track of high score
        
// Challenges: none
// Time Spent: 120 minutes
// Revision History:
// Date:         		By:      Action:
// ---------------------------------------------------
/* 11/13/2017   (Pitou Teng)            Created 
   11/23/2017   Pitou Teng              finished
   12/02/2017   Pitou Teng              add top 3 high score text area
                  
 */
import java.awt.*;
import java.awt.Checkbox;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.*;
import javax.swing.border.TitledBorder;

public class SnakeGUI extends JPanel implements ActionListener {

    SnakeGameGUI game;

    private JPanel p1, p2, p3, p4, p5, p6, p7, blank = new JPanel();
    private JButton playBut, reBut;
    private JSlider levelSlide;
    private Checkbox box;
    private JLabel highScoreLab, highScoreNumLab;
    private int highestScore = 0;

    private JTextArea highScoreText;

    ArrayList<TopSnakePlayer> top3 = new ArrayList<TopSnakePlayer>();

    char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();

    public SnakeGUI() {
        this.setBackground(Color.DARK_GRAY);
        p1 = new JPanel();
        playBut = new JButton("PLAY");
        playBut.addActionListener(this);

        reBut = new JButton("RESTART");
        reBut.addActionListener(this);
        reBut.setEnabled(false);
        p1.add(playBut);
        p1.add(reBut);

        p7 = new JPanel();
        highScoreLab = new JLabel("Highiest Score: ");
        highScoreNumLab = new JLabel("0");
        p7.add(highScoreLab);
        p7.add(highScoreNumLab);

        p6 = new JPanel();
        p6.setLayout(new BorderLayout());
        p6.add(p1, BorderLayout.CENTER);
        p6.add(p7, BorderLayout.SOUTH);

        p4 = new JPanel();
        levelSlide = new JSlider(5, 13, 9);// create Slider
        levelSlide.setMajorTickSpacing(4);
        levelSlide.setSnapToTicks(true);
        levelSlide.setPaintTicks(true);
        levelSlide.setPaintLabels(true);
        Hashtable slideLabels = new Hashtable(); // set the name of level difculty for JSlider
        slideLabels.put(5, new JLabel("Slow"));
        slideLabels.put(9, new JLabel("Medium"));
        slideLabels.put(13, new JLabel("Fast"));
        levelSlide.setLabelTable(slideLabels);
        box = new Checkbox("Wall");
        p4.add(levelSlide); // add contents
        p4.add(box);
        p4.setBorder(new TitledBorder("Game Settings"));

        box.setFocusable(false);

        p5 = new JPanel();
        p5.add(p6);
        p5.add(p4);

        p2 = new JPanel();
        game = new SnakeGameGUI();
        p2.add(game);

        p3 = new JPanel();
        p3.setLayout(new BorderLayout());
        p3.setBackground(Color.YELLOW);
        p3.add(p5, BorderLayout.NORTH);
        p3.add(p2, BorderLayout.CENTER);

        highScoreText = new JTextArea(4, 1);
        highScoreText.setBackground(Color.LIGHT_GRAY);
        highScoreText.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 15));
        highScoreText.setForeground(new Color(0, 128, 128));
        highScoreText.setSize(new Dimension(450, 200));
        highScoreText.setText(" Top 3 High Score:\n\t1.\n\t2.\n\t3.");
        highScoreText.setEditable(false);

        p3.add(highScoreText, BorderLayout.SOUTH);

        for (int i = 0; i < 3; i++) {
            top3.add(new TopSnakePlayer());
        }

        add(p3);

    }

    @Override

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == playBut) {

            levelSlide.setEnabled(false);
            playBut.setEnabled(false);
            reBut.setEnabled(true);
            box.setEnabled(false);
            game.setWall(box.getState());
            game.setMoveLength(levelSlide.getValue());
            game.atStart();

        } else if (e.getSource() == reBut) {

            playBut.setEnabled(true);
            reBut.setEnabled(false);
            game.setVisible(false);
            levelSlide.setEnabled(true);
            box.setEnabled(true);
            p3.add(highScoreText, BorderLayout.SOUTH);
            if (game.getAppleAte() > top3.get(2).getScore()) {

                TopSnakePlayer player = new TopSnakePlayer();

                boolean inWhileLoop = true;
                // name must contains letter

                while (inWhileLoop) {
                    inWhileLoop = false;
                    try {// string no letter exception
                        boolean nameHasLetter = false;

                        player.setName(JOptionPane.showInputDialog("Enter name for your High Score: "));
                        char nameInChar[] = player.getName().toLowerCase().toCharArray();
                        loop:
                        for (int i = 0; i < nameInChar.length; i++) {
                            for (int j = 0; j < alphabet.length; j++) {
                                if (nameInChar[i] == alphabet[j]) {
                                    nameHasLetter = true;
                                    break loop;
                                }
                            }
                        }
                        if (!nameHasLetter) {
                            throw new Exception();
                        } else {
                        }
                    } catch (Exception ex) {
                        inWhileLoop = true;
                        JOptionPane.showMessageDialog(null, "Your name must contain letter.");
                    }
                }

                player.setScore(game.getAppleAte());
                if (game.getAppleAte() > top3.get(1).getScore()) {
                    if (game.getAppleAte() > top3.get(0).getScore()) {
                        top3.add(0, player);
                        highestScore = game.getAppleAte();
                    } else {
                        top3.add(1, player);
                    }
                } else {
                    top3.add(2, player);
                }
                highScoreNumLab.setText(String.valueOf(game.getAppleAte()));
            }
            String scoreList;
            scoreList = " Top 3 High Score \n\t";
            for (int i = 0; i < 3; i++) {

                scoreList += i + 1 + ". " + top3.get(i).getName() + "\t" + top3.get(i).getScore() + "\n\t";;

            }
            highScoreText.setText(scoreList);
            game = new SnakeGameGUI();
            p2.add(game);
        }
    }

}
