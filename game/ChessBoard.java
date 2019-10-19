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


    private void initializePieces() {

        // Set white pieces
        boardSquares[0][1].setPiece(new Pawn(PieceColor.WHITE));
        boardSquares[1][1].setPiece(new Pawn(PieceColor.WHITE));
        boardSquares[2][1].setPiece(new Pawn(PieceColor.WHITE));
        boardSquares[3][1].setPiece(new Pawn(PieceColor.WHITE));
        boardSquares[4][1].setPiece(new Pawn(PieceColor.WHITE));
        boardSquares[5][1].setPiece(new Pawn(PieceColor.WHITE));
        boardSquares[6][1].setPiece(new Pawn(PieceColor.WHITE));
        boardSquares[7][1].setPiece(new Pawn(PieceColor.WHITE));
        /*
        boardSquares[2][0].setPiece(new Bishop(PieceColor.WHITE));
        boardSquares[5][0].setPiece(new Bishop(PieceColor.WHITE));
        boardSquares[1][0].setPiece(new Knight(PieceColor.WHITE));
        boardSquares[6][0].setPiece(new Knight(PieceColor.WHITE));
        boardSquares[0][0].setPiece(new Rook(PieceColor.WHITE));
        boardSquares[7][0].setPiece(new Rook(PieceColor.WHITE));
        boardSquares[3][0].setPiece(new Queen(PieceColor.WHITE));
        */



        // Set black pieces
        boardSquares[0][6].setPiece(new Pawn(PieceColor.BLACK));
        boardSquares[1][6].setPiece(new Pawn(PieceColor.BLACK));
        boardSquares[2][6].setPiece(new Pawn(PieceColor.BLACK));
        boardSquares[3][6].setPiece(new Pawn(PieceColor.BLACK));
        boardSquares[4][6].setPiece(new Pawn(PieceColor.BLACK));
        boardSquares[5][6].setPiece(new Pawn(PieceColor.BLACK));
        boardSquares[6][6].setPiece(new Pawn(PieceColor.BLACK));
        boardSquares[7][6].setPiece(new Pawn(PieceColor.BLACK));

    }


    public Square[][] getBoardSquares() {
        return boardSquares;
    }

    public Square getSquare(Position position){
        return boardSquares[position.getX()][position.getY()];
    }

    public void removePiece(Position pos){
        boardSquares[pos.getX()][pos.getY()].removePiece();
    }

    public void setPiece(Position pos, Piece piece){
        boardSquares[pos.getX()][pos.getY()].setPiece(piece);
    }
}


