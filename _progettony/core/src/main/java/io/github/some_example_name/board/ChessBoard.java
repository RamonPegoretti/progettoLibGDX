package io.github.some_example_name.board;

import io.github.some_example_name.enums.PieceColor;
import io.github.some_example_name.pieces.*;

public class ChessBoard {

    private Piece[][] board;
    private MoveManager moveManager;

    private PieceColor currentTurn = PieceColor.WHITE;

    private int enPassantX = -1;
    private int enPassantY = -1;

    private int promotionX = -1;
    private int promotionY = -1;

    private int selectedX = -1;
    private int selectedY = -1;

    public ChessBoard() {

        board = new Piece[8][8];
        moveManager = new MoveManager(this);

        setupPieces();
    }

    // =========================
    // INITIAL SETUP (FIXED)
    // =========================

    private void setupPieces() {

        // PAWNS
        for (int i = 0; i < 8; i++) {
            board[i][1] = new Pawn(PieceColor.WHITE);
            board[i][6] = new Pawn(PieceColor.BLACK);
        }

        // ROOKS
        board[0][0] = new Rook(PieceColor.WHITE);
        board[7][0] = new Rook(PieceColor.WHITE);
        board[0][7] = new Rook(PieceColor.BLACK);
        board[7][7] = new Rook(PieceColor.BLACK);

        // KNIGHTS
        board[1][0] = new Knight(PieceColor.WHITE);
        board[6][0] = new Knight(PieceColor.WHITE);
        board[1][7] = new Knight(PieceColor.BLACK);
        board[6][7] = new Knight(PieceColor.BLACK);

        // BISHOPS
        board[2][0] = new Bishop(PieceColor.WHITE);
        board[5][0] = new Bishop(PieceColor.WHITE);
        board[2][7] = new Bishop(PieceColor.BLACK);
        board[5][7] = new Bishop(PieceColor.BLACK);

        // QUEENS
        board[3][0] = new Queen(PieceColor.WHITE);
        board[3][7] = new Queen(PieceColor.BLACK);

        // KINGS
        board[4][0] = new King(PieceColor.WHITE);
        board[4][7] = new King(PieceColor.BLACK);
    }

    // =========================
    // BOARD ACCESS
    // =========================

    public Piece getPiece(int x, int y) {
        return board[x][y];
    }

    public void setPiece(int x, int y, Piece piece) {
        board[x][y] = piece;
    }

    public Piece[][] getBoard() {
        return board;
    }

    // =========================
    // TURN
    // =========================

    public PieceColor getCurrentTurn() {
        return currentTurn;
    }

    public void switchTurn() {
        currentTurn = (currentTurn == PieceColor.WHITE)
            ? PieceColor.BLACK
            : PieceColor.WHITE;
    }

    // =========================
    // MOVE MANAGER
    // =========================

    public MoveManager getMoveManager() {
        return moveManager;
    }

    // =========================
    // EN PASSANT
    // =========================

    public int getEnPassantX() {
        return enPassantX;
    }

    public int getEnPassantY() {
        return enPassantY;
    }

    public void setEnPassant(int x, int y) {
        enPassantX = x;
        enPassantY = y;
    }

    public void resetEnPassant() {
        enPassantX = -1;
        enPassantY = -1;
    }

    // =========================
    // PROMOTION
    // =========================

    public void setPromotionPending(int x, int y) {
        promotionX = x;
        promotionY = y;
    }

    public int getPromotionX() {
        return promotionX;
    }

    public int getPromotionY() {
        return promotionY;
    }

    // =========================
    // SELECTION
    // =========================

    public void setSelected(int x, int y) {
        selectedX = x;
        selectedY = y;
    }

    public void clearSelected() {
        selectedX = -1;
        selectedY = -1;
    }

    public boolean isSelected(int x, int y) {
        return selectedX == x && selectedY == y;
    }
}
