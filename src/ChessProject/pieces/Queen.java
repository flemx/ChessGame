package ChessProject.pieces;

import ChessProject.game.Position;

import java.util.ArrayList;

/**
 * @author dfleminks
 *
 */
public class Queen extends Piece{

    /**
     * Creates a Queen Piece
     * @param pieceColor
     */
    public Queen(PieceColor pieceColor){
        super(PieceType.QUEEN, pieceColor);
    }

    @Override
    public ArrayList<Position> returnPath(Position fromPos, Position toPos) {
        ArrayList<Position> positions = new ArrayList<Position>();

        // Check is move is same as Rook to return path
        if(fromPos.getX() == toPos.getX() || fromPos.getY() == toPos.getY()){
            positions = new Rook(this.getColor()).returnPath(fromPos, toPos);
        }
        //Check is move is same as Bishop to return path
        if(Math.abs(fromPos.getX() - toPos.getX()) == Math.abs(fromPos.getY() - toPos.getY())){
            positions = new Bishop(this.getColor()).returnPath(fromPos, toPos);
        }
        return positions;
    }
    @Override
    public boolean validMove(Position fromPos, Position toPos) {
        Integer fromX = fromPos.getX();
        Integer toX = toPos.getX();
        Integer fromY = fromPos.getY();
        Integer toY = toPos.getY();

        if(Math.abs(fromX - toX) == Math.abs(fromY - toY) ||
                fromX == toX || fromY == toY) {
            return true;
        }
        return false;

    }

}
