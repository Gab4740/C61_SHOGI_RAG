package com.example.c61_shogi_rag.engine.piece;

import com.example.c61_shogi_rag.engine.game.Board;
/**
 * Nom du fichier : NormalPiece.java
 * Description : Ce fichier définit une classe représentant une pièce classique du Shogi,
 *               incluant la validation de ses déplacements et l'application des règles du jeu.
 * Auteur(s) : [Gabriel Veilleux]
 * Entête générée par Copilot
 */
public class NormalPiece extends ShogiPiece{

    public NormalPiece(byte id, byte idPromu, int imageId, int imageIdPromu, String nom, int[][] directions) {
        super(id, idPromu, imageId, imageIdPromu, nom, directions);
    }
    @Override
    protected boolean allMoveChecks(Move move, Board board) {
        if(defaultCheck(move)){
            if(secondaryStep(board)){
                int deltaX = finalX - currX;
                int deltaY = finalY - currY;
                return MoveUtils.checkSteps(deltaX, deltaY, getDIRECTIONS());
            }
        }
        return false;
    }
}
