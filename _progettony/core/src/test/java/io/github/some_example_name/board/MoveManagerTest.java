package io.github.some_example_name.board;

import io.github.some_example_name.enums.PieceColor;
import io.github.some_example_name.pieces.Pawn;
import org.junit.Test;

import static org.junit.Assert.*;

public class MoveManagerTest {

    @Test
    public void pawnShouldMoveForwardOneSquare() {

        // create board + move manager
        ChessBoard board = new ChessBoard();
        MoveManager mm = new MoveManager(board);

        // IMPORTANT:
        // clear pawn blocking default setup for a clean test
        board.setPiece(4, 2, null);

        // place a fresh white pawn
        Pawn pawn = new Pawn(PieceColor.WHITE);
        board.setPiece(4, 1, pawn);

        // ensure it's white's turn
        while (board.getCurrentTurn() != PieceColor.WHITE) {
            board.switchTurn();
        }

        // test move: (4,1) -> (4,2)
        boolean result = mm.isLegalMove(4, 1, 4, 2);

        assertTrue("Pawn should be able to move forward one square", result);
    }

    @Test
    public void illegalMoveShouldFail() {
        ChessBoard board = new ChessBoard();
        MoveManager mm = new MoveManager(board);

        boolean result = mm.isLegalMove(0, 1, 0, 5);

        assertFalse(result);
    }
}
