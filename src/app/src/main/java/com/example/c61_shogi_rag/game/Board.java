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

    /**
    * Retourne un vecteur de positions de la pièce recherché
    *
    * @param piece La pièce recherché
    * */
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

    /**
     * Retourne un vecteur de positions de tous les pièces de la couleur recherché
     *
     * @param color La couleur recherché
     * */
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

    /**
     * Retourne un int qui corespond au nombre de pièce non-capturer de la couleur recherché
     *
     * @param color La couleur recherché
     * */
    public int getAlivePieceCountOfColor(boolean color){
        return getPositionFromColor(color).size();
    }

    /**
     * Retourne un int qui corespond au score (somme de leur IDs) de la couleur recherché.
     * Un plus haut score veux indirectement dire que la couleur peux avoir un plus gros impacte.
     *
     * @param color La couleur recherché
     * */
    public int getPieceScoreOfColor(boolean color){
        int total = 0;
        Vector<Position> allPos = getPositionFromColor(color);
        for(Position pos : allPos){
            total = getPieceAt(pos);
        }
        return total;
    }

    // IDÉE : Toutes les classe qui sont concerné dans la sauvgarde de la partie implement une interface pour leur fournir une abtract func de sauvgarde d'état.
}