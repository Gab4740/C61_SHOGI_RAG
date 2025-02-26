package com.example.c61_shogi_rag.piece;

public class Move {
    private final Position currentPosition;
    private final Position nextPosition;

    public Move(Position current, Position next){
        currentPosition = current;
        nextPosition = next;
    }

    public Position getCurrentPosition() {
        return currentPosition;
    }
    public Position getNextPosition() {
        return nextPosition;
    }
}
