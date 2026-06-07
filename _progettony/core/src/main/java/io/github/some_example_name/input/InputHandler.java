package io.github.some_example_name.input;

import com.badlogic.gdx.InputAdapter;

import io.github.some_example_name.board.ChessBoard;
import io.github.some_example_name.board.MoveManager;
import io.github.some_example_name.render.BoardRenderer;
import io.github.some_example_name.save.PGNRecorder;

public class InputHandler extends InputAdapter {

    private ChessBoard board;
    private BoardRenderer renderer;
    private MoveManager moveManager;

    private PGNRecorder pgn;

    private int selectedX = -1;
    private int selectedY = -1;

    public InputHandler(ChessBoard board, BoardRenderer renderer, PGNRecorder pgn) {
        this.board = board;
        this.renderer = renderer;
        this.moveManager = board.getMoveManager();
        this.pgn = pgn;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        // Convert screen -> world (0..8 board space)
        int x = renderer.screenToBoardX(screenX, screenY);
        int y = renderer.screenToBoardY(screenX, screenY);

        // ignore clicks outside board
        if (!renderer.isInsideBoard(x, y)) {
            return false;
        }

        // =========================
        // SELECT PIECE
        // =========================
        if (selectedX == -1) {

            if (board.getPiece(x, y) != null) {
                selectedX = x;
                selectedY = y;

                board.setSelected(x, y);
            }

            return true;
        }

        // =========================
        // TRY MOVE
        // =========================

        if (moveManager.isLegalMove(selectedX, selectedY, x, y)) {
            moveManager.applyMove(selectedX, selectedY, x, y);
            String move =
                "" + (char)('a' + selectedX) + (selectedY + 1)
                    + "-"
                    + (char)('a' + x) + (y + 1);
            if (pgn != null) {
                pgn.recordMove(move);
            }
        }

        // clear selection
        selectedX = -1;
        selectedY = -1;
        board.clearSelected();

        return true;
    }
}
