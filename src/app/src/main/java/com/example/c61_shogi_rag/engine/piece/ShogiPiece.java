package com.example.c61_shogi_rag.engine.piece;

import com.example.c61_shogi_rag.engine.game.Board;

public abstract class ShogiPiece {
    private final byte ID;  // ID neg
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
    protected boolean checkSteps(int deltaX, int deltaY){
        int tempX = 0, tempY = 0;
        for (int[] direction : DIRECTIONS) {
            for (int j = 0; j < direction.length; j++) {
                if (j == 0) {
                    tempX = direction[j];
                } else if (j == 1) {
                    tempY = direction[j];
                }
            }
            if (deltaY == tempX && deltaX == tempY) {
                return true;
            }
        }
        return false;
    }

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
