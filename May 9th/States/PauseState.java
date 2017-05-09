package com.arenafighter.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Frank on 2017-04-21.
 */

public class PauseState extends State {


    private Texture pause;

    public PauseState(GameStateManager gsm) {
        super(gsm);
        pause = new Texture("Pause Overlay.png");
    }

    @Override
    protected void handleInput(){

        /*if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            System.out.println("ESCAPE pressed");
            gsm.set(MenuState.playState);
        }*/

        Gdx.input.setInputProcessor(new InputAdapter() {

            @Override
            public boolean keyDown(int keycode) {

                System.out.println(keycode);

                /*if (keycode == 131) {
                    System.out.println("ESCAPE pressed");
                    //gsm.set(ArenaFighter.menuState);
                    gsm.set(MenuState.playState);
                }*/

                return true;
            }
        });
    }

    @Override
    public  void update(float dt){
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb){

        sb.begin();
        sb.draw(pause, 0, 0);
        sb.end();

    }

    @Override
    public  void dispose(){

    }


}
