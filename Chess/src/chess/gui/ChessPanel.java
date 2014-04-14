package chess.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import chess.logic.Board;
import chess.logic.GameChangedListener;
import chess.logic.GameLogic;
import chess.logic.GameMessage;
import chess.logic.Piece;
import chess.logic.Position;

/**
 * ChessPanel drawing the chessboard and pieces
 * @author Thomas
 *
 */
@SuppressWarnings("serial")
public class ChessPanel extends JPanel implements GameChangedListener {
	private int player = 1;
	private int mouseX = 0;
	private int mouseY = 0;
	private Image field_White;
    private Image field_Black;
    private Image pawn_White[] = new Image[2];
    private Image pawn_Black[] = new Image[2];
    private Image knight_White[] = new Image[2];
    private Image knight_Black[] = new Image[2];
    private Image bishop_White[] = new Image[2];
    private Image bishop_Black[] = new Image[2];
    private Image rook_White[] = new Image[2];
    private Image rook_Black[] = new Image[2];
    private Image queen_White[] = new Image[2];
    private Image queen_Black[] = new Image[2];
    private Image king_White[] = new Image[2];
    private Image king_Black[] = new Image[2];
	
	private boolean inGame = false;
	private GameLogic logic;
	private Piece currentPiece;
	
	public ChessPanel(GameLogic logic) {
		this.logic = logic;
		logic.addGameChangedListener(this);
		
		addKeyListener(new TAdapter());
		MAdapter mouseAdapter = new MAdapter();
        addMouseListener(mouseAdapter);
        addMouseMotionListener(mouseAdapter);
        
        try {
        	ImageIcon image = new ImageIcon(this.getClass().getResource("/res/field_White.png"));
        	field_White = image.getImage();
        	image = new ImageIcon(this.getClass().getResource("/res/field_Black.png"));
        	field_Black = image.getImage();
        	image = new ImageIcon(this.getClass().getResource("/res/pawn_White_0.png"));
        	pawn_White[0] = image.getImage();
        	image = new ImageIcon(this.getClass().getResource("/res/pawn_White_1.png"));
        	pawn_White[1] = image.getImage();
        	image = new ImageIcon(this.getClass().getResource("/res/rook_White_0.png"));
        	rook_White[0] = image.getImage();
        	image = new ImageIcon(this.getClass().getResource("/res/rook_White_1.png"));
        	rook_White[1] = image.getImage();
        	image = new ImageIcon(this.getClass().getResource("/res/knight_White_0.png"));
        	knight_White[0] = image.getImage();
        	image = new ImageIcon(this.getClass().getResource("/res/knight_White_1.png"));
        	knight_White[1] = image.getImage();
        	image = new ImageIcon(this.getClass().getResource("/res/bishop_White_0.png"));
        	bishop_White[0] = image.getImage();
        	image = new ImageIcon(this.getClass().getResource("/res/bishop_White_1.png"));
        	bishop_White[1] = image.getImage();
        	image = new ImageIcon(this.getClass().getResource("/res/queen_White_0.png"));
        	queen_White[0] = image.getImage();
        	image = new ImageIcon(this.getClass().getResource("/res/queen_White_1.png"));
        	queen_White[1] = image.getImage();
        	image = new ImageIcon(this.getClass().getResource("/res/king_White_0.png"));
        	king_White[0] = image.getImage();
        	image = new ImageIcon(this.getClass().getResource("/res/king_White_1.png"));
        	king_White[1] = image.getImage();
        	
        	
        	image = new ImageIcon(this.getClass().getResource("/res/pawn_Black_0.png"));
        	pawn_Black[0] = image.getImage();
        	image = new ImageIcon(this.getClass().getResource("/res/pawn_Black_1.png"));
        	pawn_Black[1] = image.getImage();
        	image = new ImageIcon(this.getClass().getResource("/res/rook_Black_0.png"));
        	rook_Black[0] = image.getImage();
        	image = new ImageIcon(this.getClass().getResource("/res/rook_Black_1.png"));
        	rook_Black[1] = image.getImage();
        	image = new ImageIcon(this.getClass().getResource("/res/knight_Black_0.png"));
        	knight_Black[0] = image.getImage();
        	image = new ImageIcon(this.getClass().getResource("/res/knight_Black_1.png"));
        	knight_Black[1] = image.getImage();
        	image = new ImageIcon(this.getClass().getResource("/res/bishop_Black_0.png"));
        	bishop_Black[0] = image.getImage();
        	image = new ImageIcon(this.getClass().getResource("/res/bishop_Black_1.png"));
        	bishop_Black[1] = image.getImage();
        	image = new ImageIcon(this.getClass().getResource("/res/queen_Black_0.png"));
        	queen_Black[0] = image.getImage();
        	image = new ImageIcon(this.getClass().getResource("/res/queen_Black_1.png"));
        	queen_Black[1] = image.getImage();
        	image = new ImageIcon(this.getClass().getResource("/res/king_Black_0.png"));
        	king_Black[0] = image.getImage();
        	image = new ImageIcon(this.getClass().getResource("/res/king_Black_1.png"));
        	king_Black[1] = image.getImage();
        }
        catch(Exception e) {
        	System.err.println("Can't load image");
        	System.err.println(e.getMessage());
        	System.exit(-1);
        }

        setBackground(Color.white);
        setFocusable(true);
        inGame = true;
	}
	

	
	@Override
	public void paint(Graphics g) {
        super.paint(g);

        if (inGame) {
        	for (int x = 0; x < 8; x++) {
        		for (int y = 0; y < 8; y++) {
        			if ((x % 2 == 1 && y % 2 == 0) || (x % 2 == 0 && y % 2 == 1)) {
        				g.drawImage(field_Black, x * 64, y * 64, this);
        			}
        			else if ((x % 2 == 1 && y % 2 == 1) || (x % 2 == 0 && y % 2 == 0)) {
        				g.drawImage(field_White, x * 64, y * 64, this);
        			}
        		}
        	}
        	
        	Board currentBoard = logic.getCurrentBoard();
        	LinkedList<Piece> pieces = currentBoard.getPieces();
        	
        	for (Piece p: pieces) {
        		int graficType;
        		
        		if (p.getPosY() % 2 == 0) {
        			graficType = (p.getPosX() +1) % 2;
        		}
        		else {
        			graficType = (p.getPosX()) % 2;
        		}
        		
        		if (p.getPlayer() == 1) {
        			switch (p.getType()) {
        			case PAWN: g.drawImage(pawn_White[graficType], p.getPosX()*64, p.getPosY()*64, this); break;
        			case KNIGHT: g.drawImage(knight_White[graficType], p.getPosX()*64, p.getPosY()*64, this); break;
        			case BISHOP: g.drawImage(bishop_White[graficType], p.getPosX()*64, p.getPosY()*64, this); break;
        			case ROOK: g.drawImage(rook_White[graficType], p.getPosX()*64, p.getPosY()*64, this); break;
        			case QUEEN: g.drawImage(queen_White[graficType], p.getPosX()*64, p.getPosY()*64, this); break;
        			case KING: g.drawImage(king_White[graficType], p.getPosX()*64, p.getPosY()*64, this); break;
        			}
        		}
        		else if (p.getPlayer() == 2) {
        			switch (p.getType()) {
        			case PAWN: g.drawImage(pawn_Black[graficType], p.getPosX()*64, p.getPosY()*64, this); break;
        			case KNIGHT: g.drawImage(knight_Black[graficType], p.getPosX()*64, p.getPosY()*64, this); break;
        			case BISHOP: g.drawImage(bishop_Black[graficType], p.getPosX()*64, p.getPosY()*64, this); break;
        			case ROOK: g.drawImage(rook_Black[graficType], p.getPosX()*64, p.getPosY()*64, this); break;
        			case QUEEN: g.drawImage(queen_Black[graficType], p.getPosX()*64, p.getPosY()*64, this); break;
        			case KING: g.drawImage(king_Black[graficType], p.getPosX()*64, p.getPosY()*64, this); break;
        			}
        		}
        	}
        }
	}

	
	
	/**
	 * Class dealing with any keyboard action
	 * @author Thomas
	 *
	 */
	private class TAdapter extends KeyAdapter {

        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();

            if ((key == KeyEvent.VK_LEFT)) {
            	
            }
        }
    }
    
	/**
	 * Class dealing with any mouse action
	 * @author Thomas
	 *
	 */
    private class MAdapter extends MouseAdapter {
        
    	@Override
        public void mouseClicked(MouseEvent e) {
        }
        
    	@Override
    	public void mousePressed(MouseEvent e) {
    		if (logic.getCurrentBoard().getCurrentPlayer() == player) {

    			Board board = logic.getCurrentBoard();
    			Piece piece = board.getPieceFromPosition(mouseX /64, mouseY /64);

    			if (piece != null && piece.getPlayer() == player) {
    				currentPiece = piece;
    			}
    			else if (currentPiece != null) {
    				//TODO change methods which have Pieces to methods which have strings "K1" etc.
    				if (logic.movePiece(currentPiece.getName(), new Position(mouseX / 64, mouseY / 64))) {
    					repaint();
    					currentPiece = null;

    				}
    				else {
    					System.out.println("Can't move to this position");
    				}

    			}
    		}
    	}
        
        @Override
        public void mouseReleased(MouseEvent e) {
        	
        }
        
        @Override
        public void mouseMoved(MouseEvent e) {
        	mouseX = e.getX();
        	mouseY = e.getY();
        }

    }

	@Override
	public void handleMessage(GameMessage message) {
		switch (message.getType()) {
		case (GameMessage.PIECE_MOVED): {
			repaint();
			}
		
		}
		
	}

}
