package com.arenafighter.game.States;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Frank on 2017-02-14.
 */

public abstract class State {

    protected OrthographicCamera cam;
    protected Vector3 pointer;
    protected GameStateManager gsm;

    protected State(GameStateManager gsm){

        this.gsm = gsm;
        cam = new OrthographicCamera();
        pointer = new Vector3();

    }


    protected abstract void handleInput();
    public abstract void update(float dt);
    public abstract void render(SpriteBatch sb);
    public abstract void dispose();

}
