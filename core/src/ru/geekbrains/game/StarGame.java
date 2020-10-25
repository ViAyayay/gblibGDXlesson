package ru.geekbrains.game;

import com.badlogic.gdx.Game;

import ru.geekbrains.game.screen.MenuScreen;

public class StarGame extends Game {

	@Override
	public void create() {
		setScreen(new MenuScreen(this));
	}
}
