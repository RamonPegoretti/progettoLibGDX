package io.github.some_example_name.pieces;

import io.github.some_example_name.enums.PieceColor;

public class Bishop extends Piece {

    public Bishop(PieceColor color) {
        super(color);
    }

    @Override
    public String getType() {
        return "bishop";
    }
}
