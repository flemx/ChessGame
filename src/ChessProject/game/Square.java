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
     *  Create empty square without piece
     * @param position
     */
    public Square(Position position){
        this.position = position;
    }

    /**
     *  Create square with piece
     * @param position
     */
    public Square(Position position, Piece piece){
        this.position = position;
        this.piece = piece;
    }


    /**
     *  Create clone of square
     * @param squareCopy
     */
    public Square(Square squareCopy){
        this.position =  new Position(squareCopy.getPosition());
        this.piece = squareCopy.getPiece();
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

    public boolean piecePresent(){
        if(piece == null){
            return false;
        }
        return true;
    }

    public void removePiece(){
        piece = null;
    }


}
