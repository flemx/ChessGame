package ChessProject.game;


/**
 * @author Damien Fleminks
 * Move class to hold current square and square it wants to move to
 */

public class Move {

    private Position start;
    private Position landing;


    public Move(Position start, Position landing) {
        this.start = start;
        this.landing = landing;
    }

    public Position getStart() {
        return start;
    }

    public Position getLanding() {
        return landing;
    }
}
