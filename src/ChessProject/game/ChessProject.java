package ChessProject.game;

import ChessProject.pieces.*;

import java.lang.reflect.Array;
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

    //Set to true if you want the AI agent to play
    private boolean enableAiAgent = true;

    private boolean commentsEnabled = true;

    //Difficulty levels
    private String levels[] = {"random","greedy","twoLevelMove"};

    //Selected difficulty level (Default on random)
    private String currentLevel = levels[0];

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
     *  Method that evaluates a move and moves the piece if valid, used by player
     * @param start
     * @param landing
     */
    public void playerMovePiece(Position start, Position landing){
        // Create move
        Move move = new Move(board.getSquare(start).getPosition(), board.getSquare(landing).getPosition());

        if(board.getSquare(move.getStart()).getPiece().getColor() == activePlayer){
            movePiece(move,activePlayer);
        }else{
            System.out.println("It is the other players turn");
        }
        if(gameOver){
            System.out.println("Game Over");
            changePlayer();
        }
    }

    /**
     *  Move a piece on the main board, used by both player and AI agent
     * @param move
     */
    private void movePiece(Move move, PieceColor player){
        if(move.getLanding().getX() > 7 || move.getLanding().getX() < 0 ||
                move.getLanding().getY() > 7 || move.getLanding().getY() < 0){
            // Exit method if move is out of bounds
            return;
        }

        Square squareFrom = board.getSquare(move.getStart());
        Square squareTo = board.getSquare(move.getLanding());

        Boolean canMove;


        System.out.println("--------------------------------------------------");
        System.out.println("The piece that is trying to move: " +
                squareFrom.getPiece().getType() + " - " +
                squareFrom.getPiece().getColor());
        System.out.println("Move from: x"+squareFrom.getPosition().getX() + ", y"+squareFrom.getPosition().getY() +
                " - to: x"+squareTo.getPosition().getX() + ", y"+squareTo.getPosition().getY());


        //Return simulated move and use to verify if move makes player check
//        System.out.println("Check piece before: " + squareFrom.piecePresent());
        System.out.println("Running simulation to verify if move makes player check...");
        ChessBoard SimulatedBoardMove = simulateMove(move, new ChessBoard(board), player);

        if(isCheck(player, SimulatedBoardMove)){
                System.out.println("Moves makes "+ player +" check!");

            //Check if player is checkmate
            if(isCheckMate(player, SimulatedBoardMove)){
                System.out.println("Player " + player + " is checkmate!");
                gameOver = true;
            }
            canMove = false;
        }else{
            System.out.println("No check found in simulations, continue the move...");
//            System.out.println("Check piece after: " + squareFrom.piecePresent());
            // Check if piece can move from current position
            canMove = evaluateMove(squareFrom, squareTo,player,board);
        }

        if(canMove){
            System.out.println("Move valid");

            // Set piece and check for pawn promotion
            if(squareFrom.getPiece().getType() == PieceType.PAWN){
                promotePawn(squareTo.getPosition(), squareFrom, board);
            }else{
                board.setPiece(squareTo.getPosition(), squareFrom.getPiece());
            }

            board.removePiece(squareFrom.getPosition());
            changePlayer();
            System.out.println("--------------------------------------------------");
        }
        else{
            System.out.println("Unvalid move");
            System.out.println("--------------------------------------------------");
        }
    }


    /**
     * Set a simulated move to use for calculations for check and the AI agent
     * @param move
     * @param boardCopy
     * @param player
     * @return
     */
    public ChessBoard simulateMove(Move move, ChessBoard boardCopy, PieceColor player){

        commentsEnabled = false;
        Square squareFrom = boardCopy.getSquare(move.getStart());
        Square squareTo = boardCopy.getSquare(move.getLanding());

        ChessBoard newBoard = new ChessBoard(boardCopy);
        if(evaluateMove(squareFrom, squareTo,player,boardCopy)){
            // Set piece and check for pawn promotion
            if(squareFrom.getPiece().getType() == PieceType.PAWN){
                promotePawn(squareTo.getPosition(), squareFrom,boardCopy);
            }else{
                newBoard.setPiece(squareTo.getPosition(),squareFrom.getPiece());
            }

            newBoard.removePiece(squareFrom.getPosition());
        }
        commentsEnabled = true;
        return newBoard;
    }

    /**
     * Method to determine if a move is valid on the current board
     * @param squareFrom
     * @param squareTo
     * @return
     */
    public boolean evaluateMove(Square squareFrom, Square squareTo, PieceColor currentPlayer, ChessBoard  boardCopy){
        boolean canMove;
//        System.out.println("evaluateMove Piece: x" + squareFrom.getPosition().getX() + ", y" + squareFrom.getPosition().getY());
//        System.out.println("Piece present? " + squareFrom.piecePresent());
        // Choose move evaluation based on piece type
        switch (squareFrom.getPiece().getType())
        {
            case PAWN:
                canMove = isPathClear(squareFrom,squareTo,boardCopy) &&
                        evaluatePawnMove(squareFrom,squareTo,currentPlayer);
                break;
            case KNIGHT:
                canMove = (evaluateNormalMove(squareFrom,squareTo,currentPlayer));
                break;
            case KING:
                canMove = (evaluateNormalMove(squareFrom,squareTo,currentPlayer));
                break;
            default:
                canMove =  (isPathClear(squareFrom,squareTo,boardCopy) &&
                        evaluateNormalMove(squareFrom,squareTo,currentPlayer));
                break;
        }
        return canMove;
    }


    /**
     *  Return all valid moves on the given board from current position
     * @return
     */
    public ArrayList<Move> getAllValidMoves(Position current, ChessBoard boardCopy, PieceColor currentPlayer){
        ArrayList<Move> validMoves = new ArrayList<Move>();

//        System.out.println("Running algorithm to validate all valid moves.....");
        //Disable comments temporarily to prevent spamming
        commentsEnabled = false;

        Square[][] allSquares = boardCopy.getBoardSquares();
        //Loop through all squares to validate if all valid moves from current position
        for(Integer i =0; i < 8; i++){
            for(Integer j =0; j < 8; j++){
                if(evaluateMove(boardCopy.getSquare(current), allSquares[i][j],currentPlayer,boardCopy)){
                    validMoves.add(new Move(boardCopy.getSquare(current).getPosition(),allSquares[i][j].getPosition()));
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
    public ArrayList<Move> getValidAttackMoves(ArrayList<Move> validMoves, ChessBoard boardCopy){
        ArrayList<Move> validAttackMoves = new ArrayList<Move>();

//        System.out.println("Running algorithm to validate all valid attacks moves.....");
        //Disable comments temporarily to prevent spamming
        commentsEnabled = false;

        //Check all landing squares of the moves to see if there is a piece to catch
        for(Move checkMove : validMoves){
            if(boardCopy.getSquare(checkMove.getLanding()).piecePresent()){
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
    public boolean checkKingAttack(ArrayList<Move> validAttackMoves, ChessBoard boardCopy){
        for(Move checkMove : validAttackMoves){
            if(boardCopy.getSquare(checkMove.getLanding()).getPiece().getType() == PieceType.KING){
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
    public boolean isCheck(PieceColor color, ChessBoard boardCopy){
        boolean isCheck = false;
        PieceColor otherPlayer =  PieceColor.BLACK ;
        if(color == PieceColor.WHITE){
            otherPlayer = PieceColor.BLACK;
        }if(color == PieceColor.BLACK){
            otherPlayer = PieceColor.WHITE;
        }


        for(Square piece :  getAllofColor(otherPlayer, boardCopy)){
            ArrayList<Move> validMoves = getAllValidMoves(piece.getPosition(),boardCopy,otherPlayer);
            ArrayList<Move> validAttackMoves = getValidAttackMoves(validMoves, boardCopy);
            if(checkKingAttack(validAttackMoves,boardCopy)){
                isCheck = true;
//                if (commentsEnabled) {
//                    System.out.println(color + " King is under check from " + piece.getPiece().getType() +
//                            " at location: x" +  piece.getPosition().getX() + ", y" + piece.getPosition().getY());
//                }
            }
        }

        return isCheck;
    }


    /**
     *  Evaluates all moves in a simulation to see if player is checkmate
     * @param color
     * @param boardCopy
     * @return
     */
    public boolean isCheckMate(PieceColor color, ChessBoard boardCopy){
        for(Square sqr : getAllofColor(color, boardCopy)){
            for(Move move : getAllValidMoves(sqr.getPosition(), boardCopy, color)){
                //Make simulated move in cloned board
                ChessBoard tempBoard = simulateMove(move, boardCopy, color);
                //With the new move check if player is still check, if there is a move that make player not check return false
                if(!isCheck(color, tempBoard)){
                    return false;
                }

            }
        }
        //Return true if no move was found that prevents check, player is checkmate
        return true;
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
                if(allSquares[i][j].piecePresent()){
                    if(allSquares[i][j].getPiece().getColor() == color){
                        //add square to list
                        singleColor.add(allSquares[i][j]);
                    }
                }
            }
        }

        return singleColor;
    }




    /**
     *  Checks if the path is clear the piece wants to move.
     * @param squareFrom
     * @param squareTo
     * @return
     */
    private boolean isPathClear(Square squareFrom,Square squareTo, ChessBoard boardCopy){
        boolean isClear = true;
        ArrayList<Position> positions =  squareFrom.getPiece().returnPath(squareFrom.getPosition(),squareTo.getPosition());
        if(commentsEnabled){System.out.print("Pieces in path are: ");}

        for(Position pos : positions) {
            if (boardCopy.getSquare(pos).piecePresent()) {
                isClear = false;
                if (commentsEnabled) {
                    System.out.println("postion: " + "x" + pos.getX() + " - " + "y" + pos.getY() + "   occupied by: " + (boardCopy.getSquare(pos).getPiece().getType() + "!"));
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
    private void promotePawn(Position to,Square squareFrom, ChessBoard boardCopy){
        if(squareFrom.getPiece().getColor() == PieceColor.WHITE && to.getY() == 7){
            boardCopy.setPiece(to, new Queen(PieceColor.WHITE));
            if(commentsEnabled){System.out.println("White pawn promoted to White Queen!");}
        }
        else if(squareFrom.getPiece().getColor() == PieceColor.BLACK && to.getY() == 0){
            boardCopy.setPiece(to, new Queen(PieceColor.BLACK));
            if(commentsEnabled){System.out.println("Black pawn promoted to Black Queen!");}
        }
        else{
            boardCopy.setPiece(to, squareFrom.getPiece());
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
     *  Switch active player after a move and check if it is checkmate
     *  Trigger AI mode
     */
    private void changePlayer(){
        if(activePlayer == PieceColor.BLACK) {
            activePlayer = PieceColor.WHITE;
        }else{
            activePlayer = PieceColor.BLACK;
        }
        ChessBoard  boardCopy = new ChessBoard(board);
        if(isCheck(activePlayer, boardCopy)){
            if(isCheckMate(activePlayer, boardCopy)){
                System.out.println("Player " + activePlayer + " is checkmate!");
                gameOver = true;
            }
        }
        if(activePlayer == PieceColor.BLACK){
            if(enableAiAgent){
                makeAiMove(activePlayer);
            }
        }

    }

    /**
     *  Method that makes an AI move from the AI Agent
     */
    private void makeAiMove(PieceColor player){
        //Get all  currentLevel
        Move moveChoosen;
        ArrayList<Move> allValidMoves = new ArrayList<>();
        ArrayList<Square> pieces = getAllofColor(player, board);
        for(Square piece : pieces){
            for(Move move : getAllValidMoves(piece.getPosition(), board, player)){
                ChessBoard SimulatedBoardMove = simulateMove(move, new ChessBoard(board), player);
                if(!isCheck(player, SimulatedBoardMove)){
                    Move tempMove = new Move();
                    tempMove.setSquarFrom(new Square(move.getStart(), board.getSquare(move.getStart()).getPiece()));
                    tempMove.setSquarTo(new Square(move.getLanding(),board.getSquare(move.getLanding()).getPiece()));
                    allValidMoves.add(tempMove);
                }
            }
        }
        System.out.println("--------------------------------------------------");
        System.out.println("AI makes calculated move with difficulty: " + currentLevel);

        moveChoosen = new AIAgent().makeMove(currentLevel, allValidMoves,board, this);
            if( evaluateMove(moveChoosen.getSquarFrom(), moveChoosen.getSquarTo(),player,board)){
            // Set piece and check for pawn promotion
            if(moveChoosen.getSquarFrom().getPiece().getType() == PieceType.PAWN){
                promotePawn(moveChoosen.getSquarTo().getPosition(), moveChoosen.getSquarFrom(), board);
            }else{
                board.setPiece(moveChoosen.getSquarTo().getPosition(), moveChoosen.getSquarFrom().getPiece());
            }

            board.removePiece(moveChoosen.getSquarFrom().getPosition());
            changePlayer();
        }
        System.out.println("--------------------------------------------------");
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

