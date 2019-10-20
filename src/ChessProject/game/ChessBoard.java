package ChessProject.game;

import ChessProject.pieces.*;

/**
 * @author Damien Fleminks
 *
 */
public class ChessBoard {

    private Square[][] boardSquares;



    public ChessBoard(){
        boardSquares  = new Square[8][8];

        initializeSquares();
        initializePieces();

    }

    /**
     * Generate an 8 x 8 board made oof empty Squares
     */
    private void initializeSquares() {
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                boardSquares[x][y] = new Square(new Position(x, y));
            }
        }
    }


    /**
     *  Write all the pieces n a 2D array of squares
     */
    private void initializePieces() {

        // Set white pieces
        for(Integer i =0; i < 8; i++){
            boardSquares[i][1].setPiece(new Pawn(PieceColor.WHITE));
        }
        boardSquares[0][0].setPiece(new Rook(PieceColor.WHITE));
        boardSquares[1][0].setPiece(new Knight(PieceColor.WHITE));
        boardSquares[2][0].setPiece(new Bishop(PieceColor.WHITE));
        boardSquares[3][0].setPiece(new Queen(PieceColor.WHITE));
        boardSquares[4][0].setPiece(new King(PieceColor.WHITE));
        boardSquares[5][0].setPiece(new Bishop(PieceColor.WHITE));
        boardSquares[6][0].setPiece(new Knight(PieceColor.WHITE));
        boardSquares[7][0].setPiece(new Rook(PieceColor.WHITE));


        // Set black pieces
        for(Integer i =0; i < 8; i++){
            boardSquares[i][6].setPiece(new Pawn(PieceColor.BLACK));
        }
        boardSquares[0][7].setPiece(new Rook(PieceColor.BLACK));
        boardSquares[1][7].setPiece(new Knight(PieceColor.BLACK));
        boardSquares[2][7].setPiece(new Bishop(PieceColor.BLACK));
        boardSquares[3][7].setPiece(new Queen(PieceColor.BLACK));
        boardSquares[4][7].setPiece(new King(PieceColor.BLACK));
        boardSquares[5][7].setPiece(new Bishop(PieceColor.BLACK));
        boardSquares[6][7].setPiece(new Knight(PieceColor.BLACK));
        boardSquares[7][7].setPiece(new Rook(PieceColor.BLACK));

    }

    /**
     * Resets the board to its starting state
     */
    public void resetBoard(){
        initializeSquares();
        initializePieces();
    }


    /**
     *  Return the boardsquares
     * @return
     */
    public Square[][] getBoardSquares() {
        return boardSquares;
    }

    /**
     *  Return a square from a given position
     * @param position
     * @return
     */
    public Square getSquare(Position position){
        return boardSquares[position.getX()][position.getY()];
    }

    /**
     *  Removes a piece from a square by a given position
     * @param pos
     */
    public void removePiece(Position pos){
        boardSquares[pos.getX()][pos.getY()].removePiece();
    }

    /**
     *  Sets a given piece on a square defined by position
     * @param pos
     * @param piece
     */
    public void setPiece(Position pos, Piece piece){
        boardSquares[pos.getX()][pos.getY()].setPiece(piece);
    }
}


