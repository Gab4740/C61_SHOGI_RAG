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

    /**
     * Lookup table des mouvements valide de tous les pièce du jeu sous forme de tableau 2D : int[][]
     * */
    public enum pieceMoveSet{
        Roi(new int[][]{
                {1, 0}, {0, 1}, {-1, 0}, {0, -1},   // Gauche, Droite, Haut, Bas
                {1, 1}, {1, -1}, {-1, 1}, {-1, -1}  // Toutes les Diagonales
        }),
        Char(new int[][]{
                {-1, 0}, {1, 0},            // Gauche, Droite
                {0, -1}, {0, 1}             // Haut, Base
        }),
        Fou(new int[][]{
                {-1, -1}, {1, 1},
                {-1, 1}, {1, -1}            // Toutes les Diagonales
        }),
        GeneralOrBlanc(new int[][]{         // Directionnel vers le haut
                {-1, 0}, {1, 0},            // Gauche, Droite
                {0, -1}, {0, 1},            // Haut, Bas
                {-1, 1}, {1, 1}             // Diagonales vers le haut
        }),
        GeneralOrNoir(new int[][]{          // Directionnel vers les bas
                {-1, 0}, {1, 0},            // Gauche, Droite
                {0, -1}, {0, 1},            // Haut, Bas
                {-1, -1}, {1, -1}           // Diagonales vers le bas
        }),
        GeneralArgentBlanc(new int[][]{     // Directionnel ver les Haut -> Promotion : GeneralOrBlanc
                {-1, -1}, {1, 1},           //
                {-1, 1}, {1, -1},           // Toutes les diagonales
                {0, 1},                     // Tout droit ver le haut
        }),
        GeneralArgentNoir(new int[][]{      // Directionnel ver les bas -> Promotion : GeneralOrNoir
                {-1, -1}, {1, 1},           //
                {-1, 1}, {1, -1},           // Toutes les diagonales
                {0, -1},                    // Tout droit ver le bas
        }),
        ChevalierBlanc(new int[][]{         // Directionnel ver les Haut  -> Promotion : GeneralOrBlanc
                {-1, 2}, {1, 2},            // 2 vers le haut, 1 gauche ou droite
        }),
        ChevalierNoir(new int[][]{          // Directionnel ver les Bas -> Promotion : GeneralOrNoir
                {-1, -2}, {1, -2},          // 2 vers le Bas, 1 gauche ou droite
        }),
        LanceBlanc(new int[][]{             // Directionnel ver les Haut   -> Promotion : GeneralOrBlanc
                {0, 1},                     // Tout droit vers le haut
        }),
        LanceNoir(new int[][]{              // Directionnel ver les Bas  -> Promotion : GeneralOrNoir
                {0, -1},                    // Tout droit vers le bas
        }),
        RoiDragon(new int[][]{
                {-1, 0}, {1, 0},            // Gauche, Droite
                {0, -1}, {0, 1},            // Haut, Base
                {1, 0}, {0, 1},
                {-1, 0}, {0, -1}            // Gauche, Droite, Haut, Bas
        }),
        ChevalDragon(new int[][]{
                {-1, -1}, {1, 1},
                {-1, 1}, {1, -1},           // Toutes les Diagonales
                {1, 0}, {0, 1},
                {-1, 0}, {0, -1}            // Gauche, Droite, Haut, Bas
        });

        private final int[][] moveSet;
        pieceMoveSet(int[][] moveSet) {
            this.moveSet = moveSet;
        }
        public int[][] getMoveSet() {
            return this.moveSet;
        }
    }
}