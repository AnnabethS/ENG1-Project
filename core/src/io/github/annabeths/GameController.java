package io.github.annabeths;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

public class GameController implements Screen {

    eng1game game;
    ArrayList<GameObject> gameObjects;
    ArrayList<PhysicsObject> physicsObjects;
    float testRot = 0;
    private SpriteBatch batch;
    private Sprite mario;

    public GameController(eng1game g){
        game = g;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        mario = new Sprite(new Texture(Gdx.files.internal("mario/yanderedev.jpg")));
        mario.setSize(50, 50);
        mario.setOrigin(25, 25);
        mario.setCenter(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
    }

    @Override
    public void render(float delta) {
        // do updates here
        testRot += 10;
        mario.setRotation(testRot);

        // do draws here
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        mario.draw(batch);

        batch.end();

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

}