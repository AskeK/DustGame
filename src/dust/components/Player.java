package dust.components;

import dust.gfx.CustomImage;
import dust.gfx.SpriteSheet;
import dust.managers.InputManager;
import dust.mechanicrelated.Dash;
import dust.ui.Crosshair;
import java.awt.Rectangle;
import java.util.ArrayList;
import javax.swing.JFrame;

/**
 *
 * @author askek
 */
public class Player extends Component {
    
    // Fields
    private final int speed = 8, accel = 2;
    private int velX, velY, velXGoal, velYGoal;
    private JFrame frame;
    
    // Ctor
    public Player(JFrame frame, int x, int y) {
        super(new SpriteSheet("res/spriteSheets/player.png", 32, 32), 
                x, y, 0, 0);
        
        // UI
        new Crosshair(this);
        
        // ABILITIES
        new Dash(this);
        
        this.frame = frame;
        velX = 0; velY = 0;
        velXGoal = 0; velYGoal = 0;
    }
    
    // Interpolate
    private int Interpolate(int current, int goal, int accel) {
        if (current + accel < goal)
            return current + accel;
        if (current - accel > goal)
            return current - accel;
        
        return goal;
    }
    
    // Tick
    @Override
    public void Tick(ArrayList<Component> scene) {
        
        // Movement
        if ( InputManager.wPressed ) velYGoal = -speed;
        if ( InputManager.sPressed ) velYGoal = speed;
        if ( ! InputManager.wPressed
          && ! InputManager.sPressed)
            velYGoal = 0;
        
        if ( InputManager.aPressed ) velXGoal = -speed;
        if ( InputManager.dPressed ) velXGoal = speed;
        if ( ! InputManager.aPressed
          && ! InputManager.dPressed )
            velXGoal = 0;
        
        velX = Interpolate( velX, velXGoal, accel );
        velY = Interpolate( velY, velYGoal, accel );
        
        // Collision
        scene.stream().forEach((c) -> {
            
            Tile tile = null;
            try { tile = (Tile) c; }
            catch (Exception e) { e.printStackTrace(); }
            
            if (tile != null && tile.solid) {
                
                if (new Rectangle(this.x, this.y + velY, 
                        this.spriteSheet.tileSizeX, 
                        this.spriteSheet.tileSizeY)
                        .intersects(tile.mask)) {
                    velY = 0;
                    velYGoal = 0;
                }
                
                if (new Rectangle(this.x + velX, this.y,
                        this.spriteSheet.tileSizeX,
                        this.spriteSheet.tileSizeY)
                        .intersects(tile.mask)) {
                    velX = 0;
                    velXGoal = 0;
                }
                
            }
            
        });
        
        this.x += velX;
        this.y += velY;
        
        // Abilities
        if (InputManager.spacePressed)
            Dash.Invoke();
        
        Dash.Tick(scene, frame);
        
    }
    
    // Render
    @Override
    public void Render(CustomImage screen) {
        Dash.Render(screen);
        super.Render(screen);
    }
    
}
