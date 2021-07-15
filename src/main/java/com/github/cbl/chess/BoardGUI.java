package com.github.cbl.chess;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import com.github.cbl.chess.chess.BitBoard;
import com.github.cbl.chess.chess.Board;
import com.github.cbl.chess.chess.AttackIndex;
import com.github.cbl.chess.chess.Position;
import com.github.cbl.chess.console.CLI;
import com.github.cbl.chess.chess.Piece;
import com.github.cbl.chess.notations.FenNotation;
import com.github.cbl.chess.notations.Notation;
import com.github.cbl.chess.chess.GameOfChess;
import com.github.cbl.chess.util.StateMachine;
import com.github.cbl.chess.util.Observer;

import com.github.cbl.chess.chess.Board;


public class BoardGUI extends JFrame implements ActionListener {
    private static final String pieceToChar = " ♙♘♗♖♕♔  ♟♞♝♜♛♚ ";

    private class GameObserver extends Observer {
        public void handle(Object event, Object value) {
            if((StateMachine.Event) event == StateMachine.Event.Transition) {
                for(int sq = Board.A1;sq <= Board.H8;sq++) {
                    int piece = BoardGUI.this.position.pieceAt(sq);
                    String p = String.valueOf(pieceToChar.charAt(piece));
                    BoardGUI.this.board[sq].setText(p);
		    BoardGUI.this.board[sq].setFont(new Font("Default", Font.PLAIN, 36));
                    BoardGUI.this.board[sq].setText(p);
                    BoardGUI.this.board[sq].setForeground(Piece.isColor(piece, Piece.Color.WHITE) ? Color.WHITE : Color.BLACK);
                }
                return;
            } else if((StateMachine.Event) event != StateMachine.Event.TransitionedTo) {
                return;
            }

            if((GameOfChess.State) value == GameOfChess.State.Waiting 
                || (GameOfChess.State) value == GameOfChess.State.Thinking) {
                System.out.println(
                    (BoardGUI.this.position.sideToMove == Piece.Color.WHITE ? "White" : "Black")
                    + " to move:"
                );
            }
        }
    }
	
	JButton newGame;
	JButton resign;
	JButton back;
	JButton foward;
	JButton savepgm;
	JButton savealg;
	JButton loadpgm;
	JButton loadalg;
	JTextArea gamelog;
    int selectedSquare = Board.SQUARE_NONE;

    private static Notation fen = new FenNotation();
    Position position;
    GameOfChess game;


    JButton[] board = new JButton[Board.SQUARE_COUNT];
	
	public BoardGUI(/*GameData d*/)
	{
//		data = d;
		int tileSize = 75;

		//Buttons
		newGame = new JButton();
		newGame.setBounds(25, tileSize*10, 2*tileSize, tileSize);
		newGame.setText("New Game");
		newGame.setFocusable(false);
		newGame.setForeground(Color.LIGHT_GRAY);
		newGame.setBackground(Color.black);
//		newGame.addActionListener(e -> );
		
		resign = new JButton();
		resign.setBounds(2*tileSize+25, tileSize*10, 2*tileSize, tileSize);
		resign.setText("Resign");
		resign.setFocusable(false);
		resign.setForeground(Color.LIGHT_GRAY);
		resign.setBackground(Color.black);
//		resign.addActionListener(e -> );
		
		back = new JButton();
		back.setBounds(4*tileSize+25, tileSize*10, 2*tileSize, tileSize);
		back.setText("Move back");
		back.setFocusable(false);
		back.setForeground(Color.LIGHT_GRAY);
		back.setBackground(Color.black);
//		back.addActionListener(e -> );
		
		foward = new JButton();
		foward.setBounds(6*tileSize+25, tileSize*10, 2*tileSize, tileSize);
		foward.setText("Move Foward");
		foward.setFocusable(false);
		foward.setForeground(Color.LIGHT_GRAY);
		foward.setBackground(Color.black);
//		foward.addActionListener(e -> );
		
		savepgm = new JButton();
		savepgm.setBounds(25, tileSize*11, 2*tileSize, tileSize);
		savepgm.setText("Save as PGM");
		savepgm.setFocusable(false);
		savepgm.setForeground(Color.LIGHT_GRAY);
		savepgm.setBackground(Color.black);
//		savepgm.addActionListener(e -> );
		
		savealg = new JButton();
		savealg.setBounds(25+2*tileSize, tileSize*11, 2*tileSize, tileSize);
		savealg.setText("Save as ALG");
		savealg.setFocusable(false);
		savealg.setForeground(Color.LIGHT_GRAY);
		savealg.setBackground(Color.black);
//		savealg.addActionListener(e -> );
		
		loadpgm = new JButton();
		loadpgm.setBounds(25+4*tileSize, tileSize*11, 2*tileSize, tileSize);
		loadpgm.setText("Load PGM");
		loadpgm.setFocusable(false);
		loadpgm.setForeground(Color.LIGHT_GRAY);
		loadpgm.setBackground(Color.black);
//		loadpgm.addActionListener(e -> );
		
		loadalg = new JButton();
		loadalg.setBounds(25+6*tileSize, tileSize*11, 2*tileSize, tileSize);
		loadalg.setText("Load ALG");
		loadalg.setFocusable(false);
		loadalg.setForeground(Color.LIGHT_GRAY);
		loadalg.setBackground(Color.black);
//		loadalg.addActionListener(e -> );
		
		gamelog = new JTextArea();
		gamelog.setBounds(10*tileSize,2*tileSize,3*tileSize, 3*tileSize);

        for (int r = 7; r >= 0; r--) {
            for (int f = 0; f <= 7; f++) {
                int square = Board.square(r, f);
                JButton btn = new JButton();
                btn.setOpaque(true);
                btn.setBorderPainted(false);
                btn.setBackground(
                    this.squareBg(square)
                );
                btn.setBounds((f*tileSize),((7-r)*tileSize),tileSize,tileSize);
                btn.setFont(new Font("Silom", 0, 50));
                btn.addActionListener(e -> this.selectedSquare(square));
                board[square] = btn;
            }
        }
		
		//Axis lable
		JPanel xCoordinatesPanel = new JPanel();
		xCoordinatesPanel.setBounds(0, 8*tileSize, 8*tileSize, 2*tileSize);
		xCoordinatesPanel.setBackground(new Color(230,248,220));
		JLabel xCoordinatesLabel = new JLabel();
		xCoordinatesLabel.setText("A B C D E F G H");
		xCoordinatesLabel.setFont(new Font("Mx Boli", Font.PLAIN,tileSize));
		xCoordinatesPanel.add(xCoordinatesLabel);

		JPanel yCoordinatesPanel1 = new JPanel();
		yCoordinatesPanel1.setBounds(8*tileSize,(7*tileSize),tileSize,tileSize);
		yCoordinatesPanel1.setBackground(new Color(230,248,220));
		JLabel yCoordinatesLabel1 = new JLabel();
		yCoordinatesLabel1.setText("1");
		yCoordinatesLabel1.setFont(new Font("My Boli", Font.PLAIN,tileSize));
		yCoordinatesPanel1.add(yCoordinatesLabel1);

		JPanel yCoordinatesPanel2 = new JPanel();
		yCoordinatesPanel2.setBounds(8*tileSize,(6*tileSize),tileSize,tileSize);
		yCoordinatesPanel2.setBackground(new Color(230,248,220));
		JLabel yCoordinatesLabel2 = new JLabel();
		yCoordinatesLabel2.setText("2");
		yCoordinatesLabel2.setFont(new Font("My Boli", Font.PLAIN,tileSize));
		yCoordinatesPanel2.add(yCoordinatesLabel2);

		JPanel yCoordinatesPanel3 = new JPanel();
		yCoordinatesPanel3.setBounds(8*tileSize,(5*tileSize),tileSize,tileSize);
		yCoordinatesPanel3.setBackground(new Color(230,248,220));
		JLabel yCoordinatesLabel3 = new JLabel();
		yCoordinatesLabel3.setText("3");
		yCoordinatesLabel3.setFont(new Font("My Boli", Font.PLAIN,tileSize));
		yCoordinatesPanel3.add(yCoordinatesLabel3);

		JPanel yCoordinatesPanel4 = new JPanel();
		yCoordinatesPanel4.setBounds(8*tileSize,(4*tileSize),tileSize,tileSize);
		yCoordinatesPanel4.setBackground(new Color(230,248,220));
		JLabel yCoordinatesLabel4 = new JLabel();
		yCoordinatesLabel4.setText("4");
		yCoordinatesLabel4.setFont(new Font("My Boli", Font.PLAIN,tileSize));
		yCoordinatesPanel4.add(yCoordinatesLabel4);

		JPanel yCoordinatesPanel5 = new JPanel();
		yCoordinatesPanel5.setBounds(8*tileSize,(3*tileSize),tileSize,tileSize);
		yCoordinatesPanel5.setBackground(new Color(230,248,220));
		JLabel yCoordinatesLabel5 = new JLabel();
		yCoordinatesLabel5.setText("5");
		yCoordinatesLabel5.setFont(new Font("My Boli", Font.PLAIN,tileSize));
		yCoordinatesPanel5.add(yCoordinatesLabel5);

		JPanel yCoordinatesPanel6 = new JPanel();
		yCoordinatesPanel6.setBounds(8*tileSize,(2*tileSize),tileSize,tileSize);
		yCoordinatesPanel6.setBackground(new Color(230,248,220));
		JLabel yCoordinatesLabel6 = new JLabel();
		yCoordinatesLabel6.setText("6");
		yCoordinatesLabel6.setFont(new Font("My Boli", Font.PLAIN,tileSize));
		yCoordinatesPanel6.add(yCoordinatesLabel6);

		JPanel yCoordinatesPanel7 = new JPanel();
		yCoordinatesPanel7.setBounds(8*tileSize,(1*tileSize),tileSize,tileSize);
		yCoordinatesPanel7.setBackground(new Color(230,248,220));
		JLabel yCoordinatesLabel7 = new JLabel();
		yCoordinatesLabel7.setText("7");
		yCoordinatesLabel7.setFont(new Font("My Boli", Font.PLAIN,tileSize));
		yCoordinatesPanel7.add(yCoordinatesLabel7);

		JPanel yCoordinatesPanel8 = new JPanel();
		yCoordinatesPanel8.setBounds(8*tileSize,(0*tileSize),tileSize,tileSize);
		yCoordinatesPanel8.setBackground(new Color(230,248,220));
		JLabel yCoordinatesLabel8 = new JLabel();
		yCoordinatesLabel8.setText("8");
		yCoordinatesLabel8.setFont(new Font("My Boli", Font.PLAIN,tileSize));
		yCoordinatesPanel8.add(yCoordinatesLabel8);

		
		//Frame
		JFrame frame = new JFrame();
		// frame.add(tile0);
		frame.setResizable(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(null);
		frame.setSize(1000,1000);
		frame.setVisible(true);
		frame.getContentPane().setBackground(new Color(230,248,220));
		frame.add(resign);
		frame.add(newGame);
		frame.add(back);
		frame.add(foward);
		frame.add(savepgm);
		frame.add(loadpgm);
		frame.add(savealg);
		frame.add(loadalg);
		frame.add(gamelog);
        for(JButton square : board) {
            frame.add(square);
        }
		
		frame.add(yCoordinatesPanel1);
		frame.add(yCoordinatesPanel2);
		frame.add(yCoordinatesPanel3);
		frame.add(yCoordinatesPanel4);
		frame.add(yCoordinatesPanel5);
		frame.add(yCoordinatesPanel6);
		frame.add(yCoordinatesPanel7);
		frame.add(yCoordinatesPanel8);
		frame.add(xCoordinatesPanel);

        this.newGame();
	
	}

    protected void newGame() {
        this.position = fen.parse(
            "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 0"
            // "8/8/8/8/8/8/6b1/8 w KQkq - 1 6"
        );
        this.game = new GameOfChess(this.position);
        this.game.state().addObserver(new GameObserver());
        this.game.start();
    }

    protected void selectedSquare(int square) {
        long moves = 0;
        int piece = this.position.pieceAt(square);

        if(this.selectedSquare != Board.SQUARE_NONE) {
            if(this.game.move(this.selectedSquare, square)) {
                this.selectedSquare = Board.SQUARE_NONE;
                this.clearHighlights();
                return;
            }
        }

        this.selectedSquare = square;
        System.out.println(this.position.sideToMove);
        if(Piece.isColor(piece, this.position.sideToMove)) {
            moves = this.position.legalMoves(square);
        }
        for(int sq=Board.A1;sq<=Board.H8;sq++) {
            if(BitBoard.valueAt(moves, sq)) {
                this.board[sq].setBackground(new Color(240, 110, 110));
            } else if(sq == square) {
                this.board[sq].setBackground(new Color(100, 145, 170));
            } else {
                this.board[sq].setBackground(squareBg(sq));
            }
        }
        
        // game.state().addObserver(new GameObserver(game, pos));
        // game.start();
    }

    protected void clearHighlights() {
        for(int sq=Board.A1;sq<=Board.H8;sq++) {
            this.board[sq].setBackground(squareBg(sq));
        }
    }

    protected Color squareBg(int square) {
        return Board.isWhite(square) ? new Color(105, 114, 129) : new Color(79, 86, 97);
    }

    /**
     * Invoked when an action occurs.
     * @param e the event to be processed
     */
    public void actionPerformed(ActionEvent e)
    {

    }
}
