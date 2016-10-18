package dust.gfx;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import javax.imageio.ImageIO;

/**
 *
 * @author askek
 */
public class SpriteSheet {
    
    // Fields
    private int[][] pixels;
    public int tileSizeX, tileSizeY;
    
    // Ctor
    public SpriteSheet(String URL, 
            int tileSizeX, int tileSizeY) {
        
        try {
            BufferedImage image = ImageIO.read(new File(URL));
            pixels = new int[image.getHeight()][image.getWidth()];
            for (int y = 0; y < image.getHeight(); y++) {
                for (int x = 0; x < image.getWidth(); x++) {
                    pixels[y][x] = image.getRGB(x, y);
                }
            }
            image.flush();
        } catch (Exception e) {
            System.out.println("Cant import stylesheet: " + URL);
        }
        
        this.tileSizeX = tileSizeX;
        this.tileSizeY = tileSizeY;
        
    }
    
    // Get Pixels
    public int[][] GetPixels(int spriteSheetX, int spriteSheetY) {
        int[][] response = new int[tileSizeY][tileSizeX];
        for (int y = 0; y < tileSizeY; y++) {
            for (int x = 0; x < tileSizeX; x++) {
                response[y][x] = pixels
                        [y + spriteSheetY * tileSizeY]
                        [x + spriteSheetX * tileSizeX];
            }
        }
        return response;
    }
    
}
