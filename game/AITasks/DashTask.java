package com.arenafighter.game.AITasks;

import com.arenafighter.game.Sprites.Fighter;

/**
 * Created by Winged_Shade on 5/11/2017.
 */

public class DashTask implements AITask {
    public Fighter target;

    public DashTask(Fighter f){
        target = f;
    }
    public Fighter getTarget(){
        return target;
    }
}
