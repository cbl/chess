package com.github.cbl.chess.chess;

import com.github.cbl.chess.util.StateMachine;
import com.github.cbl.chess.util.Observer;
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

    private class StateObserver extends Observer
    {
        public void handle(Object event, Object value)
        {
            if(event == StateMachine.Event.Transition) {
                System.out.println("LOLOL");
            }
        }
    }

    private StateMachine state = new StateMachine(State.Pending);
    private Position position;
    public MoveList moves;

    public GameOfChess(Position pos) {
        this.position = pos;

        this.state.allow(Transition.Start, State.Pending, State.Waiting);
        this.state.allow(Transition.Move, State.Waiting, State.Thinking);
        this.state.allow(Transition.Move, State.Thinking, State.Thinking);
        this.state.allow(Transition.Resign, State.Thinking, State.Over);
        this.state.allow(Transition.TimeExpired, State.Thinking, State.Over);
        this.state.allow(Transition.Cancel, State.Waiting, State.Over);

        this.state.addObserver(new StateObserver());        
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
        return true;
    }
}