package com.arenafighter.game.States;

import com.arenafighter.game.ArenaFighter;
import com.arenafighter.game.Sprites.Fighter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.graphics.Camera;


/**
 * Created by Frank on 2017-02-26.
 */

public class MenuState extends State{

    private Texture background;
    private Texture playBtn;
    private Texture play;
    private Texture options;
    private Texture credits;
    private Texture exit;

    private int playX;
    private int playY;
    private int optionsX;
    private int optionsY;
    private int creditsX;
    private int creditsY;
    private int exitX;
    private int exitY;

    public static PlayState playState;
    public static OptionsState optionsState;
    public static CreditsState creditsState;
    public static SetUpState setUpState;

    //Rectangle textureBounds = new Rectangle(/*textureX*/ArenaFighter.WIDTH/2-(285/2), /*textureY*/ArenaFighter.HEIGHT/3*2, /*textureWidth*/285, /*textureHeight*/152);
    //public Rectangle playBounds = new Rectangle(1137, 753, /*285*/play.getWidth(), play.getHeight()/*152*/);


    public MenuState(GameStateManager gsm) {
        super(gsm);
        background = new Texture("Main Menu.png");
        playBtn = new Texture("Play Button Yellow.png");
        play = new Texture("Play Text.png");
        options = new Texture("Options Text.png");
        credits = new Texture("Credits Text.png");
        exit = new Texture("Exit Text.png");

        playX = ArenaFighter.WIDTH/2 - play.getWidth()/2;
        playY = (int)(753/1440.0*1080);
        optionsX = ArenaFighter.WIDTH/2 - options.getWidth()/2;
        optionsY = (int)(525/1440.0*1080);
        creditsX = ArenaFighter.WIDTH/2 - credits.getWidth()/2;
        creditsY = (int)(338/1440.0*1080);
        exitX = ArenaFighter.WIDTH/2 - exit.getWidth()/2;
        exitY = (int)(117/1440.0*1080);
    }

    @Override
    public void handleInput() {
        /*if(Gdx.input.justTouched()){
            gsm.set(new PlayState(gsm));
            //dispose();
        }*/


        if(Gdx.input.justTouched()) {
            System.out.println(Gdx.input.getX());
            System.out.println(ArenaFighter.HEIGHT - Gdx.input.getY());

            //Have to "flip" the Y input, since inputs start at top-left while textures start at bottom-left
            float y = ArenaFighter.HEIGHT - Gdx.input.getY();

            Rectangle playBounds = new Rectangle(playX, playY, play.getWidth(), play.getHeight());
            Rectangle optionsBounds = new Rectangle(optionsX, optionsY, options.getWidth(), options.getHeight());
            Rectangle creditsBounds = new Rectangle(creditsX, creditsY, credits.getWidth(), credits.getHeight());
            Rectangle exitBounds = new Rectangle(exitX, exitY, exit.getWidth(), exit.getHeight());

            //System.out.println(textureBounds.x);
            //System.out.println(textureBounds.y);

            Vector2 tmp = new Vector2(Gdx.input.getX(), y/*Gdx.input.getY()*/);
            //System.out.println(tmp.x);
            //System.out.println(tmp.y);
            //cam.unproject(tmp);


            // texture x is the x position of the texture
            // texture y is the y position of the texture
            // texturewidth is the width of the texture (you can get it with texture.getWidth() or textureRegion.getRegionWidth() if you have a texture region
            // textureheight is the height of the texture (you can get it with texture.getHeight() or textureRegion.getRegionhHeight() if you have a texture region

            if(playBounds.contains(tmp.x, tmp.y)) {
                // you are touching your texture
                System.out.println("PLAY touched");
                /*if (playState == null) {
                    playState = new PlayState(gsm);
                    gsm.set(playState);
                }*/
                if (setUpState == null){
                    setUpState = new SetUpState(gsm);
                    gsm.set(setUpState);
                }
                else{
                    //Want to "restart" position when "playing new game"
                    /*PlayState.restart();
                    PlayState.resetScore();
                    PlayState.isWon = false;
                    PlayState.wait = false;
                    gsm.push(playState);*/
                    gsm.push(setUpState);
                }
            }

            if(optionsBounds.contains(tmp.x, tmp.y)){
                System.out.println("OPTIONS touched");

                if (optionsState == null) {
                    optionsState = new OptionsState(gsm);
                    gsm.set(optionsState);
                }
                else
                    gsm.push(optionsState);
            }

            if(creditsBounds.contains(tmp.x, tmp.y)){
                System.out.println("CREDITS touched");

                if (creditsState == null) {
                    creditsState = new CreditsState(gsm);
                    gsm.set(creditsState);
                }
                else
                    gsm.push(creditsState);

            }


            if(exitBounds.contains(tmp.x, tmp.y)){
                System.out.println("EXIT touched");
                System.exit(0);
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
        sb.draw(background, 0, 0, ArenaFighter.WIDTH, ArenaFighter.HEIGHT);
        //sb.draw(playBtn, (ArenaFighter.WIDTH - playBtn.getWidth()) / 2, (ArenaFighter.HEIGHT - playBtn.getHeight())/2 /*ArenaFighter.HEIGHT / 2*/);
        sb.draw(play, playX /*1137*/, playY);
        //sb.draw
        sb.draw(options, optionsX, optionsY);
        sb.draw(credits, creditsX, creditsY);
        sb.draw(exit, exitX, exitY);
        sb.end();
    }

    @Override
    public void dispose() {
        //background.dispose();
        //playBtn.dispose();
        //play.dispose();
        //options.dispose();
        //credits.dispose();
        //exit.dispose();
        System.out.println("Menu State Disposed");

    }
}
