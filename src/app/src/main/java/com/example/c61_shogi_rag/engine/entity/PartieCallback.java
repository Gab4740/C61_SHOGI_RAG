package com.example.c61_shogi_rag.engine.entity;

import java.util.List;

public interface PartieCallback {
    void onPartiesRecuperees(List<Partie> partieList);

    void onError(Exception exception);
}
