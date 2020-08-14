package ChessProject.game;
import ChessProject.pieces.*;
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
    public static Move makeMove(String moveType, ArrayList<Move> allValidMoves, ChessBoard board, ChessProject manager){
        if(moveType.equals("greedy")){
            return(greedyMove(allValidMoves,board,manager));
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
    private static Move greedyMove(ArrayList<Move> allValidMoves, ChessBoard board, ChessProject manager){
        //First check is there are any check moves
        ArrayList<Move> checkMoves = new ArrayList<>();
        for(Move move : allValidMoves){
            //see if move make other player check trough simulation
            ChessBoard SimulatedBoardMove = manager.simulateMove(move, new ChessBoard(board), manager.getActivePlayer());
            //If a check attack move is found, see if the move also makes checkmate
            if(manager.isCheck(manager.getActivePlayer(), SimulatedBoardMove)) {
                checkMoves.add(move);
                //Return checkmate move if found
                if (manager.isCheckMate(manager.getActivePlayer(), SimulatedBoardMove)) {
                    return move;
                } else {
                    //See which check moves also have attack pieces and add them to list
                    ArrayList<Move> checkAttackMoves = new ArrayList<>();
                    for (Move attackCheckMove : allValidMoves) {
                        if (attackCheckMove.getSquarTo().piecePresent()) {
                            checkAttackMoves.add(attackCheckMove);
                        }
                    }
                    //If attack check moves have been found return the move which takes the highest piece
                    if (checkAttackMoves.size() > 0) {
                        return selectBestPiece(checkAttackMoves);
                    } else {
                        //return random check move if no attack move
                        return randomMove(checkMoves);
                    }
                }
            }else{
                // If no check moves found, check for attack move and add to list
                ArrayList<Move> attackMoves = new ArrayList<>();
                for(Move attackMove : allValidMoves){
                    if(attackMove.getSquarTo().piecePresent()){
                        attackMoves.add(attackMove);
                    }
                }
                // If attack moves are available, return most valuable piece
                if(attackMoves.size() > 0){
                    return selectBestPiece(attackMoves);
                }
            }

        }
        //If no check or attack moves have been found, return random move
        return randomMove(allValidMoves);
    }

    /**
     *  This agent is capable of looking ahead at least two moves to be able to decide what move to make
     * @param allValidMoves
     * @return
     */
    private static Move twoLevelMove(ArrayList<Move> allValidMoves){


        return allValidMoves.get(0);
    }

    /**
     * Method to return a piece with the highest value
     * @param moves
     * @return
     */
    private static Move selectBestPiece(ArrayList<Move> moves){


        //Return Queen if present
        for(Move QueenMove : moves){
            if(QueenMove.getSquarTo().piecePresent() &&
                    QueenMove.getSquarTo().getPiece().getType() == PieceType.QUEEN){
                return QueenMove;
            }
        }

        //Return Rook if present
        for(Move rookMove : moves){
            if(rookMove.getSquarTo().piecePresent() &&
                    rookMove.getSquarTo().getPiece().getType() == PieceType.ROOK){
                return rookMove;
            }
        }

        //Return Knight if present
        for(Move knightBishopMove : moves){
            if(knightBishopMove.getSquarTo().piecePresent() &&
                    (knightBishopMove.getSquarTo().getPiece().getType() == PieceType.KNIGHT ||
                            knightBishopMove.getSquarTo().getPiece().getType() == PieceType.BISHOP)){
                return knightBishopMove;
            }
        }

        //Return Pawn if present
        for(Move pawnMove : moves){
            if(pawnMove.getSquarTo().piecePresent() &&
                    pawnMove.getSquarTo().getPiece().getType() == PieceType.PAWN){
                return pawnMove;
            }
        }


        return moves.get(0);
    }


}
