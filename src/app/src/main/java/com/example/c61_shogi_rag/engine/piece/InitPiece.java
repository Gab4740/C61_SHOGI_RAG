package com.example.c61_shogi_rag.engine.piece;

import com.example.c61_shogi_rag.R;
import com.example.c61_shogi_rag.engine.piece.ShogiPieces.Charriot;
import com.example.c61_shogi_rag.engine.piece.ShogiPieces.Chevalier;
import com.example.c61_shogi_rag.engine.piece.ShogiPieces.ChevalierDragon;
import com.example.c61_shogi_rag.engine.piece.ShogiPieces.Fou;
import com.example.c61_shogi_rag.engine.piece.ShogiPieces.GeneralArgent;
import com.example.c61_shogi_rag.engine.piece.ShogiPieces.GeneralOr;
import com.example.c61_shogi_rag.engine.piece.ShogiPieces.Lance;
import com.example.c61_shogi_rag.engine.piece.ShogiPieces.Pion;
import com.example.c61_shogi_rag.engine.piece.ShogiPieces.RoiDragon;
import com.example.c61_shogi_rag.engine.piece.ShogiPieces.RoiGote;
import com.example.c61_shogi_rag.engine.piece.ShogiPieces.RoiSente;

import java.util.Objects;
/**
 * Nom du fichier : InitPiece.java
 * Description : Ce fichier définit une classe permettant d'initialiser les pièces du jeu de Shogi,
 *               facilitant leur création avec les attributs appropriés (ID, images, déplacements, promotions).
 * Auteur(s) : [Gabriel Veilleux]
 * Entête générée par Copilot
 */

public class  InitPiece {
    /**
     * Méthode pour créer les pièces nécéssaires au jeu de shogi :
     *
     * { RoiSente, RoiGote, Char, Fou, GeneralOr, GeneralArgent, Chevalier, Lance, Pion, RoiDragon, ChevalDragon } -->
     * { Noir, Blanc}
     *
     * @param piece       Le nom de la pièce + Couleur -> { PIECE_COULEUR }, sans espace
     **/
    public static ShogiPiece create(String piece){
        int piece_id = 0;
        int promu_id = 0;
        int image_id = 0;
        int image_id_promu = 0;
        int[][] mR;

        String[] piece_attribus = piece.toLowerCase().split("_");
        boolean color = Objects.equals(piece_attribus[1], "blanc");
        String pieceName = piece_attribus[0];

        int adjust = 1;
        int inverse = -1;

        switch(pieceName){
            case "roisente" :
                piece_id = PieceIDs.Roi.getValue() * (color ? adjust : inverse);
                promu_id = PieceIDs.Roi.getValue() * (color ? adjust : inverse);
                image_id = color ? R.drawable.sente_king_0 :  R.drawable.sente_king_1;
                image_id_promu = color ? R.drawable.sente_king_0 :  R.drawable.sente_king_1;
                mR = MoveSet.Roi.getMoveSet();
                return new RoiSente((byte)piece_id, (byte)promu_id, image_id, image_id_promu, pieceName, mR);

            case "roigote" :
                piece_id = PieceIDs.Roi.getValue() * (color ? adjust : inverse);
                promu_id = PieceIDs.Roi.getValue() * (color ? adjust : inverse);
                image_id = color ? R.drawable.gote_king_0 :  R.drawable.gote_king_1;
                image_id_promu = color ? R.drawable.gote_king_0 :  R.drawable.gote_king_1;
                mR = MoveSet.Roi.getMoveSet();
                return new RoiGote((byte)piece_id, (byte)promu_id, image_id, image_id_promu, pieceName, mR);

            case "char" :
                piece_id = PieceIDs.Char.getValue() * (color ? adjust : inverse);
                promu_id = PieceIDs.RoiDragon.getValue() * (color ? adjust : inverse);
                image_id = color ? R.drawable.rook_0 : R.drawable.rook_1;
                image_id_promu = color ? R.drawable.p_rook_0 : R.drawable.p_rook_1;
                mR = MoveSet.Char.getMoveSet();
                return new Charriot((byte)piece_id, (byte)promu_id, image_id, image_id_promu, pieceName, mR);

            case "fou" :
                piece_id = PieceIDs.Fou.getValue() * (color ? adjust : inverse);
                promu_id = PieceIDs.ChevalDragon.getValue() * (color ? adjust : inverse);
                image_id = color ? R.drawable.bishop_0 :  R.drawable.bishop_1;
                image_id_promu = color ? R.drawable.p_bishop_0 : R.drawable.p_bishop_1;
                mR = MoveSet.Fou.getMoveSet();
                return new Fou((byte)piece_id, (byte)promu_id, image_id, image_id_promu, pieceName, mR);

            case "generalor" :
                piece_id = PieceIDs.GeneralOr.getValue() * (color ? adjust : inverse);
                promu_id = PieceIDs.GeneralOr.getValue() * (color ? adjust : inverse);
                image_id = color ? R.drawable.gold_0 :  R.drawable.gold_1;
                image_id_promu = color ? R.drawable.p_gold_0 : R.drawable.p_gold_1;
                mR = (piece_id > 0 ? MoveSet.GeneralOrBlanc.getMoveSet() : MoveSet.GeneralOrNoir.getMoveSet());
                return new GeneralOr((byte)piece_id, (byte)promu_id, image_id, image_id_promu, pieceName, mR);

            case "generalargent" :
                piece_id = PieceIDs.GeneralArgent.getValue() * (color ? adjust : inverse);
                promu_id = PieceIDs.GeneralOr.getValue() * (color ? adjust : inverse);
                image_id = color ? R.drawable.silver_0 :  R.drawable.silver_1;
                image_id_promu = color ? R.drawable.p_gold_0 : R.drawable.p_gold_1;
                mR = (piece_id > 0 ? MoveSet.GeneralArgentBlanc.getMoveSet() : MoveSet.GeneralArgentNoir.getMoveSet());
                return new GeneralArgent((byte)piece_id, (byte)promu_id, image_id, image_id_promu, pieceName, mR);

            case "chevalier" :
                piece_id = PieceIDs.Chevalier.getValue() * (color ? adjust : inverse);
                promu_id = PieceIDs.GeneralOr.getValue() * (color ? adjust : inverse);
                image_id = color ? R.drawable.knight_0 :  R.drawable.knight_1;
                image_id_promu = color ? R.drawable.p_knight_0 : R.drawable.p_knight_1;
                mR = (piece_id > 0 ? MoveSet.ChevalierBlanc.getMoveSet() : MoveSet.ChevalierNoir.getMoveSet());
                return new Chevalier((byte)piece_id, (byte)promu_id, image_id, image_id_promu, pieceName, mR);

            case "lance" :
                piece_id = PieceIDs.Lance.getValue() * (color ? adjust : inverse);
                promu_id = PieceIDs.GeneralOr.getValue() * (color ? adjust : inverse);
                image_id = color ? R.drawable.lance_0 :  R.drawable.lance_1;
                image_id_promu = color ? R.drawable.p_lance_0 : R.drawable.p_lance_1;
                mR = (piece_id > 0 ? MoveSet.LanceBlanc.getMoveSet() : MoveSet.LanceNoir.getMoveSet());
                return new Lance((byte)piece_id, (byte)promu_id, image_id, image_id_promu, pieceName, mR);

            case "pion" :
                piece_id = PieceIDs.Pion.getValue() * (color ? adjust : inverse);
                promu_id = PieceIDs.GeneralOr.getValue() * (color ? adjust : inverse);
                image_id = color ? R.drawable.pawn_0 :  R.drawable.pawn_1;
                image_id_promu = color ? R.drawable.p_pawn_0 : R.drawable.p_pawn_1;
                mR = (piece_id > 0 ? MoveSet.PionBlanc.getMoveSet() : MoveSet.PionNoir.getMoveSet());
                return new Pion((byte)piece_id, (byte)promu_id, image_id, image_id_promu, pieceName, mR);

            case "roidragon" :
                piece_id = PieceIDs.RoiDragon.getValue() * (color ? adjust : inverse);
                image_id = color ? R.drawable.p_rook_0 :  R.drawable.p_rook_1;
                mR = MoveSet.RoiDragon.getMoveSet();
                return new RoiDragon((byte)piece_id, (byte)0, image_id, 0, pieceName, mR);

            case "chevalierdragon" :
                piece_id = PieceIDs.ChevalDragon.getValue() * (color ? adjust : inverse);
                image_id = color ? R.drawable.p_knight_0 :  R.drawable.p_knight_1;
                mR = MoveSet.ChevalDragon.getMoveSet();
                return new ChevalierDragon((byte)piece_id, (byte)0, image_id, 0, pieceName, mR);

            default:
                System.out.println("Invalid piece: " + piece);
                throw new IllegalArgumentException("Unknown piece type: " + piece);
        }
    }

    public static Pion GetPion() {
        int piece_id = 0;
        int promu_id = 0;
        int image_id = 0;
        int image_id_promu = 0;
        int[][] mR;

        piece_id = PieceIDs.Pion.getValue();
        promu_id = PieceIDs.GeneralOr.getValue();
        image_id = R.drawable.pawn_0;
        image_id_promu = R.drawable.p_pawn_0;
        mR = (piece_id > 0 ? MoveSet.PionBlanc.getMoveSet() : MoveSet.PionNoir.getMoveSet());
        return new Pion((byte)piece_id, (byte)promu_id, image_id, image_id_promu, "Pion", mR);
    }

    public static Lance GetLance() {
        int piece_id = 0;
        int promu_id = 0;
        int image_id = 0;
        int image_id_promu = 0;
        int[][] mR;

        piece_id = PieceIDs.Lance.getValue();
        promu_id = PieceIDs.GeneralOr.getValue();
        image_id = R.drawable.lance_0;
        image_id_promu = R.drawable.p_lance_0;
        mR = (piece_id > 0 ? MoveSet.PionBlanc.getMoveSet() : MoveSet.PionNoir.getMoveSet());
        return new Lance((byte)piece_id, (byte)promu_id, image_id, image_id_promu, "Lance", mR);
    }
    public static Chevalier GetChevalier() {
        int piece_id = 0;
        int promu_id = 0;
        int image_id = 0;
        int image_id_promu = 0;
        int[][] mR;

        piece_id = PieceIDs.Chevalier.getValue();
        promu_id = PieceIDs.GeneralOr.getValue();
        image_id = R.drawable.knight_0;
        image_id_promu = R.drawable.p_knight_0;
        mR = (piece_id > 0 ? MoveSet.PionBlanc.getMoveSet() : MoveSet.PionNoir.getMoveSet());
        return new Chevalier((byte)piece_id, (byte)promu_id, image_id, image_id_promu, "Chevalier", mR);
    }

    public static GeneralArgent GetGeneralArgent() {
        int piece_id = 0;
        int promu_id = 0;
        int image_id = 0;
        int image_id_promu = 0;
        int[][] mR;

        piece_id = PieceIDs.GeneralArgent.getValue();
        promu_id = PieceIDs.GeneralOr.getValue();
        image_id = R.drawable.silver_0;
        image_id_promu = R.drawable.p_gold_0;
        mR = (piece_id > 0 ? MoveSet.PionBlanc.getMoveSet() : MoveSet.PionNoir.getMoveSet());
        return new GeneralArgent((byte)piece_id, (byte)promu_id, image_id, image_id_promu, "GeneralArgent", mR);
    }

    public static GeneralOr GetGeneralOr() {
        int piece_id = 0;
        int promu_id = 0;
        int image_id = 0;
        int image_id_promu = 0;
        int[][] mR;

        piece_id = PieceIDs.GeneralOr.getValue();
        promu_id = PieceIDs.GeneralOr.getValue();
        image_id = R.drawable.gold_0;
        image_id_promu = R.drawable.p_gold_0;
        mR = (piece_id > 0 ? MoveSet.PionBlanc.getMoveSet() : MoveSet.PionNoir.getMoveSet());
        return new GeneralOr((byte)piece_id, (byte)promu_id, image_id, image_id_promu, "GeneralOr", mR);
    }

    public static Fou GetFou() {
        int piece_id = 0;
        int promu_id = 0;
        int image_id = 0;
        int image_id_promu = 0;
        int[][] mR;

        piece_id = PieceIDs.Fou.getValue();
        promu_id = PieceIDs.ChevalDragon.getValue();
        image_id = R.drawable.bishop_0;
        image_id_promu = R.drawable.p_bishop_0;
        mR = (piece_id > 0 ? MoveSet.PionBlanc.getMoveSet() : MoveSet.PionNoir.getMoveSet());
        return new Fou((byte)piece_id, (byte)promu_id, image_id, image_id_promu, "Fou", mR);
    }

    public static Charriot GetCharriot() {
        int piece_id = 0;
        int promu_id = 0;
        int image_id = 0;
        int image_id_promu = 0;
        int[][] mR;

        piece_id = PieceIDs.Char.getValue();
        promu_id = PieceIDs.RoiDragon.getValue();
        image_id = R.drawable.rook_0;
        image_id_promu = R.drawable.p_rook_0;
        mR = (piece_id > 0 ? MoveSet.PionBlanc.getMoveSet() : MoveSet.PionNoir.getMoveSet());
        return new Charriot((byte)piece_id, (byte)promu_id, image_id, image_id_promu, "Fou", mR);
    }

}
