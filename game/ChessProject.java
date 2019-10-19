package ChessProject.game;

public class ChessProject {

    private ChessBoard board;

    public ChessProject(){
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
        Square squareFrom = board.getSquare(from);

        System.out.println("--------------------------------------------------");
        System.out.println("squareFrom.piecePresent: " +  squareFrom.piecePresent());
        System.out.println("The piece that is being moved: " +  squareFrom.getPiece().getType() + " - " + squareFrom.getPiece().getColor());
        System.out.println("Move from: x"+from.getY() + ", y"+from.getY());
        System.out.println("Move to: x"+to.getY() + ", y"+to.getY());
        System.out.println("validMove: " + squareFrom.getPiece().validMove(from,to));


         //TODO -> Add logic to check path & MORE
         if(squareFrom.piecePresent() &&
                 squareFrom.getPiece().validMove(from,to)){
             board.setPiece(to, squareFrom.getPiece());
             board.removePiece(from);
         }
    }
}

