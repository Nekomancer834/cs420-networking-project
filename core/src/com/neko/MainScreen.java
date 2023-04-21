package com.neko;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;

public class MainScreen implements Screen {
    protected final Network game;
    protected Screen thisScreen;
    protected OrthographicCamera camera;
    protected Stage mainStage;

    protected Image background;

    public MainScreen(final Network game){
        //assign reference to the original game class
        this.game = game;
        this.thisScreen = this;
        //set up camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1600, 900);
        mainStage = new Stage(game.viewport);
        Gdx.input.setInputProcessor(mainStage);

        //initialize assets
        background = new Image(game.mainBackground);
        background.setPosition(0,0);


    }
    @Override
    public void render(float delta){
        //init render
        camera.update();
        mainStage.act();
        game.batch.begin();

        //clear screen
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //batch draw calls

        //add actors
        mainStage.addActor(background);

        //finish render
        game.batch.end();
        mainStage.draw();

    }
    @Override
    public void show(){

    }
    @Override
    public void resize(int width, int height){
        mainStage.getViewport().update(width, height, true);
    }
    @Override
    public void pause() {
    }

    @Override
    public void resume() {
        Gdx.input.setInputProcessor(mainStage);
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        mainStage.dispose();
    }
}
