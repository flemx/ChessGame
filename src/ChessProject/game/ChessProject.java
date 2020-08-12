package ChessProject.game;

import ChessProject.pieces.*;

import java.util.ArrayList;

/**
 * @author dfleminks
 * Game controller class that manages the game
 * It holds the board and active player
 * Evaluates all the moves based on the valid directions
 * Directs the AI agent
 */
public class ChessProject {

    private ChessBoard board;

    private PieceColor activePlayer;

    private boolean gameOver;

    private boolean commentsEnabled = true;

    public ChessProject(){
        gameOver = false;
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


    /**
     *  Method that evaluates a move and moves the piece if valid
     * @param start
     * @param landing
     */
    public void movePiece(Position start, Position landing){
        if(landing.getX() > 7 || landing.getX() < 0 ||
                landing.getY() > 7 || landing.getY() < 0){
            // Exit method if move is out of bounds
            return;
        }
        // Create move
        Move move = new Move(board.getSquare(start), board.getSquare(landing));
        Boolean canMove = false;

        System.out.println("--------------------------------------------------");
        System.out.println("The piece that is trying to move: " +
                move.getStart().getPiece().getType() + " - " +
                move.getStart().getPiece().getColor());
        System.out.println("Move from: x"+start.getX() + ", y"+start.getY());
        System.out.println("Move to: x"+landing.getX() + ", y"+landing.getY());


        if(move.getStart().getPiece().getColor() == activePlayer){
            // Check if piece can move from current position
            canMove = evaluateMove(move.getStart(), move.getLanding());
        }else{
            System.out.println("It is the other players turn");
        }


        if(canMove){
            System.out.println("Move valid");

            // Set piece and check for pawn promotion
            if(move.getStart().getPiece().getType() == PieceType.PAWN){
                promotePawn(landing, move.getStart());
            }else{
                board.setPiece(landing, move.getStart().getPiece());
            }

            board.removePiece(start);
            changePlayer();
            System.out.println("--------------------------------------------------");
        }
        else{
            System.out.println("Unvalid move");
            System.out.println("--------------------------------------------------");
        }

    }

    /**
     * Method to determine if a move is valid on the current board
     * @param squareFrom
     * @param squareTo
     * @return
     */
    private boolean evaluateMove(Square squareFrom, Square squareTo){
        boolean canMove;
        // Choose move evaluation based on piece type
        switch (squareFrom.getPiece().getType())
        {
            case PAWN:
                canMove = isPathClear(squareFrom,squareTo) &&
                        evaluatePawnMove(squareFrom,squareTo);
                break;
            case KNIGHT:
                canMove = (evaluateNormalMove(squareFrom,squareTo));
                break;
            case KING:
                canMove = (evaluateNormalMove(squareFrom,squareTo) &&
                        validKingMove(squareFrom,squareTo));
                break;
            default:
                canMove =  (isPathClear(squareFrom,squareTo) &&
                        evaluateNormalMove(squareFrom,squareTo));
                break;
        }
        return canMove;
    }


    /**
     *  Return all valid moves on the board from current position
     * @return
     */
    private ArrayList<Move> getAllValidMoves(Position current){
        ArrayList<Move> moves = new ArrayList<Move>();
        Square currentPosition =  board.getSquare(current);

        Square[][] allSquares = board.getBoardSquares();
        //Loop through all squares to validate all positions
        for(Integer i =0; i < 8; i++){
            for(Integer j =0; j < 8; j++){

            }
        }

        return moves;
    }



//    private ArrayList<Position> getValidAttackMoves(ArrayList<Position> ){
//
//    }

    /**
     *  Check if King move is valid by validating no other pieces can attack after move
     * @param squareFrom
     * @param squareTo
     * @return
     */
    private boolean validKingMove(Square squareFrom,Square squareTo){
        // Add method here later to loop through all the opposites attack pieces (ADD IN PART 2 OF ASSIGNMENT)

        return true;
    }

    /**
     * Validates if move makes
     * @return
     */
    private boolean evaluateCheck(){
        //ArrayList<Square> squares = new ArrayList<Square>();

        return false;
    }

    /**
     * Return all squares from particular color
     * @return
     */
    private ArrayList<Square> getAllofColor(PieceColor color){
        ArrayList<Square> singeColor = new ArrayList<Square>();
        Square[][] allSquares = board.getBoardSquares();
        //Loop through all squares and check if piece is present and if it is the give color
        for(Integer i =0; i < 8; i++){
            for(Integer j =0; j < 8; j++){
                if(allSquares[i][j].piecePresent() & allSquares[i][j].getPiece().getColor() == color){
                    //add square to list
                    singeColor.add(allSquares[i][j]);
                }
            }
        }

        return singeColor;
    }



    private boolean isCheckMate(Square squareFrom){
        //Check if King was captures (TO BE ADJUSTED TO CHECKMATE IN NEXT VERSION
        if(squareFrom.getPiece().getType() == PieceType.KING){
            gameOver = true;
            return true;
        }
        return false;
    }


    /**
     *  Checks if the path is clear the piece wants to move.
     * @param squareFrom
     * @param squareTo
     * @return
     */
    private boolean isPathClear(Square squareFrom,Square squareTo){
        boolean isClear = true;
        ArrayList<Position> positions =  squareFrom.getPiece().returnPath(squareFrom.getPosition(),squareTo.getPosition());
        if(commentsEnabled){System.out.println("Positions in path are: ");}

        for(Position pos : positions){
            if(board.getSquare(pos).piecePresent()){
                isClear = false;
                if(commentsEnabled){System.out.println("postion: " + pos.getX() + " - " + pos.getY() + "   occupied by: " + (board.getSquare(pos).getPiece().getType() + "!"));}
            }else{
                if(commentsEnabled){ System.out.println("postion: " + pos.getX() + " - " + pos.getY());}
            }
        }
        return isClear;
    }


    /**
     *  Check if the pawn is allowed to promote if reached end of deck upon valid move.
     * @param to
     * @param squareFrom
     */
    private void promotePawn(Position to,Square squareFrom){
        if(squareFrom.getPiece().getColor() == PieceColor.WHITE && to.getY() == 7){
            board.setPiece(to, new Queen(PieceColor.WHITE));
            if(commentsEnabled){System.out.println("White pawn promoted to White Queen!");}
        }
        else if(squareFrom.getPiece().getColor() == PieceColor.BLACK && to.getY() == 0){
            board.setPiece(to, new Queen(PieceColor.BLACK));
            if(commentsEnabled){System.out.println("Black pawn promoted to Black Queen!");}
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
                if(commentsEnabled){System.out.println("Player " + activePlayer.toString() + " takes " + squareTo.getPiece().getType().toString() + "!");}

                //Check if King was captures (TO BE ADJUSTED TO CHECKMATE IN NEXT VERSION
                return isCheckMate(squareTo) ? false : true;


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

        //Check for attack move
        if(squareTo.piecePresent() & Math.abs(squareTo.getPosition().getY() - (squareFrom.getPosition().getY())) == 1 &&
                Math.abs(squareFrom.getPosition().getX() - squareTo.getPosition().getX()) == 1 ){
            if(commentsEnabled){System.out.println("Player " + activePlayer.toString() + " takes " + squareFrom.getPiece().getType().toString() + "!");}
            return true;
        }

        //Check for normal move
        if((squareFrom.piecePresent() &&
                squareFrom.getPiece().validMove(squareFrom.getPosition(),squareTo.getPosition())) &&
                !squareTo.piecePresent()){

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

    /**
     *  Checks if game is won (Checkmate, King captured or draw)
     * @return
     */
    public boolean isGameOver() {
        return gameOver;
    }

    /**
     *  Return the active player
     * @return
     */
    public PieceColor getActivePlayer() {
        return activePlayer;
    }

    public void resetGame(){
        activePlayer = PieceColor.WHITE;
        board.resetBoard();
    }
}

