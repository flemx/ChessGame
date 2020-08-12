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
        System.out.println("Move from: x"+start.getX() + ", y"+start.getY() + " - to: x"+landing.getX() + ", y"+landing.getY());


        if(move.getStart().getPiece().getColor() == activePlayer){
            // Check if piece can move from current position
            canMove = evaluateMove(move.getStart(), move.getLanding(),activePlayer);
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
    private boolean evaluateMove(Square squareFrom, Square squareTo, PieceColor currentPlayer){
        boolean canMove;
        // Choose move evaluation based on piece type
        switch (squareFrom.getPiece().getType())
        {
            case PAWN:
                canMove = isPathClear(squareFrom,squareTo) &&
                        evaluatePawnMove(squareFrom,squareTo,currentPlayer);
                break;
            case KNIGHT:
                canMove = (evaluateNormalMove(squareFrom,squareTo,currentPlayer));
                break;
            case KING:
                canMove = (evaluateNormalMove(squareFrom,squareTo,currentPlayer));
                break;
            default:
                canMove =  (isPathClear(squareFrom,squareTo) &&
                        evaluateNormalMove(squareFrom,squareTo,currentPlayer));
                break;
        }
        return canMove;
    }


    /**
     *  Return all valid moves on the given board from current position
     * @return
     */
    private ArrayList<Move> getAllValidMoves(Position current, ChessBoard boardCopy, PieceColor currentPlayer){
        ArrayList<Move> validMoves = new ArrayList<Move>();

        System.out.println("Running algorithm to validate all valid moves.....");
        //Disable comments temporarily to prevent spamming
        commentsEnabled = false;

        Square[][] allSquares = boardCopy.getBoardSquares();
        //Loop through all squares to validate if all valid moves from current position
        for(Integer i =0; i < 8; i++){
            for(Integer j =0; j < 8; j++){
                if(evaluateMove(boardCopy.getSquare(current), allSquares[i][j],currentPlayer)){
                    validMoves.add(new Move(boardCopy.getSquare(current),allSquares[i][j]));
                }
            }
        }

        commentsEnabled = true;
        return validMoves;
    }


    /**
     * Will return all possible attack moves from a list of moves
     * @param validMoves
     * @return
     */
    private ArrayList<Move> getValidAttackMoves(ArrayList<Move> validMoves){
        ArrayList<Move> validAttackMoves = new ArrayList<Move>();

        System.out.println("Running algorithm to validate all valid attacks moves.....");
        //Disable comments temporarily to prevent spamming
        commentsEnabled = false;

        //Check all landing squares of the moves to see if there is a piece to catch
        for(Move checkMove : validMoves){
            if(checkMove.getLanding().piecePresent()){
                validAttackMoves.add(checkMove);
            }
        }

        commentsEnabled = true;
        return validAttackMoves;
    }


    /**
     * Validates if king is under attack in possible attack moves
     * @return
     */
    private boolean checkKingAttack(ArrayList<Move> validAttackMoves){
        for(Move checkMove : validAttackMoves){
            if(checkMove.getLanding().getPiece().getType() == PieceType.KING){
                return true;
            }
        }
        return false;
    }


    /**
     *  Check if a given board is under check with the given color
     * @param color
     * @param boardCopy
     * @return
     */
    private boolean isCheck(PieceColor color, ChessBoard boardCopy){

        return false;
    }



    /**
     * Return all squares from given color and  board
     * @param color
     * @param boardCopy
     * @return
     */
    private ArrayList<Square> getAllofColor(PieceColor color, ChessBoard boardCopy){
        ArrayList<Square> singleColor = new ArrayList<Square>();
        Square[][] allSquares = boardCopy.getBoardSquares();
        //Loop through all squares and check if piece is present of the given color
        for(Integer i =0; i < 8; i++){
            for(Integer j =0; j < 8; j++){
                if(allSquares[i][j].piecePresent() & allSquares[i][j].getPiece().getColor() == color){
                    //add square to list
                    singleColor.add(allSquares[i][j]);
                }
            }
        }

        return singleColor;
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
        if(commentsEnabled){System.out.print("Pieces in path are: ");}

        for(Position pos : positions) {
            if (board.getSquare(pos).piecePresent()) {
                isClear = false;
                if (commentsEnabled) {
                    System.out.println("postion: " + "x" + pos.getX() + " - " + "y" + pos.getY() + "   occupied by: " + (board.getSquare(pos).getPiece().getType() + "!"));
                }
                return isClear;
            }
        }
        if (commentsEnabled) {System.out.println("No pieces in path");}
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
    private boolean evaluateNormalMove(Square squareFrom, Square squareTo, PieceColor currentPlayer){
        if(squareFrom.piecePresent() &&
                squareFrom.getPiece().validMove(squareFrom.getPosition(),squareTo.getPosition())){
            if(squareTo.piecePresent() && (squareTo.getPiece().getColor() !=  currentPlayer)){
                if(commentsEnabled){System.out.println("Player " + currentPlayer.toString() + " takes " + squareTo.getPiece().getType().toString() + "!");}
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
    private boolean evaluatePawnMove(Square squareFrom, Square squareTo, PieceColor currentPlayer){

        //Check for attack move
        if(squareTo.piecePresent() &&  (squareTo.getPiece().getColor() !=  currentPlayer) &&
                Math.abs(squareTo.getPosition().getY() - (squareFrom.getPosition().getY())) == 1 &&
                Math.abs(squareFrom.getPosition().getX() - squareTo.getPosition().getX()) == 1 ){
            if(commentsEnabled){System.out.println("Player " + currentPlayer.toString() + " takes " + squareFrom.getPiece().getType().toString() + "!");}
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

