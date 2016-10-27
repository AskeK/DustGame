package dust.tilemapeditor;

import dust.components.Component;

/**
 *
 * @author askek
 */
public class ComponentInfo {
    
    // Fields
    public String type, spriteSheet;
    public int x, y, spriteSheetX, spriteSheetY;
    
    // Ctor
    public ComponentInfo(String type, String spriteSheet, 
            int x, int y, int spriteSheetX, int spriteSheetY) {
        this.type = type;
        this.spriteSheet = spriteSheet;
        this.x = x;
        this.y = y;
        this.spriteSheetX = spriteSheetX;
        this.spriteSheetY = spriteSheetY;
    }
    
    // Duplicator
    public ComponentInfo(ComponentInfo original) {
        this.type = original.type;
        this.spriteSheet = original.spriteSheet;
        this.x = original.x;
        this.y = original.y;
        this.spriteSheetX = original.spriteSheetX;
        this.spriteSheetY = original.spriteSheetY;
    }
    
    // Duplicator with displacement
    public ComponentInfo(ComponentInfo original, int x, int y) {
        this(original);
        this.x = x;
        this.y = y;
    }
    
    
    // Get Component
    public Component GetComponent() {
        return null;
    }
    
}
