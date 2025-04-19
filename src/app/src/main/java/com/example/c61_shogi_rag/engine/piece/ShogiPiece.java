package com.example.c61_shogi_rag.engine.piece;

import com.example.c61_shogi_rag.engine.game.Board;

public abstract class ShogiPiece {
    private final byte ID;  // ID negatif = Noir, ID positif = Blanc
    private final byte ID_PROMU;
    private final int IMAGE_ID;
    private final int IMAGE_ID_PROMU;
    private final String NOM;
    private final int[][] DIRECTIONS;
    protected int currX = 0;
    protected int currY = 0;
    protected int finalX = 0;
    protected int finalY = 0;

    public ShogiPiece(byte id, byte idPromu, int imageId, int imageIdPromu, String nom, int[][] directions) {
        ID = id;
        ID_PROMU = idPromu;
        IMAGE_ID = imageId;
        IMAGE_ID_PROMU = imageIdPromu;
        NOM = nom;
        DIRECTIONS = directions;
    }

    protected boolean defaultCheck(Move move){
        if (move == null || move.getCurrentPosition() == null || move.getNextPosition() == null) {
            return false;
        }

        int currX = move.getCurrentPosition().getPosX();
        int currY = move.getCurrentPosition().getPosY();
        int finalX = move.getNextPosition().getPosX();
        int finalY = move.getNextPosition().getPosY();

        if (isInBounds(currX, currY) && isInBounds(finalX, finalY)) {
            this.currX = currX;
            this.currY = currY;
            this.finalX = finalX;
            this.finalY = finalY;
            return true;
        }
        return false;
    };
    private boolean isInBounds(int x, int y) {
        return x >= 0 && x < 9 && y >= 0 && y < 9;
    }
    protected boolean secondaryStep(Board board){
        boolean pieceColor = board.getPieceAt(new Position(currX, currY)) > 0;
        boolean targetPieceColor = board.getPieceAt(new Position(finalX, finalY)) > 0;
        if(finalX < 0 || finalY < 0 ){
            System.out.println(finalX + " " + finalY);
        }
        return pieceColor != targetPieceColor || board.getPieceAt(new Position(finalX, finalY)) == 0;
    }
    protected abstract boolean allMoveChecks(Move move, Board board);

    public boolean isValidMove(Move move, Board board){
        return allMoveChecks(move, board);
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
