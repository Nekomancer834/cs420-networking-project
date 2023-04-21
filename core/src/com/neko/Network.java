package com.neko;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.utils.viewport.*;

public class Network extends Game{
	SpriteBatch batch;
	BitmapFont font;
	Viewport viewport;
	Texture mainBackground;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		font = new BitmapFont(); // use libGDX's default Arial font


		mainBackground = new Texture(Gdx.files.internal("Backgrounds/backgroundTexture169.jpg"));


		viewport = new FitViewport(1600,900);
		this.setScreen(new MainScreen(this));
	}

	public void render() {
		super.render();
	}

	public void dispose () {
		batch.dispose();
		font.dispose();

	}
}
