package com.example.c61_shogi_rag.engine.game;

import androidx.compose.runtime.MutableState;

import com.example.c61_shogi_rag.engine.dao.PartieDAO;
import com.example.c61_shogi_rag.engine.entity.Partie;
import com.example.c61_shogi_rag.engine.entity.PartieCallback;
import com.example.c61_shogi_rag.engine.minimax.Difficulty;
import com.example.c61_shogi_rag.engine.minimax.EvaluationStrategies;
import com.example.c61_shogi_rag.engine.minimax.MinimaxCallback;
import com.example.c61_shogi_rag.engine.minimax.MinimaxManager;
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

public class Game implements MinimaxCallback {
    private final Board gameBoard;
    private Hashtable<Byte, ShogiPiece> pieces;
    private Vector<ShogiPiece> piecesForMinimax;
    private Vector<Byte> capturedPieceBlack;
    private Vector<Byte> capturedPieceWhite;
    private LinkedHashMap<String, Integer> capturedPieceWhiteHM, capturedPieceBlackHM;
    private final Time gameTimer;
    private Boolean isPlayerStarting;
    private Boolean isPlayerTurn;
    private Boolean isGameEnded;
    private Boolean GameWinner;
    private static GameSaver gameSaver;
    private static PromotionState promotionStateMap;
    private MinimaxManager manager;
    private EvaluationStrategies difficulty;
    private ShogiPiece capturedKing;
    private MutableState<Integer> counter;



    /**
     * @param isPlayerStarting : Si le joueur commence : true, si le AI commence : false
     */
    public Game(Boolean isPlayerStarting, EvaluationStrategies difficulty, MutableState<Integer> counter) {
        this.gameTimer = new Time();
        this.gameBoard = new Board();
        this.pieces = new Hashtable<>();
        this.piecesForMinimax = new Vector<>();
        this.capturedPieceBlack = new Vector<>();
        this.capturedPieceWhite = new Vector<>();
        this.isPlayerStarting = isPlayerStarting;
        this.isPlayerTurn = isPlayerStarting;
        this.isGameEnded = false;
        this.GameWinner = null;
        this.capturedKing = null;
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

        this.difficulty = difficulty;

        this.counter = counter;

        gameSaver = new GameSaver();
        promotionStateMap = new PromotionState(new HashMap<>());
    }

    /**
     * Permet de créer les pièce nécessaire au jeux
     */
    private void PieceInit() {
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

        if (isPlayerStarting) {
            ShogiPiece Roi_blanc = InitPiece.create("roisente_blanc");
            pieces.put(Roi_blanc.getID(), Roi_blanc);

            ShogiPiece Roi_noir = InitPiece.create("roigote_noir");
            pieces.put(Roi_noir.getID(), Roi_noir);
            piecesForMinimax.add(Roi_noir);

        } else {
            ShogiPiece Roi_blanc = InitPiece.create("roigote_blanc");
            pieces.put(Roi_blanc.getID(), Roi_blanc);

            ShogiPiece Roi_noir = InitPiece.create("roisente_noir");
            pieces.put(Roi_noir.getID(), Roi_noir);
            piecesForMinimax.add(Roi_noir);
        }
    }

    /**
     * Place les pièces a leur positon de départ sur l'échiquier
     */
    private void BoardInit() {
        ShogiPiece pieceToPlace;

        pieceToPlace = pieces.get((byte) PieceIDs.Pion.getValue());
        for (int i = 0; i < 9; i++) {
            gameBoard.setPieceAt(pieceToPlace, new Position(6, i));
        }

        pieceToPlace = pieces.get((byte) -PieceIDs.Pion.getValue());
        for (int i = 0; i < 9; i++) {
            gameBoard.setPieceAt(pieceToPlace, new Position(2, i));
        }

        pieceToPlace = pieces.get((byte) PieceIDs.Lance.getValue());
        gameBoard.setPieceAt(pieceToPlace, new Position(8, 0));
        gameBoard.setPieceAt(pieceToPlace, new Position(8, 8));

        pieceToPlace = pieces.get((byte) -PieceIDs.Lance.getValue());
        gameBoard.setPieceAt(pieceToPlace, new Position(0, 0));
        gameBoard.setPieceAt(pieceToPlace, new Position(0, 8));

        pieceToPlace = pieces.get((byte) PieceIDs.Chevalier.getValue());
        gameBoard.setPieceAt(pieceToPlace, new Position(8, 1));
        gameBoard.setPieceAt(pieceToPlace, new Position(8, 7));

        pieceToPlace = pieces.get((byte) -PieceIDs.Chevalier.getValue());
        gameBoard.setPieceAt(pieceToPlace, new Position(0, 1));
        gameBoard.setPieceAt(pieceToPlace, new Position(0, 7));

        pieceToPlace = pieces.get((byte) PieceIDs.GeneralArgent.getValue());
        gameBoard.setPieceAt(pieceToPlace, new Position(8, 2));
        gameBoard.setPieceAt(pieceToPlace, new Position(8, 6));

        pieceToPlace = pieces.get((byte) -PieceIDs.GeneralArgent.getValue());
        gameBoard.setPieceAt(pieceToPlace, new Position(0, 2));
        gameBoard.setPieceAt(pieceToPlace, new Position(0, 6));

        pieceToPlace = pieces.get((byte) PieceIDs.GeneralOr.getValue());
        gameBoard.setPieceAt(pieceToPlace, new Position(8, 3));
        gameBoard.setPieceAt(pieceToPlace, new Position(8, 5));

        pieceToPlace = pieces.get((byte) -PieceIDs.GeneralOr.getValue());
        gameBoard.setPieceAt(pieceToPlace, new Position(0, 3));
        gameBoard.setPieceAt(pieceToPlace, new Position(0, 5));

        pieceToPlace = pieces.get((byte) PieceIDs.Roi.getValue());
        gameBoard.setPieceAt(pieceToPlace, new Position(8, 4));

        pieceToPlace = pieces.get((byte) -PieceIDs.Roi.getValue());
        gameBoard.setPieceAt(pieceToPlace, new Position(0, 4));

        pieceToPlace = pieces.get((byte) PieceIDs.Fou.getValue());
        gameBoard.setPieceAt(pieceToPlace, new Position(7, 1));

        pieceToPlace = pieces.get((byte) -PieceIDs.Fou.getValue());
        gameBoard.setPieceAt(pieceToPlace, new Position(1, 7));

        pieceToPlace = pieces.get((byte) PieceIDs.Char.getValue());
        gameBoard.setPieceAt(pieceToPlace, new Position(7, 7));

        pieceToPlace = pieces.get((byte) -PieceIDs.Char.getValue());
        gameBoard.setPieceAt(pieceToPlace, new Position(1, 1));
    }

    /**
     * Initialise tous les variables nécessaire pour jouer la partie
     */
    public void GameInit() {
        PieceInit();
        BoardInit();
        gameTimer.startTime();
        manager = new MinimaxManager(4,true, piecesForMinimax, true, pieces, difficulty);
    }

    /**
     * Méthode qui permet d'executer le minimax selon l'état actuel de la partie
     */
    public void startMinimaxComputation() {
        manager.resetMinimax(gameBoard, capturedPieceBlackHM);
        manager.executeMinimaxAsync(this);
    }
    /**
     * Méthode callback lorsque le minimax fini ses calculs
     * */
    @Override
    public void onMoveComputed(MoveManager move){
        if(move.checkIfPieceEaten()){
            Position nextPos = move.getMove().getNextPosition();
            capturePieceAtPos(nextPos, false);
            if(promotionStateMap.isPiecePromoted(nextPos)){
                promotionStateMap.removePromotedPosition(nextPos);
            };
        }
        if(move.checkIfShouldBePromoted()){
            promotionStateMap.removePromotedPosition(move.getMove().getCurrentPosition());
            promotionStateMap.promotePiece(move.getMove().getNextPosition());
        }
        move.do_move_on_board(gameBoard);
        this.counter.setValue(this.counter.getValue() + 1) ;

        isGameEnded = isKingsAlive();
        flipPlayerTurn(false);
    }

    /**
     * Méthode qui permet de d'effectuer le déplacement d'une pièce par le joeur
     *
     * @param firstPos  : La pièce choisi,
     * @param secondPos : La nouvelle position choisi
     */
    public boolean playTurn(Position firstPos, Position secondPos) {
        boolean valid = false;
        boolean pieceIsPromoted = false;
        boolean enemyPieceToCapture = isEnemyPieceAtPos(secondPos);

        if (isPlayerPieceAtPos(firstPos) && (enemyPieceToCapture || isPositionEmpty(secondPos))) {
            ShogiPiece pieceToPlay;
            if (isPromoted(firstPos)) {
                ShogiPiece basePiece = getPieceAt(firstPos);
                pieceToPlay = pieces.get(basePiece.getID_PROMU());
                pieceIsPromoted = true;

            } else {
                pieceToPlay = getPieceAt(firstPos);
            }
            Move pieceMove = new Move(firstPos, secondPos);

            if (pieceToPlay.isValidMove(pieceMove, gameBoard)) {
                if (enemyPieceToCapture) {
                    capturePieceAtPos(secondPos, true);
                    if(promotionStateMap.isPiecePromoted(secondPos)){
                        promotionStateMap.removePromotedPosition(secondPos);
                    }
                    isGameEnded = isKingsAlive();
                }

                gameBoard.movePieceTo(firstPos, secondPos);
                gameSaver.addNewTurn(new OneTurn(pieceToPlay.getID(), firstPos, secondPos, false));
                valid = true;

                if (pieceIsPromoted) {
                    promotionStateMap.removePromotedPosition(firstPos);
                    promotionStateMap.promotePiece(secondPos);
                }
                else{
                    // PIECE ALWAYS PROMOTE
                    promotionStateMap.shouldPlayerPiecePromote(secondPos);

                }
            }
        } return valid;
    }

    /**
     * Sert a flipper le booléen de tour. Si c'est au tour du AI, appel minimax
     * */
    private void flipPlayerTurn(boolean flip){
        if(flip){
            isPlayerTurn = false;
            startMinimaxComputation();
        }
        else{
            isPlayerTurn = true;
        }
    }

    public void aiTurn() {
        flipPlayerTurn(true);
    }

    /**
     * Retourne un booléen qui représente si la partie est termine, pour la vue
     * */
    public boolean getIsGameEnded() {
        return isGameEnded;
    }

    /**
     * Retourne la classe qui possede la liste de l'historique de coup
     */
    public GameSaver getGameSaver() {
        return gameSaver;
    }

    /**
     * Retourne la classe du GameBoard
     */
    public Board getGameBoard() {
        return gameBoard;
    }

    /**
     * Retourne un booléen qui indique si c'est le tour du joueur = true ou au AI = false
     */
    public boolean getIsPlayerTurn() {
        return isPlayerTurn;
    }

    /**
     * Retourne un objet piece pour la position donné
     */
    private ShogiPiece getPieceAt(Position pos) {
        return pieces.get(gameBoard.getPieceAt(pos));
    }

    /**
     * Retourne un booléen qui indique si la position est promu
     * */
    private boolean isPromoted(Position pos) {
        return promotionStateMap.isPiecePromoted(pos);
    }

    /**
     * Retourne un Integer qui représente un Drawable pour la vue
     * */
    public Integer getPieceDrawable(Position pos) {
        Integer drawable = null;
        if (getPieceAt(pos) != null) {
            drawable = isPromoted(pos) ? getPieceAt(pos).getIMAGE_ID_PROMU() : getPieceAt(pos).getIMAGE_ID();
        }
        return drawable;
    }

    /**
     * Retourn True si c'est une pièce enemy, False si ce n'est pas une piece enemy ou pas de piece
     */
    private boolean isEnemyPieceAtPos(Position pos) {
        return gameBoard.getPieceAt(pos) < 0;
    }

    /**
     * Retourn True si une piece du joeur est a la positon, false si non
     */
    public boolean isPlayerPieceAtPos(Position pos) {
        return gameBoard.getPieceAt(pos) > 0;
    }

    /**
     * Retourn True si la case de l'échiquier est vide
     */
    private boolean isPositionEmpty(Position pos) {
        return gameBoard.getPieceAt(pos) == 0;
    }

    /**
     * Retourne true si les deux rois sont encore en vie, si au moins un roi est mort, retourne false
     */
    private boolean isKingsAlive() {
        for (byte id : capturedPieceBlack) {
            if (Math.abs(id) == PieceIDs.Roi.getValue()) {
                capturedKing = pieces.get((byte)127);
                return true;
            }
        }
        for (byte id : capturedPieceWhite) {
            if (Math.abs(id) == PieceIDs.Roi.getValue()) {
                capturedKing = pieces.get((byte) -127);
                return true;
            }
        }
        return false;
    }

    /**
     * Méthode pour capturer une pièce a la position donné.
     *
     * @param pos   : Position de la pièce a captuer,
     * @param color : True = Les blancs capture (joueur), False = Les noirs capture (AI)
     */
    private void capturePieceAtPos(Position pos, boolean color) {
        if (color) {
            capturedPieceWhite.add(gameBoard.getPieceAt(pos));
            captureWhitePiece(getPieceAt(pos).getClass().getCanonicalName());
        } else {
            capturedPieceBlack.add(gameBoard.getPieceAt(pos));
            captureBlackPiece(getPieceAt(pos).getClass().getCanonicalName());
        }
        gameBoard.removePieceAt(pos);
    }



    public boolean parachuteWhitePiece(ShogiPiece shogiPiece, Position position) {
        boolean valid = false;
        if(isDroppable(shogiPiece, position)) {
            String pieceCanonicalName = shogiPiece.getClass().getCanonicalName();
            int quantity = capturedPieceWhiteHM.get(pieceCanonicalName) - 1;
            capturedPieceWhiteHM.put(pieceCanonicalName, quantity);
            gameBoard.setPieceAt(shogiPiece, position);
            valid = true;
        }
        return  valid;
    }

    public boolean isDroppable(ShogiPiece shogiPiece, Position position) {
        String pieceCanonicalName = shogiPiece.getClass().getCanonicalName();
        final int LAST_ROW = 0;

        boolean valid = false;
        if(capturedPieceWhiteHM.containsKey(pieceCanonicalName)) {
            int quantity = capturedPieceWhiteHM.get(pieceCanonicalName);
            if(quantity > 0 && isPositionEmpty(position)) {
                valid = true;
                if(shogiPiece instanceof Pion) {

                    // TODO Vérifier si le pion fait échec et mat
                    if(position.getPosX() == LAST_ROW
                        || !gameBoard.isWhitePawnInColumn(position.getPosY())) {
                        valid = false;
                    }
                } else if (shogiPiece instanceof  Lance) {
                    if(position.getPosX() == LAST_ROW) {
                        valid = false;
                    }
                } else if (shogiPiece instanceof Chevalier) {
                    if(position.getPosX() == LAST_ROW  || position.getPosX() == LAST_ROW + 1) {
                        valid = false;
                    }
                }
            }
        }
        return valid;
    }
    public Boolean captureWhitePiece(String shogiPieceClass) {
        boolean isValid = false;
        if(capturedPieceWhiteHM.containsKey(shogiPieceClass)) {
            int quantity = capturedPieceWhiteHM.get(shogiPieceClass) + 1;
            capturedPieceWhiteHM.put(shogiPieceClass, quantity);
            isValid = true;
        }
        return isValid;
    }
    public Boolean captureBlackPiece(String shogiPieceClass) {
        boolean isValid = false;
        if(capturedPieceBlackHM.containsKey(shogiPieceClass)) {
            int quantity = capturedPieceBlackHM.get(shogiPieceClass) + 1;
            capturedPieceBlackHM.put(shogiPieceClass, quantity);
            isValid = true;
        }
        return isValid;
    }

    public boolean whoLost() {
        // sente = true ; gote = false
        return capturedKing.getNOM().equals("roisente");
    }

    public String getTimeString(){
        return gameTimer.getDisplayTime();
    }
    public LinkedHashMap<String, Integer> getCapturedPieceBlackHM() {
        return capturedPieceBlackHM;
    }
    public LinkedHashMap<String, Integer> getCapturedPieceWhiteHM() {
        return capturedPieceWhiteHM;
    }
    public HashMap<String, Boolean> getPromotionStateMap() {
        return promotionStateMap.getMap();
    }

    public ShogiPiece shouldPromote(Position position) {
        ShogiPiece shogiPiece = null;
        if(promotionStateMap.canPiecePromote(position)) {
            shogiPiece = getPieceAt(position);
        }
        return shogiPiece;
    }

    public void setGameSaver(GameSaver gameSaver) {
        Game.gameSaver = gameSaver;
    }

    public void loadSavedGame(){
        if (gameSaver != null && gameSaver.getTurnList() != null){
            GameInit();

            for (OneTurn turn : gameSaver.getTurnList().getTurnsList()){
                Position oldpos = turn.getOld_pos();
                Position newpos = turn.getNew_pos();
                byte pieceId = turn.getPiece_jouer();

                if (turn.isParachute()){
                    ShogiPiece piece = pieces.get(pieceId);
                    if (piece != null){
                        gameBoard.setPieceAt(piece, newpos);
                    }
                }else{
                    gameBoard.movePieceTo(oldpos, newpos);
                }
            }

            int nbMoves = gameSaver.getTurnList().getTurnsList().size();
            isPlayerTurn = (nbMoves % 2 == 0) == isPlayerStarting;
        }
    }
}