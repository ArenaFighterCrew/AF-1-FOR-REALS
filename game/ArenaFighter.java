package com.arenafighter.game;

import com.arenafighter.game.States.GameStateManager;
import com.arenafighter.game.States.MenuState;
import com.arenafighter.game.States.PauseState;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GLTexture;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ArenaFighter extends ApplicationAdapter {

	//private Texture mainMenu;


	public static final int WIDTH = 1920;//2560;//2880;
	public static final int HEIGHT = 1080;//1440;//1620;
	public static final String TITLE = "AF-1";

	public static boolean mToggle = true;
	public static boolean sToggle = true;
	public static boolean vToggle = true;

	public static boolean rToggle = true;
	public static boolean cToggle = false;
	public static boolean bToggle = false;

	public static boolean eToggle = true;
	public static boolean meToggle = false;
	public static boolean hToggle = false;

	public static boolean toggle3 = true;
	public static boolean toggle5 = false;
	public static boolean toggle10 = false;

	public static boolean atMenu = true;

	public static int maxWins = 3;
	public static int mapType = 1;

	public static Texture border;
	public static Texture circle;
	public static int radius;

	public static MenuState menuState;
	//public static PauseState pauseState;
	public static boolean fromPause;

	//Methods of Steven added for testing

	public static Music music;
	public static Sound death;
	public static Sound beep;
	public static Sound airhorn;

	public static long deathID;

	private GameStateManager gsm;
	private SpriteBatch batch;
	//Texture img;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		gsm = new GameStateManager();
		//img = new Texture("badlogic.jpg");

		music = Gdx.audio.newMusic(Gdx.files.internal("Parasyte Complex.mp3"));
		music.setLooping(true);
		music.setVolume(0.2f);
		music.play();

		death = Gdx.audio.newSound(Gdx.files.internal("Disabled.wav"));
		beep = Gdx.audio.newSound(Gdx.files.internal("Beep.wav"));
		airhorn = Gdx.audio.newSound(Gdx.files.internal("Airhorn.wav"));
		//death.loop();
		//death.play(2.0f);

		/*deathID = death.play();
		death.stop(deathID);
		death.setVolume(deathID, 0.2f);*/

		Gdx.gl.glClearColor(1, 0, 0, 1);
		menuState = new MenuState(gsm);
		fromPause = false;
		border = new Texture("Border.png");
		circle = new Texture("White Circle.png");
		radius = circle.getWidth()/2;
		//pauseState = new PauseState(gsm);
		gsm.push(menuState);
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
		super.dispose();
		music.dispose();
		//batch.dispose();
		//img.dispose();
	}
}
