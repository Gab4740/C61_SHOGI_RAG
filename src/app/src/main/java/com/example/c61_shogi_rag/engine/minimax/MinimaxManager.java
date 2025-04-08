package com.example.c61_shogi_rag.engine.minimax;

import com.example.c61_shogi_rag.engine.game.Board;
import com.example.c61_shogi_rag.engine.game.MoveManager;
import com.example.c61_shogi_rag.engine.game.PromotionState;
import com.example.c61_shogi_rag.engine.piece.ShogiPiece;
import com.example.c61_shogi_rag.ui.theme.ShogiBoardKt;

import java.time.chrono.MinguoEra;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Vector;

public class MinimaxManager {
    private Minimax minimaxObj;
    private Hashtable<Byte, ShogiPiece> piecesObj;
    private Board currGameBoard;
    private int searchDepth;
    private boolean maximizingPlayer;
    private boolean debug;
    private final int ALPHA = Integer.MIN_VALUE;
    private final int BETA = Integer.MAX_VALUE;
    private PromotionState promotionStateMap;

    public MinimaxManager(int searchDepth, boolean maximizingPlayer, Vector<ShogiPiece> piece, boolean debug, Hashtable<Byte, ShogiPiece> pieces) {
        this.currGameBoard = null;
        this.promotionStateMap = null;
        this.searchDepth = searchDepth;
        this.maximizingPlayer = maximizingPlayer;
        this.debug = debug;
        this.piecesObj = pieces;
        this.minimaxObj = new Minimax(piece, piecesObj);
    }

    public void resetMinimax(Board currGameBoard, HashMap<String, Boolean> promotionStateMap){
        this.currGameBoard = currGameBoard;
        this.promotionStateMap = new PromotionState(promotionStateMap);
    }
    public MoveManager executeMinimax() {
        long start = System.nanoTime();
        MoveScore moveToDo = minimaxObj.minimax(currGameBoard, searchDepth, ALPHA, BETA, maximizingPlayer, promotionStateMap);
        long end = System.nanoTime();

        if (debug) {
            long elapsedTimeNs = end - start;
            int elapsedTimeMs = (int) (elapsedTimeNs / 1_000_000.0);
            System.out.println("Leaf explored : " + minimaxObj.getLeafCounter() + ", Score of best branch: " + moveToDo.getScore() + ", time : " + elapsedTimeMs + "ms");
        }

        return moveToDo.getMove();
    }
}
