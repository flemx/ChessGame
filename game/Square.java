package ChessProject.game;

import ChessProject.pieces.*;

/**
 * @author Damien Fleminks
 * Square class to hold all pieces and returns location on board
 */
public class Square{

    private Position position;
    private Piece piece;

    /**
     *  Create a square with a piece on it
     * @param position
     * @param piece
     */
    public Square(Position position, Piece piece){
        this.position = position;
        this.piece = piece;
    }

    /**
     *  Create empty square without piece
     * @param position
     */
    public Square(Position position){
        this.position = position;
    }


    /**
     * Setter and getter for piece
     */
    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }


    /**
     *  Returns the current position
     * @return
     */
    public Position getPosition() {
        return position;
    }

    public boolean isEmpty(){
        if(piece == null){
            return true;
        }
        return false;
    }



}
