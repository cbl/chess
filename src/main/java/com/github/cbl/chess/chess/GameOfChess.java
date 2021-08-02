package com.github.cbl.chess.chess;

import com.github.cbl.chess.util.StateMachine;
import com.github.cbl.chess.notations.AlgebraicNotation;

public class GameOfChess
{
    public enum State
    {
        Pending, Waiting, Thinking, Over
    }

    public enum Transition
    {
        Start, Move, Resign, TimeExpired, Cancel
    }

    private StateMachine state = new StateMachine(State.Pending);
    private Position position;

    public GameOfChess(Position pos) {
        this.position = pos;

        this.state.allow(Transition.Start, State.Pending, State.Waiting);
        this.state.allow(Transition.Move, State.Waiting, State.Thinking);
        this.state.allow(Transition.Move, State.Thinking, State.Thinking);
        this.state.allow(Transition.Resign, State.Thinking, State.Over);
        this.state.allow(Transition.TimeExpired, State.Thinking, State.Over);
        this.state.allow(Transition.Cancel, State.Waiting, State.Over);
    }

    public boolean start() {
        return this.state.transition(Transition.Start);
    }

    public StateMachine state() {
        return this.state;
    }

    public boolean isState(State state) {
        return ((State) this.state.current()) == state;
    }

    public boolean push(Move move) {
        this.position.push(move);
        this.state.transition(Transition.Move);
        System.out.println(move.from+"->"+move.to);
        return true;
    }
}