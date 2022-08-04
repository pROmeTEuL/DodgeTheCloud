package com.CGD;

import org.jsfml.graphics.*;
import org.jsfml.system.*;
import org.jsfml.window.*;
import org.jsfml.window.event.*;

import java.util.Vector;

public class Engine {

    Engine() {
        window = new RenderWindow(new VideoMode(resolution.x, resolution.y), "Game", WindowStyle.DEFAULT);
        for (int i = 0; i < clouds.length; ++i)
            clouds[i] = new Cloud(resolution.x);
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
                        case RETURN:
                            playing = !playing;
                        default:
                            break;
                    }
                    break;
                default:
                    break;
            }
            if (playing)
                player.handleInput(event);
        }
    }
    private void update(float dtAsSeconds) {
        if (playing) {
            player.update(dtAsSeconds, resolution.y);
            for (int i = 0; i < clouds.length; ++i) {
                clouds[i].update(dtAsSeconds);
                if (clouds[i].getSprite().getGlobalBounds().intersection(player.getSprite().getGlobalBounds()) != null) {

                }
            }
        }
    }
    private void draw() {
        window.clear(new Color(0, 147, 255));
        window.draw(player.getSprite());
        for (int i = 0; i < clouds.length; ++i)
            window.draw(clouds[i].getSprite());
        if (!playing) {
            if (gameOver)
                window.draw(gameOverText);
            else
                window.draw(pauseText);
        }
        window.display();
    }
    private RenderWindow window;
    private final Vector2i resolution = new Vector2i(1280, 720);
    private Player player = new Player();
    private Cloud[] clouds = new Cloud[4];
    private boolean playing = false;
    private boolean gameOver = false;
    Font font = new Font();
    private Text pauseText = new Text();
    private Text gameOverText = new Text();
}
