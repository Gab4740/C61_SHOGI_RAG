package com.example.c61_shogi_rag.engine.piece;

import com.example.c61_shogi_rag.engine.game.Board;

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

    /***
     * Retourne un tableau de positions qui représente tous les deplacements qu'une pièce peux effectuer
     * selon l'état actuelle de la partie
     *
     * @param board : État actuelle de la partie
     */
    public Position[] getAllPossibleMoves(Board board){
        // TODO
        return new Position[0];
    }

    /**
     * Recoit un deplacement (move) et le board et vérifie a partir du tableau de directions de la
     * pièce si le deplacement est valide
     *
     * @param move  : Deplacement de la pièce
     * @param board : L'échiquier de la partie au moment du deplacement
     * */
    public boolean isValidMove(Move move, Board board){
        int currX = move.getCurrentPosition().getPosX();
        int currY = move.getCurrentPosition().getPosY();
        int finalX = move.getNextPosition().getPosX();
        int finalY = move.getNextPosition().getPosY();

        int deltaX = currX - finalX;
        int deltaY = currY - finalY;

        int displacementX = 0;
        int displacementY = 0;

        switch(this.nom){
            case "lance":
                displacementY = this.ID > 0 ? 1 : -1; // Noir ou Blanc : Directionnel
                while(currY != finalY){
                    int previousY = currY;
                    currY += displacementY ;
                    if(!checkSteps(deltaX, (currY - previousY)) || board.getBoard()[currX][currY] != 0){
                        return false;
                    }
                }
                return true;
            case "char":
                return checkCross(finalX, finalY, currX, currY, board);
            case "fou":
                return checkDiagonals(finalX, finalY, currX, currY, board);
            case "roidragon":
                if(deltaY > 1 || deltaX > 1){ return checkCross(finalX, finalY, currX, currY, board); }
                else{ return checkSteps(deltaX, deltaY); }
            case "chevalierdragon":
                if(deltaX > 1 && deltaY > 1){ return checkDiagonals(finalX, finalY, currX, currY, board); }
                else{ return checkSteps(deltaX, deltaY); }
            default:
                return (checkSteps(deltaX, deltaY) && board.getBoard()[finalX][finalY] == 0);
        }
    }
    private boolean checkCross(int finalX, int finalY, int currX, int currY, Board board){
        int displacementXDragon = (finalX != currX) ? (finalX > currX ? 1 : -1) : 0;
        int displacementYDragon = (finalY != currY) ? (finalY > currY ? 1 : -1) : 0;

        while (currX != finalX || currY != finalY) {
            int previousX = currX;
            int previousY = currY;
            currX += displacementXDragon;
            currY += displacementYDragon;

            if ((!checkSteps((currX - previousX), (currY - previousY))) || board.getBoard()[currX][currY] != 0) {
                return false;
            }
        }
        return true;
    }
    private boolean checkDiagonals(int finalX, int finalY, int currX, int currY, Board board){
        int displacementX = finalX > currX ? 1 : -1;
        int displacementY = finalY > currY ? 1 : -1;

        while(currX != finalX){
            int previousX = currX;
            int previousY = currY;
            currX += displacementX;
            currY += displacementY;

            if ((!checkSteps((currX - previousX), (currY - previousY))) || board.getBoard()[currX][currY] != 0) {
                return false;
            }
        }
        return true;
    }
    private boolean checkSteps(int deltaX, int deltaY){
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