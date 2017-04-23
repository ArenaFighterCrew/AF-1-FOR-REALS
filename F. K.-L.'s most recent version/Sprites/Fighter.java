package com.arenafighter.game.Sprites;

import com.arenafighter.game.ArenaFighter;
import com.arenafighter.game.Physics.Force;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import java.awt.geom.AffineTransform;

/**
 * Created by Frank on 2017-04-15.
 */


/*import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;*/


public /*abstract*/ class Fighter /*extends StackPane*/{
    private int fighterIndex;
    private int mass;
    private double netForceX;
    private double netForceY;
    private double maxFrictionForce;
    private double velocityX;
    private double velocityY;
    private double accelerationX;
    private double accelerationY;
    //private Shape icon;
    private int maxEnergy;
    private int currentEnergy;
    private boolean alive;
    private boolean bracing;

    //My variables
    private Texture player;
    private Rectangle bounds;
    public static Vector3 position;

    public boolean isRight;
    public boolean isLeft;
    public boolean isUp;
    public boolean isDown;

    private Force[] forces;
    //private EnergyAndForceManagerThread manager;
    public static double FRICTION_CONSTANT = 0.5;
    public static double DIAGONAL_MULTIPLIER = 0.707;

    public Fighter(int x, int y){
        mass = 500;
        netForceX = 0;
        netForceY = 0;
        velocityX = 0;
        velocityY = 0;
        accelerationX = 0;
        accelerationY = 0;

        /*icon = new Circle(18);
        icon.setFill(Color.LIGHTGRAY);
        icon.setStroke(Color.LIGHTGRAY.darker().darker());
        icon.setStrokeWidth(4);*/

        position = new Vector3(x, y, 0);
        player = new Texture("White Circle.png");
        bounds = new Rectangle(x, y, player.getWidth(), player.getHeight());

        maxEnergy = 200;
        currentEnergy = 200;
        alive = true;
        bracing = false;
        forces = new Force[4];

        boolean isRight = false;
        boolean isLeft = false;
        boolean isUp = false;
        boolean isDown = false;

        //friction = friction constant * normal force, defaults to 125
        maxFrictionForce = (int)(FRICTION_CONSTANT * mass * 0.5);

        //getChildren().add(icon);
    }
    public void collideWith(Fighter f){
        //SHITTY INACCURATE COLLISION
        /*
        double m1 = mass;
        double m2 = f.getMass();
        double v1x = velocityX;
        double v1y = velocityY;
        double v2x = f.getVelocityX();
        double v2y = f.getVelocityY();

        double vf1x = (v1x * (m1 - m2) + (2 * m2 * v2x)) / (m1 + m2);
        double vf1y = (v1y * (m1 - m2) + (2 * m2 * v2y)) / (m1 + m2);

        setVelocityX(vf1x * 5);
        setVelocityY(vf1y * 5);
        */
        /*
        double v1 = Math.sqrt(getVelocityX() * getVelocityX() + getVelocityY() * getVelocityY());
        double v2 = Math.sqrt(f.getVelocityX() * f.getVelocityX() + f.getVelocityY() * f.getVelocityY());
        double m1 = getMass();
        double m2 = f.getMass();

        double theta1, theta2;
        if(getVelocityX() != 0)
            theta1 = Math.atan(getVelocityY() / getVelocityX());
        else
            theta1 = 0;
        if(f.getVelocityX() != 0)
            theta2 = Math.atan(f.getVelocityY() / f.getVelocityX());
        else
            theta2 = 0;

        double dX = getTranslateX() - f.getTranslateX();
        double dY = getTranslateY() - f.getTranslateY();
        double phi = Math.atan(dY / dX);

        System.out.println("v1: " + v1);
        System.out.println("v2: " + v2);
        System.out.println("m1: " + m1);
        System.out.println("m2: " + m2);
        System.out.println("theta1: " + theta1);
        System.out.println("theta2: " + theta2);
        System.out.println("dX: " + dX);
        System.out.println("dY: " + dY);
        System.out.println("phi: " + phi);

        double vfx = v1 * Math.cos(theta1 - phi) * (m1 - m2) + 2 * m2 * v2 * Math.cos(theta2 - phi);
        System.out.println(vfx);
        vfx *= Math.cos(phi) / (m1 + m2);
        System.out.println(vfx);
        vfx += v1 * Math.sin(theta1 - phi) * Math.cos(phi + Math.PI / 2);
        System.out.println(vfx);

        double vfy = v1 * Math.cos(theta1 - phi) * (m1 - m2) + 2 * m2 * v2 * Math.cos(theta2 - phi);
        System.out.println(vfy);
        vfy *= Math.sin(phi) / (m1 + m2);
        System.out.println(vfy);
        vfy += v1 * Math.sin(theta1 - phi) * Math.sin(phi + Math.PI / 2);
        System.out.println(vfy);

        System.out.println("VelocityX: " + vfx);
        System.out.println("VelocityY: " + vfy);

        setVelocityX(vfx * 3);
        setVelocityY(vfy * 3);
        */
    }

    //My methods
    public Texture getTexture() {
        return player;
    }
    public Vector3 getPosition() {
        return position;
    }
    public void dispose(){
        player.dispose();
    }
    public void update(float dt){
        if(position.y < 0)
            position.y = 0;

        if(position.y > ArenaFighter.HEIGHT - player.getHeight())
            position.y = ArenaFighter.HEIGHT - player.getHeight();

        if(position.x < 0)
            position.x = 0;

        if(position.x > ArenaFighter.WIDTH - player.getWidth())
            position.x = ArenaFighter.WIDTH - player.getWidth();

        bounds.setPosition(position.x, position.y);


    }



    public double getNetForceX() {
        return netForceX;
    }

    public void setNetForceX(double netForceX) {
        this.netForceX = netForceX;
    }

    public double getNetForceY() {
        return netForceY;
    }

    public void setNetForceY(double netForceY) {
        this.netForceY = netForceY;
    }

    public double getMaxFrictionForce() {
        return maxFrictionForce;
    }

    public void setMaxFrictionForce(double maxFrictionForce) {
        this.maxFrictionForce = maxFrictionForce;
    }

    public double getVelocityX() {
        return velocityX;
    }

    public void setVelocityX(double velocityX) {
        this.velocityX = velocityX;
    }

    public double getVelocityY() {
        return velocityY;
    }

    public void setVelocityY(double velocityY) {
        this.velocityY = velocityY;
    }

    public int getIndex(){
        return fighterIndex;
    }

    public int getMass(){
        return mass;
    }

    public double getAccelerationX(){
        return accelerationX;
    }

    public double getAccelerationY(){
        return accelerationY;
    }

    /*public Shape getIcon(){
        return icon;
    }*/

    public int getMaxEnergy(){
        return maxEnergy;
    }

    public int getCurrentEnergy(){
        return currentEnergy;
    }

    public boolean isAlive(){
        return alive;
    }

    public boolean isBracing(){
        return bracing;
    }

    public double getFrictionConstant(){
        return FRICTION_CONSTANT;
    }

    public void setIndex(int newIndex){
        fighterIndex = newIndex;
    }

    public void setMass(int newMass){
        mass = newMass;
    }

    public void setAccelerationX(double newAcceleration){
        accelerationX = newAcceleration;
    }

    public void setAccelerationY(double newAcceleration){
        accelerationY = newAcceleration;
    }

    /*public void setIcon(Shape newIcon){
        icon = newIcon;
    }*/

    public void setMaxEnergy(int newMaxEnergy){
        maxEnergy = newMaxEnergy;
    }

    public void setCurrentEnergy(int newCurrentEnergy){
        currentEnergy = newCurrentEnergy;
    }

    /*public void setColor(Color c){
        icon.setFill(c);
        icon.setStroke(c.darker().darker());
    }*/

    public void setAlive(boolean newState){
        alive = newState;
    }

    public void setBracing(boolean newState){
        bracing = newState;
    }
    public void addForce(int index, Force newForce){
        //0 = right, 1 = up, 2 = left, 3 = down
        forces[index] = newForce;

        int sumX = 0;
        int sumY = 0;

        for(Force f: forces){
            if(f != null){
                sumX += f.getComponentX();
                sumY += f.getComponentY();
            }
        }

        this.setNetForceX(multiplyDiagonal(sumX));
        this.setNetForceY(multiplyDiagonal(sumY));
    }
    public void removeForce(int index){
        forces[index] = null;

        double sumX = 0;
        double sumY = 0;

        for(Force f: forces){
            if(f != null){
                sumX += f.getComponentX();
                sumY += f.getComponentY();
            }
        }

        this.setNetForceX(multiplyDiagonal(sumX));
        this.setNetForceY(multiplyDiagonal(sumY));
    }
    public double multiplyDiagonal(double sum){

        int directions = 0;
        if(isUp)
            directions++;
        if(isDown)
            directions++;
        if(isLeft)
            directions++;
        if(isRight)
            directions++;
        if(directions == 2){
            if(sum > 0){
                sum -= (sum - maxFrictionForce) * (1 - DIAGONAL_MULTIPLIER);
            }
            else
                sum -= (sum + maxFrictionForce) * (1 - DIAGONAL_MULTIPLIER);
            System.out.println("multiplied");
        }
        return sum;
    }
}
