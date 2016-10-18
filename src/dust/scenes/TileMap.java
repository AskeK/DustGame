package dust.scenes;

import dust.components.Player;
import dust.components.Tile;
import dust.gfx.SpriteSheet;
import dust.managers.Camera;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

/**
 *
 * @author askek
 */
public class TileMap extends Scene {
   
    // Ctor
    public TileMap(String URL, int xOffset, int yOffset, JFrame frame) {
        super();
        
        BufferedImage mapImg = null;
        try {
            mapImg = ImageIO.read(new File(URL));
        } catch (Exception e) { 
            System.out.println("File not found: " + URL);
        }
        
        if (mapImg != null) {
            for (int y = 0; y < mapImg.getHeight(); y++) {
                for (int x = 0; x < mapImg.getWidth(); x++) {
                    
                    SpriteSheet spriteSheet = new SpriteSheet("res/spriteSheets/outdoor.png", 32, 32);
                    
                    int colorCode = mapImg.getRGB(x, y);
                    if ((0xffffff &colorCode) == 0x00aa55) { // GRASS
                        this.components.add(new Tile(spriteSheet,
                            0, 0, x * 32 + xOffset, y * 32 + yOffset, 32, 32,
                            false));
                    }
                    
                    else if ((0xffffff &colorCode) == 0x777777) { // WALL
                        this.components.add(new Tile(spriteSheet,
                            1, 0, x * 32 + xOffset, y * 32 + yOffset, 32, 32,
                            true));
                    }
                    
                }
            }
        }
        
        this.player = new Player(frame, 128, 128);
        Camera.transform = player;
        
    }
    
    // Tick
    @Override
    public void Tick() {
        super.Tick();
    }
    
}
