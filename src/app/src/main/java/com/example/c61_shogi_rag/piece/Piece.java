package com.example.c61_shogi_rag.piece;

public class Piece {
    private final byte ID;  // ID negatif = Noir, ID positif = Blanc
    private final int IMAGE_ID;
    private final String nom;
    private final int[][] directions;

    public Piece(byte id, int imageId, String nom, int[][] directions) {
        this.ID = id;
        this.IMAGE_ID = imageId;
        this.nom = nom;
        this.directions = directions;
    }

    public byte getID() {
        return ID;
    }
    public int getIMAGE_ID() { return IMAGE_ID; }
    public String getNom() { return nom; }
    public int[][] getDirections() { return directions; }
    public boolean isValidMove(Move move){
        int currentX = move.getCurrentPosition().getPosX();
        int currentY = move.getCurrentPosition().getPosY();
        int nextX = move.getNextPosition().getPosX();
        int nextY = move.getNextPosition().getPosY();

        int deltaX = currentX - nextX;
        int deltaY = currentY - nextY;

        int tempX = 0, tempY = 0;
        for (int[] direction : directions) {
            for (int j = 0; j < direction.length; j++) {
                if (j == 0) {
                    tempX = direction[j];
                } else if (j == 1) {
                    tempY = direction[j];
                }
            }
            if (deltaX == tempX && deltaY == tempY) {
                return true;
            }
        }
        return false;
    }
}
