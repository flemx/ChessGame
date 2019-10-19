package ChessProject.game;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;



/*
	This class can be used as a starting point for creating your Chess game project. The only piece that 
	has been coded is a white pawn...a lot done, more to do!
			*/

	public class ChessGUI2 extends JFrame implements MouseListener, MouseMotionListener{
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


		public ChessGUI2(){
			Dimension boardSize = new Dimension(600, 600);

			//  Use a Layered Pane for this application
			layeredPane = new JLayeredPane();
			getContentPane().add(layeredPane);
			layeredPane.setPreferredSize(boardSize);
			layeredPane.addMouseListener(this);
			layeredPane.addMouseMotionListener(this);

			//Add a chess board to the Layered Pane
			chessBoard = new JPanel();
			layeredPane.add(chessBoard, JLayeredPane.DEFAULT_LAYER);
			chessBoard.setLayout( new GridLayout(8, 8) );
			chessBoard.setPreferredSize( boardSize );
			chessBoard.setBounds(0, 0, boardSize.width, boardSize.height);

			for (int i = 0; i < 64; i++) {
				JPanel square = new JPanel( new BorderLayout() );
				chessBoard.add( square );

				int row = (i / 8) % 2;
				if (row == 0)
					square.setBackground( i % 2 == 0 ? Color.white : Color.gray );
				else
					square.setBackground( i % 2 == 0 ? Color.gray : Color.white );
			}

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



		/*
     	 	 This method checks if there is a piece present on a particular square.
  		*/
		private Boolean piecePresent(int x, int y){
			Component c = chessBoard.findComponentAt(x, y);
			if(c instanceof JPanel){
				return false;
			}
			else{
				return true;
			}
		}

		/*
            This is a method to check if a piece is a Black piece.
        */
		private Boolean checkWhiteOponent(int newX, int newY){
			Boolean oponent;
			Component c1 = chessBoard.findComponentAt(newX, newY);
			JLabel awaitingPiece = (JLabel)c1;
			String tmp1 = awaitingPiece.getIcon().toString();
			if(((tmp1.contains("Black")))){
				oponent = true;
			}
			else{
				oponent = false;
			}
			return oponent;
		}

		/*
            This method is called when we press the Mouse. So we need to find out what piece we have
            selected. We may also not have selected a piece!
        */
		public void mousePressed(MouseEvent e){

			chessPiece = null;
			Component c =  chessBoard.findComponentAt(e.getX(), e.getY());
			System.out.println(c);
			if (c instanceof JPanel)
				return;

			Point parentLocation = c.getParent().getLocation();
			xAdjustment = parentLocation.x - e.getX();
			yAdjustment = parentLocation.y - e.getY();
			chessPiece = (JLabel)c;
			initialX = e.getX();
			initialY = e.getY();
			startX = (e.getX()/75);
			startY = (e.getY()/75);
			chessPiece.setLocation(e.getX() + xAdjustment, e.getY() + yAdjustment);
			chessPiece.setSize(chessPiece.getWidth(), chessPiece.getHeight());
			layeredPane.add(chessPiece, JLayeredPane.DRAG_LAYER);
			//System.out.println(layeredPane);
		}

		public void mouseDragged(MouseEvent me) {
			if (chessPiece == null) return;
			chessPiece.setLocation(me.getX() + xAdjustment, me.getY() + yAdjustment);
		}

		/*
           This method is used when the Mouse is released...we need to make sure the move was valid before
           putting the piece back on the board.
       */
		public void mouseReleased(MouseEvent e) {
			System.out.println("mouseReleased triggered");

			//Debugging
			System.out.println("chessPiece: ");
			System.out.println(chessPiece);
			System.out.println("startY: ");
			System.out.println(startY);
			System.out.println("startX: ");
			System.out.println(startX);

			System.out.println("\ne.getX(): ");
			System.out.println(e.getX()/75);

			System.out.println("\ne.gety(): ");
			System.out.println(e.getY()/75);



			if(chessPiece == null) return;

			chessPiece.setVisible(false);
			Boolean success =false;
			Component c =  chessBoard.findComponentAt(e.getX(), e.getY());
			String tmp = chessPiece.getIcon().toString();
			String pieceName = tmp.substring(0, (tmp.length()-4));
			Boolean validMove = false;

		/*
			The only piece that has been enabled to move is a White Pawn...but we should really have this is a separate
			method somewhere...how would this work.

			So a Pawn is able to move two squares forward one its first go but only one square after that.
			The Pawn is the only piece that cannot move backwards in chess...so be careful when committing
			a pawn forward. A Pawn is able to take any of the opponent’s pieces but they have to be one
			square forward and one square over, i.e. in a diagonal direction from the Pawns original position.
			If a Pawn makes it to the top of the other side, the Pawn can turn into any other piece, for
			demonstration purposes the Pawn here turns into a Queen.
		*/

			System.out.println("\n TEST: ");
			//System.out.println((startX == (e.getX()/75))&&((((e.getY()/75)-startY)==1)||((e.getY()/75)-startY)==2));
			System.out.println(pieceName);


			if(pieceName.equals("resource/WhitePawn")){
				if(startY == 1)
				{System.out.println("true1 ");
					if((startX == (e.getX()/75))&&((((e.getY()/75)-startY)==1)||((e.getY()/75)-startY)==2))
					{System.out.println("true2 ");

						if((((e.getY()/75)-startY)==2)){

							if((!piecePresent(e.getX(), (e.getY())))&&(!piecePresent(e.getX(), (e.getY()+75)))){
								validMove = true;
							}
							else{
								validMove = false;
							}
						}
						else{
							if((!piecePresent(e.getX(), (e.getY()))))
							{
								validMove = true;
							}
							else{
								validMove = false;
							}
						}
					}
					else{
						validMove = false;
					}
				}
				else{
					int newY = e.getY()/75;
					int newX = e.getX()/75;
					if((startX-1 >=0)||(startX +1 <=7))
					{
						if((piecePresent(e.getX(), (e.getY())))&&((((newX == (startX+1)&&(startX+1<=7)))||((newX == (startX-1))&&(startX-1 >=0)))))
						{
							if(checkWhiteOponent(e.getX(), e.getY())){
								validMove = true;
								if(startY == 6){
									success = true;
								}
							}
							else{
								validMove = false;
							}
						}
						else{
							if(!piecePresent(e.getX(), (e.getY()))){
								if((startX == (e.getX()/75))&&((e.getY()/75)-startY)==1){
									if(startY == 6){
										success = true;
									}
									validMove = true;
								}
								else{
									validMove = false;
								}
							}
							else{
								validMove = false;
							}
						}
					}
					else{
						validMove = false;
					}
				}
			}
			if(!validMove){
				int location=0;
				if(startY ==0){
					location = startX;
				}
				else{
					location  = (startY*8)+startX;
				}
				String pieceLocation = pieceName+".png";
				pieces = new JLabel( new ImageIcon(pieceLocation) );
				panels = (JPanel)chessBoard.getComponent(location);
				panels.add(pieces);
			}
			else{
				if(success){
					int location = 56 + (e.getX()/75);
					if (c instanceof JLabel){
						Container parent = c.getParent();
						parent.remove(0);
						pieces = new JLabel( new ImageIcon("resource/WhiteQueen.png") );
						parent = (JPanel)chessBoard.getComponent(location);
						parent.add(pieces);
					}
					else{
						Container parent = (Container)c;
						pieces = new JLabel( new ImageIcon("resource/WhiteQueen.png") );
						parent = (JPanel)chessBoard.getComponent(location);
						parent.add(pieces);
					}
				}
				else{
					if (c instanceof JLabel){
						Container parent = c.getParent();
						parent.remove(0);
						parent.add( chessPiece );
					}
					else {
						Container parent = (Container)c;
						parent.add( chessPiece );
					}
					chessPiece.setVisible(true);
				}
			}

			System.out.println("validMove: ");
			System.out.println(validMove);
		}

		public void mouseClicked(MouseEvent e) {

		}
		public void mouseMoved(MouseEvent e) {
		}
		public void mouseEntered(MouseEvent e){

		}
		public void mouseExited(MouseEvent e) {

		}




	}


