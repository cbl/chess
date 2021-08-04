package com.github.cbl.chess.ui;

import java.awt.Color;
import java.awt.Component;
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
import javax.swing.border.Border;

import com.github.cbl.chess.chess.Bitboard;
import com.github.cbl.chess.chess.MoveList;
import com.github.cbl.chess.chess.Move;
import com.github.cbl.chess.chess.Board;
import com.github.cbl.chess.chess.BBIndex;
import com.github.cbl.chess.chess.Position;
import com.github.cbl.chess.console.CLI;
import com.github.cbl.chess.chess.Piece;
import com.github.cbl.chess.notations.AlgebraicNotation;
import com.github.cbl.chess.notations.FenNotation;
import com.github.cbl.chess.notations.Notation;
import com.github.cbl.chess.chess.GameOfChess;
import com.github.cbl.chess.util.StateMachine;

import com.github.cbl.chess.util.Observer;
import com.github.cbl.chess.chess.Board;


public class BoardUi extends JFrame {
    char[] xAxisLabels = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H'};
    int tileSize = 75;
    int boardXOffset = 50;
    int boardYOffset = 50;
    int sideBarWidth = 350;
    int buttonOffset = 5;
    JPanel sideBar;
	JButton newGameButton;
	JButton resignButton;
	JButton backButton;
	JButton forwardButton;
	JButton loadGameButton;
	JButton saveGameButton;
    JButton saveGameDuringGameButton;
	JButton saveFenButton;
	JButton savealgButton;
	JButton loadFenButton;
	JButton loadalgButton;
    JButton[] gameButtons = {
        resignButton, backButton, forwardButton, saveGameButton, 
        saveGameDuringGameButton, saveFenButton, savealgButton
    };
    JButton[] initialGameButtons = {
        resignButton, saveGameButton
    };
    JButton[] beforeGameButtons = {
        newGameButton, loadGameButton, loadFenButton, loadalgButton
    };

    JFrame frame = new JFrame();
	JTextArea gamelog;
    int selectedSquare = Board.SQUARE_NONE;
	String fenString;
	String algString;

    private static Notation fen = new FenNotation();
	private static Notation alg = new AlgebraicNotation();
    GameOfChess game;
    MoveList moves = new MoveList();
	JButton[] board = new JButton[Board.SQUARE_COUNT];

    private static final String pieceToChar = " ♙♘♗♖♕♔  ♟♞♝♜♛♚ ";

    private class GameObserver extends Observer {
        BoardUi ui;

        GameObserver(BoardUi ui) {
            this.ui = ui;
        }

        public void handle(Object e, Object v) {
            StateMachine.Event event = (StateMachine.Event) e;

            if(event == StateMachine.Event.Transition) {
                handleTransition((GameOfChess.Transition) v);
            } else if(event == StateMachine.Event.TransitionedFrom) {
                handleTransitionedFrom((GameOfChess.State) v);
            } else if(event == StateMachine.Event.TransitionedTo) {
                handleTransitionedTo((GameOfChess.State) v);
            }
        }

        void handleTransition(GameOfChess.Transition transition) {
            BoardUi.this.renderBoardState();

            if(transition == GameOfChess.Transition.Start) {
                handleStartTransition();
            } else if(transition == GameOfChess.Transition.Resign) {
                handleResignTransition();
            }
        }

        void handleTransitionedFrom(GameOfChess.State state) {
            //
        }

        void handleTransitionedTo(GameOfChess.State state) {
            if(state == GameOfChess.State.Over) {
                handleOverState();
            }
        }

        void handleResignTransition() {
            String color = Piece.Color.toString(ui.game.position.sideToMove);
            ui.log(color+" resigned the game.");
        }

        void handleStartTransition() {
            for(JButton button : ui.beforeGameButtons) {
                if(button != null) ui.frame.remove(button);
            }
            
            for(JButton button : ui.initialGameButtons) {
                if(button != null) ui.frame.add(button);
            }
            ui.frame.revalidate(); 
            ui.frame.repaint();
        }

        void handleOverState() {
            for(JButton button : BoardUi.this.gameButtons)
                if(button != null) BoardUi.this.frame.remove(button);
            for(JButton button : BoardUi.this.beforeGameButtons)
                if(button != null) BoardUi.this.frame.add(button);
            BoardUi.this.frame.revalidate(); 
            BoardUi.this.frame.repaint();
            
            GameOfChess.Outcome outcome = BoardUi.this.game.outcome();
            if(outcome.winner != Piece.Color.NONE) {
                String color = Piece.Color.toString(outcome.winner);
                BoardUi.this.log(color+" won the game! ("+outcome.termination.name()+")");
            } else if(outcome.termination != null) {
                BoardUi.this.log("Draw! ("+outcome.termination.name()+")");
            }
        }
    }
	
	public BoardUi()
	{
		//Buttons
		newGameButton = new JButton();
		newGameButton.setBounds(boardWidth(), buttonY(0), sideBarWidth, buttonHeight());
		newGameButton.setText("New Game");
		newGameButton.setFocusable(false);
		newGameButton.setForeground(Color.LIGHT_GRAY);
		newGameButton.setBackground(Color.black);
		
		resignButton = new JButton();
		resignButton.setBounds(boardWidth(), buttonY(0), sideBarWidth/2-buttonOffset, buttonHeight());
		resignButton.setText("Resign");
		resignButton.setFocusable(false);
		resignButton.setForeground(Color.LIGHT_GRAY);
		resignButton.setBackground(Color.black);
		
		loadGameButton = new JButton();
		loadGameButton.setBounds(boardWidth(), buttonY(1), sideBarWidth/2-buttonOffset, buttonHeight());
		loadGameButton.setText("Import Game");
		loadGameButton.setFocusable(false);
		loadGameButton.setForeground(Color.LIGHT_GRAY);
		loadGameButton.setBackground(Color.black);

		saveGameButton = new JButton();
		saveGameButton.setBounds(boardWidth(), buttonY(1), sideBarWidth/2-buttonOffset, buttonHeight());
		saveGameButton.setText("Export Game");
		saveGameButton.setFocusable(false);
		saveGameButton.setForeground(Color.LIGHT_GRAY);
		saveGameButton.setBackground(Color.black);

        saveGameDuringGameButton = new JButton();
		saveGameDuringGameButton.setBounds(boardWidth(), buttonY(1), sideBarWidth, buttonHeight());
		saveGameDuringGameButton.setText("Export Game");
		saveGameDuringGameButton.setFocusable(false);
		saveGameDuringGameButton.setForeground(Color.LIGHT_GRAY);
		saveGameDuringGameButton.setBackground(Color.black);

		loadFenButton = new JButton();
		loadFenButton.setBounds(boardWidth(), buttonY(2), sideBarWidth/2-buttonOffset, buttonHeight());
		loadFenButton.setText("Import FEN");
		loadFenButton.setFocusable(false);
		loadFenButton.setForeground(Color.LIGHT_GRAY);
		loadFenButton.setBackground(Color.black);
		loadFenButton.addActionListener(e -> parseFen());
		
		loadalgButton = new JButton();
		loadalgButton.setBounds(boardWidth(), buttonY(2), sideBarWidth/2-buttonOffset, tileSize);
		loadalgButton.setText("Import ALG");
		loadalgButton.setFocusable(false);
		loadalgButton.setForeground(Color.LIGHT_GRAY);
		loadalgButton.setBackground(Color.black);
		loadalgButton.addActionListener(e -> parseAlg());

		saveFenButton = new JButton();
		saveFenButton.setBounds(boardWidth(), buttonY(2), 2*tileSize, tileSize);
		saveFenButton.setText("Export as FEN");
		saveFenButton.setFocusable(false);
		saveFenButton.setForeground(Color.LIGHT_GRAY);
		saveFenButton.setBackground(Color.black);
		saveFenButton.addActionListener(e -> onClickSaveFen());
		
		savealgButton = new JButton();
		savealgButton.setBounds(boardWidth(), buttonY(2), 2*tileSize, tileSize);
		savealgButton.setText("Export as ALG");
		savealgButton.setFocusable(false);
		savealgButton.setForeground(Color.LIGHT_GRAY);
		savealgButton.setBackground(Color.black);
		savealgButton.addActionListener(e -> createAlg());

		gamelog = new JTextArea();
		gamelog.setBounds(boardWidth(), buttonY(3), sideBarWidth, 4*tileSize);
		Border border = BorderFactory.createLineBorder(Color.BLACK, 5);
		gamelog.setBorder(border);

        for (int r = 7; r >= 0; r--) {
            for (int f = 0; f <= 7; f++) {
                int square = Board.square(r, f);
                JButton btn = new JButton();
                btn.setOpaque(true);
                btn.setBorderPainted(false);
                btn.setBackground(
                    this.squareBg(square)
                );
                placeOnBoard(btn, (f*tileSize), ((7-r)*tileSize), tileSize, tileSize);
                // btn.setBounds(1,1,tileSize,tileSize);
                btn.setFont(new Font("Silom", 0, 50));
                btn.addActionListener(e -> this.selectedSquare(square));
                board[square] = btn;
            }
        }

		//Axis Labels
        for(int i = 7; i >= 0; i--) {
            JPanel yCoordinateP = new JPanel();
            placeOnBoard(yCoordinateP, 8*tileSize, i*tileSize+(tileSize/3), tileSize/3, tileSize);
            // yCoordinateP.setBackground(new Color(230,248,220));
            yCoordinateP.setOpaque(false);
            JLabel yCoordinateL = new JLabel();
            yCoordinateL.setText(Integer.toString(8-i));
            yCoordinateL.setFont(new Font("My Boli", Font.PLAIN, tileSize/4));
            yCoordinateP.add(yCoordinateL);
            frame.add(yCoordinateP);

            JPanel xCoordinatesP = new JPanel();
            placeOnBoard(xCoordinatesP, i*tileSize, 8*tileSize, tileSize, tileSize);
            // xCoordinatesP.setBackground(new Color(230,248,220));
            xCoordinatesP.setOpaque(false);
            JLabel xCoordinatesL = new JLabel();
            xCoordinatesL.setText(Character.toString(xAxisLabels[i]));
            xCoordinatesL.setFont(new Font("Mx Boli", Font.PLAIN, tileSize/4));
            xCoordinatesP.add(xCoordinatesL);
            frame.add(xCoordinatesP);
        }

		//Frame
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(null);
		frame.setSize(frameWidth(), frameHeight());
		frame.setVisible(true);
		frame.getContentPane().setBackground(new Color(230,248,220));
		frame.add(newGameButton);
		frame.add(loadGameButton);
		frame.add(gamelog);

        for(JButton square : board) {
            frame.add(square);
        }

        JPanel boardBG = new JPanel();
        placeOnBoard(boardBG, -tileSize/2, -tileSize/2, tileSize*9, tileSize*9);
        boardBG.setBackground(squareBg(Piece.Color.WHITE));
        frame.add(boardBG);
		
		newGameButton.addActionListener(e -> onClickNewGame(frame));
		resignButton.addActionListener(e -> game.resign());
		saveGameButton.addActionListener(e -> onClickSave(frame));
		loadGameButton.addActionListener(e -> onClickLoad(frame));
        saveGameDuringGameButton.addActionListener(e -> onClickSaveDuringGame(frame));

        frame.repaint();
	}

    protected int buttonY(int pos) {
        return boardYOffset+buttonOffset+pos*tileSize;
    }

    protected int buttonHeight() {
        return tileSize-2*buttonOffset;
    }

    protected int boardWidth() {
        return tileSize * 8 + 2 * boardXOffset;
    }

    protected int frameWidth() {
        return tileSize * 8 + 3 * boardXOffset - tileSize/2 + sideBarWidth;
    }

    protected int frameHeight() {
        return tileSize * 8 + (int) (2.5 * (double) boardYOffset);
    }

    protected void placeOnBoard(Component c, int x, int y, int width, int height)
    {
        c.setBounds(x+boardXOffset, y+boardYOffset, width, height);
    }

    protected void onClickNewGame(JFrame frame) {
        Position position = fen.parse(
            "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 0"
            // "k1K5/8/2Q5/8/8/8/8/8 w - - 0 0"
            // "kp5Q/8/8/8/8/8/8/K7 w - - 0 0"
            // "rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR b KQkq e3 0 1"
            // "rnbqkbnr/pp1ppppp/8/2p5/4P3/5N2/PPPP1PPP/RNBQKB1R b KQkq - 1 2"
            // "8/8/8/8/8/8/6b1/8 w KQkq - 1 6"
        );
		
        this.game = new GameOfChess(position);
        this.game.state().addObserver(new GameObserver(this));
        this.game.start();
    }

    protected void renderBoardState() {
        for(int sq = Board.A1;sq <= Board.H8;sq++) {
            int piece = game.position.pieceAt(sq);
            String p = String.valueOf(pieceToChar.charAt(piece));
            board[sq].setText(p);
            board[sq].setFont(new Font("Mx Boli", Font.PLAIN, 45));
            board[sq].setForeground(Piece.isColor(piece, Piece.Color.WHITE) ? Color.WHITE : Color.BLACK);
        }
    }

	protected void onClickSave(JFrame frame) {
		frame.remove(saveGameButton);
		frame.remove(loadalgButton);
		frame.remove(loadFenButton);
		frame.add(saveFenButton);
		frame.add(savealgButton);
		frame.add(loadGameButton);
		frame.revalidate(); 
		frame.repaint();
	}

    protected void clearLog() {
        gamelog.setText("");
    }

    protected void log(String log) {
        gamelog.setText(
            gamelog.getText()+"\n"+log    
        );
    }

    protected void onClickSaveDuringGame(JFrame frame) {
		frame.remove(saveGameButton);
		frame.remove(loadalgButton);
		frame.remove(loadFenButton);
		frame.add(saveFenButton);
		frame.add(savealgButton);
		frame.revalidate(); 
		frame.repaint();
	}

	protected void onClickLoad(JFrame frame) {
		frame.remove(loadGameButton);
		frame.remove(savealgButton);
		frame.remove(saveFenButton);
		frame.add(loadFenButton);
		frame.add(loadalgButton);
		frame.add(saveGameButton);
		frame.revalidate(); 
		frame.repaint();
	}

	protected void parseFen() {
		this.fenString = gamelog.getText();
		Position position = fen.parse(fenString);
		this.game = new GameOfChess(position);
        this.game.state().addObserver(new GameObserver(this));
        this.game.start();
	}

	protected void parseAlg() {
		this.algString = gamelog.getText();
		Position position = alg.parse(algString);
		System.out.println(position);
		this.game = new GameOfChess(position);
        this.game.state().addObserver(new GameObserver(this));
        this.game.start();
	}

	protected void onClickSaveFen() {
		String fenString = fen.compose(game.position);
        log(fenString);
	}

	protected void createAlg() {
        // TODO
	}

    /**
     * Select the given square.
     */
    protected void selectedSquare(int square) {
        if(game == null) return;
        if(game.outcome().termination != null) return;

        if(canMakeMove()) {
            makeMoveTo(square);
        } else {
            highlightMoves(square);
        }        
    }

    /**
     * Highlight all moves that can be made from the given square.
     */
    protected void highlightMoves(int square) {
        moves = game.position.generateLegalMoves(Board.BB_SQUARES[square]);
        selectedSquare = square;
        for(int sq=Board.A1;sq<=Board.H8;sq++) {
            if(Bitboard.valueAt(moves.getToMask(), sq)) {
                board[sq].setBackground(new Color(240, 110, 110));
            } else if(sq == square) {
                board[sq].setBackground(new Color(100, 145, 170));
            } else {
                board[sq].setBackground(squareBg(sq));
            }
        }
    }

    /**
     * Make a move to the given square.
     */
    protected void makeMoveTo(int square) {        
        if(this.selectedSquare == Board.SQUARE_NONE) {
            return;
        }

        if(this.moves.exists(this.selectedSquare, square)) {
            this.game.push(this.moves.get(this.selectedSquare, square));
        }

        // Clear move list.
        this.moves = new MoveList();
        // Clear highlights.
        this.clearHighlights();
        this.selectedSquare = Board.SQUARE_NONE;
    }

    /**
     * Determine if a move can be made.
     * @return
     */
    protected boolean canMakeMove() {
        return this.moves.size() > 0;
    }

    /**
     * Clear board square highlights.
     */
    protected void clearHighlights() {
        for(int sq=Board.A1;sq<=Board.H8;sq++) {
            this.board[sq].setBackground(squareBg(sq));
        }
    }

    /**
     * Get the square background for the given square.
     */
    protected Color squareBg(int square) {
        return Board.isWhite(square) ? new Color(105, 114, 129) : new Color(79, 86, 97);
    }
}