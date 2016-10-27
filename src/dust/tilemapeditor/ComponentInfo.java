package dust.tilemapeditor;

import dust.components.Component;
import dust.components.Player;
import dust.components.Tile;
import dust.gfx.SpriteSheet;
import javax.swing.JFrame;

/**
 *
 * @author askek
 */
public class ComponentInfo {
    
    // Fields
    public String type, spriteSheet;
    public int x, y, spriteSheetX, spriteSheetY;
    public boolean solid;
    
    // Ctor
    public ComponentInfo(String type, String spriteSheet, 
            int x, int y, int spriteSheetX, int spriteSheetY,
            boolean solid) {
        this.type = type;
        this.spriteSheet = spriteSheet;
        this.x = x;
        this.y = y;
        this.spriteSheetX = spriteSheetX;
        this.spriteSheetY = spriteSheetY;
        this.solid = solid;
    }
    
    // Duplicator
    public ComponentInfo(ComponentInfo original) {
        this.type = original.type;
        this.spriteSheet = original.spriteSheet;
        this.x = original.x;
        this.y = original.y;
        this.spriteSheetX = original.spriteSheetX;
        this.spriteSheetY = original.spriteSheetY;
        this.solid = original.solid;
    }
    
    // Duplicator with displacement
    public ComponentInfo(ComponentInfo original, int x, int y) {
        this(original);
        this.x = x;
        this.y = y;
    }
    
    
    // Get Component
    public Component GetComponent(JFrame frame) {
        
        if (type.equals("Player")) {
        
            return new Player(frame, this.x, this.y);
        
        } else if (type.equals("Tile")) {
            
            SpriteSheet ss = TileMapInstance.GetSpriteSheet(spriteSheet);
            return new Tile(ss, spriteSheetX, spriteSheetY, 
                    x, y, ss.tileSizeX, ss.tileSizeY, solid);
        
        }
        
        return null;
        
    }
    
}
