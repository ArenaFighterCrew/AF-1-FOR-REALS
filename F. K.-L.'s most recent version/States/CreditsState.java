package com.arenafighter.game.States;

import com.arenafighter.game.ArenaFighter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Frank on 2017-04-06.
 */

public class CreditsState extends State {

    private Texture background;
    private Texture back;

    private int backX;
    private int backY;

    public CreditsState(GameStateManager gsm) {
        super(gsm);
        background = new Texture("Credits Page.png");
        back = new Texture("Back Button.png");

        backX = (int)(104/1440.0*1080);
        backY = (int)(1227/1440.0*1080);
    }

    @Override
    public void handleInput(){

        if(Gdx.input.isTouched()) {
            System.out.println(Gdx.input.getX());
            System.out.println(ArenaFighter.HEIGHT - Gdx.input.getY());

            //Have to "flip" the Y input, since inputs start at top-left while textures start at bottom-left
            float y = ArenaFighter.HEIGHT - Gdx.input.getY();

            Rectangle backBounds = new Rectangle(backX, backY, /*285*/back.getWidth(), back.getHeight()/*152*/);

            Vector2 tmp = new Vector2(Gdx.input.getX(), y/*Gdx.input.getY()*/);

            if(backBounds.contains(tmp.x, tmp.y)) {
                // you are touching your texture
                System.out.println("BACK touched");
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
        sb.draw(background, 0, 0);
        sb.draw(back, backX, backY);
        sb.end();

    }

    @Override
    public void dispose() {
        //background.dispose();
        //back.dispose();
        System.out.println("Credits Disposed");
    }

}

