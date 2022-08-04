package com.CGD;

import org.jsfml.graphics.*;
import org.jsfml.system.*;
import org.jsfml.window.*;
import org.jsfml.window.event.*;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Path;
import java.util.Scanner;

public class Engine {

    Engine() {
        try {
            file = new File(htPath);
            if (file.createNewFile()) {
                System.out.println("CREATING NEW SAVE FILE...");
                System.out.println("DONE");
            } else {
                System.out.println("READING FROM FILE");
                Scanner scan = new Scanner(file);
                highestTime = scan.nextInt();
            }
        } catch (Exception e) {
            System.out.println("FAILED TO CREATE OR READ PROGRESS!");
            System.out.println(e);
        }
        window = new RenderWindow(new VideoMode(resolution.x, resolution.y), "Game", WindowStyle.DEFAULT);
        for (int i = 0; i < clouds.length; ++i)
            clouds[i] = new Cloud(resolution.x);
        try {
            font.loadFromFile(Path.of("src/main/resources/fonts/orange_juice.ttf"));
        } catch (Exception e) {
            System.out.println("FAILED TO READ THE FONT!");
            System.out.println(e);
        }
        pauseText.setFont(font);
        pauseText.setCharacterSize(100);
        pauseText.setColor(Color.WHITE);
        pauseText.setString("Game Paused");
        pauseText.setOrigin(pauseText.getLocalBounds().width / 2, pauseText.getLocalBounds().height / 2);
        pauseText.setPosition(resolution.x / 2, resolution.y / 2);

        gameOverText.setFont(font);
        gameOverText.setCharacterSize(100);
        gameOverText.setColor(Color.RED);
        gameOverText.setString("Game Over");
        gameOverText.setOrigin(gameOverText.getLocalBounds().width / 2, gameOverText.getLocalBounds().height / 2);
        gameOverText.setPosition(resolution.x / 2, resolution.y / 2);

        timeText.setFont(font);
        timeText.setCharacterSize(100);
        timeText.setColor(Color.BLACK);
        timeText.setString("Time: " + (int)time);
        timeText.setOrigin(timeText.getLocalBounds().left, timeText.getLocalBounds().top);
        timeText.setPosition(0, 0);

        highestTimeText.setFont(font);
        highestTimeText.setCharacterSize(100);
        highestTimeText.setColor(Color.BLACK);
        highestTimeText.setString("Highest time: " + (int)highestTime);
        highestTimeText.setOrigin(pauseText.getLocalBounds().left, pauseText.getLocalBounds().top);
        highestTimeText.setPosition(timeText.getGlobalBounds().width + 100, 0);
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
                    try {
                        FileWriter writer = new FileWriter(file);
                        writer.write(String.valueOf((int)highestTime));
                        writer.close();
                    }   catch (Exception e) {
                        System.out.println("FAILED TO SAVE PROGRESS!");
                        System.out.println(e);
                    }
                    window.close();
                    break;
                case KEY_PRESSED:
                    switch (event.asKeyEvent().key) {
                        case ESCAPE:
                            try {
                                FileWriter writer = new FileWriter(file);
                                writer.write(String.valueOf((int)highestTime));
                                writer.close();
                            }   catch (Exception e) {
                                System.out.println("FAILED TO SAVE PROGRESS!");
                                System.out.println(e);
                            }
                            window.close();
                            break;
                        case RETURN:
                            if (gameOver) {
                                for (int i = 0; i < clouds.length; ++i) {
                                    clouds[i] = new Cloud(resolution.x);
                                }
                                time = 0;
                                player.stop();
                            }
                            playing = !playing;
                            gameOver = false;
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
            time += dtAsSeconds;
            player.update(dtAsSeconds, resolution.y);
            for (int i = 0; i < clouds.length; ++i) {
                clouds[i].update(dtAsSeconds);
                if (clouds[i].getSprite().getGlobalBounds().intersection(player.getSprite().getGlobalBounds()) != null) {
                    gameOver = true;
                    playing = false;
                }
            }
            timeText.setString("Time: " + (int)time);
            if (time > highestTime)
                highestTime = time;
            highestTimeText.setString("Highest time: " + (int)highestTime);
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
        window.draw(timeText);
        window.draw(highestTimeText);
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
    private float time = 0;
    private float highestTime = 0;
    private Text timeText = new Text();
    private Text highestTimeText = new Text();
    private final String htPath = "src/main/resources/progress.txt";
    private File file;
}
