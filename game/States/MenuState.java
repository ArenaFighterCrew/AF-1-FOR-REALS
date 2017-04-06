package com.arenafighter.game.States;

import com.arenafighter.game.ArenaFighter;
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
    }

    @Override
    public void handleInput() {
        /*if(Gdx.input.justTouched()){
            gsm.set(new PlayState(gsm));
            //dispose();
        }*/


        if(Gdx.input.isTouched()) {
            System.out.println(Gdx.input.getX());
            System.out.println(ArenaFighter.HEIGHT - Gdx.input.getY());

            //Have to "flip" the Y input, since inputs start at top-left while textures start at bottom-left
            float y = ArenaFighter.HEIGHT - Gdx.input.getY();

            Rectangle playBounds = new Rectangle(ArenaFighter.WIDTH/2 - play.getWidth()/2/*1137*/, 753, /*285*/play.getWidth(), play.getHeight()/*152*/);
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
                System.out.println("Is touched");
                gsm.set(new PlayState(gsm));
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
        sb.draw(play, ArenaFighter.WIDTH/2 - play.getWidth()/2 /*1137*/, 753);
        //sb.draw(options, ArenaFighter.WIDTH/2 - options.getWidth()/2, 600);
        //sb.draw(credits, ArenaFighter.WIDTH/2 - credits.getWidth()/2, 380);
        //sb.draw(exit, ArenaFighter.WIDTH/2 - exit.getWidth()/2, 200);
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        playBtn.dispose();
        System.out.println("Menu State Disposed");

    }
}
