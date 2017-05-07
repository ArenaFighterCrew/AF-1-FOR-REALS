package com.arenafighter.game.Physics;

/**
 * Created by Frank on 2017-04-15.
 */

import com.arenafighter.game.Sprites.Fighter;

import java.util.ArrayList;
import java.util.Stack;

public class EnergyAndForceManagerThread extends Thread{
    ArrayList<Fighter> fighters = new ArrayList<Fighter>();
    static int TIMESTEP = 1000/40;
    static double FRICTION_MULTIPLIER = 0.9;
    public boolean keepRunning = true;


    private long timer;
    private boolean paused = false;

    @Override
    public void run(){
        timer = System.currentTimeMillis() + TIMESTEP;

        while(keepRunning){                                    //loop forever
            if(!paused && System.currentTimeMillis() > timer){     //once timestep is reached
                for(Fighter f: fighters){               //for each fighter...
                    updateFighter(f);
                    //System.out.println("player " + f.getIndex() + " updated");
                }
                //sets the next timer trigger
                timer += TIMESTEP;
            }
        }
    }

    public void pause(){
        if(!paused) {
            paused = true;
            System.out.println("Physics thread paused");
        }
    }
    public void unpause(){
        if(paused) {
            timer = System.currentTimeMillis() + TIMESTEP;
            paused = false;
            System.out.println("Physics thread unpaused");
        }
    }
    public boolean isPaused(){
        return paused;
    }


    public void addFighter(Fighter f){

        //NOTE: will not accept new fighters once the thread is run

        if(!this.isAlive()){
            fighters.add(f);
            System.out.println("Fighter added to Thread.");
        }
        else
            System.out.println("Fighter not added: Thread already running.");
    }
    public void updateFighter(Fighter f){

        //double positionX = f.getTranslateX();
        double positionX = f.getPosition().x;
        double netForceX = f.getNetForceX();
        double velocityX = f.getVelocityX();
        double accelerationX = f.getAccelerationX();

        //double positionY = f.getTranslateY();
        double positionY = f.getPosition().y;
        double netForceY = f.getNetForceY();
        double velocityY = f.getVelocityY();
        double accelerationY = f.getAccelerationY();

        double frictionForce = f.getMaxFrictionForce();
        int mass = f.getMass();


        Stack<Impact> impacts = f.getImpacts();

        while(!f.getImpacts().isEmpty()){
            Impact i = impacts.pop();
            System.out.println("ImpactX: " + i.getComponentX());
            System.out.println("ImpactY: " + i.getComponentY());
            netForceX += i.getComponentX();
            netForceY += i.getComponentY();
        }


        //adjusts accelerationX if forceX is greater than the max friction, otherwise sets to 0
        if(netForceX > 0 && netForceX > frictionForce){
            accelerationX = (netForceX - frictionForce) / mass;
        }
        else if(netForceX < 0 && Math.abs(netForceX) > frictionForce){
            accelerationX = (netForceX + frictionForce) / mass;
        }
        else if(Math.abs(velocityX) < 0.25){
            velocityX = 0;
            accelerationX = 0;
        }
        else{
            accelerationX = 0;
        }

        //adjusts the position based on velocity and acceleration
        positionX += TIMESTEP / 10 * (velocityX + TIMESTEP * accelerationX / 2);

        //adjusts the velocity based on acceleration
        velocityX += TIMESTEP * accelerationX / 2;
        velocityX *= FRICTION_MULTIPLIER;

        //adjusts accelerationY if forceY is greater than the max friction, otherwise sets to 0
        if(netForceY > 0 && netForceY > frictionForce){
            accelerationY = (netForceY - frictionForce) / mass;
        }
        else if(netForceY < 0 && Math.abs(netForceY) > frictionForce){
            accelerationY = (netForceY + frictionForce) / mass;
        }
        else if(Math.abs(velocityY) < 0.25){
            velocityY = 0;
            accelerationY = 0;
        }
        else{
            accelerationY = 0;
        }

        //adjusts positionY based on velocityY and accelerationY
        positionY += TIMESTEP / 10 * (velocityY + TIMESTEP * accelerationY / 2);

        //adjusts velocityY based on accelerationY
        velocityY += TIMESTEP * accelerationY / 2;
        velocityY *= FRICTION_MULTIPLIER;

        f.setAccelerationX(accelerationX);
        //f.setTranslateX(positionX);
        f.getPosition().x = (float)positionX;
        f.setVelocityX(velocityX);

        f.setAccelerationY(accelerationY);
        //f.setTranslateY(positionY);
        f.getPosition().y = (float)positionY;
        f.setVelocityY(velocityY);

        f.getBounds().setPosition(f.getPosition().x, f.getPosition().y);
    }
}
