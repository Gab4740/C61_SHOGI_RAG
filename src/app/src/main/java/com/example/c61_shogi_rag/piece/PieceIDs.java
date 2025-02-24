package com.example.c61_shogi_rag.piece;

public enum PieceIDs{
    Roi(127),
    Char(9),
    Fou(8),
    GeneralOr(6),
    GeneralArgent(5),
    Chevalier(4),
    Lance(3),
    Pion(1),
    RoiDragon(11),
    ChevalDragon(10);

    private final int value;
    PieceIDs(int value) {
        this.value = value;
    }
    public int getValue() {
        return this.value;
    }
}