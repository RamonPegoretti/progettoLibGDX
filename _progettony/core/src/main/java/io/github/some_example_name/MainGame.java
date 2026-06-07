package io.github.some_example_name;

import com.badlogic.gdx.Game;
import io.github.some_example_name.screens.GameScreen;

public class MainGame extends Game {

    @Override
    public void create() {
        setScreen(new GameScreen());
    }
}
