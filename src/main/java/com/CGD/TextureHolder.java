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
        if (m_Textures.containsKey(path))
            return m_Textures.get(path);
        Texture texture = new Texture();
        try {
            texture.loadFromFile(Path.of(path));
        } catch (Exception e) {
            System.out.println("FAILED TO LOAD FROM FILE!");
            System.out.println(e);
        }
        m_Textures.put(path, texture);
        return texture;
    }
    private static TextureHolder instance = null;
    private Map<String, Texture> m_Textures = new HashMap<>();
}
