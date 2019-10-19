package ChessProject.pieces;

import ChessProject.game.Position;

import java.util.ArrayList;

public class Rook extends Piece {

    public Rook(PieceColor pieceColor){
        super(PieceType.ROOK, pieceColor);
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

        // To be added
        return false;

    }
}
