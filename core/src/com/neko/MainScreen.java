package com.neko;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;

public class MainScreen implements Screen {
    protected final Network game;
    protected Screen thisScreen;
    protected OrthographicCamera camera;
    protected Stage mainStage;

    //create all the UI elements
    protected Image background;
    protected Image senderImg;
    protected Image receiverImg;
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
    protected  Boolean startFlag = false;
    protected double totalTime = 0;
    protected double propTime = 0;
    protected double transmissionTime = 0;
    protected double propConstant = 2.8E+8;
    protected double displayTime;
    protected double boxX = 10;
    protected double boxWidth = 0;
    protected double frameX = 0;
    protected double frameY = 0;
    protected double frameWidth;

    int i=0;
    double selectedLen;
    double selectedRate;
    double selectedPacket;
    double dDT;
    double growRate;

    protected String[] lenListArray = {"10 km", "100 km", "500 km", "1000 km"};
    protected float[] lenListNumArray = {10000, 100000, 500000, 1000000};
    protected String[] rateListArray = {"512 Kbps","1 Mbps","10 Mbps","100 Mbps"};
    protected float[] rateListNumArray = {512000, 1000000, 10000000, 100000000};
    protected String[] packListArray = {"100 Bytes","500 Bytes","1 KByte"};
    protected float[] packListNumArray = {100*8, 500*8, 1000*8};

    public MainScreen(final Network game){
        //assign reference to the original game class
        this.game = game;
        this.thisScreen = this;
        //set up camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 850, 450);
        mainStage = new Stage(game.viewport);
        Gdx.input.setInputProcessor(mainStage);

        //initialize assets
        background = new Image(game.mainBackground);
        background.setPosition(0,0);
        senderImg = new Image(game.icon);
        senderImg.setPosition(50,290);
        senderImg.setScale(.3f);
        receiverImg = new Image(game.icon);
        receiverImg.setPosition(mainStage.getWidth()-receiverImg.getWidth()*.3f-50,290);
        receiverImg.setScale(.3f);

        shape = new ShapeRenderer();
        shape.setProjectionMatrix(mainStage.getViewport().getCamera().combined);


        //length
        lenLabel = new Label("Length", game.cstmFnt1);
        lenList = new SelectBox(game.metalUI);
        lenList.setWidth(lenLabel.getWidth()+20);
        lenList.setHeight(lenLabel.getHeight()*.66f);
        lenList.setItems(lenListArray);

        //rate
        rateLabel = new Label("Rate",game.cstmFnt1);
        rateList = new SelectBox(game.metalUI);
        rateList.setWidth(lenList.getWidth());
        rateList.setHeight(lenList.getHeight());
        rateList.setItems(rateListArray);

        //packet size
        packLabel = new Label("Packet Size",game.cstmFnt1);
        packList = new SelectBox(game.metalUI);
        packList.setWidth(lenList.getWidth());
        packList.setHeight(lenList.getHeight());
        packList.setItems(packListArray);

        //both buttons
        startBtn = new TextButton("START",game.metalUI);
        startBtn.setWidth(lenLabel.getWidth());
        startBtn.setHeight(lenLabel.getHeight());
        startBtn.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                selectedPacket = packListNumArray[packList.getSelectedIndex()];
                selectedLen = lenListNumArray[lenList.getSelectedIndex()];
                selectedRate = rateListNumArray[rateList.getSelectedIndex()];

                propTime = selectedLen/(float)propConstant;     //connection length / prop speed
                transmissionTime = selectedPacket/selectedRate; //packet size / transmission rate
                totalTime= (propTime + transmissionTime); //total time in ms


                boxWidth=(transmissionTime/totalTime)*(selectedPacket/10);
                growRate = ((selectedPacket/10) / (transmissionTime));
                boxX=frameX-boxWidth;
                i=0;
                displayTime=0;
                startFlag = true;

            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                return true;
            }
            @Override
            public void enter (InputEvent event, float x, float y, int pointer, Actor fromActor) {


            }
            @Override
            public void exit (InputEvent event, float x, float y, int pointer, Actor fromActor) {

            }
        });
        resetBtn = new TextButton("RESET", game.metalUI);
        resetBtn.setWidth(lenLabel.getWidth());
        resetBtn.setHeight(lenLabel.getHeight());
        resetBtn.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                //startFlag = true;

            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                startFlag = false;
                boxWidth = 0;
                boxX=frameX-boxWidth;
                i=0;
                growRate=0;
                displayTime=0;
                return true;
            }
            @Override
            public void enter (InputEvent event, float x, float y, int pointer, Actor fromActor) {


            }
            @Override
            public void exit (InputEvent event, float x, float y, int pointer, Actor fromActor) {

            }
        });
        //TODO: add Btn handlers

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

        timerLabel = new Label("0.00 ms",game.cstmFnt2);
        timerLabel.setPosition((mainStage.getWidth()/2)-(timerLabel.getWidth()/2),mainStage.getHeight()/2-50);

        propSpdLabel = new Label("Propagation Speed: 2.8 x 10^8 m/sec",game.cstmFnt1);
        propSpdLabel.setPosition((mainStage.getWidth()/2)-(propSpdLabel.getWidth()/2), mainStage.getHeight()/2-100);

        //lenList.getSelectedIndex(); this is the better way to use the selected value
        System.out.println("mainStageX: "+mainStage.getWidth());
        System.out.println("mainStageY: "+mainStage.getHeight());

    }
    @Override
    public void render(float delta){

        //math that updates every render
        frameWidth = mainStage.getWidth()*.66f;
        frameX = (mainStage.getWidth()/2)-(frameWidth/2);
        frameY = (mainStage.getHeight()*.66f);

        dDT = (float).000005;

        System.out.printf("frames: %10s frameWidth: %10s boxWidth: %10s\r", i,frameWidth,boxWidth);


        //init render
        camera.update();
        mainStage.act();

        //clear screen
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);




        //TODO: moving red box logic

        if(startFlag){
            i++;
            displayTime+= dDT;
            if(displayTime>totalTime){ //if time elapsed passes total time then stop loop
                displayTime=totalTime;
                startFlag=false;
            }
            boxX+=((frameWidth+boxWidth)/(totalTime/dDT)); // move the red box

            timerLabel.setText(String.format("%.2f ms", displayTime*1000)); // output the current (virtual) time elapsed
        }

        //add actors
        mainStage.addActor(background);
        //mainStage.addActor(senderImg);
        //mainStage.addActor(receiverImg);
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


        //finish render of non-active assets
        mainStage.draw();

        //shape render calls


        //moving red box
        shape.begin(ShapeType.Filled);
        shape.setColor(Color.RED);
        shape.rect((float)boxX,(float)frameY,(float)boxWidth,30);

        //white blocks on either end of the bar window
        shape.setColor(Color.WHITE);
        shape.rect(0, (float)frameY, (float)frameX, 30);
        shape.rect((float)(frameX+frameWidth), (float)frameY, (float)(mainStage.getWidth()-frameX-frameWidth), 30);
        shape.end();

        //hollow grey box
        shape.begin(ShapeType.Line);
        shape.setColor(Color.DARK_GRAY);
        shape.rect((float)frameX,(float)frameY,(float)frameWidth, 30);
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
