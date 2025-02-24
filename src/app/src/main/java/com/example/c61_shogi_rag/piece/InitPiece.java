package com.example.c61_shogi_rag.piece;

public class InitPiece {
    /**
     * Méthode pour créer les pièces nécéssaires au jeu de shogi
     *
     * @param piece       Le nom de la pièce + Couleur -> { PIECE_COULEUR }, sans espace
     * */
    public static Piece init(String piece){
        byte piece_id = 0;
        int image_id = 0;
        String piece_nom = "";
        int[][] directions = new int[1][1];

        String[] piece_attribus = piece.split("_");


        // TODO


        return new Piece(piece_id, image_id, piece_nom, directions);
    }
}
