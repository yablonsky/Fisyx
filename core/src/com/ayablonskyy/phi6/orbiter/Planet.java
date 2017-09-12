package com.ayablonskyy.phi6.orbiter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by and on 8/12/2017.
 */

public class Planet extends Sprite{
    private final float radius;
    public final float mass;
    private final float density = 1;

    private Color color;
    private Circle circle;
    public Vector2 position;
    public Vector2 velocity;
    public Vector2 acceleration;

    public Planet(Vector2 position, float radius, Color color) {
        super(createTexture(radius, color));
        this.position = position;
        this.radius = radius;
        this.color = color;
        velocity = new Vector2();
        acceleration = new Vector2();
        circle = new Circle(position, radius);
        mass = circle.area() * density;
        setBounds(position.x - radius, position.y - radius, radius * 2, radius * 2);
    }

    public Planet(float x, float y, float radius, Color color) {
        this(new Vector2(x, y), radius, color);
    }

    private static Texture createTexture(float radius, Color color) {
        Pixmap pixmap = new Pixmap((int) radius * 2 + 1,(int) radius * 2+ 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(color);
        pixmap.fillCircle((int) radius, (int) radius, (int) radius);
        Texture texture = new Texture(pixmap);
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        pixmap.dispose();
        return texture;
    }

    public void draw(Batch batch, BitmapFont font) {
        draw(batch, font, false);
    }

    public void draw(Batch batch, BitmapFont font, boolean debug) {
        super.draw(batch);
        if (debug) {
            font.draw(batch, "velocity: " + velocity.len(), position.x, position.y);
        }
    }

    public void update(float deltaT) {
        velocity.add(acceleration);
        position.add(velocity);
        setPosition();
        acceleration.setZero();
    }

    public void applyForce(Vector2 force) {
        acceleration.add(force.cpy().add(force).scl(1 / mass));
    }

    public void attract(Planet other) {
        Vector2 force = position.cpy().sub(other.position);
        float distance = force.len();
        force.setLength(1);
        float strength = mass * other.mass / (distance * distance);
        force.scl(strength / 100000);
        other.acceleration.add(force);
    }

    public void setPosition() {
        super.setPosition(position.x - radius, position.y - radius);
        circle.setPosition(position);
    }

    public boolean bump(Planet other) {
        if (circle.overlaps(other.getCircle())) {
            Gdx.app.log("Planet.velocity", new Float(velocity.len()).toString());
            Vector2 normal = position.cpy().sub(other.getPosition());
            float dist = normal.len();
            normal.setLength(1);
            velocity.add(normal.cpy().scl(-2 * velocity.dot(normal)));
            float deviation = circle.radius + other.getCircle().radius - dist;
//            position.add(normal.cpy().scl(deviation));
//            position.add(normal.cpy().scl(-2 * deviation));
            return true;
        }
        return false;
    }

    public void dispose() {
        getTexture().dispose();
    }

    public Circle getCircle() {
        return circle;
    }

    public Vector2 getPosition() {
        return position;
    }

    public boolean boxBounce(int originX, int originY, float viewportWidth, float viewportHeight) {
        if (position.x >= originX + viewportWidth - radius) {
            velocity.x = -velocity.x;
            return true;
        } else if (position.x <= originX + radius) {
            velocity.x = -velocity.x;
            return true;
        } else if (position.y >= originY + viewportHeight - radius) {
            velocity.y = -velocity.y;
            return true;
        } else if (position.y <= originY + radius) {
            velocity.y = -velocity.y;
            return true;
        }
        return false;
    }
}
