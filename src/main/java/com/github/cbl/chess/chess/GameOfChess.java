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
        CHECKMATE, STALEMATE, INSUFFICIENT_MATERIAL, THREEFOLD_REPETITION, RESIGNED
    }

    public class Outcome {
        public Termination termination;
        public int winner = Piece.Color.NONE;
        Outcome(Termination termination, int winner) {
            this.termination = termination;
            this.winner = winner;
        }
    }

    private StateMachine state = new StateMachine(State.Pending);
    public Position position;
    public MoveList moves;
    private boolean resigned = false;

    public GameOfChess(Position pos) {
        position = pos;

        state.allow(Transition.Start, State.Pending, State.Waiting);
        state.allow(Transition.Move, State.Waiting, State.Thinking);
        state.allow(Transition.Move, State.Thinking, State.Thinking);
        state.allow(Transition.Resign, State.Thinking, State.Over);
        state.allow(Transition.Cancel, State.Waiting, State.Over);
        state.allow(Transition.Over, State.Thinking, State.Over);
    }

    public boolean start() {
        return state.transition(Transition.Start);
    }

    public StateMachine state() {
        return state;
    }

    public boolean isState(State state) {
        return ((State) this.state.current()) == state;
    }

    public boolean push(Move move) {
        position.push(move);
        state.transition(Transition.Move);

        // Check if the game has ended.
        if(outcome().termination != null) 
            state.transition(Transition.Over);

        return true;
    }

    public void resign() {
        resigned = true;
        state.transition(Transition.Resign);
    }

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
}