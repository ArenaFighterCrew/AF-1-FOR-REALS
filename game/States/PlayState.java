package com.arenafighter.game.States;

import com.arenafighter.game.ArenaFighter;
import com.arenafighter.game.Physics.*;
import com.arenafighter.game.Physics.EnergyAndForceManagerThread;
import com.arenafighter.game.Physics.Force;
import com.arenafighter.game.Sprites.AIFighter;
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
import com.badlogic.gdx.utils.Timer;

import java.awt.Font;

import javax.swing.Box;

import sun.awt.image.InputStreamImageSource;

/**
 * Created by Frank on 2017-02-27.
 */

public class PlayState extends State {

    private Player player;
    private Texture bg;
    private static Texture border;
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

    /**Scores and their textures*/
    public static int score1;
    private static String score1S;
    private Texture icon1;
    private int icon1X;
    public static int score2;
    private static String score2S;
    private Texture icon2;
    private int icon2X;
    public static int score3;
    private static String score3S;
    private Texture icon3;
    private int icon3X;
    public static int score4;
    private static String score4S;
    private Texture icon4;
    private int icon4X;
    private BitmapFont font;
    public static int maxWins;

    private static boolean stop;

    private int scoreY;
    private int scoreYI;
    private int halffont = 65;
    private int offset = 20;
    private int padding = 40;
    private int iconW;
    private int iconH;

    public static Map map;
    public static int mapType;
    private static boolean mapSet;

    private static Timer timer;
    private static Timer timer2;
    private static int time;

    private PauseState pauseState;
    private WinState winState;
    public static boolean isWon;

    private static boolean pauseVisible;
    //private static boolean end;
    private static boolean canScore;

    private boolean newGame;

    //private int winner;
    public static boolean wait;
    private static boolean go;

    Rectangle optionsBounds;
    Rectangle menuBounds;
    Rectangle exitBounds;
    Vector2 tmp;

    public static EnergyAndForceManagerThread eafmt;

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
    public static PlayerFighter player1;
    public static int player1X;
    public static int player1Y;
    public static Vector2 center1;

    public static AIFighter player2;
    public static int player2X;
    public static int player2Y;
    public static Vector2 center2;

    public static AIFighter player3;
    public static int player3X;
    public static int player3Y;
    public static Vector2 center3;

    public static AIFighter player4;
    public static int player4X;
    public static int player4Y;
    public static Vector2 center4;

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
    Force rightForce = new Force(155, 0);
    Force leftForce = (Force)rightForce.getInverse();

    /////We switched NEGATIVE 155 to POSITIVE 155
    Force upForce = new Force(155, Math.PI / 2);
    Force downForce = (Force)upForce.getInverse();

    ///////////////////////////////////////////////////////////////////////////////////////


    protected PlayState(GameStateManager gsm) {
        super(gsm);
        //player = new Player(50, 100);

        cam.setToOrtho(false, ArenaFighter.WIDTH * 6, ArenaFighter.HEIGHT * 6);
        bg = new Texture("Play State.png");
        border = ArenaFighter.border;

        //GOTTA MOVE THE BOUNDS WITH THE GOD DAMN PLAYER
        //
        //////////////////////////////////////////////

        mapType = ArenaFighter.mapType;
        map = MapManager.setMap(mapType);
        mapSet = true;

        canScore = false;

        newGame = true;

        if(mapType == 1){
            player1X = (ArenaFighter.WIDTH - map.getTexture().getWidth()) / 2 + ArenaFighter.radius*6;
            player1Y = (ArenaFighter.HEIGHT - border.getHeight() - map.getTexture().getHeight()) / 2 + ArenaFighter.radius*4;

            player2X = (ArenaFighter.WIDTH + map.getTexture().getWidth()) / 2 - ArenaFighter.radius*8;
            player2Y = (ArenaFighter.HEIGHT - border.getHeight() - map.getTexture().getHeight()) / 2 + ArenaFighter.radius*4;

            player3X = (ArenaFighter.WIDTH - map.getTexture().getWidth()) / 2 + ArenaFighter.radius*6;
            player3Y = (ArenaFighter.HEIGHT - border.getHeight() + map.getTexture().getHeight()) / 2 - ArenaFighter.radius*6;

            player4X = (ArenaFighter.WIDTH + map.getTexture().getWidth()) / 2 - ArenaFighter.radius*8;
            player4Y = (ArenaFighter.HEIGHT - border.getHeight() + map.getTexture().getHeight()) / 2 - ArenaFighter.radius*6;
        }

        if(mapType == 2){
            player1X = (ArenaFighter.WIDTH - map.getTexture().getWidth()) / 2 + ArenaFighter.radius*4;
            player1Y = (ArenaFighter.HEIGHT - border.getHeight() - map.getTexture().getHeight()) / 2 + ArenaFighter.radius*3;

            player2X = (ArenaFighter.WIDTH + map.getTexture().getWidth()) / 2 - ArenaFighter.radius*6;
            player2Y = (ArenaFighter.HEIGHT - border.getHeight() - map.getTexture().getHeight()) / 2 + ArenaFighter.radius*3;

            player3X = (ArenaFighter.WIDTH - map.getTexture().getWidth()) / 2 + ArenaFighter.radius*4;
            player3Y = (ArenaFighter.HEIGHT - border.getHeight() + map.getTexture().getHeight()) / 2 - ArenaFighter.radius*5;

            player4X = (ArenaFighter.WIDTH + map.getTexture().getWidth()) / 2 - ArenaFighter.radius*6;
            player4Y = (ArenaFighter.HEIGHT - border.getHeight() + map.getTexture().getHeight()) / 2 - ArenaFighter.radius*5;
        }

        player1 = new PlayerFighter(player1X, player1Y, 1, 1);
        player2 = new AIFighter(player2X, player2Y, 2, 2);
        player3 = new AIFighter(player3X, player3Y, 3, 3);
        player4 = new AIFighter(player4X, player4Y, 4, 4);


        center1 = new Vector2();
        center2 = new Vector2();
        center3 = new Vector2();
        center4 = new Vector2();

        //player2.setTexture();

        //winner = 0;
        wait = false;
        go = false;

        stop = true;

        icon1 = player1.getTexture();
        icon2 = player2.getTexture();
        icon3 = player3.getTexture();
        icon4 = player4.getTexture();

        //player2 = new Fighter(ArenaFighter.WIDTH-50, 100);

        iconW = icon1.getWidth()*2;
        iconH = icon1.getHeight()*2;

        scoreY = ArenaFighter.HEIGHT - border.getHeight()/2 + halffont;
        scoreYI = ArenaFighter.HEIGHT - border.getHeight()/2 - iconH/2;
        icon1X = ArenaFighter.WIDTH/4*0 + padding;
        icon2X = ArenaFighter.WIDTH/4*1 + padding;
        icon3X = ArenaFighter.WIDTH/4*2 + padding;
        icon4X = ArenaFighter.WIDTH/4*3 + padding;

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
        maxWins = ArenaFighter.maxWins;

        pauseState = new PauseState(gsm);
        pauseVisible = false;

        isWon = false;

        time = 5;

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
        eafmt.addFighter(player3);
        eafmt.addFighter(player4);

        while(true){
            if(!eafmt.isAlive()){
                eafmt.start();
                System.out.println("Force/Energy Manager thread started");
                break;
            }
        }

        /*while(true){
            if(!player2.getControlThread().isAlive()){
                player2.getControlThread().start();
                System.out.println("Player2 AI control thread started");
            }
            else if(!player3.getControlThread().isAlive()){
                player3.getControlThread().start();
                System.out.println("Player3 AI control thread started");
            }
            else if(!player4.getControlThread().isAlive()){
                player4.getControlThread().start();
                System.out.println("Player4 AI control thread started");
                break;
            }
        }*/


        //////////////////////////////////////////////////////////////////////////////////////

        timer.schedule(new Timer.Task(){
                           @Override
                           public void run() {

                               /*if(end){
                                   if(time > 0)
                                       time--;
                                   if(time == 0){
                                       System.out.println(Boolean.toString(player1.getDead()));
                                       restart();
                                   }
                               }*/

                               if(wait && !ArenaFighter.atMenu){
                                   //go =false;
                                   eafmt.pause();
                                   if(time > 0 && !pauseVisible)
                                       time--;

                                   if(time == 0){
                                       wait = false;
                                       //System.out.println(Boolean.toString(player1.getDead()));
                                       //roundEnd();
                                       if(isWon){
                                           wait = true;
                                           win();
                                       }
                                       else
                                           restart();
                                   }
                               }


                               else {
                                   if (!pauseVisible && !ArenaFighter.atMenu) {
                                       if (time > 0) {
                                           maxWins = ArenaFighter.maxWins;
                                           /*if(stop){
                                               eafmt.pause();
                                               stop = false;
                                           }*/
                                           /*player2.getControlThread().pause();
                                           player3.getControlThread().pause();
                                           player4.getControlThread().pause();*/
                                           time--;
                                           if (time < 4 && time >= 1 && !isWon && ArenaFighter.sToggle)
                                               ArenaFighter.beep.play(0.6f);
                                       }
                                       if (time == 0) {
                                           time--;
                                           canScore = true;
                                           mapSet = true;
                                           /*player2.getControlThread().unpause();
                                           player3.getControlThread().unpause();
                                           player4.getControlThread().unpause();*/
                                           eafmt.unpause();
                                           if(!isWon) {
                                               if(newGame){
                                                   //Creates the AI
                                                   while(true){
                                                       if(!player2.getControlThread().isAlive()){
                                                           player2.getControlThread().start();
                                                           System.out.println("Player2 AI control thread started");
                                                       }
                                                       else if(!player3.getControlThread().isAlive()){
                                                           player3.getControlThread().start();
                                                           System.out.println("Player3 AI control thread started");
                                                       }
                                                       else if(!player4.getControlThread().isAlive()){
                                                           player4.getControlThread().start();
                                                           System.out.println("Player4 AI control thread started");
                                                           break;
                                                       }
                                                       newGame = false;
                                                   }
                                               }
                                               if(ArenaFighter.sToggle)
                                                   ArenaFighter.airhorn.play(0.3f);
                                           }
                                       }
                                   }
                               }
                           }

                       }
                , 0        //    (delay)
                , 1.15f    //    (seconds)
                //, 4        //    (repeat count)
        );
    }

    //private void pauseMenu(){
    //}

    public static void switchPos(){

        if(mapType == 1){
            player1X = (ArenaFighter.WIDTH - map.getTexture().getWidth()) / 2 + ArenaFighter.radius*6;
            player1Y = (ArenaFighter.HEIGHT - border.getHeight() - map.getTexture().getHeight()) / 2 + ArenaFighter.radius*4;

            player2X = (ArenaFighter.WIDTH + map.getTexture().getWidth()) / 2 - ArenaFighter.radius*8;
            player2Y = (ArenaFighter.HEIGHT - border.getHeight() - map.getTexture().getHeight()) / 2 + ArenaFighter.radius*4;

            player3X = (ArenaFighter.WIDTH - map.getTexture().getWidth()) / 2 + ArenaFighter.radius*6;
            player3Y = (ArenaFighter.HEIGHT - border.getHeight() + map.getTexture().getHeight()) / 2 - ArenaFighter.radius*6;

            player4X = (ArenaFighter.WIDTH + map.getTexture().getWidth()) / 2 - ArenaFighter.radius*8;
            player4Y = (ArenaFighter.HEIGHT - border.getHeight() + map.getTexture().getHeight()) / 2 - ArenaFighter.radius*6;
        }

        if(mapType == 2){
            player1X = (ArenaFighter.WIDTH - map.getTexture().getWidth()) / 2 + ArenaFighter.radius*4;
            player1Y = (ArenaFighter.HEIGHT - border.getHeight() - map.getTexture().getHeight()) / 2 + ArenaFighter.radius*3;

            player2X = (ArenaFighter.WIDTH + map.getTexture().getWidth()) / 2 - ArenaFighter.radius*6;
            player2Y = (ArenaFighter.HEIGHT - border.getHeight() - map.getTexture().getHeight()) / 2 + ArenaFighter.radius*3;

            player3X = (ArenaFighter.WIDTH - map.getTexture().getWidth()) / 2 + ArenaFighter.radius*4;
            player3Y = (ArenaFighter.HEIGHT - border.getHeight() + map.getTexture().getHeight()) / 2 - ArenaFighter.radius*5;

            player4X = (ArenaFighter.WIDTH + map.getTexture().getWidth()) / 2 - ArenaFighter.radius*6;
            player4Y = (ArenaFighter.HEIGHT - border.getHeight() + map.getTexture().getHeight()) / 2 - ArenaFighter.radius*5;
        }

    }

    private void playerCenter(){

        center1.set(player1.getPosition().x + ArenaFighter.radius, player1.getPosition().y + ArenaFighter.radius);
        center2.set(player2.getPosition().x + ArenaFighter.radius, player2.getPosition().y + ArenaFighter.radius);
        center3.set(player3.getPosition().x + ArenaFighter.radius, player3.getPosition().y + ArenaFighter.radius);
        center4.set(player4.getPosition().x + ArenaFighter.radius, player4.getPosition().y + ArenaFighter.radius);

    }


    private void win(){

        ArenaFighter.atMenu = true;
        eafmt.pause();
        //if(isWon) {
            if (winState == null) {
                winState = new WinState(gsm);
                gsm.set(winState);
            } else
                gsm.push(winState);
        //}

    }

    public static void resetScore(){

        score1 = 0;
        score2 = 0;
        score3 = 0;
        score4 = 0;

        score1S = Integer.toString(score1);
        score2S = Integer.toString(score2);
        score3S = Integer.toString(score3);
        score4S = Integer.toString(score4);
    }

    private static void delay(){

        score1S = Integer.toString(score1);
        score2S = Integer.toString(score2);
        score3S = Integer.toString(score3);
        score4S = Integer.toString(score4);

        if(score1 == maxWins || score2 == maxWins || score3 == maxWins || score4 == maxWins) {
            isWon = true;
            //go = false;
        }
        wait = true;
        time = 2;

        EnergyAndForceManagerThread.resetQueued = true;
    }

    private static void initialPos(){

        player1.getPosition().x = player1X;
        player1.getPosition().y = player1Y;
        player1.getBounds().setPosition(player1.getPosition().x, player1.getPosition().y);
        player2.getPosition().x = player2X;
        player2.getPosition().y = player2Y;
        player2.getBounds().setPosition(player2.getPosition().x, player2.getPosition().y);
        player3.getPosition().x = player3X;
        player3.getPosition().y = player3Y;
        player3.getBounds().setPosition(player3.getPosition().x, player3.getPosition().y);
        player4.getPosition().x = player4X;
        player4.getPosition().y = player4Y;
        player4.getBounds().setPosition(player4.getPosition().x, player4.getPosition().y);
    }


    public static void restart(){

        if(ArenaFighter.bToggle) {
            if (mapType == 1 && mapSet) {
                mapSet = false;
                mapType = 2;
                map = MapManager.setMap(mapType);
            }

            if (mapType == 2 && mapSet) {
                mapSet = false;
                mapType = 1;
                map = MapManager.setMap(mapType);
            }

            switchPos();
        }

        player1.setDead(false);
        player2.setDead(false);
        player3.setDead(false);
        player4.setDead(false);

        initialPos();

        /*player1.getPosition().x = player1X;
        player1.getBounds().setPosition(player1.getPosition().x, player1.getPosition().y);
        System.out.println(player1.getPosition().x);
        //System.out.println(player1.getPosition().x);
        PlayState.player1.getPosition().y = player1Y;
        PlayState.player2.getPosition().x = player2X;
        PlayState.player2.getPosition().y = player2Y;
        PlayState.player3.getPosition().x = player3X;
        PlayState.player3.getPosition().y = player3Y;
        PlayState.player4.getPosition().x = player4X;
        PlayState.player4.getPosition().y = player4Y;*/

        time = 5;

        System.out.println("Restarted");
    }


    private void death(){
        //Checks if player is dead

        if(mapType == 1) {
            if (!map.getBounds1().contains(center1) && !player1.getDead()) {
                player1.setDead(true);
                if(ArenaFighter.sToggle)
                    ArenaFighter.death.play(0.2f);
            }

            if (!map.getBounds1().contains(center2) && !player2.getDead()) {
                player2.setDead(true);
                if(ArenaFighter.sToggle)
                    ArenaFighter.death.play(0.2f);
            }

            if (!map.getBounds1().contains(center3) && !player3.getDead()) {
                player3.setDead(true);
                if(ArenaFighter.sToggle)
                    ArenaFighter.death.play(0.2f);
            }

            if (!map.getBounds1().contains(center4) && !player4.getDead()) {
                player4.setDead(true);
                if(ArenaFighter.sToggle)
                    ArenaFighter.death.play(0.2f);
            }
        }

        if(mapType == 2) {
            if (!map.getBounds2().contains(center1) && !player1.getDead()) {
                player1.setDead(true);
                if(ArenaFighter.sToggle)
                    ArenaFighter.death.play(0.2f);
            }

            if (!map.getBounds2().contains(center2) && !player2.getDead()) {
                player2.setDead(true);
                if(ArenaFighter.sToggle)
                    ArenaFighter.death.play(0.2f);
            }

            if (!map.getBounds2().contains(center3) && !player3.getDead()) {
                player3.setDead(true);
                if(ArenaFighter.sToggle)
                    ArenaFighter.death.play(0.2f);
            }

            if (!map.getBounds2().contains(center4) && !player4.getDead()) {
                player4.setDead(true);
                if(ArenaFighter.sToggle)
                    ArenaFighter.death.play(0.2f);
            }
        }

    }


    private void score(){

        death();

        //Checks if player is last one alive
        if (player1.getDead() && player2.getDead() && player3.getDead() && player4.getDead() && canScore){
            canScore = false;
            delay();
        }

        else if(!player1.getDead() && player2.getDead() && player3.getDead() && player4.getDead() && canScore){
            score1++;
            canScore = false;
            delay();
        }

        else if(!player2.getDead() && player1.getDead() && player3.getDead() && player4.getDead() && canScore){
            score2++;
            canScore = false;
            delay();
        }

        else if(!player3.getDead() && player1.getDead() && player2.getDead() && player4.getDead() && canScore){
            score3++;
            canScore = false;
            delay();
        }

        else if(!player4.getDead() && player1.getDead() && player2.getDead() && player3.getDead() && canScore){
            score4++;
            canScore = false;
            delay();
        }





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
                public boolean keyDown(int keycode) {

                    System.out.println(keycode);

                    if (keycode == 131) {
                        System.out.println("ESCAPE pressed");
                        //gsm.set(ArenaFighter.menuState);
                        //gsm.set(pauseState);
                        if (pauseVisible == false) {
                            pauseVisible = true;
                            eafmt.pause();
                        }
                        else {
                            pauseVisible = false;
                            eafmt.unpause();
                        }
                    }

                    if(pauseVisible == false && time <= 0) {

                        //System.out.println("Debug");

                        if(keycode == 32)
                            player1.walk(0, true);

                        else if(keycode == 51)
                            player1.walk(1, true);

                        else if(keycode == 29)
                            player1.walk(2, true);

                        else if(keycode == 47)
                            player1.walk(3, true);

                        else if(keycode == 62){
                            player1.dodge();
                        }

                    }
                    return true;
                }


                @Override
                public boolean keyUp(int keycode) {
                    System.out.println(keycode);

                    if(pauseVisible == false && time <= 0) {

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


                    }
                    return true;
                }


            });

        if(pauseVisible == false && time <= 0) {
            if (Gdx.input.justTouched()) {
                double x = Gdx.input.getX() - 39;
                double y = ArenaFighter.HEIGHT - Gdx.input.getY() - 85;
                player1.dash(x, y);
            }
        }

        if(pauseVisible == true) {
            if (Gdx.input.justTouched()) {
                System.out.println(Gdx.input.getX());
                System.out.println(ArenaFighter.HEIGHT - Gdx.input.getY());

                //Have to "flip" the Y input, since inputs start at top-left while textures start at bottom-left
                float y = ArenaFighter.HEIGHT - Gdx.input.getY();

                //if(optionsBounds.getX() == 0) {
                    //Rectangle playBounds = new Rectangle(playX, playY, play.getWidth(), play.getHeight());
                    optionsBounds = new Rectangle(optionsX, optionsY, options.getWidth(), options.getHeight());
                    menuBounds = new Rectangle(menuX, menuY, menu.getWidth(), menu.getHeight());
                    exitBounds = new Rectangle(exitX, exitY, exit.getWidth(), exit.getHeight());

                    //System.out.println(textureBounds.x);
                    //System.out.println(textureBounds.y);

                    tmp = new Vector2(Gdx.input.getX(), y/*Gdx.input.getY()*/);
                    //System.out.println(tmp.x);
                    //System.out.println(tmp.y);
                    //cam.unproject(tmp);
                //}

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
                    ArenaFighter.atMenu = true;
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

    /*public boolean keyUp(int keycode) {
        return false;
    }*/

    @Override
    public void update(float dt) {

        /////Trying to pause the AI here
        /*if(go)
            eafmt.unpause();

        if(!go)
            eafmt.pause();

        if(canScore)
            eafmt.unpause();
        */

        //System.out.println(Integer.toString(time));

        playerCenter();

        score();

        handleInput();

        //win();

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

        if(!player1.getDead())
            sb.draw(player1.getTexture(), player1.getPosition().x, player1.getPosition().y);

        if(!player2.getDead())
            sb.draw(player2.getTexture(), player2.getPosition().x, player2.getPosition().y);

        if(!player3.getDead())
            sb.draw(player3.getTexture(), player3.getPosition().x, player3.getPosition().y);

        if(!player4.getDead())
            sb.draw(player4.getTexture(), player4.getPosition().x, player4.getPosition().y);

        //font.getData().setScale(20);

        //Scores
        sb.draw(border,0, ArenaFighter.HEIGHT - border.getHeight());
        font.draw(sb, score1S, icon1X + iconW + offset, scoreY);
        font.draw(sb, score2S, icon2X + iconW + offset, scoreY);
        font.draw(sb, score3S, icon3X + iconW + offset, scoreY);
        font.draw(sb, score4S, icon4X + iconW + offset, scoreY);

        sb.draw(icon1, icon1X, scoreYI, iconW, iconH);
        sb.draw(icon2, icon2X, scoreYI, iconW, iconH);
        sb.draw(icon3, icon3X, scoreYI, iconW, iconH);
        sb.draw(icon4, icon4X, scoreYI, iconW, iconH);

        if(time < 4 && time > 0 && !wait/*!end*/){
            font.draw(sb, Integer.toString(time), ArenaFighter.WIDTH/2 - halffont + offset, ArenaFighter.HEIGHT/2 - offset);
        }

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
