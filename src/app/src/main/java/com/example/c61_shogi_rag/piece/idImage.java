package com.example.c61_shogi_rag.piece;

public class idImage {
    public enum ImageIDs{
        pion(1),
        lance(2),
        cheval(3),
        generalArgent(4),
        generalOr(5),
        fou(6),
        tour(7),
        roiDragon(8),
        chevalDragon(9),
        generalPromu(10),
        chevalPromu(11),
        lancePromu(12),
        pionPromu(13),
        roiBlanc(14),
        roiNoir(15);

        private final int value;
        ImageIDs(int value) {
            this.value = value;
        }
        public int getValue() {
            return this.value;
        }
    }
}
