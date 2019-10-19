package ChessProject.game;

import ChessProject.pieces.*;

/**
 * @author Damien Fleminks
 *
 */
public class ChessBoard {

    private Square[][] boardSquares = new Square[8][8];

    public ChessBoard(){
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


    private void initializePieces() {

        // Set white pieces
        boardSquares[0][1].setPiece(new Pawn(PieceColor.WHITE));
        boardSquares[1][1].setPiece(new Pawn(PieceColor.WHITE));
        boardSquares[2][1].setPiece(new Pawn(PieceColor.WHITE));
    }


    public Square[][] getBoardSquares() {
        return boardSquares;
    }

    public Square getSquare(Position position){
        return boardSquares[position.getxPos()][position.getyPos()];
    }
}


