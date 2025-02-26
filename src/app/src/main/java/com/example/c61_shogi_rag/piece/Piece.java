package com.example.c61_shogi_rag.piece;

public class Piece {
    private final byte ID;  // ID negatif = Noir, ID positif = Blanc
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
    public int getIMAGE_ID() { return IMAGE_ID; }
    public String getNom() { return nom; }
    public int[][] getDirections() { return directions; }
    public boolean isValidMove(){

        // TODO

        return true;
    }
}
