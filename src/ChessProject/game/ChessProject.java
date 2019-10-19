package ChessProject.game;

import ChessProject.pieces.*;

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
        }


        if(canMove){
            System.out.println("Move valid");
            System.out.println("--------------------------------------------------");

            board.setPiece(to, squareFrom.getPiece());
            board.removePiece(from);
            changePlayer();
        }
        else{
            System.out.println("Unvalid move");
            System.out.println("--------------------------------------------------");
        }

    }

    /**
     * Evaluate if piece can make move or can attack, val;id for most pieces
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

