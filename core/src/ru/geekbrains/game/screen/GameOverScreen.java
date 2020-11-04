package ru.geekbrains.game.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.game.base.BaseScreen;
import ru.geekbrains.game.math.Rect;
import ru.geekbrains.game.sprite.Background;
import ru.geekbrains.game.sprite.ExitButton;
import ru.geekbrains.game.sprite.GameOverTxt;
import ru.geekbrains.game.sprite.NewGameButton;
import ru.geekbrains.game.sprite.PlayButton;
import ru.geekbrains.game.sprite.Star;

public class GameOverScreen extends BaseScreen {

    private static final int STAR_COUNT = 256;

    private Game game;

    private TextureAtlas atlas;
    private TextureAtlas menuAtlas;
    private Texture bg;
    private Music music;
    private Background background;
    private Star[] stars;
    private ExitButton exitButton;
    private GameOverTxt gameOverTxt;
    private NewGameButton playButton;

    public GameOverScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        super.show();
        atlas = new TextureAtlas("textures/mainAtlas.tpack");
        menuAtlas = new TextureAtlas("textures/menuAtlas.tpack");
        bg = new Texture("textures/bg.png");
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/music.mp3"));
        background = new Background(bg);
        stars = new Star[STAR_COUNT];
        for (int i = 0; i < STAR_COUNT; i++) {
            stars[i] = new Star(atlas);
        }
        exitButton = new ExitButton(menuAtlas);
        gameOverTxt = new GameOverTxt(atlas);
        playButton = new NewGameButton(atlas, game);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        draw();
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        gameOverTxt.resize(worldBounds);
        for (Star star : stars) {
            star.resize(worldBounds);
        }
        exitButton.resize(worldBounds);
        playButton.resize(worldBounds);
    }

    @Override
    public void dispose() {
        bg.dispose();
        atlas.dispose();
        super.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        exitButton.touchDown(touch, pointer, button);
        playButton.touchDown(touch, pointer, button);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        exitButton.touchUp(touch, pointer, button);
        playButton.touchUp(touch, pointer, button);
        return false;
    }

    private void update(float delta) {
        for (Star star : stars) {
            star.update(delta);
        }
        gameOverTxt.update(delta);
    }

    private void draw() {
        batch.begin();
        background.draw(batch);
        for (Star star : stars) {
            star.draw(batch);
        }
        gameOverTxt.draw(batch);
        exitButton.draw(batch);
        playButton.draw(batch);
        batch.end();
    }
}
