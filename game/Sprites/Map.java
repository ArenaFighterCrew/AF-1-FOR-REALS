package com.arenafighter.game.Sprites;

import com.arenafighter.game.ArenaFighter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.physics.box2d.Shape;

/**
 * Created by Frank on 2017-04-25.
 */

public class Map {

    private Rectangle bounds1;
    private Circle bounds2;
    private Rectangle bounds3;
    //private Shape2D bounds;
    public Texture map;
    public int mapX;
    public int mapY;
    public int width1;
    public int height1;
    public int width2;
    public int t;

    public int x2;
    public int y2;

    public Map(int x, int y, Texture texture, int type){

        mapX = x;
        mapY = y;
        map = texture;
        t = type;

        width1 = map.getWidth();
        height1 = map.getHeight();
        bounds1 = new Rectangle(x /*- ArenaFighter.radius*/, y /*- ArenaFighter.radius*/ ,width1, height1);

        bounds2 = new Circle(x + (map.getWidth()/2) /*- ArenaFighter.radius*/, y + (map.getWidth()/2) /*- ArenaFighter.radius*/, (map.getWidth()/*+(2* ArenaFighter.radius)*/)/2);
        //width2 = (int)(Math.sqrt(map.getWidth()/4));
        width2 = (int)(map.getWidth()/(Math.sqrt(2)));

        x2 = x + (map.getWidth()/2) - (width2) / 2;
        y2 = y + (map.getWidth()/2) - (width2) / 2;

        bounds3 = new Rectangle(x2, y2, width2, width2);
        /*switch (type) {
            //Rectangular
            case 1:
                //bounds1 = new Rectangle(x - ArenaFighter.radius, y - ArenaFighter.radius ,map.getWidth()+(2* ArenaFighter.radius), map.getHeight()+(2* ArenaFighter.radius));
                bounds = bounds1;
                break;
            //Circle
            case 2:
                bounds = bounds2;
                break;
        }*/


    }

    public int getMapX(){
        return mapX;
    }

    public int getMapY(){
        return mapY;
    }

    public Texture getTexture(){
        return map;
    }

    public Rectangle getBounds1(){
        return bounds1;
    }

    public Circle getBounds2(){
        return bounds2;
    }

    public Rectangle getBounds3(){ return bounds3; }

    public void getBounds(int type){
        switch (type) {
            case 1:
                getBounds1();
                break;
            case 2:
                getBounds2();
                break;
        }

    }

}
