package io.github.some_example_name.pieces;

import io.github.some_example_name.enums.PieceColor;

public class Queen extends Piece {

    public Queen(PieceColor color) {
        super(color);
    }

    @Override
    public String getType() {
        return "queen";
    }
}
