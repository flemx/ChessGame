package ChessProject.game;

/**
 * Keep track of a Piece or Square coordination on the board
 * @author Damien Fleminks
 *
 */
public class Position {

    private int xPos;
    private int yPos;


    /**
     * Constructor
     * @param yPos
     * @param xPos
     */
    public Position(int yPos, int xPos){
        this.yPos = yPos;
        this.xPos = xPos;
    }


    /**
     *  Make ure that the position is not outside of bounds
     * @return
     */
    public boolean isValid() {
        if ((xPos >= 0 && xPos < 8)
                && (yPos >= 0 && yPos < 8)) {
            return true;
        }
        return false;
    }


    /**
     *
     * Getters & Setters for coordinate variables
     */
    public int getxPos() {
        return xPos;
    }

    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public void setyPos(int yPos) {
        this.yPos = yPos;
    }
}
