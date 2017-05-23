package com.arenafighter.game.States;

import com.arenafighter.game.ArenaFighter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Frank on 2017-05-10.
 */

public class WinState extends State {

    private Texture screen;
    private Texture white;
    private int whiteX;
    private Texture iconW;

    private Texture red;
    private int redX;
    private Texture iconR;

    private Texture blue;
    private int blueX;
    private Texture iconB;

    private Texture green;
    private int greenX;
    private Texture iconG;

    private Texture menu;
    private int menuX;
    private int menuY;

    private int x;
    private int y;



    public WinState(GameStateManager gsm) {
        super(gsm);

        screen = new Texture("Win Screen.png");
        x = ArenaFighter.WIDTH/2;
        y = 847;

        white = new Texture("White.png");
        whiteX = x - white.getWidth()/2;
        iconW = new Texture("Big White.png");

        red = new Texture("Red.png");
        redX = x - red.getWidth()/2;
        iconR = new Texture("Big Red.png");

        blue = new Texture("Blue.png");
        blueX = x - blue.getWidth()/2;
        iconB = new Texture("Big Blue.png");

        green = new Texture("Green.png");
        greenX = x - green.getWidth()/2;
        iconG = new Texture("Big Green.png");

        menu = new Texture("Pause Menu.png");
        menuX = 60;
        menuY = 60;


    }


    @Override
    public void handleInput(){

        if(Gdx.input.isTouched()) {
            System.out.println(Gdx.input.getX());
            System.out.println(ArenaFighter.HEIGHT - Gdx.input.getY());

            //Have to "flip" the Y input, since inputs start at top-left while textures start at bottom-left
            float y = ArenaFighter.HEIGHT - Gdx.input.getY();

            Rectangle menuBounds = new Rectangle(menuX, menuY, menu.getWidth(), menu.getHeight());

            Vector2 tmp = new Vector2(Gdx.input.getX(), y/*Gdx.input.getY()*/);

            if(menuBounds.contains(tmp.x, tmp.y)) {
                // you are touching your texture
                System.out.println("MENU touched");
                gsm.set(ArenaFighter.menuState);
            }


        }
    }

    @Override
    public void update(float dt) {

        handleInput();

    }

    @Override
    public void render(SpriteBatch sb) {

        sb.begin();
        sb.draw(screen , 0, 0);

        if(PlayState.score1 == PlayState.maxWins) {
            sb.draw(white, whiteX, y);
            sb.draw(iconW, (ArenaFighter.WIDTH - iconW.getWidth())/2, 50);
        }

        if(PlayState.score2 == PlayState.maxWins) {
            sb.draw(red, redX, y);
            sb.draw(iconR, (ArenaFighter.WIDTH - iconR.getWidth())/2, 50);
        }

        if(PlayState.score3 == PlayState.maxWins) {
            sb.draw(blue, blueX, y);
            sb.draw(iconB, (ArenaFighter.WIDTH - iconB.getWidth())/2, 50);
        }

        if(PlayState.score4 == PlayState.maxWins) {
            sb.draw(green, greenX, y);
            sb.draw(iconG, (ArenaFighter.WIDTH - iconG.getWidth())/2, 50);
        }

        sb.draw(menu, menuX, menuY);
        sb.end();

    }

    @Override
    public void dispose() {

    }



}
