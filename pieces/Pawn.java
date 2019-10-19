package ChessProject.pieces;

import ChessProject.game.Position;

/**
 * @author dfleminks
 *
 */
public class Pawn extends Piece{

    /**
     *  Create a pawn
     * @param pieceColor
     */
    public Pawn(PieceColor pieceColor){
        super(PieceType.PAWN, pieceColor);
    }

    @Override
    public Position[] returnPath(Position fromPos, Position toPos) {
        return new Position[0];
    }

    @Override
    public boolean validMove(Position fromPos, Position toPos) {
        return false;
    }
}
