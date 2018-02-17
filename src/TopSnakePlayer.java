
// File name:  TopSnakePlayer.java
// Written by: Pitou Teng   
// Description: has the name and score property
// Challenges: none
// Time Spent: 120 minutes
// Revision History:
// Date:         		By:      Action:
// ---------------------------------------------------
/* 12/02/2017   (Pitou Teng)            Created and finish                       
                  
 */
public class TopSnakePlayer {

    private int score;
    private String name;

    public TopSnakePlayer() {
        score = 0;
        name = "Not Claimed";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

}
