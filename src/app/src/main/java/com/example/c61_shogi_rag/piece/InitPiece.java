package com.example.c61_shogi_rag.piece;

import android.widget.Switch;

import java.util.Objects;

public class InitPiece {


    /**
     * Méthode pour créer les pièces nécéssaires au jeu de shogi :
     *
     * { Roi, Char, Fou, GeneralOr, GeneralArgent, Chevalier, Lance, Pion, RoiDragon, ChevalDragon } -->
     * { Noir, Blanc}
     *
     * @param piece       Le nom de la pièce + Couleur -> { PIECE_COULEUR }, sans espace
     **/
    public static Piece create(String piece, int[][] movesRule){
        int piece_id = 0;
        int image_id = 0;

        String[] piece_attribus = piece.toLowerCase().split("_");
        boolean color = Objects.equals(piece_attribus[1], "blanc");
        String composed = piece_attribus[0] + "_" + piece_attribus[1];
        String pieceName = piece_attribus[0];

        int adjust = 1;
        int inverse = -1;
        switch(pieceName){
            case "roi" :             piece_id = PieceIDs.Roi.getValue() * (color ? adjust : inverse);           break;
            case "char" :            piece_id = PieceIDs.Char.getValue() * (color ? adjust : inverse);          break;
            case "fou" :             piece_id = PieceIDs.Fou.getValue() * (color ? adjust : inverse);           break;
            case "generalor" :       piece_id = PieceIDs.GeneralOr.getValue() * (color ? adjust : inverse);     break;
            case "generalargent" :   piece_id = PieceIDs.GeneralArgent.getValue() * (color ? adjust : inverse); break;
            case "chevalier" :       piece_id = PieceIDs.Chevalier.getValue() * (color ? adjust : inverse);     break;
            case "lance" :           piece_id = PieceIDs.Lance.getValue() * (color ? adjust : inverse);         break;
            case "pion" :            piece_id = PieceIDs.Pion.getValue() * (color ? adjust : inverse);          break;
            case "roidragon" :       piece_id = PieceIDs.RoiDragon.getValue() * (color ? adjust : inverse);     break;
            case "chevalierdragon" : piece_id = PieceIDs.ChevalDragon.getValue() * (color ? adjust : inverse);  break;
            default:                 composed = "NULL"; movesRule = null;                                       break;
        }
        return new Piece((byte)piece_id, image_id, composed, movesRule);
    }
}