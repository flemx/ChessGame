package ChessProject.pieces;

import ChessProject.game.Position;

import java.util.ArrayList;

public class Queen extends Piece{

    public Queen(PieceColor pieceColor){
        super(PieceType.QUEEN, pieceColor);
    }

    @Override
    public ArrayList<Position> returnPath(Position fromPos, Position toPos) {
        return new ArrayList<Position>();
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
