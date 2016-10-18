package dust.ui;

import dust.components.Player;
import dust.gfx.CustomImage;
import dust.gfx.SpriteSheet;
import dust.managers.Camera;
import java.awt.MouseInfo;
import javax.swing.JFrame;

/**
 *
 * @author askek
 */
public class Crosshair {
    
    // Fields
    public static SpriteSheet sprite;
    public static int x, y;
    private static Player player;
    
    // Ctor
    public Crosshair(Player player) {
        sprite = new SpriteSheet("res/spriteSheets/crosshair.png", 16, 16);
        x = 0; y = 0;
        Crosshair.player = player;
    }
    
    // Tick
    public static void Tick(JFrame frame) {
        
        int mouseX = MouseInfo.getPointerInfo().getLocation().x
                - frame.getX() - (player.x - Camera.x + player.spriteSheet.tileSizeY / 2) - 8;
        
        int mouseY = MouseInfo.getPointerInfo().getLocation().y
                - frame.getY() - (player.y - Camera.y + player.spriteSheet.tileSizeX / 2) - 8;
        
        float angle = (float) Math.atan2(mouseY, mouseX);
        int dist = (int) Math.sqrt(mouseX * mouseX + mouseY * mouseY);
        x = (int) (player.x - Camera.x + player.spriteSheet.tileSizeX / 2 + Math.cos(angle) * dist / 3) - 8;
        y = (int) (player.y - Camera.y + player.spriteSheet.tileSizeY / 2 + Math.sin(angle) * dist / 3) - 8;
        
    }
    
    // Render
    public static void Render(CustomImage screen) {
        screen.ApplyPolyDimensional(sprite.GetPixels(0, 0), x, y);
    }
    
}
