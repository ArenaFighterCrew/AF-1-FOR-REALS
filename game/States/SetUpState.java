package com.arenafighter.game.States;

import com.arenafighter.game.ArenaFighter;
import com.arenafighter.game.Sprites.AIFighter;
import com.arenafighter.game.Sprites.MapManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.awt.Menu;

/**
 * Created by Frank on 2017-05-21.
 */

public class SetUpState extends State{

    private Texture background;
    private Texture back;
    private Texture play;

    private Texture fillR;
    private Texture fillC;
    private Texture fillB;

    private Texture fillE;
    private Texture fillM;
    private Texture fillH;

    private Texture fill3;
    private Texture fill5;
    private Texture fill10;

    private int backX;
    private int backY;

    private int playX;
    private int playY;

    private int rX;
    private int rY;
    private int cX;
    private int cY;
    private int bX;
    private int bY;

    private int eX;
    private int eY;
    private int mX;
    private int mY;
    private int hX;
    private int hY;

    private int X3;
    private int Y3;
    private int X5;
    private int Y5;
    private int X10;
    private int Y10;

    private int fill;

    public SetUpState(GameStateManager gsm) {
        super(gsm);

        background = new Texture("Set Up.png");
        back = new Texture("Back Button.png");
        play = new Texture("Play Text.png");

        fillR = new Texture("Fill.png");
        fillC = new Texture("Fill.png");
        fillB = new Texture("Fill.png");

        fillE = new Texture("Fill.png");
        fillM = new Texture("Fill.png");
        fillH = new Texture("Fill.png");

        fill3 = new Texture("Fill.png");
        fill5 = new Texture("Fill.png");
        fill10 = new Texture("Fill.png");

        backX = (int)(104/1440.0*1080);
        backY = (int)(1227/1440.0*1080);

        playX = 1541;
        playY = 893;

        rX = 163;
        rY = 530;
        cX = 228;
        cY = 341;
        bX = 275;
        bY = 148;

        eX = 866;
        eY = 528;
        mX = 822;
        mY = 341;
        hX = 862;
        hY = 148;

        X3 = 1478;
        Y3 = 530;
        X5 = 1477;
        Y5 = 341;
        X10 = 1465;
        Y10 = 148;

        fill = (int)(70/1440.0*1080)-10;

    }

    @Override
    public void handleInput(){

        if(Gdx.input.isTouched()) {
            System.out.println(Gdx.input.getX());
            System.out.println(ArenaFighter.HEIGHT - Gdx.input.getY());

            //Have to "flip" the Y input, since inputs start at top-left while textures start at bottom-left
            float y = ArenaFighter.HEIGHT - Gdx.input.getY();

            Rectangle backBounds = new Rectangle(backX, backY, back.getWidth(), back.getHeight());
            Rectangle playBounds = new Rectangle(playX, playY, play.getWidth(), play.getHeight());

            Rectangle rBounds = new Rectangle(rX, rY, fill, fill);
            Rectangle cBounds = new Rectangle(cX, cY, fill, fill);
            Rectangle bBounds = new Rectangle(bX, bY, fill, fill);

            Rectangle eBounds = new Rectangle(eX, eY, fill, fill);
            Rectangle mBounds = new Rectangle(mX, mY, fill, fill);
            Rectangle hBounds = new Rectangle(hX, hY, fill, fill);

            Rectangle bounds3 = new Rectangle(X3, Y3, fill, fill);
            Rectangle bounds5 = new Rectangle(X5, Y5, fill, fill);
            Rectangle bounds10 = new Rectangle(X10, Y10, fill, fill);

            Vector2 tmp = new Vector2(Gdx.input.getX(), y/*Gdx.input.getY()*/);

            if(backBounds.contains(tmp.x, tmp.y)) {
                // you are touching your texture
                System.out.println("BACK touched");
                gsm.set(ArenaFighter.menuState);
            }

            /**Toggle group for Map Types*/
            if(rBounds.contains(tmp.x, tmp.y)) {
                ArenaFighter.rToggle = true;
                ArenaFighter.cToggle = false;
                ArenaFighter.bToggle = false;
                ArenaFighter.mapType = 1;
            }
            if(cBounds.contains(tmp.x, tmp.y)) {
                ArenaFighter.rToggle = false;
                ArenaFighter.cToggle = true;
                ArenaFighter.bToggle = false;
                ArenaFighter.mapType = 2;
            }
            if(bBounds.contains(tmp.x, tmp.y)) {
                ArenaFighter.rToggle = false;
                ArenaFighter.cToggle = false;
                ArenaFighter.bToggle = true;
            }

            /**Toggle group for Difficulties*/
            if(eBounds.contains(tmp.x, tmp.y)) {
                ArenaFighter.eToggle = true;
                ArenaFighter.meToggle = false;
                ArenaFighter.hToggle = false;
                AIFighter.DIFFICULTY = 1;
            }
            if(mBounds.contains(tmp.x, tmp.y)) {
                ArenaFighter.eToggle = false;
                ArenaFighter.meToggle = true;
                ArenaFighter.hToggle = false;
                AIFighter.DIFFICULTY = 0.5;
            }
            if(hBounds.contains(tmp.x, tmp.y)) {
                ArenaFighter.eToggle = false;
                ArenaFighter.meToggle = false;
                ArenaFighter.hToggle = true;
                AIFighter.DIFFICULTY = 0;
            }

            /**Toggle group for Amount of Wins*/
            if(bounds3.contains(tmp.x, tmp.y)) {
                ArenaFighter.toggle3 = true;
                ArenaFighter.toggle5 = false;
                ArenaFighter.toggle10 = false;
                ArenaFighter.maxWins = 3;
            }
            if(bounds5.contains(tmp.x, tmp.y)) {
                ArenaFighter.toggle3 = false;
                ArenaFighter.toggle5 = true;
                ArenaFighter.toggle10 = false;
                ArenaFighter.maxWins = 5;
            }
            if(bounds10.contains(tmp.x, tmp.y)) {
                ArenaFighter.toggle3 = false;
                ArenaFighter.toggle5 = false;
                ArenaFighter.toggle10 = true;
                ArenaFighter.maxWins = 10;
            }

            if(playBounds.contains(tmp.x, tmp.y)) {
                // you are touching your texture
                System.out.println("PLAY touched");

                if (MenuState.playState == null) {
                    MenuState.playState = new PlayState(gsm);
                    gsm.set(MenuState.playState);
                    ArenaFighter.atMenu = false;
                }
                else{
                    PlayState.maxWins = ArenaFighter.maxWins;
                    PlayState.mapType = ArenaFighter.mapType;
                    PlayState.map = MapManager.setMap(PlayState.mapType);
                    PlayState.switchPos();
                    PlayState.isWon = false;

                    PlayState.restart();
                    PlayState.resetScore();
                    PlayState.isWon = false;
                    PlayState.wait = false;
                    gsm.push(MenuState.playState);
                    ArenaFighter.atMenu = false;
                }
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
        sb.draw(play, playX, playY);

        /**Render Group for Map Types*/
        if(ArenaFighter.rToggle)
            sb.draw(fillR, rX, rY, fill, fill);

        if(ArenaFighter.cToggle)
            sb.draw(fillC, cX, cY, fill+1, fill);

        if(ArenaFighter.bToggle)
            sb.draw(fillB, bX, bY, fill, fill);

        /**Render Group for Difficulties*/
        if(ArenaFighter.eToggle)
            sb.draw(fillE, eX, eY, fill, fill);

        if(ArenaFighter.meToggle)
            sb.draw(fillM, mX, mY, fill+1, fill);

        if(ArenaFighter.hToggle)
            sb.draw(fillH, hX, hY, fill, fill);

        /**Render Group for Amount of Wins*/
        if(ArenaFighter.toggle3)
            sb.draw(fill3, X3, Y3, fill, fill);

        if(ArenaFighter.toggle5)
            sb.draw(fill5, X5, Y5, fill+1, fill);

        if(ArenaFighter.toggle10)
            sb.draw(fill10, X10, Y10, fill, fill);

        sb.end();

    }

    @Override
    public void dispose() {
        //background.dispose();
        //back.dispose();
        System.out.println("Set Up Disposed");
    }
}
