package com.example.c61_shogi_rag.engine.game;

import com.example.c61_shogi_rag.engine.piece.Move;
import com.example.c61_shogi_rag.engine.piece.Position;
/**
 * Nom du fichier : OneTurn.java
 * Description : Ce fichier définit une classe représentant un tour de jeu dans une partie de Shogi,
 *               incluant les informations sur la pièce jouée, les positions et l'utilisation du parachutage.
 * Auteur : Gabriel Veilleux
 * Entête générée par Copilot
 */
public class MoveManager {
    private final byte originalTarget;
    private final Move move;

    public MoveManager(byte originalTarget, Move move) {
        this.originalTarget = originalTarget;
        this.move = move;
    }

    /**
     * Modifie l'instance du board pour accomplir le move.
     *
     * @param board -> Board a modifier
     * */
    public void do_move_on_board(Board board){
        Position posOriginal = new Position(move.getCurrentPosition().getPosX(), move.getCurrentPosition().getPosY());
        Position posNew = new Position(move.getNextPosition().getPosX(), move.getNextPosition().getPosY());
        board.movePieceTo(posOriginal, posNew);
    }

    /**
     * Modifie l'instance du board pour undo un move déjà accompli.
     *
     * @param board -> Board a modifier
     * */
    public void undo_move_on_board(Board board){
        Position posOriginal = new Position(move.getCurrentPosition().getPosX(), move.getCurrentPosition().getPosY());
        Position posNew = new Position(move.getNextPosition().getPosX(), move.getNextPosition().getPosY());
        board.movePieceTo(posNew, posOriginal);
        board.setPieceAt(originalTarget, posNew);
    }

    public boolean checkIfPieceEaten(){
        return originalTarget != 0;
    }
    public boolean checkIfShouldBePromoted(){
        return move.getNextPosition().getPosX() >= 6;
    }

    public Move getMove() {
        return move;
    }
}
