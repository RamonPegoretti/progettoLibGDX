package io.github.some_example_name.pieces;

import io.github.some_example_name.enums.PieceColor;

public class Rook extends Piece {

    public Rook(PieceColor color) {
        super(color);
    }

    @Override
    public String getType() {
        return "rook";
    }
}
