package com.neko;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.*;
import com.badlogic.gdx.graphics.g2d.freetype.*;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

public class Network extends Game{
	SpriteBatch batch;
	BitmapFont font1;
	BitmapFont font2;
	BitmapFont font3;
	Label.LabelStyle cstmFnt1;
	Label.LabelStyle cstmFnt2;
	Label.LabelStyle cstmFnt3;
	Viewport viewport;
	Texture mainBackground;
	Texture icon;
	Skin metalUI;

	
	@Override
	public void create () {
		batch = new SpriteBatch();
		metalUI = new Skin(Gdx.files.internal("metalui/metal-ui.json")); //idk why but having the outer folder was an issue for linux intellij(?)

		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/OpenSans-Regular.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 24;
		font1 = generator.generateFont(parameter);
		parameter.size = 40;
		font2 = generator.generateFont(parameter);
		parameter.size = 70;
		font3 = generator.generateFont(parameter);

		cstmFnt1 = new Label.LabelStyle(font1, Color.BLACK);
		cstmFnt2 = new Label.LabelStyle(font2, Color.BLACK);
		cstmFnt3 = new Label.LabelStyle(font3, Color.BLACK);
		generator.dispose(); // don't forget to dispose to avoid memory leaks!


		mainBackground = new Texture(Gdx.files.internal("Backgrounds/backgroundTexture169.jpg"));
		icon = new Texture(Gdx.files.internal("img.png"));


		//viewport = new FitViewport(1280,720);
		viewport = new FitViewport(850,450);
		this.setScreen(new MainScreen(this));
	}

	public void render() {
		super.render();
	}

	public void dispose () {
		batch.dispose();
		font1.dispose();
		font2.dispose();
		font3.dispose();

	}
}
