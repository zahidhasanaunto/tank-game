/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.IOException;
import java.nio.file.Paths;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 *
 * @author bruceoutdoors
 */
public class AssetManager {

    final String DEFAULT_TILE_PATH = "assets/default-tile.png";
    final String ACTIVE_TILE_PATH = "assets/active-tile.png";
    final String ENEMY_TILE_PATH = "assets/enemy-tile.png";
    final String PLAYER_TILE_PATH = "assets/player-tile.png";
    final String ONHOVER_TILE_PATH = "assets/onhover-tile.png";
    final String EXPLOSION_TILE_PATH = "assets/explosion-tile.png";

    public ImageIcon DEFAULT_TILE;
    public ImageIcon ACTIVE_TILE;
    public ImageIcon ENEMY_TILE;
    public ImageIcon PLAYER_TILE;
    public ImageIcon ONHOVER_TILE;
    public ImageIcon EXPLOSION_TILE;

    private static AssetManager instance = null;

    private AssetManager() throws IOException {
        DEFAULT_TILE = new ImageIcon(ImageIO.read(Paths.get(DEFAULT_TILE_PATH).toFile()));
        ACTIVE_TILE = new ImageIcon(ImageIO.read(Paths.get(ACTIVE_TILE_PATH).toFile()));
        ENEMY_TILE = new ImageIcon(ImageIO.read(Paths.get(ENEMY_TILE_PATH).toFile()));
        PLAYER_TILE = new ImageIcon(ImageIO.read(Paths.get(PLAYER_TILE_PATH).toFile()));
        ONHOVER_TILE = new ImageIcon(ImageIO.read(Paths.get(ONHOVER_TILE_PATH).toFile()));
        EXPLOSION_TILE = new ImageIcon(ImageIO.read(Paths.get(EXPLOSION_TILE_PATH).toFile()));
    }

    public static AssetManager getInstance() throws IOException {
        if (instance == null) {
            instance = new AssetManager();
        }

        return instance;
    }
}
