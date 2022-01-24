package io.github.annabeths;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

import io.github.annabeths.Projectiles.ProjectileDataHolder;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

public class GameController implements Screen {

    eng1game game;
    ArrayList<GameObject> gameObjects;
    ArrayList<PhysicsObject> physicsObjects;
    float testRot = 0;
    private SpriteBatch batch;
    
    BitmapFont font;
    GlyphLayout hpTextLayout;
    WaterBackground bg;

    ProjectileDataHolder projectileHolder;

    EnemyCollege testCollege;

    private Boat playerBoat;

    public GameController(eng1game g){ //passes the game class so that we can change scene back later
        game = g;
        gameObjects = new ArrayList<GameObject>();
        physicsObjects = new ArrayList<PhysicsObject>();
        bg = new WaterBackground(Gdx.graphics.getWidth(),
                                 Gdx.graphics.getHeight());
        
        projectileHolder = new ProjectileDataHolder();
        testCollege = new EnemyCollege(new Vector2(50,50), new Texture("img/castle1.png"), this, projectileHolder.stock);
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        
        // Create text object for player HP and load font
        font = new BitmapFont(Gdx.files.internal("fonts/bobcat.fnt"), false);
		hpTextLayout = new GlyphLayout();
		
        
        // Create the player boat and place it in the centre of the screen
        playerBoat = new PlayerBoat(this);
        playerBoat.SetPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight()/2); // place the player 
    }

    @Override
    public void render(float delta) {
        // do updates here
    	
        bg.Update(delta);
    	playerBoat.Update(delta);
        testCollege.Update(delta, playerBoat);

        if (physicsObjects.size() > 0)
        {
            Iterator<PhysicsObject> i = physicsObjects.iterator(); // use an iterator to safely remove objects from 
            // the list whilst traversing it
            while(i.hasNext())
            {
                PhysicsObject p = i.next();
                p.Update(delta); // update the current physicsobject
                if(playerBoat.CheckCollisionWith(p))
                {
                    if(playerBoat.OnCollision(p)) //if it returns true, then remove other
                        i.remove();
                }
            }
        }
        
        // do draws here
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin(); //begin the sprite batch
        
        bg.Draw(batch);
        testCollege.Draw(batch);
        playerBoat.Draw(batch);

        if (physicsObjects.size() > 0)
        {
            for (PhysicsObject physicsObject : physicsObjects) {
                physicsObject.Draw(batch);
            }
        }


        // Draw the text showing the player's HP
        hpTextLayout.setText(font, "HP: " + playerBoat.HP);
        font.getData().setScale(1);
        font.draw(batch, hpTextLayout, 5, Gdx.graphics.getHeight() - 10);
        batch.end(); //end the sprite batch

        //begin debug sprite batch
        ShapeRenderer sr = new ShapeRenderer();
        sr.begin(ShapeType.Line);
        sr.polygon(playerBoat.collisionPolygon.getTransformedVertices());
        sr.circle(playerBoat.collisionPolygon.getX()+playerBoat.collisionPolygon.getOriginX(),
        playerBoat.collisionPolygon.getY()+playerBoat.collisionPolygon.getOriginY(), 5);
        sr.end();
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

    public void gameOver(){

    }
    
    public void NewPhysicsObject(PhysicsObject obj) {
    	// A new PhysicsObject has been created, add it to the list so it receives updates
    	physicsObjects.add(obj);
    }

    public void RemovePhysicsObject(PhysicsObject obj){
        physicsObjects.remove(obj);
    }
}
