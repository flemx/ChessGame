package ChessProject.game;

import ChessProject.pieces.PieceColor;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


/**
 *  @author dfleminks
 *  Modified from give examnple
 *  - Seperated Chess logic from GUI -
 */
public class ChessGUI extends JFrame {


    private ChessProject chessGame;

    private JLayeredPane layeredPane;
    private JPanel chessBoard;
    private JLabel chessPiece;
    private int xAdjustment;
    private int yAdjustment;
    private int startX;
    private int startY;
    private JPanel panels;
    private JLabel pieces;


    /**
     *   MouseAdapter Class to handle mouse events
     */
    private class BoardMouseAdapter extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {

            chessPiece = null;
            Component c =  chessBoard.findComponentAt(e.getX(), e.getY());
            if (c instanceof JPanel)
                return;

            startX = (e.getX()/75);
            startY = (e.getY()/75);
            Point parentLocation = c.getParent().getLocation();
            xAdjustment = parentLocation.x - e.getX();
            yAdjustment = parentLocation.y - e.getY();
            chessPiece = (JLabel)c;
            chessPiece.setLocation(e.getX() + xAdjustment, e.getY() + yAdjustment);
            chessPiece.setSize(chessPiece.getWidth(), chessPiece.getHeight());
            layeredPane.add(chessPiece, JLayeredPane.DRAG_LAYER);
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            if (chessPiece == null) return;
            chessPiece.setLocation(e.getX() + xAdjustment, e.getY() + yAdjustment);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if(chessPiece == null) return;
            chessPiece.setVisible(false);

            chessGame.canMoveTo(new Position(startX,startY), new Position((e.getX()/75),(e.getY()/75)));
            renderBoard();
        }
    }


    /**
     *   Draws the board in
     */
    public ChessGUI(){
        // Use BoardMouseAdapter class to capture mouse events
        BoardMouseAdapter mouseAdapter = new BoardMouseAdapter();

        Dimension boardSize = new Dimension(600, 600);

        //  Use a Layered Pane for this application
        layeredPane = new JLayeredPane();
        getContentPane().add(layeredPane);
        layeredPane.setPreferredSize(boardSize);
        layeredPane.addMouseListener(mouseAdapter);
        layeredPane.addMouseMotionListener(mouseAdapter);

        //Add a chess board to the Layered Pane
        chessBoard = new JPanel();
        layeredPane.add(chessBoard, JLayeredPane.DEFAULT_LAYER);
        chessBoard.setLayout( new GridLayout(8, 8) );
        chessBoard.setPreferredSize( boardSize );
        chessBoard.setBounds(0, 0, boardSize.width, boardSize.height);

        // Start Chess engine ChessProject
        chessGame = new ChessProject();

        //Draw squares and pieces on gui board
        renderBoard();


    }


    /**
     *  Clear board, Iterate through chessBoard and draw pieces as per the Chessboard Class
     */
    private void renderBoard(){ ;
        drawSquares();
        Square[][] boardSquares = chessGame.getBoard().getBoardSquares();
        for(int x = 0; x < 8; x++){
            for(int y = 0; y < 8; y++){
                Square square = boardSquares[x][y];
                // If square is occupied set the piece on the GUI
                if(square.piecePresent()) {
                    // Convert 2D x,y to  1D for chessBoard JPanel
                    setPiece(square, (x + 8 * y));
                }
            }
        }
    }


    /**
     *  Draw a chesspiece on the Jpanel board based on current ChessBoard state
     * @param square
     * @param convert2D
     */
    private void setPiece(Square square, Integer convert2D){

        /* Try to add "resource/ to variable if piece images don't render */
        String resource = "resource/";
        //String resource = "";
        resource += (square.getPiece().getColor() == PieceColor.WHITE) ? "White" : "Black";

        switch (square.getPiece().getType())
        {
            case KING:  resource += "King.png";
                break;
            case QUEEN: resource += "Queen.png";
                break;
            case ROOK:  resource += "Rook.png";
                break;
            case KNIGHT:  resource += "Knight.png";
                break;
            case BISHOP:  resource += "Bishop.png";
                break;
            case PAWN:  resource += "Pawn.png";
                break;
        }
        // Set image on convert2D position
        pieces = new JLabel( new ImageIcon(resource) );
        panels = (JPanel)chessBoard.getComponent(convert2D);
        panels.add(pieces);

    }

    /**
     *  Clear the JPanel board and draw new squares
     */
    private void drawSquares(){
        chessBoard.removeAll();
        chessBoard.repaint();
        for (int i = 0; i < 64; i++) {
            JPanel square = new JPanel( new BorderLayout() );
            chessBoard.add( square );

            int row = (i / 8) % 2;
            if (row == 0)
                square.setBackground( i % 2 == 0 ? Color.white : Color.gray );
            else
                square.setBackground( i % 2 == 0 ? Color.gray : Color.white );
        }

    }




}
