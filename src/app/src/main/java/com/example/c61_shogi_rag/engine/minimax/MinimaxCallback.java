package com.example.c61_shogi_rag.engine.minimax;

import com.example.c61_shogi_rag.engine.game.MoveManager;

public interface MinimaxCallback {
    void onMoveComputed(MoveManager move);
}