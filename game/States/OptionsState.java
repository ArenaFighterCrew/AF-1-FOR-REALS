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

public class OptionsState extends State{

    private Texture background;
    private Texture back;
    private Texture fillM;
    private Texture fillS;
    private Texture fillV;


    public static int opened = 0;
    //public int mToggle = GameStateManager.optionsM;
    //public int sToggle = GameStateManager.optionsS;
    //public int vToggle = GameStateManager.optionsV;

    private int backX;
    private int backY;
    private int mX;
    private int mY;
    private int sX;
    private int sY;
    private int vX;
    private int vY;
    private int fill;


    public OptionsState(GameStateManager gsm) {

        super(gsm);
        background = new Texture("Options Page.png");
        back = new Texture("Back Button.png");
        fillM = new Texture("Fill.png");
        fillS = new Texture("Fill.png");
        fillV = new Texture("Fill.png");

        /*opened = 0;
        mToggle = 1;
        sToggle = 1;
        vToggle = 1;*/

        backX = (int)(104/1440.0*1080);
        backY = (int)(1227/1440.0*1080);
        mX = 815;//(int)(1080/1440.0*1080);
        mY = 547;//(int)(724/1440.0*1080);
        sX = 733;
        sY = 351;
        vX = 737;
        vY = 153;
        fill = (int)(70/1440.0*1080)-10;
    }

    @Override
    public void handleInput(){


        if(Gdx.input.justTouched()/*.isTouched()*/) {
            System.out.println(Gdx.input.getX());
            System.out.println(ArenaFighter.HEIGHT - Gdx.input.getY());

            //Have to "flip" the Y input, since inputs start at top-left while textures start at bottom-left
            float y = ArenaFighter.HEIGHT - Gdx.input.getY();

            Rectangle backBounds = new Rectangle(backX, backY, back.getWidth(), back.getHeight());
            Rectangle mBounds = new Rectangle(mX, mY, fill, fill);
            Rectangle sBounds = new Rectangle(sX, sY, fill, fill);
            Rectangle vBounds = new Rectangle(vX, vY, fill, fill);


            Vector2 tmp = new Vector2(Gdx.input.getX(), y/*Gdx.input.getY()*/);

            if(backBounds.contains(tmp.x, tmp.y)) {
                // you are touching your texture
                System.out.println("BACK touched");
                if (ArenaFighter.fromPause == true) {
                    ArenaFighter.fromPause = false;
                    gsm.set(MenuState.playState);
                }
                else
                    gsm.set(ArenaFighter.menuState);
            }

            if(mBounds.contains(tmp.x, tmp.y)) {
                // you are touching your texture
                System.out.println("MUSIC touched");
                if(ArenaFighter.mToggle)
                    ArenaFighter.mToggle = false;
                else if(!ArenaFighter.mToggle)
                    ArenaFighter.mToggle = true;
                //gsm.set(new MenuState(gsm));
            }

            if(sBounds.contains(tmp.x, tmp.y)) {
                // you are touching your texture
                System.out.println("SFX touched");
                if(ArenaFighter.sToggle)
                    ArenaFighter.sToggle = false;
                else if(!ArenaFighter.sToggle)
                    ArenaFighter.sToggle = true;
                //gsm.set(new MenuState(gsm));
            }

            if(vBounds.contains(tmp.x, tmp.y)) {
                // you are touching your texture
                System.out.println("VFX touched");
                if(ArenaFighter.vToggle)
                    ArenaFighter.vToggle = false;
                else if(!ArenaFighter.vToggle)
                    ArenaFighter.vToggle = true;
                //gsm.set(new MenuState(gsm));
            }

        }

    }

    @Override
    public void update(float dt) {

        handleInput();

        if(!ArenaFighter.mToggle)
            ArenaFighter.music.setVolume(0);

        if(ArenaFighter.mToggle)
            ArenaFighter.music.setVolume(0.2f);

        /*if(ArenaFighter.sToggle == 0)
            ArenaFighter.death.setVolume(ArenaFighter.deathID, 0);

        if(ArenaFighter.sToggle == 1)
            ArenaFighter.death.setVolume(ArenaFighter.deathID, 0.2f);*/


    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(background, 0, 0);
        sb.draw(back, backX, backY);

        if(ArenaFighter.mToggle)
            sb.draw(fillM, mX, mY, fill, fill);

        if(ArenaFighter.sToggle)
            sb.draw(fillS, sX, sY, fill+1, fill);

        if(ArenaFighter.vToggle)
            sb.draw(fillV, vX, vY, fill, fill);

        sb.end();


    }

    @Override
    public void dispose() {
        //opened = 1;
        //background.dispose();
        //back.dispose();
        //fillM.dispose();
        //fillS.dispose();
        //fillV.dispose();
        System.out.println("Options Disposed");

    }



}
