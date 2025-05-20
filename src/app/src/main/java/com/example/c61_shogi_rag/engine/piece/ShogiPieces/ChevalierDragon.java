package com.example.c61_shogi_rag.engine.piece.ShogiPieces;

import com.example.c61_shogi_rag.engine.game.Board;
import com.example.c61_shogi_rag.engine.piece.ExceptionPiece;
import com.example.c61_shogi_rag.engine.piece.MoveUtils;
/**
 * Nom du fichier : ChevalierDragon.java
 * Description : Ce fichier définit la classe représentant la pièce "Chevalier Dragon" du Shogi,
 *               incluant la gestion de ses déplacements spécifiques et exceptions sur le plateau de jeu.
 * Auteur(s) : [Gabriel Veilleux]
 * Entête générée par Copilot
 */
public class ChevalierDragon extends ExceptionPiece {
    public ChevalierDragon(byte id, byte idPromu, int imageId, int imageIdPromu, String nom, int[][] directions) {
        super(id, idPromu, imageId, imageIdPromu, nom, directions);
    }

    @Override
    public boolean exception(Board board) {
        if(secondaryStep(board)){
            int deltaX = finalX - currX;
            int deltaY = finalY - currY;
            if(Math.abs(deltaX) > 1 && Math.abs(deltaY) > 1){ return MoveUtils.checkDiagonals(finalX, finalY, currX, currY, board, getDIRECTIONS()); }
            else{ return MoveUtils.checkSteps(deltaX, deltaY, getDIRECTIONS()); }
        }
        return false;
    }
}
