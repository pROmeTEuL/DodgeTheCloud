package com.CGD;

import org.jsfml.graphics.*;
import org.jsfml.system.*;
import org.jsfml.window.*;
import org.jsfml.window.event.*;

public class Engine {

    Engine() {
        m_window = new RenderWindow(new VideoMode(resolution.x, resolution.y), "Game", WindowStyle.DEFAULT);
    }
    public void run() {
        Clock clock = new Clock();
        while (m_window.isOpen()) {
            Time dt = clock.restart();
            input();
            update(dt.asSeconds());
            draw();
        }
    }
    private void input() {
        for (Event event : m_window.pollEvents()) {
            switch (event.type) {
                case CLOSED:
                    m_window.close();
                    break;
                case KEY_PRESSED:
                    switch (event.asKeyEvent().key) {
                        case ESCAPE:
                            m_window.close();
                            break;
                        default:
                            break;
                    }
                    break;
                default:
                    break;
            }
            m_player.handleInput(event);
        }
    }
    private void update(float dtAsSeconds) {
        m_player.update(dtAsSeconds, resolution.y);
    }
    private void draw() {
        m_window.clear(new Color(0, 147, 255));
        m_window.draw(m_player.getSprite());
        m_window.display();
    }
    private RenderWindow m_window;
    private final Vector2i resolution = new Vector2i(1280, 720);
    private Player m_player = new Player();
}
