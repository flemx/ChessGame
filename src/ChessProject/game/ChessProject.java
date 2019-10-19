package ChessProject.game;

import ChessProject.pieces.*;

/**
 * @author dfleminks
 * The main Class that controlls most of the logic of the game
 * It holds the board and active player (WILL ADD MOVE CLASS LATER TO KEEP TRACK OF ALL MOVES)
 * Evaluates all the moves based on the valid directions
 */
public class ChessProject {

    private ChessBoard board;

    private PieceColor activePlayer;

    public ChessProject(){
        activePlayer = PieceColor.WHITE;
        board = new ChessBoard();
    }

    /**
     * Returns ChessBoard
     * @return
     */
    public ChessBoard getBoard() {
        return board;
    }


    public void canMoveTo(Position from, Position to){
        if(to.getX() > 7 || to.getX() < 0 ||
                to.getY() > 7 || to.getY() < 0){
            // Exit method if move is out of bounds
            return;
        }

        Square squareFrom = board.getSquare(from);
        Square squareTo = board.getSquare(to);
        Boolean canMove = false;

        System.out.println("--------------------------------------------------");
        System.out.println("The piece that is trying to move: " +  squareFrom.getPiece().getType() + " - " + squareFrom.getPiece().getColor());
        System.out.println("Move from: x"+from.getY() + ", y"+from.getY());
        System.out.println("Move to: x"+to.getY() + ", y"+to.getY());


        if(squareFrom.getPiece().getColor() == activePlayer){
            // Choose move evaluation based on piece type
            switch (squareFrom.getPiece().getType())
            {
                case PAWN:
                    canMove = evaluatePawnMove(squareFrom,squareTo);
                    break;
                default:
                    canMove =  evaluateNormalMove(squareFrom,squareTo);
                    break;
            }
        }else{
            System.out.println("It is the other players turn");
        }


        if(canMove){
            System.out.println("Move valid");

            // Set piece and check for pawn promotion
            if(squareFrom.getPiece().getType() == PieceType.PAWN){
                promotePawn(to, squareFrom);
            }else{
                board.setPiece(to, squareFrom.getPiece());
            }

            board.removePiece(from);
            changePlayer();
            System.out.println("--------------------------------------------------");
        }
        else{
            System.out.println("Unvalid move");
            System.out.println("--------------------------------------------------");
        }

    }

    /**
     *  Check if the pawn is allowed to promote if reached end of deck upon valid move
     * @param to
     * @param squareFrom
     */
    private void promotePawn(Position to,Square squareFrom){
        if(squareFrom.getPiece().getColor() == PieceColor.WHITE && to.getY() == 7){
            board.setPiece(to, new Queen(PieceColor.WHITE));
            System.out.println("White pawn promoted to White Queen!");
        }
        else if(squareFrom.getPiece().getColor() == PieceColor.BLACK && to.getY() == 0){
            board.setPiece(to, new Queen(PieceColor.BLACK));
            System.out.println("Black pawn promoted to Black Queen!");
        }
        else{
            board.setPiece(to, squareFrom.getPiece());
        }
    }

    /**
     * Evaluate if piece can make move or can attack, valid for most pieces
     * @param squareFrom
     * @param squareTo
     * @return
     */
    private boolean evaluateNormalMove(Square squareFrom, Square squareTo){
        if(squareFrom.piecePresent() &&
                squareFrom.getPiece().validMove(squareFrom.getPosition(),squareTo.getPosition())){
            if(squareTo.piecePresent() && (squareTo.getPiece().getColor() !=  activePlayer)){
                System.out.println("Player " + activePlayer.toString() + " takes " + squareFrom.getPiece().getType().toString() + "!");
                return true;
            }
            if(!squareTo.piecePresent()){
                return true;
            }
        }
        return false;
    }




    /**
     *  Evaluate if pawn can make a move due to alternative attack rule
     * @param squareFrom
     * @param squareTo
     * @return
     */
    private boolean evaluatePawnMove(Square squareFrom, Square squareTo){
        //Check for normal move
        if((squareFrom.piecePresent() &&
                squareFrom.getPiece().validMove(squareFrom.getPosition(),squareTo.getPosition())) &&
                !squareTo.piecePresent()){

            return true;
        }

        //Check for attack move
        if(Math.abs(squareTo.getPosition().getY() - (squareFrom.getPosition().getY())) == 1 &&
                Math.abs(squareFrom.getPosition().getX() - squareTo.getPosition().getX()) == 1 ){
            System.out.println("Player " + activePlayer.toString() + " takes " + squareFrom.getPiece().getType().toString() + "!");
            return true;
        }
        return false;
    }


    /**
     *  Switch active player after a move
     */
    private void changePlayer(){
        if(activePlayer == PieceColor.BLACK) {
            activePlayer = PieceColor.WHITE;
        }else{
            activePlayer = PieceColor.BLACK;
        }
    }
}

