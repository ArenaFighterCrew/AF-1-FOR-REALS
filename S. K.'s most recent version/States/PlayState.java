package com.arenafighter.game.States;

import com.arenafighter.game.ArenaFighter;
import com.arenafighter.game.Physics.*;
import com.arenafighter.game.Physics.EnergyAndForceManagerThread;
import com.arenafighter.game.Physics.Force;
import com.arenafighter.game.Sprites.Fighter;
import com.arenafighter.game.Sprites.Map;
import com.arenafighter.game.Sprites.MapManager;
import com.arenafighter.game.Sprites.Obstacle;
import com.arenafighter.game.Sprites.Player;
import com.arenafighter.game.Sprites.PlayerFighter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.utils.Array;

import java.awt.Font;

import javax.swing.Box;

import sun.awt.image.InputStreamImageSource;

/**
 * Created by Frank on 2017-02-27.
 */

public class PlayState extends State {

    private Player player;
    private Texture bg;
    private Texture border;
    //private Texture floor;
    //private Vector2 floorPos1, floorPos2;
    //private Obstacle box; not used anymore, was just for testing purposes

    private Texture pause;
    private Texture options;
    private Texture menu;
    private Texture exit;

    private int optionsX;
    private int optionsY;
    private int menuX;
    private int menuY;
    private int exitX;
    private int exitY;

    //Scores
    private int score1;
    private String score1S;
    private BitmapFont font;
    private int score2;
    private String score2S;
    //private BitmapFont font2;
    private int score3;
    private String score3S;
    //private BitmapFont font3;
    private int score4;
    private String score4S;
    //private BitmapFont font4;

    private Map map;

    private PauseState pauseState;
    private boolean pauseVisible;

    EnergyAndForceManagerThread eafmt;

    //private MenuState menuState;
    private OptionsState optionsState;

    //spacing between boxes (tubes for flappy bird)
    private static final int BOX_SPACING = 3500;
    //amount of boxes that can be on the screen at a given time
    private static final int BOX_COUNT = 4;
    //private static final int FLOOR_Y_OFFSET = -50;

    private Array<Obstacle> obstacles;

    //Steven's variables and processes
    /////////////////////////////////////////////////////////////////////////////

    //Static for main menu to access it (restart position)
    public static Fighter player1 = new PlayerFighter();
    Fighter player2 = new PlayerFighter();

    /*EnergyAndForceManagerThread eafmt = new EnergyAndForceManagerThread();
    eafmt.addFighter(player1);
    eafmt.addFighter(player2);

    while(true){
        if(!eafmt.isAlive()){
            eafmt.start();
            System.out.println("Force/Energy Manager thread started");
            break;
        }
    }
    */
    Force rightForce = new Force(165, 0);
    Force leftForce = (Force)rightForce.getInverse();

    /////We switched NEGATIVE 155 to POSITIVE 155
    Force upForce = new Force(165, Math.PI / 2);
    Force downForce = (Force)upForce.getInverse();

    ///////////////////////////////////////////////////////////////////////////////////////


    protected PlayState(GameStateManager gsm) {
        super(gsm);
        //player = new Player(50, 100);

        //GOTTA MOVE THE BOUNDS WITH THE GOD DAMN PLAYER
        //
        //////////////////////////////////////////////
        player1 = new Fighter(300, 200);

        map = MapManager.map;

        //player2 = new Fighter(ArenaFighter.WIDTH-50, 100);
        cam.setToOrtho(false, ArenaFighter.WIDTH * 6, ArenaFighter.HEIGHT * 6);
        bg = new Texture("Play State.png");
        border = ArenaFighter.border;
        //floor = new Texture("Floor.png");
        //floorPos1 = new Vector2(cam.position.x - cam.viewportWidth / 2, FLOOR_Y_OFFSET);
        //floorPos2 = new Vector2((cam.position.x - cam.viewportWidth / 2) + floor.getWidth(), FLOOR_Y_OFFSET);
        //box = new Obstacle(100); not used anymore, was just for testing purposes

        pause = new Texture("Pause Overlay.png");
        options = new Texture("Pause Options.png");
        menu = new Texture("Pause Menu.png");
        exit = new Texture("Pause Exit.png");

        optionsX = ArenaFighter.WIDTH/2 - options.getWidth()/2;
        optionsY = 436;
        menuX = ArenaFighter.WIDTH/2 - menu.getWidth()/2;
        menuY = 282;
        exitX = ArenaFighter.WIDTH/2 - exit.getWidth()/2;
        exitY = 111;

        font = new BitmapFont(Gdx.files.internal("Break.fnt"));
        score1 = 0;
        score1S = Integer.toString(score1);
        score2 = 0;
        score2S = Integer.toString(score2);
        score3 = 0;
        score3S = Integer.toString(score3);
        score4 = 0;
        score4S = Integer.toString(score4);

        pauseState = new PauseState(gsm);
        pauseVisible = false;

        obstacles = new Array<Obstacle>();

        //creates 4 boxes (tubes) on the screen to have "constant flow" of boxes
        for(int i = 1; i <= BOX_COUNT; i++){
            obstacles.add(new Obstacle(i *(BOX_SPACING + Obstacle.BOX_WIDTH)));
        }

        //Steven's processes
        ///////////////////////////////////////////////////////////////////////////////////
        eafmt = new EnergyAndForceManagerThread();
        eafmt.addFighter(player1);
        eafmt.addFighter(player2);

        while(true){
            if(!eafmt.isAlive()){
                eafmt.start();
                System.out.println("Force/Energy Manager thread started");
                break;
            }
        }


        //////////////////////////////////////////////////////////////////////////////////////

    }

    private void pauseMenu(){



    }



    @Override
    protected void handleInput() {
        /*
        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            System.out.println("ESCAPE pressed");
            gsm.set(new MenuState(gsm));
        }



        if(Gdx.input.isKeyPressed(Input.Keys.D) && !player1.isRight){
            player1.isRight = true;
            player1.addForce(0, rightForce);
            System.out.println("Right force added");
        }

        if(Gdx.input.isKeyPressed(Input.Keys.W) && !player1.isUp){
            player1.isUp = true;
            player1.addForce(1, upForce);
            System.out.println("Up force added");
        }

        if(Gdx.input.isKeyPressed(Input.Keys.A) && !player1.isLeft){
            player1.isLeft = true;
            player1.addForce(2, leftForce);
            System.out.println("Left force added");
        }

        if(Gdx.input.isKeyPressed(Input.Keys.S) && !player1.isDown){
            player1.isDown = true;
            player1.addForce(3, downForce);
            System.out.println("Down force added");
        }
        */

        Gdx.input.setInputProcessor(new InputAdapter() {

            @Override
            public boolean keyDown (int keycode) {

                System.out.println(keycode);

                if(keycode == 131) {
                    System.out.println("ESCAPE pressed");
                    //gsm.set(ArenaFighter.menuState);
                    //gsm.set(pauseState);
                    if(pauseVisible == false) {
                        pauseVisible = true;
                        eafmt.pause();
                    }
                    else {
                        pauseVisible = false;
                        eafmt.unpause();
                    }
                }

                if(keycode == 32)
                    player1.walk(0, true);

                else if(keycode == 51)
                    player1.walk(1, true);

                else if(keycode == 29)
                    player1.walk(2, true);

                else if(keycode == 47)
                    player1.walk(3, true);

                else if(keycode == 59){
                    player1.dodge();
                }
                return true;
            }


            @Override
            public boolean keyUp (int keycode) {
                System.out.println(keycode);

                //PLAYER 1 CONTROL RELEASES
                if(keycode == 32)
                    player1.walk(0, false);

                else if(keycode == 51)
                    player1.walk(1, false);

                //WARNING: DIRECTIONS 2 AND 3 ARE SWAPPED
                else if(keycode == 47)
                    player1.walk(3, false);

                else if(keycode == 29)
                    player1.walk(2, false);

                return true;
            }



        });

        if (Gdx.input.justTouched()) {
            score1++;
        }


        if(pauseVisible == true) {
            if (Gdx.input.justTouched()) {
                System.out.println(Gdx.input.getX());
                System.out.println(ArenaFighter.HEIGHT - Gdx.input.getY());

                //Have to "flip" the Y input, since inputs start at top-left while textures start at bottom-left
                float y = ArenaFighter.HEIGHT - Gdx.input.getY();

                //Rectangle playBounds = new Rectangle(playX, playY, play.getWidth(), play.getHeight());
                Rectangle optionsBounds = new Rectangle(optionsX, optionsY, options.getWidth(), options.getHeight());
                Rectangle menuBounds = new Rectangle(menuX, menuY, menu.getWidth(), menu.getHeight());
                Rectangle exitBounds = new Rectangle(exitX, exitY, exit.getWidth(), exit.getHeight());

                //System.out.println(textureBounds.x);
                //System.out.println(textureBounds.y);

                Vector2 tmp = new Vector2(Gdx.input.getX(), y/*Gdx.input.getY()*/);
                //System.out.println(tmp.x);
                //System.out.println(tmp.y);
                //cam.unproject(tmp);


                // texture x is the x position of the texture
                // texture y is the y position of the texture
                // texturewidth is the width of the texture (you can get it with texture.getWidth() or textureRegion.getRegionWidth() if you have a texture region
                // textureheight is the height of the texture (you can get it with texture.getHeight() or textureRegion.getRegionhHeight() if you have a texture region


                if (optionsBounds.contains(tmp.x, tmp.y)) {
                    System.out.println("OPTIONS touched");
                    ArenaFighter.fromPause = true;
                    //gsm.set(ArenaFighter.menuState);
                    //gsm.set(MenuState.optionsState);
                    if (optionsState == null) {
                        optionsState = new OptionsState(gsm);
                        gsm.set(optionsState);
                    } else
                        gsm.set(optionsState);
                }

                if (menuBounds.contains(tmp.x, tmp.y)) {
                    System.out.println("MENU touched");
                    pauseVisible = false;
                    gsm.set(ArenaFighter.menuState);
                }

                if (exitBounds.contains(tmp.x, tmp.y)) {
                    System.out.println("EXIT touched");
                    System.exit(0);
                }


            }
        }


        //My initial inputs/physics


        /*
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

            if(Gdx.input.isKeyJustPressed(Input.Keys.A)){.isKeyPressed(Input.Keys.A)){
                player.dashX(-1);
            }

            if(Gdx.input.isKeyPressed(Input.Keys.D)){
                player.dashX(1);
            }

        }*/








    }

    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public void update(float dt) {

        if(map.getBounds().contains(player1.getBounds()) == false){
            score1++;
        }

        score1S = Integer.toString(score1);

        handleInput();
        //updateGround();
        //player.update(dt);
        //player1.update(dt);
        ///////////////////////////need to look with his updateFighter method


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
        //sb.draw(player.getTexture(), player.getPosition().x, player.getPosition().y);
        sb.draw(map.getTexture(), map.getMapX(), map.getMapY());
        sb.draw(player1.getTexture(), player1.getPosition().x, player1.getPosition().y);
        //font.getData().setScale(20);

        //Scores
        sb.draw(border,0, ArenaFighter.HEIGHT - border.getHeight());
        font.draw(sb, score1S, 0, 1080);
        font.draw(sb, score2S, ArenaFighter.WIDTH/4*1, 1080);
        font.draw(sb, score3S, ArenaFighter.WIDTH/4*2, 1080);
        font.draw(sb, score4S, ArenaFighter.WIDTH/4*3, 1080);

        //Pause Overlay
        if(pauseVisible == true) {
            sb.draw(pause, 0, 0);
            sb.draw(options, optionsX, optionsY);
            sb.draw(menu, menuX, menuY);
            sb.draw(exit, exitX, exitY);
        }
        //for(Obstacle obstacle : obstacles)
          //  sb.draw(obstacle.getBox(), obstacle.getPosBox().x, obstacle.getPosBox().y);

        //sb.draw(floor, floorPos1.x, floorPos1.y);
        //sb.draw(floor, floorPos2.x, floorPos2.y);

        sb.end();
    }

    @Override
    public void dispose() {
        //bg.dispose();
        //player1.dispose();
        //floor.dispose();
        /*for(Obstacle obstacle: obstacles)
            obstacle.dispose();*/
        //eafmt.keepRunning = false;

        //eafmt = null;
        /*player1 = null;
        player2 = null;
        player = null;
        bg = null;
        obstacles = null;*/


        System.out.println("Play State Disposed");
    }

    /*private void updateGround(){
        if(cam.position.x - (cam.viewportWidth / 2) > floorPos1.x + floor.getWidth())
           floorPos1.add(floor.getWidth() * 2, 0);
        if(cam.position.x - (cam.viewportWidth / 2) > floorPos2.x + floor.getWidth())
            floorPos2.add(floor.getWidth() * 2, 0);
    }*/
}
