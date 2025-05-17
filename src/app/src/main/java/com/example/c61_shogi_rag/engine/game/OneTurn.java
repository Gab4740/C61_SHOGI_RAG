package com.example.c61_shogi_rag.engine.game;

import com.example.c61_shogi_rag.engine.piece.Position;

public class OneTurn {
    private byte piece_jouer;
    private Position old_pos;
    private Position new_pos;
    private boolean parachute;

    public OneTurn(byte piece_jouer, Position old_pos, Position new_pos, boolean parachute) {
        this.piece_jouer = piece_jouer;
        this.old_pos = old_pos;
        this.new_pos = new_pos;
        this.parachute = parachute;
    }

    public void setPiece_jouer(byte piece_jouer) {
        this.piece_jouer = piece_jouer;
    }

    public void setOld_pos(Position old_pos) {
        this.old_pos = old_pos;
    }

    public void setNew_pos(Position new_pos) {
        this.new_pos = new_pos;
    }

    public void setParachute(boolean parachute) {
        this.parachute = parachute;
    }

    public byte getPiece_jouer() {
        return piece_jouer;
    }

    public Position getOld_pos() {
        return old_pos;
    }

    public Position getNew_pos() {
        return new_pos;
    }

    public boolean isParachute() {
        return parachute;
    }
}
