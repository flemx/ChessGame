package ChessProject.game;
import java.util.*;

/**
 * @author dfleminks
 * Artificial Intelligence Agent
 *
 */


public class AIAgent {


    /**
     *  Method to be called from game and decides which agent to use
     * @param moveType
     * @param allValidMoves
     * @return
     */
    public static Move makeMove(String moveType, ArrayList<Move> allValidMoves){
        if(moveType.equals("greedy")){
            return(greedyMove(allValidMoves));
        }
        if(moveType.equals("twoLevelMove")){
            return(twoLevelMove(allValidMoves));
        }
        if(moveType.equals("random")){
            return(randomMove(allValidMoves));
        }
        return null;
    }

    /**
     *  Take list off possible moves and return random move, the most simplest AI algorithm
     * @param allValidMoves
     * @return
     */
    private static Move randomMove(ArrayList<Move> allValidMoves){

        int moveNum = new Random().nextInt(allValidMoves.size());
        if(moveNum == allValidMoves.size()){
            moveNum = moveNum -1;
        }
        Move move = allValidMoves.get(moveNum);

        System.out.println("AI Agent randomly selected move, from x"
                + move.getSquarFrom().getPosition().getX()+ "-y"+move.getSquarFrom().getPosition().getY() + " to x" +
                move.getSquarTo().getPosition().getX() + "-y" + move.getSquarTo().getPosition().getY());
        return move;
    }

    /**
     *  This agent is capable of looking at all its possible moves and then deciding which move to make based on its evaluation function
     * @param allValidMoves
     * @return
     */
    private static Move greedyMove(ArrayList<Move> allValidMoves){


        return allValidMoves.get(0);
    }

    /**
     *  This agent is capable of looking ahead at least two moves to be able to decide what move to make
     * @param allValidMoves
     * @return
     */
    private static Move twoLevelMove(ArrayList<Move> allValidMoves){


        return allValidMoves.get(0);
    }


}
