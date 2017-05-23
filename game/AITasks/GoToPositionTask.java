package com.arenafighter.game.AITasks;

import com.arenafighter.game.ArenaFighter;
import com.arenafighter.game.Sprites.Map;

/**
 * Created by Winged_Shade on 5/11/2017.
 */

public class GoToPositionTask implements AITask{
    int x;
    int y;

    boolean xReached = false;
    boolean yReached = false;

    int maxX;
    int minX;
    int maxY;
    int minY;

    int offset = AITask.POSITION_LEEWAY*13;

    public GoToPositionTask(Map m){
        if(!(this instanceof ApproachFighterTask)) {

            if(m.t == 1) {
                /*System.out.println(m.mapX);
                minX = m.mapX + offset;
                maxX = minX*2;// + m.width1 - offset*2;
                minY = m.mapY + offset;
                maxY = minY;// + m.height1 - offset*2;*/

                x = ArenaFighter.WIDTH / 4 + (int) (Math.random() * ArenaFighter.WIDTH / 2);
                y = ArenaFighter.HEIGHT / 4 + (int) (Math.random() * ArenaFighter.HEIGHT / 4);

            }

            if(m.t == 2) {
                /*minX = m.x2 + offset;
                maxX = minX*2;//minX + m.width2 - offset*2;
                minY = m.y2 + offset;
                maxY = minY;//minY + m.width2 - offset*2;*/

                x = (int)(ArenaFighter.WIDTH / 2.3) + (int) (Math.random() * ArenaFighter.WIDTH / 8);
                y = (int)(ArenaFighter.HEIGHT / 2.3) + (int) (Math.random() * ArenaFighter.HEIGHT / 12);

            }


            //x = ArenaFighter.WIDTH / 4 + (int) (Math.random() * ArenaFighter.WIDTH / 2);
            //y = ArenaFighter.HEIGHT / 4 + (int) (Math.random() * ArenaFighter.HEIGHT / 4);

            //x = minX + (int) (Math.random() * maxX);
            //y = minY + (int) (Math.random() * maxY);

        }
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public void setXReached(boolean b){
        xReached = b;
    }
    public boolean getXReached(){
        return xReached;
    }
    public void setYReached(boolean b){
        yReached = b;
    }
    public boolean getYReached(){
        return yReached;
    }
}
