package dust.components;

import dust.gfx.CustomImage;
import dust.gfx.SpriteSheet;
import dust.managers.Camera;
import java.util.ArrayList;

/**
 *
 * @author askek
 */
public abstract class Component {
    
    // Fields
    public SpriteSheet spriteSheet;
    public int x, y, spriteSheetX, spriteSheetY;
    
    // Ctor
    public Component(SpriteSheet spriteSheet, 
            int x, int y, int spriteSheetX, int spriteSheetY) {
        
        this.spriteSheet = spriteSheet;
        this.x = x;
        this.y = y;
        this.spriteSheetX = spriteSheetX;
        this.spriteSheetY = spriteSheetY;
        
    }
    
    // Tick
    public abstract void Tick(ArrayList<Component> scene);
    
    // Render
    public void Render(CustomImage screen) {
        screen.ApplyPolyDimensional(spriteSheet.GetPixels(spriteSheetX, spriteSheetY), 
                x - Camera.x, y - Camera.y);
    }
    
}
