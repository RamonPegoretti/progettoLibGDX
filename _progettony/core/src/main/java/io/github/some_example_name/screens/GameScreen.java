package io.github.some_example_name.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import io.github.some_example_name.board.ChessBoard;
import io.github.some_example_name.input.InputHandler;
import io.github.some_example_name.render.BoardRenderer;
import io.github.some_example_name.save.PGNExporter;
import io.github.some_example_name.save.PGNRecorder;

public class GameScreen implements Screen {

    private ChessBoard board;
    private BoardRenderer renderer;
    private InputHandler input;

    private SpriteBatch batch;

    private PGNRecorder pgn;

    @Override
    public void show() {

        board = new ChessBoard();
        renderer = new BoardRenderer(board);

        batch = new SpriteBatch();

        pgn = new PGNRecorder();
        input = new InputHandler(board, renderer, pgn);

        Gdx.input.setInputProcessor(input);
    }

    @Override
    public void render(float delta) {
        renderer.render(batch);
        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.S)) {
            PGNExporter.save("game.pgn", pgn.getMoves());
        }
    }

    @Override public void resize(int w, int h) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}

    @Override
    public void dispose() {
        PGNExporter.save("game.pgn", pgn.getMoves());
        batch.dispose();
    }
}
