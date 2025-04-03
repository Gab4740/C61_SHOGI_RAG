package com.example.c61_shogi_rag.engine.game;

import com.example.c61_shogi_rag.engine.piece.Move;
import com.example.c61_shogi_rag.engine.piece.Position;

public class MoveManager {
    // private final byte originalPiece;
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

    public void prettyPrint(){
        System.out.println(originalTarget + " " + move.getCurrentPosition().getPosX() + "," + move.getCurrentPosition().getPosY() + " to " + move.getNextPosition().getPosX() + "," + move.getNextPosition().getPosY());
    }
}
