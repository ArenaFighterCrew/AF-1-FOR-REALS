package com.arenafighter.game.Sprites;

import com.arenafighter.game.ArenaFighter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Frank on 2017-02-27.
 */

public class Player {

    private static final int FRICTION = -200;
    private static final int MOVEMENT = 1800;
    private Vector3 position;
    private Vector3 velocity;


    /*public World world;
    public Body body;*/

    private Rectangle bounds;

    private Texture player;

    public Player(/*World world,*/ int x, int y){

        position = new Vector3(x, y, 0);
        velocity = new Vector3(0, 0, 0);
        player = new Texture("White Circle.png");

        bounds = new Rectangle(x, y, player.getWidth(), player.getHeight());

        //this.world = world;

        //definePlayer();
    }

    /*public void definePlayer(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(0,0);
        bdef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();


    }*/


    public void update(float dt){
        //makes the character fall out of screen "flappy bird style"
        if(position.y > 0 && velocity.y > 0) {//can only add "gravity" if position in y is > 0
            velocity.add(0, FRICTION, 0);
            if (velocity.y <= 0)
                velocity.y = 0;
        }

        if(position.y > 0 && velocity.y < 0) {
            velocity.add(0, -1 * FRICTION, 0);
            if (velocity.y >= 0)
                velocity.y = 0;
        }

        if(velocity.x > 0) {
            velocity.add(FRICTION, 0, 0);
            if (velocity.x <= 0)
                velocity.x = 0;
        }

        if(velocity.x < 0) {
            velocity.add(-1 * FRICTION, 0, 0);
            if (velocity.x >= 0)
                velocity.x = 0;
        }

        velocity.scl(dt);
        position.add(velocity.x, velocity.y, 0);

        //make it so that it cant "fall under the screen", creates a "floor"
        if(position.y < 0)
            position.y = 0;

        if(position.y > ArenaFighter.HEIGHT - player.getHeight())
            position.y = ArenaFighter.HEIGHT - player.getHeight();

        if(position.x < 0)
            position.x = 0;

        if(position.x > ArenaFighter.WIDTH - player.getWidth())
            position.x = ArenaFighter.WIDTH - player.getWidth();

        velocity.scl(1/dt);

        bounds.setPosition(position.x, position.y);


        //From the Mario tutorial using box2D Body
        //player.setPosition(body.getPosition().x, body.getPosition().y);

    }

    public Vector3 getPosition() {
        return position;
    }

    public Texture getTexture() {
        return player;
    }

    public void moveY(int direction){
        //adds a positive amount to the Y velocity, making it "jump" like in Flappy Bird
        velocity.y = direction * 1500;

        /*if(direction == 1){
            //accelerationY = (netForceX + frictionForce) / mass;
        }*/
    }

    public void moveX(int direction){
        //adds a positive amount to the Y velocity, making it "jump" like in Flappy Bird
        velocity.x = direction * 1500;

        /*if(direction == 1){
            //accelerationY = (netForceX + frictionForce) / mass;
        }*/
    }

    public void dashY(int direction){

        velocity.y = direction * 2700;
    }

    public void dashX(int direction){
        velocity.x = direction * 2700;
    }

    /*public void stop(int axis){
        if(axis == 1)
            velocity.x = 0;
        else if (axis == 0)
            velocity.y = 0;
    }*/

    public Rectangle getBounds(){
        return bounds;
    }

    public void dispose(){
        player.dispose();
    }
}
