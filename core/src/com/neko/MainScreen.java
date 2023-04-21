package com.neko;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label.*;

public class MainScreen implements Screen {
    protected final Network game;
    protected Screen thisScreen;
    protected OrthographicCamera camera;
    protected Stage mainStage;

    //create all the UI elements
    protected Image background;
    protected Label lenLabel;
    protected Label rateLabel;
    protected Label packLabel;
    protected Label timerLabel;
    protected Label propSpdLabel;
    protected SelectBox lenList;
    protected SelectBox rateList;
    protected SelectBox packList;
    protected TextButton startBtn;
    protected TextButton resetBtn;
    protected ShapeRenderer shape;
    protected float boxX = 0;
    protected float boxWidth = 0;

    protected String[] lenListArray = {"10 km", "100 km", "500 km", "1000 km"};
    protected int[] lenListNumArray = {10000, 100000, 500000, 1000000};
    protected String[] rateListArray = {"512 Kbps","1 Mbps","10 Mbps","100 Mbps"};
    protected int[] rateListNumArray = {512000, 1000000, 10000000, 100000000};
    protected String[] packListArray = {"100 Bytes","500 Bytes","1 KByte"};
    protected int[] packListNumArray = {100, 1000000, 500, 1000};

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

        shape = new ShapeRenderer();


        //length
        lenLabel = new Label("Length", game.cstmFnt1);
        //lenLabel.setPosition(220,.1f*mainStage.getHeight());
        lenList = new SelectBox(game.metalUI);
        //lenList.setPosition(lenLabel.getX()+lenLabel.getWidth()+20,.1f*mainStage.getHeight()+6);
        lenList.setWidth(lenLabel.getWidth()+20);
        lenList.setHeight(lenLabel.getHeight()*.66f);
        lenList.setItems(lenListArray);

        //rate
        rateLabel = new Label("Rate",game.cstmFnt1);
        //rateLabel.setPosition(lenList.getX()+lenList.getWidth()+20, .1f*mainStage.getHeight());
        rateList = new SelectBox(game.metalUI);
        //rateList.setPosition(rateLabel.getX()+rateLabel.getWidth()+20,.1f*mainStage.getHeight()+6);
        rateList.setWidth(lenList.getWidth());
        rateList.setHeight(lenList.getHeight());
        rateList.setItems(rateListArray);

        //packet size
        packLabel = new Label("Packet Size",game.cstmFnt1);
        //packLabel.setPosition(rateList.getX()+rateList.getWidth()+20, .1f*mainStage.getHeight());
        packList = new SelectBox(game.metalUI);
        //packList.setPosition(packLabel.getX()+packLabel.getWidth()+20,.1f*mainStage.getHeight()+6);
        packList.setWidth(lenList.getWidth());
        packList.setHeight(lenList.getHeight());
        packList.setItems(packListArray);

        //both buttons
        startBtn = new TextButton("START",game.metalUI);
        startBtn.setWidth(lenLabel.getWidth());
        startBtn.setHeight(lenLabel.getHeight());
        resetBtn = new TextButton("RESET", game.metalUI);
        resetBtn.setWidth(lenLabel.getWidth());
        resetBtn.setHeight(lenLabel.getHeight());

        lenLabel.setPosition(mainStage.getWidth()/2-(lenLabel.getWidth()+20+ //this absurd monstrosity scales the labels, dropdowns, and buttons to center on the screen horizontally
                                                        lenList.getWidth()+20+
                                                        rateLabel.getWidth()+20+
                                                        rateList.getWidth()+20+
                                                        packLabel.getWidth()+20+
                                                        packList.getWidth()+20+
                                                        startBtn.getWidth()+20+ // start button width
                                                        resetBtn.getWidth() //reset button width
                                                        )/2, .1f*mainStage.getHeight());
        lenList.setPosition(lenLabel.getX()+lenLabel.getWidth()+20,.1f*mainStage.getHeight()+6);
        rateLabel.setPosition(lenList.getX()+lenList.getWidth()+20, .1f*mainStage.getHeight());
        rateList.setPosition(rateLabel.getX()+rateLabel.getWidth()+20,.1f*mainStage.getHeight()+6);
        packLabel.setPosition(rateList.getX()+rateList.getWidth()+20, .1f*mainStage.getHeight());
        packList.setPosition(packLabel.getX()+packLabel.getWidth()+20,.1f*mainStage.getHeight()+6);
        startBtn.setPosition(packList.getX()+packList.getWidth()+20, .1f*mainStage.getHeight()+6);
        resetBtn.setPosition(startBtn.getX()+startBtn.getWidth()+20, .1f*mainStage.getHeight()+6);

        timerLabel = new Label("0.760 ms",game.cstmFnt2);
        timerLabel.setPosition((mainStage.getWidth()/2)-(timerLabel.getWidth()/2),mainStage.getHeight()/2-50);

        propSpdLabel = new Label("Propagation Speed: 2.8 x 10^8 m/sec",game.cstmFnt1);
        propSpdLabel.setPosition((mainStage.getWidth()/2)-(propSpdLabel.getWidth()/2), mainStage.getHeight()/2-150);

        //lenList.getSelectedIndex(); this is the better way to use the selected value

    }
    @Override
    public void render(float delta){
        //init render
        camera.update();
        mainStage.act();
        shape.begin(ShapeType.Filled);

        //clear screen
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //shape render calls
        shape.setColor(Color.RED);
        shape.rect(boxX,0,boxWidth,25);
        boxX++;
        boxWidth++;
        if(boxX>1290/2){
            boxX = 0;
        }
        if(boxWidth>800){
            boxWidth = 0;
        }

        //add actors
        mainStage.addActor(background);
        mainStage.addActor(lenLabel);
        mainStage.addActor(rateLabel);
        mainStage.addActor(packLabel);
        mainStage.addActor(timerLabel);
        mainStage.addActor(propSpdLabel);

        mainStage.addActor(lenList);
        mainStage.addActor(rateList);
        mainStage.addActor(packList);

        mainStage.addActor(startBtn);
        mainStage.addActor(resetBtn);

        //finish render
        mainStage.draw();
        shape.end();

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
