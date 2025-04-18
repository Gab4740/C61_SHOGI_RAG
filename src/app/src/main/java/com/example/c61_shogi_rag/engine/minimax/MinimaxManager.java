package com.example.c61_shogi_rag.engine.minimax;

import com.example.c61_shogi_rag.engine.game.Board;
import com.example.c61_shogi_rag.engine.game.MoveManager;
import com.example.c61_shogi_rag.engine.game.PromotionState;
import com.example.c61_shogi_rag.engine.piece.ShogiPiece;
import com.example.c61_shogi_rag.ui.theme.ShogiBoardKt;

import java.time.chrono.MinguoEra;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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

    public void resetMinimax(Board currGameBoard){
        this.currGameBoard = currGameBoard;
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
        // MULTI-THREADING FUCKERY :
        //List<MoveManager> allMoves = new ArrayList<>();
        //MoveGeneration moveGenerator = new MoveGeneration(piece, currGameBoard, promotionStateMap, piecesObj);
        //while (moveGenerator.genMove()) {
        //    MoveManager move = moveGenerator.getCurrMoveToReturn();
        //    if (move != null) {
        //        allMoves.add(move);
        //    }
        //}
        //ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        //List<Callable<MoveScore>> tasks = new ArrayList<>();
        //for (MoveManager move : allMoves) {
        //    tasks.add(() -> {
        //        Board clonedBoard = currGameBoard.clone(); // Make sure this is a deep clone!
        //        PromotionState copyPromo = promotionStateMap.clone();
        //        move.do_move_on_board(clonedBoard);
        //        Minimax minimaxObj = new Minimax(piece, piecesObj, difficulty);
        //        MoveScore eval = minimaxObj.minimax(clonedBoard, searchDepth - 1, ALPHA, BETA, !maximizingPlayer, copyPromo);
        //        eval.setMove(move); // associate the move that led to this score
        //        return eval;
        //    });
        //}
        //MoveScore bestMoveScore = null;
        //try {
        //    List<Future<MoveScore>> results = executor.invokeAll(tasks);
        //    for (Future<MoveScore> future : results) {
        //        MoveScore result = future.get();
        //        if (bestMoveScore == null ||
        //                (maximizingPlayer && result.getScore() > bestMoveScore.getScore()) ||
        //                (!maximizingPlayer && result.getScore() < bestMoveScore.getScore())) {
        //            bestMoveScore = result;
        //        }
        //    }
        //} catch (InterruptedException | ExecutionException e) {
        //    e.printStackTrace();
        //} finally {
        //    executor.shutdown();
        //}

        long start = System.nanoTime();
        Board boardCopy = currGameBoard.clone();
        PromotionState promotionCopy = promotionStateMap.clone();

        minimaxObj = new Minimax(piece, piecesObj, difficulty);
        MoveScore moveToDo = minimaxObj.minimax(boardCopy, searchDepth, ALPHA, BETA, maximizingPlayer, promotionCopy);
        long end = System.nanoTime();

        if (debug) {
            long elapsedTimeNs = end - start;
            int elapsedTimeMs = (int) (elapsedTimeNs / 1_000_000.0);
            System.out.println("Leaf explored : " + minimaxObj.getLeafCounter() + ", Score of best branch: " + moveToDo.getScore() + ", time : " + elapsedTimeMs + "ms");
        }

        return moveToDo.getMove();
    }
}
