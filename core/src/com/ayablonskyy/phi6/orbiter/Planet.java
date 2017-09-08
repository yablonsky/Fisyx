package com.ayablonskyy.phi6.orbiter;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by and on 8/12/2017.
 */

public class Planet extends Sprite{
    private final float radius;
    private final float mass;
    private final float density = 1;

    private Color color;
    private Circle circle;
    private Vector2 position;
    public Vector2 velocity;
    private Vector2 acceleration;


    public Planet(float x, float y, float radius, Color color) {
        super(createTexture(radius, color));
        position = new Vector2(x, y);
        this.radius = radius;
        this.color = color;
        velocity = new Vector2();
        acceleration = new Vector2();
        circle = new Circle(position, radius);
        mass = circle.area() * density;
        setBounds(x - radius, y - radius, radius * 2, radius * 2);
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

    public void update(float deltaT) {
        velocity.add(acceleration);
        position.add(velocity);
        setPosition();
        acceleration.setZero();
    }

    public void applyForce(Vector2 force) {
        acceleration.add(force.cpy().add(force).scl(1 / mass));
    }

    public void setPosition() {
        super.setPosition(position.x - radius, position.y - radius);
        circle.setPosition(position);
    }

    public void bump(Planet other) {
        if (circle.overlaps(other.getCircle())) {
            Vector2 normal = position.cpy().sub(other.getPosition());
            float dist = normal.len();
            normal.setLength(1);
            float deviation = circle.radius + other.getCircle().radius - dist;
            velocity.add(normal.cpy().scl(-2 * velocity.dot(normal)));
            position.add(normal.cpy().scl(deviation));
        }
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
}
