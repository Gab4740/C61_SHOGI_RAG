package com.example.c61_shogi_rag.engine.piece.ShogiPieces;

import com.example.c61_shogi_rag.engine.game.Board;
import com.example.c61_shogi_rag.engine.piece.ExceptionPiece;
import com.example.c61_shogi_rag.engine.piece.MoveUtils;
/**
 * Nom du fichier : RoiDragon.java
 * Description : Ce fichier définit la classe représentant la pièce "Roi Dragon" du Shogi,
 *               qui hérite des comportements exceptionnels et gère ses déplacements spécifiques sur le plateau.
 * Auteur : Gabriel Veilleux
 * Entête générée par Copilot
 */
public class RoiDragon extends ExceptionPiece {
    public RoiDragon(byte id, byte idPromu, int imageId, int imageIdPromu, String nom, int[][] directions) {
        super(id, idPromu, imageId, imageIdPromu, nom, directions);
    }

    @Override
    public boolean exception(Board board) {
        if(secondaryStep(board)){
            int deltaX = finalX - currX;
            int deltaY = finalY - currY;
            if((Math.abs(deltaY) > 1 && deltaX == 0) || (Math.abs(deltaX) > 1 && deltaY == 0)){
                return MoveUtils.checkCross(finalX, finalY, currX, currY, board, getDIRECTIONS());
            }
            else{ return MoveUtils.checkSteps(deltaX, deltaY, getDIRECTIONS()); }
        }
        return false;
    }
}