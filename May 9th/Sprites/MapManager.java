package com.arenafighter.game.Sprites;

import com.arenafighter.game.ArenaFighter;
import com.arenafighter.game.States.PlayState;
import com.badlogic.gdx.graphics.Texture;
import com.sun.media.sound.MidiUtils;

/**
 * Created by Frank on 2017-04-25.
 */

public class MapManager {

    private static Texture mapT;
    private static int mapX;
    private static int mapY;
    public static Map map;
    //public static bounds;


    public static Map setMap(int type){

        switch (type) {
            case 1:
                mapT = new Texture("Map1.png");
                mapX = (ArenaFighter.WIDTH - mapT.getWidth()) / 2;
                mapY = (ArenaFighter.HEIGHT - ArenaFighter.border.getHeight() - mapT.getHeight()) / 2;
                map = new Map(mapX, mapY, mapT, type);
                return map;
            case 2:
                mapT = new Texture("Map2.png");
                mapX = (ArenaFighter.WIDTH - mapT.getWidth()) / 2;
                mapY = (ArenaFighter.HEIGHT - ArenaFighter.border.getHeight() - mapT.getHeight()) / 2;
                map = new Map(mapX, mapY, mapT, type);
                return map;
        }
        return map;
    }



}
