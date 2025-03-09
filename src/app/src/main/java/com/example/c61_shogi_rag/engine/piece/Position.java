package com.example.c61_shogi_rag.engine.piece;

public class Position {
    private final int posX;
    private final int posY;

    public Position(int x, int y){
        posX = x;
        posY = y;
    }

    public int getPosX() {
        return posX;
    }
    public int getPosY() {
        return posY;
    }
    public int[] getPos(){
        int []temp = new int[2];
        temp[0] = posX;
        temp[1] = posY;
        return temp;
    }
}
