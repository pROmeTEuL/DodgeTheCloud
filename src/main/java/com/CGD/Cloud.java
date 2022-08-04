package com.CGD;

import org.jsfml.graphics.Sprite;
import org.jsfml.system.Vector2f;

import java.util.Random;

public class Cloud {
    public Cloud(float givenResolutionX) {
        resolutionX = givenResolutionX;
        spawn();
        sprite.setOrigin(0, 0);
    }
    public void spawn() {
        Random rng = new Random();
        switch (Math.abs(rng.nextInt() % 3)) {
            case 0:
                sprite = new Sprite(TextureHolder.getInstance().getTexture("graphics/cloud1.png"));
                System.out.println("cloud created with sprite 1");
                break;
            case 1:
                sprite = new Sprite(TextureHolder.getInstance().getTexture("graphics/cloud2.png"));
                System.out.println("cloud created with sprite 2");
                break;
            case 2:
                sprite = new Sprite(TextureHolder.getInstance().getTexture("graphics/cloud3.png"));
                System.out.println("cloud created with sprite 3");
                break;
            default:
                System.out.println("Random failed!");
                sprite = new Sprite(TextureHolder.getInstance().getTexture("graphics/cloud1.png"));
                break;
        }
        position = new Vector2f(resolutionX + sprite.getLocalBounds().width, Math.abs(rng.nextInt() % 7 * 100));
        speedModifier = Math.abs(rng.nextInt() % 151);
        sprite.setPosition(position);
    }
    public void update(float elapsedTime) {
        if (sprite.getGlobalBounds().left + sprite.getGlobalBounds().width < 0) {
            System.out.println("spawning");
            spawn();
            return;
        }
        position = new Vector2f(position.x - ((speed + speedModifier) * elapsedTime), position.y);
        System.out.println("moving: " + position.x + ";" + position.y);
        sprite.setPosition(position);
    }
    public Sprite getSprite() {
        return sprite;
    }
    private Vector2f position;
    private final float speed = 200;
    private float speedModifier;
    private float resolutionX;
    private Sprite sprite;
}
