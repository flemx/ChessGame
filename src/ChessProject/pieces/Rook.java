package ChessProject.pieces;

import ChessProject.game.Position;

import java.util.ArrayList;

/**
 * @author dfleminks
 */
public class Rook extends Piece {

    /**
     *  Creates a Rook Piece
     * @param pieceColor
     */
    public Rook(PieceColor pieceColor){
        super(PieceType.ROOK, pieceColor);
    }

    @Override
    public ArrayList<Position> returnPath(Position fromPos, Position toPos) {
        //Total Steps to take
        Integer steps =  fromPos.getY() == toPos.getY() ?
                Math.abs(toPos.getX() - fromPos.getX()) : Math.abs(toPos.getY() - fromPos.getY());

        ArrayList<Position> positions = new ArrayList<Position>();
        Integer minY = fromPos.getY() < toPos.getY() ? fromPos.getY() : toPos.getY();
        Integer minX = fromPos.getX() < toPos.getX() ? fromPos.getX() : toPos.getX();

        for(Integer i = 1; i < steps; i++) {
            if(fromPos.getX() == toPos.getX()){
                System.out.println("true1");
                positions.add(new Position(fromPos.getX(),minY+i));
            }
            if(fromPos.getY() == toPos.getY()){
                System.out.println("true2");
                positions.add(new Position(minX+i,fromPos.getY()));
            }
        }

        return positions;
    }


    @Override
    public boolean validMove(Position fromPos, Position toPos) {
        Integer fromX = fromPos.getX();
        Integer toX = toPos.getX();
        Integer fromY = fromPos.getY();
        Integer toY = toPos.getY();

        // Short formula to determine if rook move is valid
        return (fromX == toX) || (fromY == toY);

    }
}
