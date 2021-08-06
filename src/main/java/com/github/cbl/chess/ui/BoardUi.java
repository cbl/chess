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
import com.github.cbl.chess.chess.Piece;
import com.github.cbl.chess.notations.AlgebraicNotation;
import com.github.cbl.chess.notations.FenNotation;
import com.github.cbl.chess.notations.Notation;
import com.github.cbl.chess.chess.GameOfChess;
import com.github.cbl.chess.util.StateMachine;

import com.github.cbl.chess.util.Observer;
import com.github.cbl.chess.chess.Board;


public class BoardUi extends JFrame {

    // Labels
    char[] xAxisLabels = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H'};

    // Sizes
    int sqSize = 75;
    int boardXOffset = 50;
    int boardYOffset = 50;
    int sideBarWidth = 350;
    int buttonOffset = 5;

    // Colors
    Color bgColor = new Color(27,38,44);
    Color lightSquareColor = new Color(105, 114, 129);
    Color darkSquareColor = new Color(79, 86, 97);
    Color btnTextColor = Color.BLACK;

    // Components
    JPanel sideBar;
	JButton newGameButton;
	JButton resignButton;
	JButton backButton;
	JButton forwardButton;
	JButton loadGameButton;
	JButton loadFenButton;
    JButton[] board = new JButton[Board.SQUARE_COUNT];
    JButton[] gameButtons = { resignButton, backButton, forwardButton };
    JButton[] initialGameButtons = { resignButton };
    JButton[] beforeGameButtons = { newGameButton, loadGameButton};
    JFrame frame = new JFrame();
	JTextArea gameLog;
    JTextArea input;

    // Chess
    int selectedSquare = Board.SQUARE_NONE;
    private static Notation fen = new FenNotation();
    GameOfChess game;
    MoveList moves = new MoveList();
    private static final String pieceToChar = " ♙♘♗♖♕♔  ♟♞♝♜♛♚ ";

    private class GameObserver extends Observer {
        /**
         * Handle any event emitted by the GameOfChess state.
         */
        public void handle(Object e, Object v) {
            updateGameState();
        }
    }
	
    /**
     * Create a new BoardUi instance.
     */
	public BoardUi()
	{
		//Buttons
		initNewGameButton();
        initResignButton();
		initLoadGameButton();
		initLoadFenButton();

        // Text fields
		initGameLog();
        initInput();

        // Board
        initBoard();

        // Frame
        initFrame();
	}

    /**
     * Initialize the main frame.
     */
    protected void initFrame() {
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(null);
		frame.setSize(frameWidth(), frameHeight());
		frame.setVisible(true);
		frame.getContentPane().setBackground(bgColor);

		frame.add(newGameButton);
		frame.add(loadGameButton);
		frame.add(gameLog);

        for(JButton square : board) {
            frame.add(square);
        }

        // Apply board background.
        JPanel boardBG = new JPanel();
        placeOnBoard(boardBG, -sqSize/2, -sqSize/2, sqSize*9, sqSize*9);
        boardBG.setBackground(squareBg(Piece.Color.WHITE));
        frame.add(boardBG);
        frame.repaint();
    }

    /**
     * Initialize the "New Game" button.
     */
    protected void initNewGameButton()
    {
        newGameButton = makeButton("New Game");
		newGameButton.setBounds(boardWidth(), buttonY(0), sideBarWidth, buttonHeight());
		newGameButton.setFocusable(false);

        newGameButton.addActionListener(e -> {
            Position pos = fen.parse(FenNotation.startingFen);
            startGame(pos);
        });
    }

    /**
     * Initialize the "Start Game" button.
     */
    protected void startGame(Position pos) {
        game = new GameOfChess(pos);
        game.state().addObserver(new GameObserver());
        game.start();

        clearLog();
        frame.add(input);
        input.setEditable(false);
        frame.remove(newGameButton);
        frame.remove(loadGameButton);
        frame.remove(loadFenButton);
        render();
        log("New game has started! ("+Piece.Color.toString(pos.sideToMove)+" to move)");
    }

    /**
     * Initialize the "Resign" button.
     */
    protected void initResignButton() {
        resignButton = makeButton("Resign");
		resignButton.setBounds(boardWidth(), buttonY(0), sideBarWidth, buttonHeight());
		resignButton.setFocusable(false);

        resignButton.addActionListener(e -> {
            game.resign();
        });
    }

    /**
     * Initialize the "Import Game" button.
     */
    protected void initLoadGameButton() {
        loadGameButton = makeButton("Import Game");
		loadGameButton.setBounds(boardWidth(), buttonY(1), sideBarWidth, buttonHeight());
		loadGameButton.setFocusable(false);
		
        loadGameButton.addActionListener(e -> {
            input.setText("");
            input.setEditable(true);
            frame.add(input);
            frame.add(loadFenButton);
            frame.remove(loadGameButton);
            render();

            clearLog();
            log("Paste the FEN representation of the game you want to import into the input field above.");
        });
    }

    /**
     * Initialize the "Import" button.
     */
    protected void initLoadFenButton() {
        loadFenButton = makeButton("Import");
		loadFenButton.setBounds(boardWidth(), buttonY(2), sideBarWidth, buttonHeight());
		loadFenButton.setFocusable(false);
		
        loadFenButton.addActionListener(e -> {
            String fenString = input.getText();
            startGame(fen.parse(fenString));
        });
    }

    /**
     * Create a new button containing the given text.
     */
    protected JButton makeButton(String text)
    {
        JButton btn = new JButton();
        btn.setText(text);
		btn.setForeground(btnTextColor);
		btn.setBackground(lightSquareColor);
        return btn;
    }

    /**
     * Initialize game log textarea.
     */
    protected void initGameLog() {
        gameLog = new JTextArea();
		gameLog.setBounds(boardWidth(), buttonY(4), sideBarWidth, 4*sqSize);
        gameLog.setEditable(false);
        gameLog.setWrapStyleWord(true);
        gameLog.setLineWrap(true);
		Border border = BorderFactory.createLineBorder(Color.BLACK, 4);
		gameLog.setBorder(border);
    }

    /**
     * Initialize input textarea.
     */
    protected void initInput() {
        input = new JTextArea();
		input.setBounds(boardWidth(), buttonY(3), sideBarWidth, sqSize);
        input.setWrapStyleWord(true);
        input.setLineWrap(true);
		Border border2 = BorderFactory.createLineBorder(Color.BLACK, 4);
		input.setBorder(border2);
    }

    /**
     * Initialize chess board.
     */
    protected void initBoard() {
        initBoardSquares();
        initBoardAxisLabels();
    }

    /**
     * Initialize chess board squares.
     */
    protected void initBoardSquares() {
        // For each row...
        for (int r = 7; r >= 0; r--) {
            // For each file...
            for (int f = 0; f <= 7; f++) {
                int square = Board.square(r, f);
                JButton btn = new JButton();
                btn.setOpaque(true);
                btn.setBorderPainted(false);
                btn.setBackground(
                    this.squareBg(square)
                );
                placeOnBoard(btn, (f*sqSize), ((7-r)*sqSize), sqSize, sqSize);
                btn.setFont(new Font("Silom", 0, 50));
                btn.addActionListener(e -> this.selectedSquare(square));
                board[square] = btn;
            }
        }
    }

    /**
     * Initialize chess board axis labels.
     */
    protected void initBoardAxisLabels() {
        //Axis Labels
        for(int i = 7; i >= 0; i--) {
            JPanel yCoordinateP = new JPanel();
            placeOnBoard(yCoordinateP, 8*sqSize, i*sqSize+(sqSize/3), sqSize/3, sqSize);
            // yCoordinateP.setBackground(new Color(230,248,220));
            yCoordinateP.setOpaque(false);
            JLabel yCoordinateL = new JLabel();
            yCoordinateL.setText(Integer.toString(8-i));
            yCoordinateL.setFont(new Font("My Boli", Font.PLAIN, sqSize/4));
            yCoordinateP.add(yCoordinateL);
            frame.add(yCoordinateP);

            JPanel xCoordinatesP = new JPanel();
            placeOnBoard(xCoordinatesP, i*sqSize, 8*sqSize, sqSize, sqSize);
            // xCoordinatesP.setBackground(new Color(230,248,220));
            xCoordinatesP.setOpaque(false);
            JLabel xCoordinatesL = new JLabel();
            xCoordinatesL.setText(Character.toString(xAxisLabels[i]));
            xCoordinatesL.setFont(new Font("Mx Boli", Font.PLAIN, sqSize/4));
            xCoordinatesP.add(xCoordinatesL);
            frame.add(xCoordinatesP);
        }
    }

    /**
     * Gets the button y offset at a given file.
     */
    protected int buttonY(int file) {
        return boardYOffset+buttonOffset+file*sqSize;
    }
    
    /**
     * Gets the button height.
     */
    protected int buttonHeight() {
        return sqSize-2*buttonOffset;
    }

    /**
     * Gets the board width.
     */
    protected int boardWidth() {
        return sqSize * 8 + 2 * boardXOffset;
    }

    /**
     * Gets the main frame width.
     */
    protected int frameWidth() {
        return sqSize * 8 + 3 * boardXOffset - sqSize/2 + sideBarWidth;
    }

    /**
     * Gets the main frame height.
     */
    protected int frameHeight() {
        return sqSize * 8 + (int) (2.5 * (double) boardYOffset);
    }

    /**
     * Place a Component on the chess board.
     */
    protected void placeOnBoard(Component c, int x, int y, int width, int height) {
        c.setBounds(x+boardXOffset, y+boardYOffset, width, height);
    }

    /**
     * Render the current board state.
     */
    protected void renderBoardState() {
        for(int sq = Board.A1;sq <= Board.H8;sq++) {
            int piece = game.position.pieceAt(sq);
            String p = String.valueOf(pieceToChar.charAt(piece));
            board[sq].setText(p);
            board[sq].setFont(new Font("Mx Boli", Font.PLAIN, 45));
            board[sq].setForeground(Piece.isColor(piece, Piece.Color.WHITE) ? Color.WHITE : Color.BLACK);
        }
    }

    /**
     * Clear the game log.
     */
    protected void clearLog() {
        gameLog.setText("");
    }

    /**
     * Append a game log.
     */
    protected void log(String log) {
        gameLog.setText(
            gameLog.getText()+"\n"+log    
        );
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
        return Board.isWhite(square) ? lightSquareColor : darkSquareColor;
    }

    /**
     * Update the game state.
     */
    protected void updateGameState() {
        updateGameLog();
        renderBoardState();

        if(game.canResign()) showResignButton();
        if(game.outcome().termination == null) return;

        frame.remove(resignButton);
        frame.add(newGameButton);
        frame.add(loadGameButton);
        render();
    }
    
    /**
     * Show resign button.
     */
    protected void showResignButton() {
        frame.add(resignButton);
        render();
    }

    /**
     * Render main frame.
     */
    protected void render() {
        frame.revalidate(); 
		frame.repaint();
    }

    /**
     * Update the gamelog.
     */
    protected void updateGameLog() {
        if(game == null) return;
        
        String str = "";
        int i = 2;
        for(Move move : game.position.moves) {
            if(move == null) break;
            if(i%2 == 0) str += Integer.toString(i/2) + ". ";
            str += move.toString()+" ";
            if(i%2 == 1) str += "\n";
            i++;
        }

        str += "\n";

        GameOfChess.Outcome outcome = game.outcome();
        if(outcome.winner != Piece.Color.NONE) {
            String color = Piece.Color.toString(outcome.winner);
            str += color+" won the game! ("+outcome.termination.name()+")";
        } else if(outcome.termination != null) {
            str += "Draw! ("+outcome.termination.name()+")";
        }

        gameLog.setText(str);

        input.setText(fen.compose(game.position));
    }
}