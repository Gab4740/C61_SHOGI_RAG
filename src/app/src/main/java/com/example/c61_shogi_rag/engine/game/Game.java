package com.example.c61_shogi_rag.engine.game;

import com.example.c61_shogi_rag.engine.dao.PartieDAO;
import com.example.c61_shogi_rag.engine.entity.Partie;
import com.example.c61_shogi_rag.engine.entity.PartieCallback;
import com.example.c61_shogi_rag.engine.minimax.MoveGeneration;
import com.example.c61_shogi_rag.engine.piece.InitPiece;
import com.example.c61_shogi_rag.engine.piece.Move;
import com.example.c61_shogi_rag.engine.piece.PieceIDs;
import com.example.c61_shogi_rag.engine.piece.Position;
import com.example.c61_shogi_rag.engine.piece.ShogiPiece;
import com.example.c61_shogi_rag.engine.piece.ShogiPieces.Charriot;
import com.example.c61_shogi_rag.engine.piece.ShogiPieces.Chevalier;
import com.example.c61_shogi_rag.engine.piece.ShogiPieces.Fou;
import com.example.c61_shogi_rag.engine.piece.ShogiPieces.GeneralArgent;
import com.example.c61_shogi_rag.engine.piece.ShogiPieces.GeneralOr;
import com.example.c61_shogi_rag.engine.piece.ShogiPieces.Lance;
import com.example.c61_shogi_rag.engine.piece.ShogiPieces.Pion;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Vector;

public class Game {
    private final Board gameBoard;
    private Hashtable<Byte, ShogiPiece> pieces;
    private Vector<ShogiPiece> piecesForMinimax;
    private Vector<Byte> capturedPieceBlack;
    private Vector<Byte> capturedPieceWhite;
    private LinkedHashMap<String , Integer> capturedPieceWhiteHM, capturedPieceBlackHM;

    private final Time gameTimer;
    private Boolean isPlayerStarting;
    private Boolean isPlayerTurn;
    private Boolean isGameEnded;
    private Boolean GameWinner;
    private static GameSaver gameSaver;
    private HashMap<String, Boolean> promotionStateMap;


    /**
     * @param isPlayerStarting : Si le joueur commence : true, si le AI commence : false
     * */
    public Game(Boolean isPlayerStarting){
        this.pieces = new Hashtable<>();
        this.piecesForMinimax = new Vector<>();
        this.capturedPieceBlack = new Vector<>();
        this.capturedPieceWhite = new Vector<>();
        this.gameTimer = new Time();
        this.gameBoard = new Board();
        this.isPlayerStarting = isPlayerStarting;
        this.isPlayerTurn = isPlayerStarting;
        this.isGameEnded = false;
        this.gameSaver = new GameSaver();
        this.GameWinner = null;
        this.promotionStateMap = new HashMap<>();

        this.capturedPieceWhiteHM = new LinkedHashMap<>(20);
        this.capturedPieceBlackHM = new LinkedHashMap<>(20);


        this.capturedPieceWhiteHM.put(Pion.class.getCanonicalName(), 0);
        this.capturedPieceBlackHM.put(Pion.class.getCanonicalName(), 0);
        this.capturedPieceWhiteHM.put(Lance.class.getCanonicalName(), 0);
        this.capturedPieceBlackHM.put(Lance.class.getCanonicalName(), 0);
        this.capturedPieceWhiteHM.put(Chevalier.class.getCanonicalName(), 0);
        this.capturedPieceBlackHM.put(Chevalier.class.getCanonicalName(), 0);
        this.capturedPieceWhiteHM.put(GeneralArgent.class.getCanonicalName(), 0);
        this.capturedPieceBlackHM.put(GeneralArgent.class.getCanonicalName(), 0);
        this.capturedPieceWhiteHM.put(GeneralOr.class.getCanonicalName(), 0);
        this.capturedPieceBlackHM.put(GeneralOr.class.getCanonicalName(), 0);
        this.capturedPieceWhiteHM.put(Fou.class.getCanonicalName(), 0);
        this.capturedPieceBlackHM.put(Fou.class.getCanonicalName(), 0);
        this.capturedPieceWhiteHM.put(Charriot.class.getCanonicalName(), 0);
        this.capturedPieceBlackHM.put(Charriot.class.getCanonicalName(), 0);
    }
    /**
     * Permet de créer les pièce nécessaire au jeux
     * */
    private void PieceInit(){
        ShogiPiece pionBlanc = InitPiece.create("Pion_blanc");
        pieces.put(pionBlanc.getID(), pionBlanc);

        ShogiPiece pionNoir = InitPiece.create("Pion_noir");
        pieces.put(pionNoir.getID(), pionNoir);
        piecesForMinimax.add(pionNoir);

        ShogiPiece Lance_blanc = InitPiece.create("Lance_blanc");
        pieces.put(Lance_blanc.getID(), Lance_blanc);

        ShogiPiece Lance_noir = InitPiece.create("Lance_noir");
        pieces.put(Lance_noir.getID(), Lance_noir);
        piecesForMinimax.add(Lance_noir);

        ShogiPiece Chevalier_blanc = InitPiece.create("Chevalier_blanc");
        pieces.put(Chevalier_blanc.getID(), Chevalier_blanc);

        ShogiPiece Chevalier_noir = InitPiece.create("Chevalier_noir");
        pieces.put(Chevalier_noir.getID(), Chevalier_noir);
        piecesForMinimax.add(Chevalier_noir);

        ShogiPiece GeneralArgent_blanc = InitPiece.create("GeneralArgent_blanc");
        pieces.put(GeneralArgent_blanc.getID(), GeneralArgent_blanc);

        ShogiPiece GeneralArgent_noir = InitPiece.create("GeneralArgent_noir");
        pieces.put(GeneralArgent_noir.getID(), GeneralArgent_noir);
        piecesForMinimax.add(GeneralArgent_noir);

        ShogiPiece Generalor_blanc = InitPiece.create("Generalor_blanc");
        pieces.put(Generalor_blanc.getID(), Generalor_blanc);

        ShogiPiece Generalor_noir = InitPiece.create("Generalor_noir");
        pieces.put(Generalor_noir.getID(), Generalor_noir);
        piecesForMinimax.add(Generalor_noir);

        ShogiPiece Fou_blanc = InitPiece.create("Fou_blanc");
        pieces.put(Fou_blanc.getID(), Fou_blanc);

        ShogiPiece Fou_noir = InitPiece.create("Fou_noir");
        pieces.put(Fou_noir.getID(), Fou_noir);
        piecesForMinimax.add(Fou_noir);

        ShogiPiece Char_blanc = InitPiece.create("Char_blanc");
        pieces.put(Char_blanc.getID(), Char_blanc);

        ShogiPiece Char_noir = InitPiece.create("Char_noir");
        pieces.put(Char_noir.getID(), Char_noir);
        piecesForMinimax.add(Char_noir);

        ShogiPiece Roi_Dragon_blanc = InitPiece.create("roidragon_blanc");
        pieces.put(Roi_Dragon_blanc.getID(), Roi_Dragon_blanc);

        ShogiPiece Roi_Dragon_noir = InitPiece.create("roidragon_noir");
        pieces.put(Roi_Dragon_noir.getID(), Roi_Dragon_noir);
        piecesForMinimax.add(Roi_Dragon_noir);

        ShogiPiece Cheval_Dragon_blanc = InitPiece.create("chevalierdragon_blanc");
        pieces.put(Cheval_Dragon_blanc.getID(), Cheval_Dragon_blanc);

        ShogiPiece Cheval_Dragon_noir = InitPiece.create("chevalierdragon_noir");
        pieces.put(Cheval_Dragon_noir.getID(), Cheval_Dragon_noir);
        piecesForMinimax.add(Cheval_Dragon_noir);

        if(isPlayerStarting){
            ShogiPiece Roi_blanc = InitPiece.create("roisente_blanc");
            pieces.put(Roi_blanc.getID(), Roi_blanc);

            ShogiPiece Roi_noir = InitPiece.create("roigote_noir");
            pieces.put(Roi_noir.getID(), Roi_noir);
            piecesForMinimax.add(Roi_noir);

        }else{
            ShogiPiece Roi_blanc = InitPiece.create("roigote_blanc");
            pieces.put(Roi_blanc.getID(), Roi_blanc);

            ShogiPiece Roi_noir = InitPiece.create("roisente_noir");
            pieces.put(Roi_noir.getID(), Roi_noir);
            piecesForMinimax.add(Roi_noir);
        }
    }
    /**
     * Place les pièces a leur positon de départ sur l'échiquier
     * */
    private void BoardInit(){
        ShogiPiece pieceToPlace;

        pieceToPlace = pieces.get((byte)PieceIDs.Pion.getValue());
        for(int i =0; i<9; i++){
            gameBoard.setPieceAt(pieceToPlace, new Position(6,i));
        }

        pieceToPlace = pieces.get((byte)-PieceIDs.Pion.getValue());
        for(int i =0; i<9; i++){
            gameBoard.setPieceAt(pieceToPlace, new Position(2,i));
        }

        pieceToPlace = pieces.get((byte)PieceIDs.Lance.getValue());
        gameBoard.setPieceAt(pieceToPlace, new Position(8,0));
        gameBoard.setPieceAt(pieceToPlace, new Position(8,8));

        pieceToPlace = pieces.get((byte)-PieceIDs.Lance.getValue());
        gameBoard.setPieceAt(pieceToPlace, new Position(0,0));
        gameBoard.setPieceAt(pieceToPlace, new Position(0,8));

        pieceToPlace = pieces.get((byte)PieceIDs.Chevalier.getValue());
        gameBoard.setPieceAt(pieceToPlace, new Position(8,1));
        gameBoard.setPieceAt(pieceToPlace, new Position(8,7));

        pieceToPlace = pieces.get((byte)-PieceIDs.Chevalier.getValue());
        gameBoard.setPieceAt(pieceToPlace, new Position(0,1));
        gameBoard.setPieceAt(pieceToPlace, new Position(0,7));

        pieceToPlace = pieces.get((byte)PieceIDs.GeneralArgent.getValue());
        gameBoard.setPieceAt(pieceToPlace, new Position(8,2));
        gameBoard.setPieceAt(pieceToPlace, new Position(8,6));

        pieceToPlace = pieces.get((byte)-PieceIDs.GeneralArgent.getValue());
        gameBoard.setPieceAt(pieceToPlace, new Position(0,2));
        gameBoard.setPieceAt(pieceToPlace, new Position(0,6));

        pieceToPlace = pieces.get((byte)PieceIDs.GeneralOr.getValue());
        gameBoard.setPieceAt(pieceToPlace, new Position(8,3));
        gameBoard.setPieceAt(pieceToPlace, new Position(8,5));

        pieceToPlace = pieces.get((byte)-PieceIDs.GeneralOr.getValue());
        gameBoard.setPieceAt(pieceToPlace, new Position(0,3));
        gameBoard.setPieceAt(pieceToPlace, new Position(0,5));

        pieceToPlace = pieces.get((byte)PieceIDs.Roi.getValue());
        gameBoard.setPieceAt(pieceToPlace, new Position(8,4));

        pieceToPlace = pieces.get((byte)-PieceIDs.Roi.getValue());
        gameBoard.setPieceAt(pieceToPlace, new Position(0,4));

        pieceToPlace = pieces.get((byte)PieceIDs.Fou.getValue());
        gameBoard.setPieceAt(pieceToPlace, new Position(7,1));

        pieceToPlace = pieces.get((byte)-PieceIDs.Fou.getValue());
        gameBoard.setPieceAt(pieceToPlace, new Position(1,7));

        pieceToPlace = pieces.get((byte)PieceIDs.Char.getValue());
        gameBoard.setPieceAt(pieceToPlace, new Position(7,7));

        pieceToPlace = pieces.get((byte)-PieceIDs.Char.getValue());
        gameBoard.setPieceAt(pieceToPlace, new Position(1,1));
    }
    /**
     * Initialise tous les variables nécessaire pour jouer la partie
     * */
    public void GameInit(){
        PieceInit();
        BoardInit();
        gameTimer.startTime();

        // TEST USAGE GEN MOVES
        Vector<MoveManager> moveessssGEn = new Vector<>();
        MoveGeneration moveGenerationTest = new MoveGeneration(piecesForMinimax, gameBoard);
        while(moveGenerationTest.genMove()){
            if(moveGenerationTest.getCurrMoveToReturn() != null){
                moveessssGEn.add(moveGenerationTest.getCurrMoveToReturn());
            }
            // MINIMAX recursive call GOES HERE
        }
        for(MoveManager m : moveessssGEn){
            m.do_move_on_board(gameBoard);
            System.out.println("_____________________________________");
            prettyPrintConsoleBoard(gameBoard.getBoard());
            System.out.println("            -------------------");
            m.undo_move_on_board(gameBoard);
            prettyPrintConsoleBoard(gameBoard.getBoard());
            m.prettyPrint();
        }
    }
    /**
     * Méthode qui permet de d'effectuer le déplacement d'une pièce
     *
     * @param firstPos : La pièce choisi,
     * @param secondPos : La nouvelle position choisi
     * */
    public boolean playTurn(Position firstPos, Position secondPos){
        boolean valid = false;
        boolean pieceIsPromoted = false;
        boolean enemyPieceToCapture =  isEnemyPieceAtPos(secondPos);

        if(isPlayerPieceAtPos(firstPos) && (enemyPieceToCapture || isPositionEmpty(secondPos))){
            ShogiPiece pieceToPlay;
            if(isPromoted(firstPos)){
                ShogiPiece basePiece = getPieceAt(firstPos);
                pieceToPlay = pieces.get(basePiece.getID_PROMU());
                pieceIsPromoted = true;

            }else{
                pieceToPlay = getPieceAt(firstPos);
            }
            Move pieceMove = new Move(firstPos, secondPos);

            if(pieceToPlay.isValidMove(pieceMove, gameBoard)){
                if(enemyPieceToCapture){
                    capturePieceAtPos(secondPos, true);
                }
                gameBoard.movePieceTo(firstPos, secondPos);

                gameSaver.addNewTurn(new OneTurn(pieceToPlay.getID(), firstPos, secondPos, false));

                // isPlayerTurn = !isPlayerTurn;
                // DEBUG
                isPlayerTurn = true;
                valid = true;

                if(pieceIsPromoted){
                    promotionStateMap.remove(getPositionKey(firstPos.getPosX(), firstPos.getPosY()));
                    promotionStateMap.put(getPositionKey(secondPos.getPosX(), secondPos.getPosY()), true);
                }
            }
            isGameEnded = isKingsAlive();
        }
        return valid;
    }
    /**
     * Méthode qui permet de d'effectuer le parachutage d'une piece
     *
     * @param parachutePos : La position cliquer,
     * @param piece : Pièce a parachuter
     * */
    public boolean parachute(Position parachutePos, byte piece){
        boolean valid = false;


        return valid;
    }
    public boolean getIsGameEnded(){ return isGameEnded; }
    /**
     * Retourne la classe qui possede la liste de l'historique de coup
     * */
    public static GameSaver getGameSaver() {return gameSaver; }
    /**
     * Retourne la classe du GameBoard
     * */
    public Board getGameBoard(){ return gameBoard; }
    /**
     * Retourne un booléen qui indique si c'est le tour du joueur = true ou au AI = false
     * */
    public boolean getIsPlayerTurn(){ return isPlayerTurn; }
    /**
     * Retourne un objet piece pour la position donné
     * */
    private ShogiPiece getPieceAt(Position pos){
        return pieces.get(gameBoard.getPieceAt(pos));
    }
    private boolean isPromoted(Position pos) {
        return Boolean.TRUE.equals(promotionStateMap.get(getPositionKey(pos.getPosX(), pos.getPosY())));
    }
    private String getPositionKey(int row, int col) {
        return row + "-" + col;  // Use row-column as the key
    }
    public Integer getPieceDrawable(Position pos){
        Integer drawable = null;
        if(getPieceAt(pos) != null){
            drawable = isPromoted(pos) ? getPieceAt(pos).getIMAGE_ID_PROMU() : getPieceAt(pos).getIMAGE_ID();
        }
        return drawable;
    }
    /**
     * Retourn True si c'est une pièce enemy, False si ce n'est pas une piece enemy ou pas de piece
     * */
    private boolean isEnemyPieceAtPos(Position pos){ return gameBoard.getPieceAt(pos) < 0; }
    /**
     * Retourn True si une piece du joeur est a la positon, false si non
     * */
    public boolean isPlayerPieceAtPos(Position pos){ return gameBoard.getPieceAt(pos) > 0; }
    /**
     * Retourn True si la case de l'échiquier est vide
     * */
    private boolean isPositionEmpty(Position pos){ return gameBoard.getPieceAt(pos) == 0; }
    /**
     * Retourne true si les deux rois sont encore en vie, si au moins un roi est mort, retourne false
     * */
    private boolean isKingsAlive(){
        for(byte id : capturedPieceBlack){
            if(Math.abs(id) == PieceIDs.Roi.getValue()){
                return true;
            }
        }
        for(byte id : capturedPieceWhite){
            if(Math.abs(id) == PieceIDs.Roi.getValue()){
                return true;
            }
        }
        return false;
    }
    /**
     * Méthode pour capturer une pièce a la position donné.
     *
     * @param pos : Position de la pièce a captuer,
     * @param color : True = Les blancs capture (joueur), False = Les noirs capture (AI)
     * */
    private void capturePieceAtPos(Position pos, boolean color){
        if (color) {
            capturedPieceWhite.add(gameBoard.getPieceAt(pos));
        } else {
            capturedPieceBlack.add(gameBoard.getPieceAt(pos));
        }
        gameBoard.removePieceAt(pos);
    }
    private void promotePiece(int row, int col) {
        String positionKey = getPositionKey(row, col);
        if (promotionStateMap.containsKey(positionKey)) {
            promotionStateMap.put(positionKey, true);
        } else {
            System.out.println("Invalid position!");
        }
    }
    private void revertPiece(int row, int col) {
        String positionKey = getPositionKey(row, col);
        if (promotionStateMap.containsKey(positionKey)) {
            promotionStateMap.put(positionKey, false);
        } else {
            System.out.println("No piece at position (" + row + ", " + col + ") to revert.");
        }
    }

    // DEBUG TOOLS
    public void prettyPrintConsoleBoard(byte[][] array) {
        int maxLength = getMaxNumberLength(array);

        for (byte[] row : array) {
            for (int num : row) {
                System.out.printf("%" + maxLength + "d ", num);
            }
            System.out.println();
        }
    }

    private int getMaxNumberLength(byte[][] array) {
        int maxLength = 0;
        for (byte[] row : array) {
            for (int num : row) {
                maxLength = Math.max(maxLength, String.valueOf(num).length());
            }
        }
        return maxLength;
    }

    public void archiverPartie(int id_gagnant, int id_perdant){

        if (isGameEnded){
            try{
                Gson gson = new Gson();

                String jsonString = gson.toJson(gameSaver.getTurnList());

                PartieDAO.addPartie(id_gagnant, id_perdant, jsonString, new PartieCallback() {
                    @Override
                    public void onPartiesRecuperees(List<Partie> partieList) {
                        //voir pour peut etre mettre un toast
                    }

                    @Override
                    public void onError(Exception e) {
                        //voir pour peut etre mettre un toast
                    }
                });

            }catch (Exception exception){
                exception.printStackTrace();
            }

        }
    }

    public Boolean parachuteWhitePiece(String shogiPieceClass) {
        boolean isValid = false;
        if(capturedPieceWhiteHM.containsKey(shogiPieceClass)) {
            int quantity = capturedPieceWhiteHM.get(shogiPieceClass);
            if(quantity > 0) {
                isValid = true;
            }
        }

        return isValid;    }

    public Boolean parachuteBlackPiece(String shogiPieceClass) {
        boolean isValid = false;
        if(capturedPieceBlackHM.containsKey(shogiPieceClass)) {
            int quantity = capturedPieceBlackHM.get(shogiPieceClass);
            if(quantity > 0) {
                isValid = true;
            }
        }
        return isValid;
    }

    public Boolean captureWhitePiece(String shogiPieceClass) {
        boolean isValid = false;
        if(capturedPieceBlackHM.containsKey(shogiPieceClass)) {
            int quantity = capturedPieceBlackHM.get(shogiPieceClass) + 1;
            capturedPieceBlackHM.put(shogiPieceClass, quantity);
            isValid = true;
        }
        return isValid;
    }
    public Boolean captureBlackPiece(String shogiPieceClass) {
        boolean isValid = false;
        if(capturedPieceWhiteHM.containsKey(shogiPieceClass)) {
            int quantity = capturedPieceWhiteHM.get(shogiPieceClass) + 1;
            capturedPieceWhiteHM.put(shogiPieceClass, quantity);
            isValid = true;
        }
        return isValid;
    }

    public LinkedHashMap<String, Integer> getCapturedPieceBlackHM() {
        return capturedPieceBlackHM;
    }

    public LinkedHashMap<String, Integer> getCapturedPieceWhiteHM() {
        return capturedPieceWhiteHM;
    }

}
