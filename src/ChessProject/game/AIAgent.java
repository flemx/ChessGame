package ChessProject.game;
import java.util.*;

/**
 * @author dfleminks
 * Artificial Intelligence Agent
 *
 */


public class AIAgent {

    /**
     *  Take list off possible moves and return random move, the most simplest AI algorithm
     * @param alValidMoves
     * @return
     */
    public static Move randomMove(ArrayList<Move> alValidMoves){

        int moveNum = new Random().nextInt(alValidMoves.size());
        Move move = alValidMoves.get(moveNum);

        System.out.println("AI Agent randomly selected move, from x"
                + move.getStart().getX()+ "-y"+move.getStart().getY() + " to x" +
                move.getLanding().getX() + "-y" + move.getLanding().getY());
        return move;
    }


}
