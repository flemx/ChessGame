package ChessProject.game;


/**
 * @author Damien Fleminks
 * Move class to hold current square / position and square / position it wants to move to
 */

public class Move {

    private Position start;
    private Position landing;

    private Square squarFrom;
    private Square squarTo;

    // Hold board positions for advanced AI calculations
    private ChessBoard boardSimulation;

    /**
     * Create Move with positions
     * @param start
     * @param landing
     */
    public Move(Position start, Position landing) {
        this.start = start;
        this.landing = landing;
    }

    /**
     * Create move with squares
     * @param squarFrom
     * @param squarTo
     */
    public Move(Square squarFrom, Square squarTo) {
        this.squarFrom = new Square(squarFrom);
        this.squarTo = new Square(squarTo);
    }

    public Move() {

    }

    public Position getStart() {
        return start;
    }

    public Position getLanding() {
        return landing;
    }

    public Square getSquarFrom() {
        return squarFrom;
    }

    public Square getSquarTo() {
        return squarTo;
    }

    public void setSquarFrom(Square squarFrom) {
        this.squarFrom = squarFrom;
    }

    public void setSquarTo(Square squarTos) {
        this.squarTo = squarTos;
    }

    public ChessBoard getBoardSimulation() {
        return boardSimulation;
    }

    public void setBoardSimulation(ChessBoard boardSimulation) {
        this.boardSimulation = new ChessBoard(boardSimulation);
    }
}
