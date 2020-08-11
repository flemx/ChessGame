package ChessProject.pieces;

import ChessProject.game.Position;

import java.util.ArrayList;


/**
 *  Piece Super Class
 *  @author dfleminks
 *
 *  Parent Class for all pieces,
 *  including methods to return valid positions and positions path
 *
 */
public abstract class Piece {

    private final PieceType pieceType;
    private final PieceColor pieceColor;

    /**
     *
     * @param pieceType
     * @param pieceColor
     */
    public Piece(PieceType pieceType, PieceColor pieceColor){
        this.pieceType = pieceType;
        this.pieceColor = pieceColor;
    }

    /**
     *  Getters for pieceType & pieceColor
     */
    public PieceType getType(){
        return this.pieceType;
    }

    public PieceColor getColor(){
        return this.pieceColor;
    }

    /**
     * Returns the path of the desired move as an array of Positions
     * @param fromPos
     * @param toPos
     * @return
     */
    public abstract ArrayList<Position> returnPath(Position fromPos, Position toPos);

    /**
     *  Evaluate if the player's move is a move the piece can make
     * @param fromPos
     * @param toPos
     * @return boolean if move is valid
     */
    public abstract boolean validMove(Position fromPos, Position toPos);




}
