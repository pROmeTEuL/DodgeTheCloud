package com.CGD;

import org.jsfml.graphics.*;
import org.jsfml.system.*;
import org.jsfml.window.*;
import org.jsfml.window.event.*;

import java.util.Vector;

public class Engine {

    Engine() {
        window = new RenderWindow(new VideoMode(resolution.x, resolution.y), "Game", WindowStyle.DEFAULT);
    }
    public void run() {
        Clock clock = new Clock();
        while (window.isOpen()) {
            Time dt = clock.restart();
            input();
            update(dt.asSeconds());
            draw();
        }
    }
    private void input() {
        for (Event event : window.pollEvents()) {
            switch (event.type) {
                case CLOSED:
                    window.close();
                    break;
                case KEY_PRESSED:
                    switch (event.asKeyEvent().key) {
                        case ESCAPE:
                            window.close();
                            break;
                        default:
                            break;
                    }
                    break;
                default:
                    break;
            }
            player.handleInput(event);
        }
    }
    private void update(float dtAsSeconds) {
        player.update(dtAsSeconds, resolution.y);
        for (Cloud el : clouds) {
            System.out.println("cloud update");
            el.update(dtAsSeconds);
        }
    }
    private void draw() {
        window.clear(new Color(0, 147, 255));
        window.draw(player.getSprite());
        for (Cloud el : clouds) {
            System.out.println("cloud draw");
            window.draw(el.getSprite());
        }
        window.display();
    }
    private RenderWindow window;
    private final Vector2i resolution = new Vector2i(1280, 720);
    private Player player = new Player();
    private Vector<Cloud> clouds = new Vector<Cloud>(resolution.x);
}
