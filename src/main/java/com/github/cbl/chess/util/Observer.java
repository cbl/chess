package com.github.cbl.chess.util;

public abstract class Observer
{
    abstract public void handle(Object event, Object value);
}