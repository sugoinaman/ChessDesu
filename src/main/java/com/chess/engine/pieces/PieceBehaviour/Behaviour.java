package com.chess.engine.pieces.PieceBehaviour;

public interface Behaviour {
    public abstract int getDirection();

    public abstract boolean isBlack();
    public abstract boolean isWhite();
}
