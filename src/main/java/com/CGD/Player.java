package com.CGD;

import org.jsfml.graphics.Sprite;
import org.jsfml.system.Vector2f;
import org.jsfml.window.event.Event;

public class Player {

    public Player() {
        sprite = new Sprite(TextureHolder.getInstance().getTexture("graphics/plane.png"));
        sprite.setOrigin(sprite.getLocalBounds().left, sprite.getLocalBounds().top);
        position = new Vector2f(0, 0);
        sprite.setPosition(position);
    }
    public void handleInput(final Event event) {
        if (event.type != Event.Type.KEY_PRESSED && event.type != Event.Type.KEY_RELEASED)
            return;
        switch (event.asKeyEvent().key) {
            case UP:
                up = (event.type == Event.Type.KEY_PRESSED);
                System.out.println("up");
                break;
            case DOWN:
                down = (event.type == Event.Type.KEY_PRESSED);
                System.out.println("down");
                break;
            default:
                break;
        }
    }
    public void update(final float elapsedTime, final float resolutionY) {
        if (up && sprite.getGlobalBounds().top > 0)
            position = new Vector2f(position.x, position.y - elapsedTime * speed);
        if (down && sprite.getGlobalBounds().top + sprite.getGlobalBounds().height < resolutionY)
            position = new Vector2f(position.x, position.y + elapsedTime * speed);
        sprite.setPosition(position);
    }
    public Sprite getSprite() {
        return sprite;
    }
    private Vector2f position;
    private final Sprite sprite;
    private final float speed = 200.f;
    private boolean up = false;
    private boolean down = false;
}
