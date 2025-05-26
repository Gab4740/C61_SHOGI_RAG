package com.example.c61_shogi_rag.engine.minimax;

import com.example.c61_shogi_rag.engine.game.Board;
import com.example.c61_shogi_rag.engine.game.MoveManager;
import com.example.c61_shogi_rag.engine.game.PromotionState;
import com.example.c61_shogi_rag.engine.piece.ShogiPiece;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/**
 * Nom du fichier : MinimaxManager.java
 * Description : Ce fichier implémente la gestion de l'algorithme Minimax pour le jeu de Shogi,
 *               permettant l'exécution asynchrone et l'évaluation des mouvements à différentes profondeurs.
 * Auteur : Gabriel Veilleux
 * Entête générée par Copilot
 */
public class MinimaxManager {
    private final EvaluationStrategies difficulty;
    private Minimax minimaxObj;
    private Hashtable<Byte, ShogiPiece> piecesObj;
    private Board currGameBoard;
    private int searchDepth;
    private boolean maximizingPlayer;
    private boolean debug;
    private final int ALPHA = Integer.MIN_VALUE;
    private final int BETA = Integer.MAX_VALUE;
    private PromotionState promotionStateMap;
    private Vector<ShogiPiece> piece;
    private ExecutorService executorService;
    private LinkedHashMap<String, Integer> capturedPieceBlackHM;

    public MinimaxManager(int searchDepth, boolean maximizingPlayer, Vector<ShogiPiece> piece, boolean debug, Hashtable<Byte, ShogiPiece> pieces, EvaluationStrategies difficulty) {
        this.currGameBoard = null;
        this.promotionStateMap = new PromotionState(new HashMap<>());
        this.searchDepth = searchDepth;
        this.maximizingPlayer = maximizingPlayer;
        this.debug = debug;
        this.piecesObj = pieces;
        this.piece = piece;
        this.difficulty = difficulty;
        this.executorService = Executors.newWorkStealingPool();
    }

    public void resetMinimax(Board currGameBoard, LinkedHashMap<String, Integer> capturedPieceBlackHM){
        this.currGameBoard = currGameBoard;
        this.capturedPieceBlackHM = capturedPieceBlackHM;
    }

    public void executeMinimaxAsync(MinimaxCallback callback) {
        executorService.submit(() -> {
            MoveManager move = executeMinimax();
            if (callback != null) {
                callback.onMoveComputed(move);
            }
        });
    }

    private MoveManager executeMinimax() {
        long start = System.nanoTime();
        Board boardCopy = currGameBoard.clone();
        PromotionState promotionCopy = promotionStateMap.clone();
        LinkedHashMap<String, Integer> capturedPieceBlackHMCopy = (LinkedHashMap<String, Integer>) capturedPieceBlackHM.clone();

        minimaxObj = new Minimax(piece, piecesObj, difficulty);
        MoveScore moveToDo = minimaxObj.minimax(boardCopy, searchDepth, ALPHA, BETA, maximizingPlayer, promotionCopy, capturedPieceBlackHMCopy);
        long end = System.nanoTime();

        if (debug) {
            long elapsedTimeNs = end - start;
            int elapsedTimeMs = (int) (elapsedTimeNs / 1_000_000.0);
            System.out.println("Leaf explored : " + minimaxObj.getLeafCounter() + ", Score of best branch: " + moveToDo.getScore() + ", time : " + elapsedTimeMs + "ms");
        }

        return moveToDo.getMove();
    }
}
