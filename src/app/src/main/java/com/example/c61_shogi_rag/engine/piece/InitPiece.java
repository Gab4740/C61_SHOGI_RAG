package com.example.c61_shogi_rag.engine.piece;

import com.example.c61_shogi_rag.R;

import java.util.Objects;

public class  InitPiece {
    /**
     * Méthode pour créer les pièces nécéssaires au jeu de shogi :
     *
     * { RoiSente, RoiGote, Char, Fou, GeneralOr, GeneralArgent, Chevalier, Lance, Pion, RoiDragon, ChevalDragon } -->
     * { Noir, Blanc}
     *
     * @param piece       Le nom de la pièce + Couleur -> { PIECE_COULEUR }, sans espace
     **/
    public static Piece create(String piece){
        int piece_id = 0;
        int image_id = 0;
        int[][] mR;

        String[] piece_attribus = piece.toLowerCase().split("_");
        boolean color = Objects.equals(piece_attribus[1], "blanc");
        String pieceName = piece_attribus[0];

        int adjust = 1;
        int inverse = -1;
        switch(pieceName){
            case "roisente" :
                piece_id = PieceIDs.Roi.getValue() * (color ? adjust : inverse);
                mR = MoveSet.Roi.getMoveSet();
                image_id = color ? R.drawable.sente_king_0 :  R.drawable.sente_king_1;
                break;

            case "roigote" :
                piece_id = PieceIDs.Roi.getValue() * (color ? adjust : inverse);
                mR = MoveSet.Roi.getMoveSet();
                image_id = color ? R.drawable.gote_king_0 :  R.drawable.gote_king_1;
                break;

            case "char" :
                piece_id = PieceIDs.Char.getValue() * (color ? adjust : inverse);
                mR = MoveSet.Char.getMoveSet();
                image_id = color ? R.drawable.rook_0 :  R.drawable.rook_1;
                break;

            case "fou" :
                piece_id = PieceIDs.Fou.getValue() * (color ? adjust : inverse);
                mR = MoveSet.Fou.getMoveSet();
                image_id = color ? R.drawable.bishop_0 :  R.drawable.bishop_1;
                break;

            case "generalor" :
                piece_id = PieceIDs.GeneralOr.getValue() * (color ? adjust : inverse);
                mR = (piece_id > 0 ? MoveSet.GeneralOrBlanc.getMoveSet() : MoveSet.GeneralOrNoir.getMoveSet());
                image_id = color ? R.drawable.gold_0 :  R.drawable.gold_1;
                break;

            case "generalargent" :
                piece_id = PieceIDs.GeneralArgent.getValue() * (color ? adjust : inverse);
                mR = (piece_id > 0 ? MoveSet.GeneralArgentBlanc.getMoveSet() : MoveSet.GeneralArgentNoir.getMoveSet());
                image_id = color ? R.drawable.silver_0 :  R.drawable.silver_1;
                break;

            case "chevalier" :
                piece_id = PieceIDs.Chevalier.getValue() * (color ? adjust : inverse);
                mR = (piece_id > 0 ? MoveSet.ChevalierBlanc.getMoveSet() : MoveSet.ChevalierNoir.getMoveSet());
                image_id = color ? R.drawable.knight_0 :  R.drawable.knight_1;
                break;

            case "lance" :
                piece_id = PieceIDs.Lance.getValue() * (color ? adjust : inverse);
                mR = (piece_id > 0 ? MoveSet.LanceBlanc.getMoveSet() : MoveSet.LanceNoir.getMoveSet());
                image_id = color ? R.drawable.lance_0 :  R.drawable.lance_1;
                break;

            case "pion" :
                piece_id = PieceIDs.Pion.getValue() * (color ? adjust : inverse);
                mR = (piece_id > 0 ? MoveSet.PionBlanc.getMoveSet() : MoveSet.PionNoir.getMoveSet());
                image_id = color ? R.drawable.pawn_0 :  R.drawable.pawn_1;
                break;

            case "roidragon" :
                piece_id = PieceIDs.RoiDragon.getValue() * (color ? adjust : inverse);
                mR = MoveSet.RoiDragon.getMoveSet();
                image_id = color ? R.drawable.p_rook_0 :  R.drawable.p_rook_1;
                break;

            case "chevalierdragon" :
                piece_id = PieceIDs.ChevalDragon.getValue() * (color ? adjust : inverse);
                mR = MoveSet.ChevalDragon.getMoveSet();
                image_id = color ? R.drawable.p_knight_0 :  R.drawable.p_knight_1;
                break;

            default:
                System.out.println("Invalid piece: " + piece);
                throw new IllegalArgumentException("Unknown piece type: " + piece);
        }
        return new Piece((byte)piece_id, image_id, pieceName, mR);
    }
}