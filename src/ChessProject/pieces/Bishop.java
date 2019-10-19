
package ChessProject.pieces;
import ChessProject.game.Position;
import java.util.ArrayList;


/**
 * @author dfleminks
 */
public class Bishop extends Piece{

    /**
     *  Create a Bishop piece
     * @param pieceColor
     */
    public Bishop(PieceColor pieceColor){
        super(PieceType.BISHOP, pieceColor);
    }

    @Override
    public ArrayList<Position> returnPath(Position fromPos, Position toPos) {
        //Total Steps to take
        Integer steps = Math.abs(toPos.getX() - fromPos.getX());
        ArrayList<Position> positions = new ArrayList<Position>();

        boolean x1 =  toPos.getX() > fromPos.getX();
        boolean x2 =  toPos.getX() < fromPos.getX();
        boolean y1 =  toPos.getY() > fromPos.getY();
        boolean y2 =  toPos.getY() < fromPos.getY();



        //evaluate 4 different diagonal directions and return positions
        if(x1 && y1){
            for(Integer i = 1; i < steps; i++){
                positions.add(new Position(fromPos.getX()+i, fromPos.getY()+i));
            }
        }
        if(x2 && y1){
            for(Integer i = 1; i < steps; i++){
                positions.add(new Position(fromPos.getX()-i, fromPos.getY()+i));
            }
        }
        if(x2 && y2){
            for(Integer i = 1; i < steps; i++){
                positions.add(new Position(fromPos.getX()-i, fromPos.getY()-i));
            }
        }
        if(x1 && y2){
            for(Integer i = 1; i < steps; i++){
                positions.add(new Position(fromPos.getX()+i, fromPos.getY()-i));
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

        if(Math.abs(fromX - toX) == Math.abs(fromY - toY)) {
            return true;
        }
        return false;
    }



}
