package com.CGD;

import org.jsfml.graphics.Texture;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class TextureHolder {
    private TextureHolder(){}
    public static TextureHolder getInstance() {
        if (instance == null)
            instance = new TextureHolder();
        return instance;
    }
    public Texture getTexture(String filename) {
        String path = "src/main/resources/" + filename;
        if (textures.containsKey(path))
            return textures.get(path);
        Texture texture = new Texture();
        try {
            texture.loadFromFile(Path.of(path));
        } catch (Exception e) {
            System.out.println("FAILED TO LOAD FROM FILE!");
            System.out.println(e);
        }
        textures.put(path, texture);
        return texture;
    }
    private static TextureHolder instance = null;
    private Map<String, Texture> textures = new HashMap<>();
}
