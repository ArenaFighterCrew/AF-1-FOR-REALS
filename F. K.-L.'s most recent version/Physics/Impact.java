package com.arenafighter.game.Physics;

/**
 * Created by Frank on 2017-04-15.
 */

public class Impact extends Vector{

    public Impact(int iMagnitude, double iAngle){
        super(iMagnitude, iAngle);
    }
    public Force toForce(){
        return new Force((int)(this.getMagnitude() * FORCE_IMPACT_RATIO), this.getAngle());
    }
}
