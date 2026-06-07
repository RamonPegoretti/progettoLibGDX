package io.github.some_example_name.pieces;

import io.github.some_example_name.enums.PieceColor;

public abstract class Piece {

    protected PieceColor color;

    public Piece(PieceColor color) {
        this.color = color;
    }

    public PieceColor getColor() {
        return color;
    }

    public abstract String getType();

    private boolean hasMoved = false;

    public boolean hasMoved() {
        return hasMoved;
    }

    public void setMoved(boolean moved) {
        this.hasMoved = moved;
    }

    public boolean isWhite() {
        return getColor() == PieceColor.WHITE;
    }
}
