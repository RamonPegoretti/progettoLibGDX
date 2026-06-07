package io.github.some_example_name.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import io.github.some_example_name.board.ChessBoard;
import io.github.some_example_name.pieces.Piece;

public class BoardRenderer {

    private ChessBoard board;

    // WORLD CAMERA (chess only)
    private OrthographicCamera camera;

    // ASSETS
    private Texture boardTexture;
    private Texture[][] pieceTextures;

    private BitmapFont font;

    // SCREEN UI LAYOUT
    private float panelX;
    private float panelWidth;

    public BoardRenderer(ChessBoard board) {
        this.board = board;

        font = new BitmapFont();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 8, 8);
        camera.update();

        boardTexture = new Texture("ui/board.png");

        loadPieces();
    }

    private void loadPieces() {

        pieceTextures = new Texture[2][6];

        String[] names = {
            "pawn", "rook", "knight", "bishop", "queen", "king"
        };

        for (int i = 0; i < names.length; i++) {
            pieceTextures[0][i] = new Texture("pieces/white/" + names[i] + ".png");
            pieceTextures[1][i] = new Texture("pieces/black/" + names[i] + ".png");
        }
    }

    public void render(SpriteBatch batch) {

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        panelWidth = w * 0.25f;
        panelX = w - panelWidth;

        // CLEAR
        Gdx.gl.glClearColor(0.12f, 0.12f, 0.14f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // =========================
        // WORLD (BOARD)
        // =========================
        batch.setProjectionMatrix(camera.combined);

        batch.begin();

        batch.draw(boardTexture, 0, 0, 8, 8);
        drawPieces(batch);

        batch.end();

        // =========================
        // SCREEN UI (PANEL)
        // =========================
        batch.setProjectionMatrix(new com.badlogic.gdx.math.Matrix4().idt());

        batch.begin();

        drawPanel(batch, w, h);

        batch.end();
    }

    private void drawPieces(SpriteBatch batch) {

        Piece[][] b = board.getBoard();

        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {

                Piece p = b[x][y];
                if (p == null) continue;

                Texture tex = getTexture(p);

                batch.draw(tex, x, y, 1, 1);
            }
        }
    }

    private Texture getTexture(Piece p) {

        int c = p.isWhite() ? 0 : 1;

        switch (p.getType().toLowerCase()) {
            case "pawn": return pieceTextures[c][0];
            case "rook": return pieceTextures[c][1];
            case "knight": return pieceTextures[c][2];
            case "bishop": return pieceTextures[c][3];
            case "queen": return pieceTextures[c][4];
            case "king": return pieceTextures[c][5];
        }

        return null;
    }

    // =========================
    // SIDE PANEL (SCREEN SPACE)
    // =========================
    private void drawPanel(SpriteBatch batch, float w, float h) {

        float x = panelX;

        font.draw(batch, "Move History", x + 20, h - 40);
        font.draw(batch, "WHITE 10:00", x + 20, h - 100);
        font.draw(batch, "BLACK 10:00", x + 20, 80);

        // optional background rectangle (simple fake UI)
        font.draw(batch, "----------------------", x + 20, h - 140);
    }

    // =========================
    // INPUT HELPERS
    // =========================
    public int screenToBoardX(int screenX, int screenY) {
        com.badlogic.gdx.math.Vector3 v =
            camera.unproject(new com.badlogic.gdx.math.Vector3(screenX, screenY, 0));
        return (int) v.x;
    }

    public int screenToBoardY(int screenX, int screenY) {
        com.badlogic.gdx.math.Vector3 v =
            camera.unproject(new com.badlogic.gdx.math.Vector3(screenX, screenY, 0));
        return (int) v.y;
    }

    public boolean isInsideBoard(int x, int y) {
        return x >= 0 && x < 8 && y >= 0 && y < 8;
    }

    public OrthographicCamera getCamera() {
        return camera;
    }
}
