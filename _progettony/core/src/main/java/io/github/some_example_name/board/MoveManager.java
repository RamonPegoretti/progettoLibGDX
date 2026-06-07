package io.github.some_example_name.board;

import io.github.some_example_name.enums.PieceColor;
import io.github.some_example_name.pieces.Piece;

public class MoveManager {

    private ChessBoard board;

    public MoveManager(ChessBoard board) {
        this.board = board;
    }

    // =========================
    // LEGAL MOVE
    // =========================

    public boolean isLegalMove(int sx, int sy, int dx, int dy) {

        Piece piece = board.getPiece(sx, sy);

        if (piece == null) return false;

        // TURN CHECK (SAFE FIX)
        if (piece.getColor() != board.getCurrentTurn()) {
            return false;
        }

        if (!isPseudoLegalMove(sx, sy, dx, dy)) {
            return false;
        }

        if (piece.getType().equalsIgnoreCase("king")
            && Math.abs(dx - sx) == 2) {

            return canCastle(sx, sy, dx, dy);
        }

        return !wouldLeaveKingInCheck(sx, sy, dx, dy);
    }

    // =========================
    // APPLY MOVE
    // =========================

    public void applyMove(int sx, int sy, int dx, int dy) {

        Piece piece = board.getPiece(sx, sy);

        boolean isPawn = piece.getType().equalsIgnoreCase("pawn");

        int epX = board.getEnPassantX();
        int epY = board.getEnPassantY();

        // EN PASSANT
        if (isPawn && dx == epX && dy == epY) {
            int dir = piece.getColor() == PieceColor.WHITE ? 1 : -1;
            board.setPiece(dx, dy - dir, null);
        }

        board.resetEnPassant();

        // CASTLING
        if (piece.getType().equalsIgnoreCase("king")) {

            int row = sy;

            if (dx == sx + 2) {
                Piece rook = board.getPiece(7, row);
                board.setPiece(5, row, rook);
                board.setPiece(7, row, null);
            }

            if (dx == sx - 2) {
                Piece rook = board.getPiece(0, row);
                board.setPiece(3, row, rook);
                board.setPiece(0, row, null);
            }
        }

        // MOVE
        board.setPiece(dx, dy, piece);
        board.setPiece(sx, sy, null);

        // EN PASSANT SETUP
        if (isPawn) {

            int dir = piece.getColor() == PieceColor.WHITE ? 1 : -1;

            if (Math.abs(dy - sy) == 2) {
                board.setEnPassant(sx, sy + dir);
            }
        }

        // PROMOTION
        if (isPawn && (dy == 7 || dy == 0)) {
            board.setPromotionPending(dx, dy);
        }

        // TURN SWITCH
        board.switchTurn();
    }

    // =========================
    // PSEUDO LEGAL (UNCHANGED LOGIC)
    // =========================

    public boolean isPseudoLegalMove(int sx, int sy, int dx, int dy) {

        Piece piece = board.getPiece(sx, sy);
        if (piece == null) return false;

        Piece target = board.getPiece(dx, dy);

        if (target != null && target.getColor() == piece.getColor()) {
            return false;
        }

        int dxA = Math.abs(dx - sx);
        int dyA = Math.abs(dy - sy);

        switch (piece.getType().toLowerCase()) {

            case "pawn":
                return pawnMove(piece, sx, sy, dx, dy, target);

            case "rook":
                return (sx == dx || sy == dy) && clear(sx, sy, dx, dy);

            case "bishop":
                return dxA == dyA && clear(sx, sy, dx, dy);

            case "queen":
                return (sx == dx || sy == dy || dxA == dyA) && clear(sx, sy, dx, dy);

            case "knight":
                return (dxA == 2 && dyA == 1) || (dxA == 1 && dyA == 2);

            case "king":
                return dxA <= 1 && dyA <= 1 || (dyA == 0 && dxA == 2);
        }

        return false;
    }

    // =========================
    // PAWN
    // =========================

    private boolean pawnMove(Piece piece, int sx, int sy, int dx, int dy, Piece target) {

        int dir = piece.getColor() == PieceColor.WHITE ? 1 : -1;
        int startRow = piece.getColor() == PieceColor.WHITE ? 1 : 6;

        if (sx == dx && target == null) {

            if (dy - sy == dir) return true;

            if (sy == startRow
                && dy - sy == 2 * dir
                && board.getPiece(sx, sy + dir) == null) {
                return true;
            }
        }

        if (Math.abs(dx - sx) == 1 && dy - sy == dir) {

            if (target != null) return true;

            return dx == board.getEnPassantX()
                && dy == board.getEnPassantY();
        }

        return false;
    }

    // =========================
    // PATH CLEAR
    // =========================

    private boolean clear(int sx, int sy, int dx, int dy) {

        int stepX = Integer.compare(dx, sx);
        int stepY = Integer.compare(dy, sy);

        int x = sx + stepX;
        int y = sy + stepY;

        while (x != dx || y != dy) {

            if (board.getPiece(x, y) != null) {
                return false;
            }

            x += stepX;
            y += stepY;
        }

        return true;
    }

    // =========================
    // CHECK LOGIC (UNCHANGED STYLE)
    // =========================

    private boolean wouldLeaveKingInCheck(int sx, int sy, int dx, int dy) {

        Piece moving = board.getPiece(sx, sy);
        Piece captured = board.getPiece(dx, dy);

        board.setPiece(dx, dy, moving);
        board.setPiece(sx, sy, null);

        boolean check = isKingInCheck(moving.getColor());

        board.setPiece(sx, sy, moving);
        board.setPiece(dx, dy, captured);

        return check;
    }

    private boolean isKingInCheck(PieceColor color) {

        int kingX = -1, kingY = -1;

        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {

                Piece p = board.getPiece(x, y);

                if (p != null && p.getColor() == color
                    && p.getType().equalsIgnoreCase("king")) {

                    kingX = x;
                    kingY = y;
                }
            }
        }

        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {

                Piece p = board.getPiece(x, y);

                if (p == null || p.getColor() == color) continue;

                if (canAttack(p, x, y, kingX, kingY)) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean canAttack(Piece p, int sx, int sy, int dx, int dy) {

        int dxA = Math.abs(dx - sx);
        int dyA = Math.abs(dy - sy);

        switch (p.getType().toLowerCase()) {

            case "pawn":
                int dir = p.getColor() == PieceColor.WHITE ? 1 : -1;
                return (dx - sx == 1 || dx - sx == -1) && dy - sy == dir;

            case "rook":
                return (sx == dx || sy == dy) && clear(sx, sy, dx, dy);

            case "bishop":
                return dxA == dyA && clear(sx, sy, dx, dy);

            case "queen":
                return (sx == dx || sy == dy || dxA == dyA) && clear(sx, sy, dx, dy);

            case "knight":
                return (dxA == 2 && dyA == 1) || (dxA == 1 && dyA == 2);

            case "king":
                return dxA <= 1 && dyA <= 1;
        }

        return false;
    }

    // =========================
    // CASTLING
    // =========================

    private boolean canCastle(int sx, int sy, int dx, int dy) {

        if (isKingInCheck(board.getPiece(sx, sy).getColor())) {
            return false;
        }

        if (dx == 6) {
            if (board.getPiece(5, sy) != null) return false;
            if (board.getPiece(6, sy) != null) return false;
            if (board.getPiece(7, sy) == null) return false;

            return true;
        }

        if (dx == 2) {
            if (board.getPiece(1, sy) != null) return false;
            if (board.getPiece(2, sy) != null) return false;
            if (board.getPiece(3, sy) != null) return false;
            if (board.getPiece(0, sy) == null) return false;

            return true;
        }

        return false;
    }
}
