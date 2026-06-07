package io.github.some_example_name.pieces;

import io.github.some_example_name.enums.PieceColor;

public class Knight extends Piece {

    public Knight(PieceColor color) {
        super(color);
    }

    @Override
    public String getType() {
        return "knight";
    }
}
