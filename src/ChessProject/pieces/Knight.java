package ChessProject.pieces;

import ChessProject.game.Position;
import java.util.ArrayList;

/**
 * @author dfleminks
 */
public class Knight extends Piece{

    /**
     * Creates a Knight piece
     * @param pieceColor
     */
    public Knight(PieceColor pieceColor){
        super(PieceType.KNIGHT, pieceColor);
    }

    @Override
    public  ArrayList<Position> returnPath(Position fromPos, Position toPos) {
        return new ArrayList<Position>();
    }

    @Override
    public boolean validMove(Position fromPos, Position toPos) {
        Integer fromX = fromPos.getX();
        Integer toX = toPos.getX();
        Integer fromY = fromPos.getY();
        Integer toY = toPos.getY();

        if(Math.abs(fromX - toX) == 2 && Math.abs(fromY - toY) == 1){
            return true;
        }
        if(Math.abs(fromX - toX) == 1 && Math.abs(fromY - toY) == 2){
            return true;
        }
        return false;

    }
}
