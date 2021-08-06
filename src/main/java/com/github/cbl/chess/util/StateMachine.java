package com.github.cbl.chess.util;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class StateMachine
{
    private Map<Object, Map<Object, Object>> transitions = new HashMap<Object, Map<Object, Object>>();
    private List<Observer> observer = new ArrayList<Observer>();
    private Object state;

    public enum Event {
        Transition,
        TransitionedFrom,
        TransitionedTo
    }

    /**
     * Create a new StateMachine instance.
     */
    public StateMachine(Object initialState) {
        this.state = initialState;
    }

    /**
     * Add an observer that should listen on state and transition events.
     */
    public void addObserver(Observer observer) {
        this.observer.add(observer);
    }

    /**
     * Get the current state.
     */
    public Object current() {
        return this.state;
    }

    /**
     * Execute a transition.
     */
    public boolean transition(Object transition) {
        if(! this.transitions.containsKey(transition)) {
            return false;
        }

        if(! this.transitions.get(transition).containsKey(this.state)) {
            return false;
        }

        Object from = this.state;
        this.state = this.transitions.get(transition).get(this.state);
        this.fireEvents(transition, from, this.state);

        return true;
    }

    /**
     * Allow a transition.
     */
    public StateMachine allow(Object transition, Object from, Object to)
    {
        if(! this.transitions.containsKey(transition)) {
            this.transitions.put(transition, new HashMap<Object, Object>());
        }
        
        this.transitions.get(transition).put(from, to);

        return this;
    }

    /**
     * Fire events for a transition.
     */
    private void fireEvents(Object transition, Object from, Object to) {
        for(Observer observer : this.observer) {
            observer.handle(Event.Transition, transition);
            observer.handle(Event.TransitionedFrom, from);
            observer.handle(Event.TransitionedTo, to);
        }
    }
}
