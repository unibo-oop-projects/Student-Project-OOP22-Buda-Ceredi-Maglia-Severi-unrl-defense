package it.unibo.unrldef.graphics.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import java.awt.Image;

import javax.imageio.ImageIO;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Class that contains the dimensions of the sprites in the game.
 * @author danilo.maglia@studio.unibo.it
 */
public class SpriteLoader {
    public final Map<String, Sprite> sprites = new HashMap<>();
    private static final String ASSETS_FOLDER = "resources/assets" + File.separator;

    public void loadSpritesFromFile (String fileName) {
        JSONParser parser = new JSONParser();
        String fileContent = "";
        JSONObject json = new JSONObject();

        try {
			// read the whole file passed as argument and put the content in a string
			fileContent = new String(Files.readAllBytes(Paths.get(fileName)));
			json = (JSONObject) parser.parse(fileContent);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}

        JSONArray sprites = (JSONArray) json.get("sprites");

        // for each sprite in the json file, create a new sprite and put it in the map
        for (Object s : sprites) {
            JSONObject sprite = (JSONObject) s;
            int width = Integer.parseInt(sprite.get("width").toString());
            int height = Integer.parseInt(sprite.get("height").toString());
            Image spriteImage = null;
            try {
                spriteImage = ImageIO.read(new File(ASSETS_FOLDER + sprite.get("fileName").toString()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Sprite newSprite = new Sprite(width, height, spriteImage);
            this.sprites.put(sprite.get("name").toString(), newSprite);
        }

    }
    
    //returns the sprite given its name
    public Sprite getSprite(String name) {
        return this.sprites.get(name);
    }
}
