package com.arenafighter.game.States;

import com.arenafighter.game.ArenaFighter;
import com.arenafighter.game.Sprites.Obstacle;
import com.arenafighter.game.Sprites.Player;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import javax.swing.Box;

import sun.awt.image.InputStreamImageSource;

/**
 * Created by Frank on 2017-02-27.
 */

public class PlayState extends State {

    private Player player;
    private Texture bg;
    //private Texture floor;
    //private Vector2 floorPos1, floorPos2;
    //private Obstacle box; not used anymore, was just for testing purposes

    //spacing between boxes (tubes for flappy bird)
    private static final int BOX_SPACING = 3500;
    //amount of boxes that can be on the screen at a given time
    private static final int BOX_COUNT = 4;
    //private static final int FLOOR_Y_OFFSET = -50;

    private Array<Obstacle> obstacles;

    protected PlayState(GameStateManager gms) {
        super(gms);
        player = new Player(50, 100);
        cam.setToOrtho(false, ArenaFighter.WIDTH * 6, ArenaFighter.HEIGHT * 6);
        bg = new Texture("Play State.png");
        //floor = new Texture("Floor.png");
        //floorPos1 = new Vector2(cam.position.x - cam.viewportWidth / 2, FLOOR_Y_OFFSET);
        //floorPos2 = new Vector2((cam.position.x - cam.viewportWidth / 2) + floor.getWidth(), FLOOR_Y_OFFSET);
        //box = new Obstacle(100); not used anymore, was just for testing purposes

        obstacles = new Array<Obstacle>();

        //creates 4 boxes (tubes) on the screen to have "constant flow" of boxes
        for(int i = 1; i <= BOX_COUNT; i++){
            obstacles.add(new Obstacle(i *(BOX_SPACING + Obstacle.BOX_WIDTH)));
        }
    }

    @Override
    protected void handleInput() {

        if(Gdx.input.isKeyPressed(Input.Keys.W)){
            player.moveY(1);
            //if(!Gdx.input.isKeyPressed(Input.Keys.ANY_KEY))
               // player.stop(0);
            //if(keyUp(Input.Keys.W))
                //player.stop(0);
        }

        //if(Gdx.input.)

        if(Gdx.input.isKeyPressed(Input.Keys.S)){
            player.moveY(-1);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.A)){
            player.moveX(-1);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.D)){
            player.moveX(1);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)){

            if(Gdx.input.isKeyPressed(Input.Keys.W)){
                player.dashY(1);
            }

            if(Gdx.input.isKeyPressed(Input.Keys.S)){
                player.dashY(-1);
            }

            if(Gdx.input/*.isKeyJustPressed(Input.Keys.A)){*/.isKeyPressed(Input.Keys.A)){
                player.dashX(-1);
            }

            if(Gdx.input.isKeyPressed(Input.Keys.D)){
                player.dashX(1);
            }

        }


    }

    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public void update(float dt) {
        handleInput();
        //updateGround();
        player.update(dt);
        //tracks the player with the camera
        //cam.position.x = player.getPosition().x + 80;

        //use the reposition methods on boxes (tubes) when one of them is out of the camera to the left
        /*for(int i = 0; i < obstacles.size; i++){
            Obstacle obstacle = obstacles.get(i);

            if(cam.position.x - (cam.viewportWidth / 2) > obstacle.getPosBox().x + obstacle.getBox().getWidth()){
                obstacle.reposition(obstacle.getPosBox().x + ((Obstacle.BOX_WIDTH + BOX_SPACING) * BOX_COUNT));
            }
            // general collision, not pixel perfect (should be improved)
            if(obstacle.collides(player.getBounds()))
                gsm.set(new PlayState(gsm));
        }*/
        /*if(player.getPosition().y <= floor.getHeight() + FLOOR_Y_OFFSET)
            gsm.set(new PlayState(gsm));*/
        cam.update();

    }

    @Override
    public void render(SpriteBatch sb) {
        //sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(bg, /*cam.position.x - (cam.viewportWidth / 2)*/0, 0);
        sb.draw(player.getTexture(), player.getPosition().x, player.getPosition().y);

        //for(Obstacle obstacle : obstacles)
          //  sb.draw(obstacle.getBox(), obstacle.getPosBox().x, obstacle.getPosBox().y);

        //sb.draw(floor, floorPos1.x, floorPos1.y);
        //sb.draw(floor, floorPos2.x, floorPos2.y);

        sb.end();
    }

    @Override
    public void dispose() {
        bg.dispose();
        player.dispose();
        //floor.dispose();
        for(Obstacle obstacle: obstacles)
            obstacle.dispose();
        System.out.println("Play State Disposed");
    }

    /*private void updateGround(){
        if(cam.position.x - (cam.viewportWidth / 2) > floorPos1.x + floor.getWidth())
           floorPos1.add(floor.getWidth() * 2, 0);
        if(cam.position.x - (cam.viewportWidth / 2) > floorPos2.x + floor.getWidth())
            floorPos2.add(floor.getWidth() * 2, 0);
    }*/
}
