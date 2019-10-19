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
        Integer fromX = fromPos.getX();
        Integer toX = toPos.getX();
        Integer fromY = fromPos.getY();
        Integer toY = toPos.getY();

        //Checks if it makes a big move at start
        if( Math.abs(toY - fromY) == 2 && Math.abs(fromX - toX) == 0
                && (fromY == 1 || fromY == 6)){
            return checkColor(fromY, toY);
        }
        //Evaluates normal move
        if( Math.abs(toY - fromY) == 1 && Math.abs(fromX - toX) == 0){
            return checkColor(fromY, toY);
        }
        return false;

    }

    /**
     * Returns boolean based on valid move used by validMove method
     */
    private boolean checkColor(Integer fromY, Integer toY){
        if(this.getColor() == PieceColor.WHITE){
            return (fromY < toY);
        }
        if(this.getColor() == PieceColor.BLACK){
            return (fromY > toY);
        }
        return false;
    }
}
