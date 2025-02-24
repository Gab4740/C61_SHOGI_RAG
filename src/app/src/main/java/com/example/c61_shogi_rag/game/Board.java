package com.example.c61_shogi_rag.game;

import com.example.c61_shogi_rag.piece.Piece;
import com.example.c61_shogi_rag.piece.Position;

import java.util.Vector;

public class Board {
    private final int BOARD_SIZE = 9;
    private byte[][] board;

    public Board(){
        board = new byte[BOARD_SIZE][BOARD_SIZE];
    }

    public byte[][] getBoard(){
        return board;
    }
    public byte getPieceAt(Position pos){
        return board[pos.getPosX()][pos.getPosY()];
    }
    public void setPieceAt(Piece piece, Position pos){
        board[pos.getPosX()][pos.getPosY()] = piece.getID();
    }
    public Vector<Position> getPositionsFromPiece(Piece piece){
        Vector<Position> allPos = new Vector<>();
        byte pieceId = piece.getID();

        for(int i = 0; i < BOARD_SIZE; i++){
            for(int j = 0; j < BOARD_SIZE; j++){
                if(board[i][j] == pieceId){
                    allPos.add(new Position(i, j));
                }
            }
        }
        return allPos;
    }
    public Vector<Position> getPositionFromColor(boolean color){
        Vector<Position> allPos = new Vector<>();

        for(int i = 0; i < BOARD_SIZE; i++){
            for(int j = 0; j < BOARD_SIZE; j++){
                if ((color && board[i][j] > 0) || (!color && board[i][j] < 0)) {
                    allPos.add(new Position(i, j));
                }
            }
        }
        return allPos;
    }
}