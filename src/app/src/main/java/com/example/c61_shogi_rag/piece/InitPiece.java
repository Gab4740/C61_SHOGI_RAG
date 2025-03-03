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
            case "roi" :
                piece_id = PieceIDs.Roi.getValue() * (color ? adjust : inverse);
                mR = pieceMoveSet.Roi.getMoveSet();
                break;
            case "char" :
                piece_id = PieceIDs.Char.getValue() * (color ? adjust : inverse);
                mR = pieceMoveSet.Char.getMoveSet();
                break;
            case "fou" :
                piece_id = PieceIDs.Fou.getValue() * (color ? adjust : inverse);
                mR = pieceMoveSet.Fou.getMoveSet();
                break;
            case "generalor" :
                piece_id = PieceIDs.GeneralOr.getValue() * (color ? adjust : inverse);
                mR = (piece_id > 0 ? pieceMoveSet.GeneralOrBlanc.getMoveSet() : pieceMoveSet.GeneralOrNoir.getMoveSet());
                break;
            case "generalargent" :
                piece_id = PieceIDs.GeneralArgent.getValue() * (color ? adjust : inverse);
                mR = (piece_id > 0 ? pieceMoveSet.GeneralArgentBlanc.getMoveSet() : pieceMoveSet.GeneralArgentNoir.getMoveSet());
                break;
            case "chevalier" :
                piece_id = PieceIDs.Chevalier.getValue() * (color ? adjust : inverse);
                mR = (piece_id > 0 ? pieceMoveSet.ChevalierBlanc.getMoveSet() : pieceMoveSet.ChevalierNoir.getMoveSet());
                break;
            case "lance" :
                piece_id = PieceIDs.Lance.getValue() * (color ? adjust : inverse);
                mR = (piece_id > 0 ? pieceMoveSet.LanceBlanc.getMoveSet() : pieceMoveSet.LanceNoir.getMoveSet());
                break;
            case "pion" :
                piece_id = PieceIDs.Pion.getValue() * (color ? adjust : inverse);
                mR = (piece_id > 0 ? pieceMoveSet.PionBlanc.getMoveSet() : pieceMoveSet.PionNoir.getMoveSet());
                break;
            case "roidragon" :
                piece_id = PieceIDs.RoiDragon.getValue() * (color ? adjust : inverse);
                mR = pieceMoveSet.RoiDragon.getMoveSet();
                break;
            case "chevalierdragon" :
                piece_id = PieceIDs.ChevalDragon.getValue() * (color ? adjust : inverse);
                mR = pieceMoveSet.ChevalDragon.getMoveSet();
                break;
            default:
                System.out.println("Invalid piece: " + piece);
                throw new IllegalArgumentException("Unknown piece type: " + piece);
        }
        return new Piece((byte)piece_id, image_id, pieceName, mR);
    }

    /**
     * Lookup table des mouvements valide de tous les pièce du jeu sous
     * forme de tableau 2D : int[][]
     * <a href="https://shogischool.com/shogi-pieces/">Documentation des pièces</a>
     * */
    public enum pieceMoveSet{
        Roi(new int[][]{                    // King (玉 Gyoku )
                {1, 0}, {0, 1},
                {-1, 0}, {0, -1},           // Gauche, Droite, Haut, Bas
                {1, 1}, {1, -1},
                {-1, 1}, {-1, -1}           // Diagonales
        }),

        Char(new int[][]{                   // Rook (飛 Hisya) -> Promotion : RoiDragon
                {-1, 0}, {1, 0},            // Gauche, Droite
                {0, -1}, {0, 1}             // Haut, Bas -> n nombres de cases
        }),

        Fou(new int[][]{                    // Bishop (角 Kaku) -> Promotion : ChevalDragon
                {-1, -1}, {1, 1},
                {-1, 1}, {1, -1}            // Diagonales  -> n nombres de cases
        }),

        GeneralOrBlanc(new int[][]{         // Gold (金 Kin) Directionnel vers le haut
                {-1, 0}, {1, 0},            // Gauche, Droite
                {0, -1}, {0, 1},            // Haut, Bas
                {-1, 1}, {1, 1}             // Diagonales vers direction
        }),

        GeneralOrNoir(new int[][]{          // Gold (金 Kin) Directionnel vers le bas
                {-1, 0}, {1, 0},            // Gauche, Droite
                {0, -1}, {0, 1},            // Haut, Bas
                {-1, -1}, {1, -1}           // Diagonales vers direction
        }),

        GeneralArgentBlanc(new int[][]{     // Silver (銀 Gin) Directionnel vers le Haut -> Promotion : GeneralOrBlanc
                {-1, -1}, {1, 1},           //
                {-1, 1}, {1, -1},           // Toutes les diagonales
                {0, 1}                      // Tout droit ver le haut
        }),

        GeneralArgentNoir(new int[][]{      // Silver (銀 Gin) Directionnel vers le bas -> Promotion : GeneralOrNoir
                {-1, -1}, {1, 1},           //
                {-1, 1}, {1, -1},           // Toutes les diagonales
                {0, -1}                     // Tout droit ver le bas
        }),

        ChevalierBlanc(new int[][]{         // Knight (桂 Kei) Directionnel vers le Haut  -> Promotion : GeneralOrBlanc
                {-1, 2}, {1, 2}             // 2 vers le haut, 1 gauche ou droite
        }),

        ChevalierNoir(new int[][]{          // Knight (桂 Kei) Directionnel vers le Bas -> Promotion : GeneralOrNoir
                {-1, -2}, {1, -2}           // 2 vers le Bas, 1 gauche ou droite
        }),

        LanceBlanc(new int[][]{             // Lance (香 Kyo) Directionnel vers le Haut -> Promotion : GeneralOrBlanc
                {0, 1}                      // Tout droit vers le haut
        }),

        LanceNoir(new int[][]{              // Lance (香 Kyo) Directionnel ver les Bas  -> Promotion : GeneralOrNoir
                {0, -1}                     // Tout droit vers le bas
        }),

        PionBlanc(new int[][]{              // Pawn (歩 Fu) Directionnel vers le haut
                {0, 1}                      // Tout droit vers le haut
        }),

        PionNoir(new int[][]{              // Pawn (歩 Fu) Directionnel vers le haut
                {0, -1}                    // Tout droit vers le bas
        }),

        RoiDragon(new int[][]{              // Dragon (竜 Ryu)
                {-1, 0}, {1, 0},            // Gauche, Droite
                {0, -1}, {0, 1},            // Haut, Bas -> n nombre de cases
                {-1, 1}, {1, 1},
                {-1, -1}, {1, -1}           // Tous les Diagonales
        }),

        ChevalDragon(new int[][]{           // Horse (馬 Uma)
                {-1, 0}, {1, 0},            // Gauche, Droite
                {0, -1}, {0, 1},            // Haut, Bas
                {-1, 1}, {1, 1},
                {-1, -1}, {1, -1}           // Toutes les Diagonales -> n nombre de cases
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