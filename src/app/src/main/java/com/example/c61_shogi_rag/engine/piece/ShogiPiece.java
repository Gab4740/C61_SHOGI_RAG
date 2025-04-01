package com.example.c61_shogi_rag.engine.piece;

import com.example.c61_shogi_rag.engine.game.Board;

public abstract class ShogiPiece {
    private final byte ID;  // ID negatif = Noir, ID positif = Blanc
    private final byte ID_PROMU;
    private final int IMAGE_ID;
    private final int IMAGE_ID_PROMU;
    private final String NOM;
    private final int[][] DIRECTIONS;

    public ShogiPiece(byte id, byte idPromu, int imageId, int imageIdPromu, String nom, int[][] directions) {
        ID = id;
        ID_PROMU = idPromu;
        IMAGE_ID = imageId;
        IMAGE_ID_PROMU = imageIdPromu;
        NOM = nom;
        DIRECTIONS = directions;
    }

    public abstract boolean isValidMove(Move move, Board board);

    public byte getID() {
        return ID;
    }

    public byte getID_PROMU() {
        return ID_PROMU;
    }

    public int getIMAGE_ID() {
        return IMAGE_ID;
    }

    public int getIMAGE_ID_PROMU() {
        return IMAGE_ID_PROMU;
    }

    public String getNOM() {
        return NOM;
    }

    public int[][] getDIRECTIONS() {
        return DIRECTIONS;
    }
}
