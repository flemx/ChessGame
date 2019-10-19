package ChessProject.pieces;

import ChessProject.game.Position;

public class King extends Piece {

    public King(PieceColor pieceColor){
        super(PieceType.KING, pieceColor);
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

        // To be added
        return false;

    }
}
