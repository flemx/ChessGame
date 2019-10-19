package ChessProject.game;

import ChessProject.game.*;
import ChessProject.pieces.*;
import ChessProject.pieces.PieceColor;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class ChessGUI extends JFrame {

    ChessBoard realBoard;

    public JLayeredPane layeredPane;
    JPanel chessBoard;
    JLabel chessPiece;
    int xAdjustment;
    int yAdjustment;
    int startX;
    int startY;
    int initialX;
    int initialY;
    JPanel panels;
    JLabel pieces;


    private class BoardMouseAdapter extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) { }

        @Override
        public void mouseDragged(MouseEvent e) { }

        @Override
        public void mouseReleased(MouseEvent e) { }
    }


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

        //Draw squares on the board
        for (int i = 0; i < 64; i++) {
            JPanel square = new JPanel( new BorderLayout() );
            chessBoard.add( square );

            int row = (i / 8) % 2;
            if (row == 0)
                square.setBackground( i % 2 == 0 ? Color.white : Color.gray );
            else
                square.setBackground( i % 2 == 0 ? Color.gray : Color.white );
        }


        // Get real chessboard
        realBoard = new ChessBoard();
        renderPieces();


    }

    private void renderPieces(){
        Square[][] boardSquares = realBoard.getBoardSquares();
        for(int x = 0; x < 8; x++){
            for(int y = 0; y < 8; y++){
                Square square = boardSquares[x][y];

                // If square is occupied set the piece on the GUI
                if(!square.isEmpty()){
                    // Convert 2D x,y to  1D for chessBoard JPanel
                    setPiece(square,(x + 8 * y));
                }
            }
        }
    }



    private void setPiece(Square square, Integer convert2D){
        String resource = "resource/";
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



    private void initialiseBoard(){
        // Setting up the Initial Chess board.

        for(int i=8;i < 16; i++){
            pieces = new JLabel( new ImageIcon("resource/WhitePawn.png") );
            panels = (JPanel)chessBoard.getComponent(i);
            panels.add(pieces);
        }
        pieces = new JLabel( new ImageIcon("resource/WhiteRook.png") );
        panels = (JPanel)chessBoard.getComponent(0);
        panels.add(pieces);
        pieces = new JLabel( new ImageIcon("resource/WhiteKnight.png") );
        panels = (JPanel)chessBoard.getComponent(1);
        panels.add(pieces);
        pieces = new JLabel( new ImageIcon("resource/WhiteKnight.png") );
        panels = (JPanel)chessBoard.getComponent(6);
        panels.add(pieces);
        pieces = new JLabel( new ImageIcon("resource/WhiteBishop.png") );
        panels = (JPanel)chessBoard.getComponent(2);
        panels.add(pieces);
        pieces = new JLabel( new ImageIcon("resource/WhiteBishop.png") );
        panels.add(pieces);
        pieces = new JLabel( new ImageIcon("resource/WhiteKing.png") );
        panels = (JPanel)chessBoard.getComponent(3);
        panels.add(pieces);
        pieces = new JLabel( new ImageIcon("resource/WhiteQueen.png") );
        panels = (JPanel)chessBoard.getComponent(4);
        panels.add(pieces);
        pieces = new JLabel( new ImageIcon("resource/WhiteRook.png") );
        panels = (JPanel)chessBoard.getComponent(7);
        panels.add(pieces);
        for(int i=48;i < 56; i++){
            pieces = new JLabel( new ImageIcon("resource/BlackPawn.png") );
            panels = (JPanel)chessBoard.getComponent(i);
            panels.add(pieces);
        }
        pieces = new JLabel( new ImageIcon("resource/BlackRook.png") );
        panels = (JPanel)chessBoard.getComponent(56);
        panels.add(pieces);
        pieces = new JLabel( new ImageIcon("resource/BlackKnight.png") );
        panels = (JPanel)chessBoard.getComponent(57);
        panels.add(pieces);
        pieces = new JLabel( new ImageIcon("resource/BlackKnight.png") );
        panels = (JPanel)chessBoard.getComponent(62);
        panels.add(pieces);
        pieces = new JLabel( new ImageIcon("resource/BlackBishop.png") );
        panels = (JPanel)chessBoard.getComponent(58);
        panels.add(pieces);
        pieces = new JLabel( new ImageIcon("resource/BlackBishop.png") );
        panels = (JPanel)chessBoard.getComponent(61);
        panels.add(pieces);
        pieces = new JLabel( new ImageIcon("resource/BlackKing.png") );
        panels = (JPanel)chessBoard.getComponent(59);
        panels.add(pieces);
        pieces = new JLabel( new ImageIcon("resource/BlackQueen.png") );
        panels = (JPanel)chessBoard.getComponent(60);
        panels.add(pieces);
        pieces = new JLabel( new ImageIcon("resource/BlackRook.png") );
        panels = (JPanel)chessBoard.getComponent(63);
        panels.add(pieces);

    }




}
