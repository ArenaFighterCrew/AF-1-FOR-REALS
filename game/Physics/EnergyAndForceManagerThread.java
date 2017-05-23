package com.arenafighter.game.Physics;

/**
 * Created by Frank on 2017-04-15.
 */

import com.arenafighter.game.ArenaFighter;
import com.arenafighter.game.Sprites.Fighter;

import java.util.ArrayList;
import java.util.Stack;

public class EnergyAndForceManagerThread extends Thread{
    static ArrayList<Fighter> fighters = new ArrayList<Fighter>();
    static int TIMESTEP = 1000/40;
    static double FRICTION_MULTIPLIER = 0.9;
    public boolean keepRunning = true;
    public static boolean resetQueued = false;


    private long timer;
    private boolean paused = false;

    @Override
    public void run(){
        timer = System.currentTimeMillis() + TIMESTEP;

        while(keepRunning){                                    //loop forever
            if(!paused && System.currentTimeMillis() > timer){     //once timestep is reached

                //INTERSECTION ALGORITHMN
                for(int i = 0; i < fighters.size(); i++){
                    Fighter f1 = fighters.get(i);
                    for(int j = i + 1; j < fighters.size(); j++){
                        Fighter f2 = fighters.get(j);
                        if(!f1.getDead() && !f2.getDead()) {
                            double distance = Math.sqrt(Math.pow(f1.getPosition().x - f2.getPosition().x, 2) + Math.pow(f1.getPosition().y - f2.getPosition().y, 2));
                            if (distance < ArenaFighter.radius * 2) {
                                f1.collideWith(f2);
                            }
                        }
                    }
                }

                //UPDATE FIGHTER VARIABLES
                for(Fighter f: fighters){
                    updateFighter(f);
                }

                //NEXT TIMER TRIGGER
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
            for(Fighter f: fighters){
                f.removeForce(0);
                f.removeForce(1);
                f.removeForce(2);
                f.removeForce(3);

                f.isLeft = false;
                f.isRight = false;
                f.isUp = false;
                f.isDown = false;
            }
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

        int mass = f.getMass();


        Stack<Impact> impacts = f.getImpacts();

        while(!f.getImpacts().isEmpty()){
            Impact i = impacts.pop();
            //System.out.println("ImpactX: " + i.getComponentX());
            //System.out.println("ImpactY: " + i.getComponentY());
            netForceX += i.getComponentX();
            netForceY += i.getComponentY();
        }


        //adjusts accelerationX if forceX is greater than the max friction, otherwise sets to 0
        if(netForceX > 0){
            accelerationX = (netForceX) / mass;
        }
        else if(netForceX < 0){
            accelerationX = (netForceX) / mass;
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
        if(netForceY > 0){
            accelerationY = (netForceY) / mass;
        }
        else if(netForceY < 0){
            accelerationY = (netForceY) / mass;
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
        if(resetQueued){
            resetForces();
            resetQueued = false;
        }
    }
    public void resetForces(){
        for(Fighter f: fighters){
            f.removeForce(0);
            f.removeForce(1);
            f.removeForce(2);
            f.removeForce(3);

            f.isLeft = false;
            f.isRight = false;
            f.isUp = false;
            f.isDown = false;

            f.setVelocityX(0);
            f.setVelocityY(0);
            f.setAccelerationX(0);
            f.setAccelerationY(0);
        }
    }
    public static ArrayList<Fighter> getFighters(){
        return fighters;
    }
}