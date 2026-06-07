package io.github.some_example_name.save;

import java.util.ArrayList;
import java.util.List;

/**
 * Stores game moves without touching core chess logic.
 */
public class PGNRecorder {

    private final List<String> moves = new ArrayList<>();
    private int turn = 1;
    private boolean whiteMove = true;

    public void recordMove(String move) {

        if (whiteMove) {
            moves.add(turn + ". " + move);
        } else {
            moves.add(move);
            turn++;
        }

        whiteMove = !whiteMove;
    }

    public List<String> getMoves() {
        return moves;
    }

    public void reset() {
        moves.clear();
        turn = 1;
        whiteMove = true;
    }
}
