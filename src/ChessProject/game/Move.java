package ChessProject.game;


/**
 * @author Damien Fleminks
 * Move class to hold current square and square it wants to move to
 */

public class Move {

    private Square start;
    private Square landing;


    public Move(Square start, Square landing) {
        this.start = start;
        this.landing = landing;
    }

    public Square getStart() {
        return start;
    }

    public Square getLanding() {
        return landing;
    }
}
