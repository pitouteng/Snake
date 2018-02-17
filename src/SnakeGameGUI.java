
// File name: SnakeGameGUI.java
// Written by: Pitou Teng   
// Description: JPanel that contains all the core of the game
        
// Challenges: none
// Time Spent: 120 minutes
// Revision History:
// Date:         		By:      Action:
// ---------------------------------------------------
/* 11/11/2017   (Pitou Teng)            Created     
   11/20/2017   Piou Teng               finsihed
   12/01/2017   Pitou Teng              Add snake open feature
                  
 */
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class SnakeGameGUI extends JPanel implements ActionListener, KeyListener {

    final int SCREEN_SIZE_X = 450, SCREEN_SIZE_Y = 350;

    final int MAX_DOT = 250;
    final int BLOCK = 20;
    int moveLength = 13;
    int plusLength = 2;

    final int xDot[] = new int[350];
    final int yDot[] = new int[350];

    boolean gameOn = true;
    boolean goRight = true;
    boolean goUp = false;
    boolean goLeft = false;
    boolean goDown = false;
    
    boolean wallOn = false;

    Timer time;
    Random rand = new Random();
    int snakeLength = 10; // 8
    int fruitX, fruitY;

    int appleAte = 0;
    JLabel appleAteLab = new JLabel("0");


    public SnakeGameGUI() {
        this.setPreferredSize(new Dimension(SCREEN_SIZE_X, SCREEN_SIZE_Y));
        this.addKeyListener(this);

        setVisible(true);
        setBackground(Color.BLACK);

        this.addKeyListener(this);
        this.setFocusable(true);
        // all initial snake block location is (0,0), so they will apears at the top left corner
        // this for Loop fix unintended paint on the panel when the class is initiate
        for (int i = 0; i < snakeLength; i++) {
            xDot[i] = -30;
            yDot[i] = -30;
        }
    }


    @Override
    public void paint(Graphics g) {

        super.paint(g);

        if (gameOn) {
            Font myFont = new Font("Time", Font.BOLD, 15);
            g.setFont(myFont);
            g.setColor(Color.LIGHT_GRAY);
            g.drawString("Apple Eaten: " + appleAte, BLOCK + 5, 18);
            g.setColor(Color.RED);
            g.fillOval(fruitX, fruitY, BLOCK / 2 + 5, BLOCK + 2); // red apple
            g.fillOval(fruitX + (BLOCK / 2), fruitY, BLOCK / 2 + 5, BLOCK + 2);
            g.setColor(new Color(26, 140, 39));
            g.fillOval(fruitX + 3, fruitY, BLOCK - 7, 7);// green leaf
            g.setColor(new Color(22, 124, 34));
            g.fillRect(fruitX + BLOCK / 2, fruitY - 6, 3, BLOCK / 2);
            g.setColor(new Color(229, 249, 132));
            g.fillOval(fruitX + BLOCK - (BLOCK / 8), fruitY + BLOCK / 6, BLOCK / 3, BLOCK / 2 + 3); // apple bite

            g.setColor(Color.GREEN);

            // start the game with 3 block for the snake
            for (int i = 1; i < snakeLength; i++) {
                g.fillRoundRect(xDot[i], yDot[i], BLOCK, BLOCK, BLOCK / 3, BLOCK / 3);
            }
            g.setColor(new Color(14, 124, 58));
            for (int i = 1; i < snakeLength; i++) {
                g.fillOval(xDot[i], yDot[i], BLOCK / 2, BLOCK / 2);
            }
            
            g.setColor(new Color(46, 186, 14));
            // snake Open its mouth when next to apple
            if (Math.abs(xDot[0] - fruitX) < 80 && Math.abs(yDot[0] - fruitY) < 80) {
                if(goUp || goDown){
                    g.fillRect(xDot[0], yDot[0], BLOCK/3, BLOCK);
                    g.fillRect(xDot[0]+(BLOCK*2/3), yDot[0], BLOCK/3, BLOCK);
                }
                else{
                    g.fillRect(xDot[0], yDot[0], BLOCK, BLOCK/3);
                    g.fillRect(xDot[0], yDot[0]+(BLOCK*2/3), BLOCK, BLOCK/3);
                }
            } else {
                g.fillRoundRect(xDot[0], yDot[0], BLOCK, BLOCK, BLOCK / 3, BLOCK / 3);
            }
        } else { // if game is over
            Font myFont = new Font("Time", Font.BOLD, 20);
            g.setFont(myFont);
            g.setColor(Color.RED);

            g.drawString("Game Over", this.getWidth() / 2 - 60, this.getHeight() / 2 - 20);
            g.setColor(Color.cyan);

            g.drawString("Apple Eaten: " + appleAte, this.getWidth() / 2 - 70, this.getHeight() / 2);
        }
    }

    public void atStart() {
        switch (moveLength) // ajust snakeLength and plusLength because the length of snake turn out different at different moveLength
        {
            case 13:
                plusLength = 1;
                snakeLength -= 3;
                break;
            case 5:
                plusLength = 3;
                snakeLength += 4;
                break;

        }
        for (int i = 0; i < snakeLength; i++) {

            xDot[i] = 100 - (i * moveLength) - (i * 1);
            yDot[i] = SCREEN_SIZE_Y / 2;

        }
        fruit();
        time = new Timer(30, this);
        time.stop();
        time.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (gameOn) {
            move();
            checkEatBody();
            checkEat();
        }

        repaint();

    }

    private void move() {
        for (int i = snakeLength - 1; i > 0; i--) {
            xDot[i] = xDot[i - 1];
            yDot[i] = yDot[i - 1];
        }

        if (wallOn) {
            withWall();
        } else {
            withoutWall();
        }

        if (goRight) {
            xDot[0] += moveLength;

        } else if (goUp) {
            yDot[0] -= moveLength;
        } else if (goLeft) {
            xDot[0] -= moveLength;
        } else if (goDown) {
            yDot[0] += moveLength;
        }
    }

    public void fruit() {
        fruitX = rand.nextInt(SCREEN_SIZE_X - 40) + 20;
        fruitY = rand.nextInt(SCREEN_SIZE_Y - 40) + 20;
    }

    public void checkEat() {
        if ((xDot[0] >= fruitX && xDot[0] <= fruitX + BLOCK && yDot[0] >= fruitY && yDot[0] <= fruitY + BLOCK) || (xDot[0] + BLOCK >= fruitX && xDot[0] + BLOCK <= fruitX + BLOCK && yDot[0] + BLOCK >= fruitY && yDot[0] + BLOCK <= fruitY + BLOCK)) {
            appleAte++;
            
            fruit();
            snakeLength = snakeLength + plusLength; // cause flickering at the top left corner when eat apple
            
            int temp = snakeLength;
            for (int i = 0; i < plusLength  ; i++) { // stop flickering at the top left corner when eat apple
                
                xDot[temp]= -30;
                yDot[temp]= -30;
                temp--;
            }
        }
    }

    public void checkEatBody() {
        for (int i = 1; i < snakeLength; i++) {

            if ((xDot[0] == xDot[i]) && (yDot[0] == yDot[i])) {
                gameOn = false;
            }
        }

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP && !goDown && !goUp) {
            goUp = true;
            goRight = false;
            goLeft = false;

        } else if (e.getKeyCode() == KeyEvent.VK_DOWN && !goUp && !goDown) {
            goLeft = false;
            goRight = false;
            goDown = true;
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT && !goLeft && !goRight) {
            goUp = false;
            goRight = true;
            goDown = false;
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT && !goRight && !goLeft) {
            goUp = false;
            goLeft = true;
            goDown = false;
        }

    }
    
    // different method is called depend on the game setting. withWall() or withWall()
    private void withWall() { // game over when snake touched the edge of the screen
        if (xDot[0] < 0 || xDot[0] > SCREEN_SIZE_X || yDot[0] < 0 || yDot[0] > SCREEN_SIZE_Y) {
            gameOn = false;
        }

    }
    private void withoutWall() {
        // make sure the snake will not go off the universe
        if (yDot[0] < 1) {
            yDot[0] = SCREEN_SIZE_Y - BLOCK - 1;
        }
        if (yDot[0] > SCREEN_SIZE_Y - BLOCK) {
            yDot[0] = 0;
        }
        if (xDot[0] < 0) {
            xDot[0] = SCREEN_SIZE_X - 1;
        }
        if (xDot[0] > SCREEN_SIZE_X) {
            xDot[0] = 0;
        }
    }

    // setter and getter
    public void setMoveLength(int l) {
        moveLength = l;
    }

    public void setWall(boolean l) {
        wallOn = l;
    }

    public int getAppleAte() {
        return appleAte;
    }

    // NOT USED METHODS FROM KeyListener and MouseListener Interface
    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

}
