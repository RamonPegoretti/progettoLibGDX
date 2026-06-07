package io.github.some_example_name.pieces;

import io.github.some_example_name.enums.PieceColor;

public class Pawn extends Piece {

    public Pawn(PieceColor color) {
        super(color);
    }

    @Override
    public String getType() {
        return "pawn";
    }
}
