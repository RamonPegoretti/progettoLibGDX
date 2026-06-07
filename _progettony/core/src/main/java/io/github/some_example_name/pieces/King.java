package io.github.some_example_name.pieces;

import io.github.some_example_name.enums.PieceColor;

public class King extends Piece {

    public King(PieceColor color) {
        super(color);
    }

    @Override
    public String getType() {
        return "king";
    }
}
