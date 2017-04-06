package com.arenafighter.game;

import com.arenafighter.game.States.GameStateManager;
import com.arenafighter.game.States.MenuState;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GLTexture;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ArenaFighter extends ApplicationAdapter {

	//private Texture mainMenu;


	public static final int WIDTH = 2560;//1920;//2880;
	public static final int HEIGHT = 1440;//1080;//1620;

	public static final String TITLE = "AF-1";

	private GameStateManager gsm;
	private SpriteBatch batch;
	//Texture img;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		gsm = new GameStateManager();
		//img = new Texture("badlogic.jpg");
		Gdx.gl.glClearColor(1, 0, 0, 1);
		gsm.push(new MenuState(gsm));
	}

	@Override
	public void render () {
		//Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render(batch);
		/*
		batch.begin();
		batch.draw(img, 0, 0);
		batch.end();
		*/
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		//img.dispose();
	}
}
