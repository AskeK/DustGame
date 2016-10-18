package dust.components;

import dust.gfx.SpriteSheet;
import java.awt.Rectangle;
import java.util.ArrayList;

/**
 *
 * @author askek
 */
public class Tile extends Component {
    
    // Fields
    public Rectangle mask;
    public boolean solid;
    
    // Ctor
    public Tile(SpriteSheet spriteSheet, 
            int spriteSheetX, int spriteSheetY, 
            int x, int y, int width, int height,
            boolean solid) {
        super(spriteSheet, x, y, spriteSheetX, spriteSheetY);
        mask = new Rectangle(x, y, width, height);
        this.solid = solid;
    }
    
    // Tick
    @Override
    public void Tick(ArrayList<Component> scene) {
        mask.x = this.x;
        mask.y = this.y;
    }
    
    
}
