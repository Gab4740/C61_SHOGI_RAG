package com.example.c61_shogi_rag.engine.piece;

/**
 * Lookup table des mouvements valide de tous les pièce du jeu sous
 * forme de tableau 2D : int[][]
 * <a href="https://shogischool.com/shogi-pieces/">Documentation des pièces</a>
 * */
public enum MoveSet{
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
            {-1, -1}, {1, -1}             // Diagonales vers direction
    }),

    GeneralOrNoir(new int[][]{          // Gold (金 Kin) Directionnel vers le bas
            {-1, 0}, {1, 0},            // Gauche, Droite
            {0, -1}, {0, 1},            // Haut, Bas
            {-1, 1}, {1, 1}           // Diagonales vers direction
    }),

    GeneralArgentBlanc(new int[][]{     // Silver (銀 Gin) Directionnel vers le Haut -> Promotion : GeneralOrBlanc
            {-1, -1}, {1, 1},           //
            {-1, 1}, {1, -1},           // Toutes les diagonales
            {0, -1}                      // Tout droit ver le haut
    }),

    GeneralArgentNoir(new int[][]{      // Silver (銀 Gin) Directionnel vers le bas -> Promotion : GeneralOrNoir
            {-1, -1}, {1, 1},           //
            {-1, 1}, {1, -1},           // Toutes les diagonales
            {0, 1}                     // Tout droit ver le bas
    }),

    ChevalierBlanc(new int[][]{         // Knight (桂 Kei) Directionnel vers le Haut  -> Promotion : GeneralOrBlanc
            {-1, -2}, {1, -2}             // 2 vers le haut, 1 gauche ou droite
    }),

    ChevalierNoir(new int[][]{          // Knight (桂 Kei) Directionnel vers le Bas -> Promotion : GeneralOrNoir
            {-1, 2}, {1, 2}           // 2 vers le Bas, 1 gauche ou droite
    }),

    LanceBlanc(new int[][]{             // Lance (香 Kyo) Directionnel vers le Haut -> Promotion : GeneralOrBlanc
            {0, -1}                      // Tout droit vers le haut
    }),

    LanceNoir(new int[][]{              // Lance (香 Kyo) Directionnel ver les Bas  -> Promotion : GeneralOrNoir
            {0, 1}                     // Tout droit vers le bas
    }),

    PionBlanc(new int[][]{              // Pawn (歩 Fu) Directionnel vers le haut
            {0, -1}                      // Tout droit vers le haut
    }),

    PionNoir(new int[][]{              // Pawn (歩 Fu) Directionnel vers le haut
            {0, 1}                    // Tout droit vers le bas
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
    MoveSet(int[][] moveSet) {
        this.moveSet = moveSet;
    }
    public int[][] getMoveSet() {
        return this.moveSet;
    }
}
