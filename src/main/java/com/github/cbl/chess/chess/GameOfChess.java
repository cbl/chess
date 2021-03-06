package com.github.cbl.chess.chess;

import com.github.cbl.chess.util.StateMachine;
import com.github.cbl.chess.notations.AlgebraicNotation;

public class GameOfChess {
    public enum State {
        Pending, Waiting, Thinking, Over
    }

    public enum Transition {
        Start, Move, Resign, Cancel, Over
    }

    public enum Termination {
        CHECKMATE, STALEMATE, INSUFFICIENT_MATERIAL, RESIGNED
    }

    public class Outcome {
        public Termination termination;
        public int winner = Piece.Color.NONE;

        /**
         * Create a new Outcome instance.
         */
        Outcome(Termination termination, int winner) {
            this.termination = termination;
            this.winner = winner;
        }
    }

    private StateMachine state = new StateMachine(State.Pending);
    public Position position;
    public MoveList moves;
    private boolean resigned = false;

    /**
     * Create a new GameOfChess instance.
     */
    public GameOfChess(Position pos) {
        position = pos;

        state.allow(Transition.Start, State.Pending, State.Waiting);
        state.allow(Transition.Move, State.Waiting, State.Thinking);
        state.allow(Transition.Move, State.Thinking, State.Thinking);
        state.allow(Transition.Resign, State.Thinking, State.Over);
        state.allow(Transition.Cancel, State.Waiting, State.Over);
        state.allow(Transition.Over, State.Thinking, State.Over);
    }

    /**
     * Start the game.
     */
    public void start() {
        state.transition(Transition.Start);

        checkTermination();        
    }

    /**
     * Gets the instance of the StateMachine.
     */
    public StateMachine state() {
        return state;
    }

    /**
     * Determines if the current game state matches the given state.
     */
    public boolean isState(State state) {
        return ((State) this.state.current()) == state;
    }

    /**
     * Push a move to the current position. It is not checked whether the move 
     * is legal or not. So it is the responsibility of the dependor to pass 
     * only legal moves.
     */
    public boolean push(Move move) {
        if(isState(State.Pending) || isState(State.Over)) return false;

        position.push(move);
        state.transition(Transition.Move);

        checkTermination();

        return true;
    }

    /**
     * Check if the game has ended.
     */
    protected void checkTermination() {
        if(outcome().termination != null) state.transition(Transition.Over);
    }

    /**
     * Resign the game.
     */
    public void resign() {
        resigned = true;
        state.transition(Transition.Resign);
    }

    /**
     * Gets and instance of the outcome of the game.
     */
    public Outcome outcome() {
        if(resigned) {
            return new Outcome(Termination.RESIGNED, position.themColor());
        }

        if(position.isCheckmate()) {
            return new Outcome(Termination.CHECKMATE, position.themColor());
        }

        if(position.isStalemate()) {
            return new Outcome(Termination.STALEMATE, position.themColor());
        }

        if(position.isInsufficientMaterial()) {
            return new Outcome(Termination.INSUFFICIENT_MATERIAL, Piece.Color.NONE);
        }
        
        return new Outcome(null, Piece.Color.NONE);
    }

    /**
     * Determines whether a player can resign.
     * 
     * A player can resign when the first move was made.
     */
    public boolean canResign() {
        return position.moves[0] != null;
    }
}