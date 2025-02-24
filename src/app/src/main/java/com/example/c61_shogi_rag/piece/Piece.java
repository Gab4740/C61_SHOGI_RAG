package com.example.c61_shogi_rag.piece;

public class Piece {
    private final byte ID;
    private final int IMAGE_ID;
    private final String nom;
    private final int[][] directions;

    public Piece(byte id, int imageId, String nom, int[][] directions) {
        ID = id;
        IMAGE_ID = imageId;
        this.nom = nom;
        this.directions = directions;
    }

    public byte getID() {
        return ID;
    }
}
