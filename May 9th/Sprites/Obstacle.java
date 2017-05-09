package com.arenafighter.game.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

/**
 * Created by Frank on 2017-02-27.
 */

public class Obstacle {

    private Texture box;
    //Gives a position to the box, like tubes in FLappy Bird
    private Vector2 posBox;
    private Random rand;
    public static final int BOX_WIDTH = 500;
    //variables for tube spacing in flappy bird
    private static final int FLUCTUATION = 130;
    private static final int BOX_GAP = 100;
    private static final int LOWEST_OPENING = 120;
    //invisible rectangle for hit box of the boxes
    private Rectangle boundsBox;


    public Obstacle(float x){
        box = new Texture("Wooden Box.png");
        rand = new Random();
        //position of tubes in x & y for flappy bird game
        posBox = new Vector2(x, rand.nextInt(FLUCTUATION) + BOX_GAP + LOWEST_OPENING);

        boundsBox = new Rectangle(posBox.x, posBox.y, box.getWidth(), box.getHeight());
    }

    public Vector2 getPosBox() {
        return posBox;
    }

    public Texture getBox() {
        return box;
    }

    //"repositions" an already existing box (tube for the flappy bird game)
    public void reposition(float x){
        posBox.set(x, rand.nextInt(FLUCTUATION) + BOX_GAP + LOWEST_OPENING);
        boundsBox.setPosition(posBox.x, posBox.y);
    }

    public boolean collides(Rectangle player){
        return player.overlaps(boundsBox);
    }

    public void dispose(){
        box.dispose();
    }

}
