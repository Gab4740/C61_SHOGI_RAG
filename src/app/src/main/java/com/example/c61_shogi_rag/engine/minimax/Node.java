package com.example.c61_shogi_rag.engine.minimax;

import com.example.c61_shogi_rag.engine.game.Board;

import java.util.Vector;

public class Node {
    private final Board board;

    public Node(Board board){
        this.board = board;
    }
    public Board getBoard() {
        return board;
    }
    public Vector<Node> generateChildren(){
        Vector<Node> moves = new Vector<>();
        // TODO
        return moves;
    };
}