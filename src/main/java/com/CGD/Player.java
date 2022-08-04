package com.CGD;

import org.jsfml.graphics.Sprite;
import org.jsfml.system.Vector2f;
import org.jsfml.window.event.Event;

public class Player {

    public Player() {
        m_sprite = new Sprite(TextureHolder.getInstance().getTexture("graphics/plane.png"));
        m_sprite.setOrigin(m_sprite.getLocalBounds().left, m_sprite.getLocalBounds().top);
        m_position = new Vector2f(0, 0);
        m_sprite.setPosition(m_position);
    }
    public void handleInput(final Event event) {
        if (event.type != Event.Type.KEY_PRESSED && event.type != Event.Type.KEY_RELEASED)
            return;
        switch (event.asKeyEvent().key) {
            case UP:
                m_up = (event.type == Event.Type.KEY_PRESSED);
                System.out.println("up");
                break;
            case DOWN:
                m_down = (event.type == Event.Type.KEY_PRESSED);
                System.out.println("down");
                break;
            default:
                break;
        }
    }
    public void update(final float elapsedTime, final float resolutionY) {
        if (m_up && m_sprite.getGlobalBounds().top > 0)
            m_position = new Vector2f(m_position.x, m_position.y - elapsedTime * m_speed);
        if (m_down && m_sprite.getGlobalBounds().top + m_sprite.getGlobalBounds().height < resolutionY)
            m_position = new Vector2f(m_position.x, m_position.y + elapsedTime * m_speed);
        m_sprite.setPosition(m_position);
    }
    public Sprite getSprite() {
        return m_sprite;
    }
    private Vector2f m_position;
    private final Sprite m_sprite;
    private final float m_speed = 200.f;
    private boolean m_up = false;
    private boolean m_down = false;
}
