package com.github.cbl.chess.console.commands;

import com.github.cbl.chess.notations.Notation;
import com.github.cbl.chess.notations.FenNotation;
import com.github.cbl.chess.chess.Position;
import com.github.cbl.chess.chess.Piece;
import com.github.cbl.chess.chess.GameOfChess;
import com.github.cbl.chess.console.Command;
import com.github.cbl.chess.util.Observer;
import com.github.cbl.chess.util.StateMachine;

import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;

public class PlayCommand extends Command
{
    protected static final String NAME = "play";
    protected static final String[] ARGUMENTS = {
        "--fen,-f:The FEN of the starting position.",
    };

    public String name() { return PlayCommand.NAME; }
    public String[] arguments() { return PlayCommand.ARGUMENTS; }
    private Position position;
    private GameOfChess game;

    private static class GameObserver extends Observer {
        private GameOfChess game;
        private Position position;
        public GameObserver(GameOfChess game, Position pos) {
            this.game = game;
            this.position = pos;
        }
        public void handle(Object event, Object value)
        {
            if((StateMachine.Event) event == StateMachine.Event.Transition) {
                System.out.println(this.position.toAscii(0));
                return;
            } else if((StateMachine.Event) event != StateMachine.Event.TransitionedTo) {
                return;
            }

            if((GameOfChess.State) value == GameOfChess.State.Waiting 
                || (GameOfChess.State) value == GameOfChess.State.Thinking) {
                System.out.println(
                    (this.position.sideToMove == Piece.Color.WHITE ? "White" : "Black")
                    + " to move:"
                );
                this.makeMove();
            }
        }

        private void makeMove() {
            Scanner scanner = new Scanner(System.in); 
            // while(true) {
            //     System.out.print("> ");
            //     String move = scanner.nextLine();
            //     // if(this.game.push(move)) {
            //     //     return;
            //     // } else {
            //     //     System.out.println("Move ["+move+"] is not a legal move.");
            //     // }
            // }
        }
    }

    public int handle(Command.Arguments arguments)
    {
        Notation fen = new FenNotation();
        Position pos = fen.parse(
            "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 0"
            // "8/8/8/8/8/8/6b1/8 w KQkq - 1 6"
        );
        GameOfChess game = new GameOfChess(pos);
        game.state().addObserver(new GameObserver(game, pos));
        game.start();

        return 0;
    }
}