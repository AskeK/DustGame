package dust.components;

import dust.gfx.CustomImage;
import dust.gfx.SpriteSheet;
import dust.managers.Camera;
import dust.managers.InputManager;
import dust.mechanicrelated.Dash;
import dust.ui.Crosshair;
import java.awt.MouseInfo;
import java.awt.Rectangle;
import java.util.ArrayList;
import javax.swing.JFrame;

/**
 *
 * @author askek
 */
public class Player extends Component {
    
    // Fields
    private ArrayList<Component> components;
    
    private final int speed = 8, accel = 2;
    private int velX, velY, velXGoal, velYGoal;
    private JFrame frame;
    
    // SHOULD BE IN WEAPON FRAMEWORK
    private final int fireRate = 4, recoil = 4;
    
    // Ctor
    public Player(JFrame frame, int x, int y) {
        super(new SpriteSheet("res/spriteSheets/player.png", 32, 32), 
                x, y, 0, 0);
        
        // UI
        new Crosshair(this);
        
        // ABILITIES
        new Dash(this);
        
        components = new ArrayList<>();
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
    int shootCounter = 0;
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
        
        // Shooting
        // Projectile Class
        class Projectile extends Component {
            
            // Fields
            private final float angle;
            private final int speed;
            public float newx, newy;
            public boolean shouldRemove = false;
            
            // Ctor
            public Projectile(SpriteSheet spriteSheet,
                    int x, int y, int ssx, int ssy,
                    float angle, int speed) {
                super(spriteSheet, x, y, ssx, ssy);
                this.angle = angle;
                this.speed = speed;
                newx = x; newy = y;
            }
            
            // Tick
            @Override
            public void Tick(ArrayList<Component> scene) {
                newx += Math.cos(angle) * speed;
                newy += Math.sin(angle) * speed;
                this.x = (int) newx;
                this.y = (int) newy;
                
                scene.stream().forEach((c) -> { 
                    
                    Tile tile = null;
                    try { tile = (Tile) c; }
                    catch(Exception e) {}
                    
                    if (tile != null && tile.solid) {
                        if (new Rectangle(x, y, spriteSheet.tileSizeX, spriteSheet.tileSizeY)
                                .intersects(tile.mask)) {
                            shouldRemove = true;
                        }
                    }
                    
                });
            }
        
        }
        
        if (InputManager.lbPressed && shootCounter >= fireRate) {
            shootCounter = 0;
            
            int mouseX = MouseInfo.getPointerInfo().getLocation().x
                - frame.getX() - (this.x - Camera.x + this.spriteSheet.tileSizeY / 2);
            int mouseY = MouseInfo.getPointerInfo().getLocation().y
                    - frame.getY() - (this.y - Camera.y + this.spriteSheet.tileSizeX / 2) - 16;
            float angle = (float) Math.atan2(mouseY, mouseX);
            
            SpriteSheet projectileSpriteSheet = new SpriteSheet("res/spriteSheets/projectiles.png", 6, 6);
            components.add(new Projectile(projectileSpriteSheet,
                    this.x + this.spriteSheet.tileSizeX / 2,
                    this.y + this.spriteSheet.tileSizeY / 2,
                    0, 0, angle, 10));
            
            boolean collision = false;
            for (Component c : scene) {
                Tile tile = null;
                try { tile = (Tile) c; }
                catch (Exception e) { }
                
                if (tile != null && tile.solid) {
                    if (new Rectangle(this.x - (int) (Math.cos(angle) * recoil), 
                            this.y - (int) (Math.sin(angle) * recoil),
                            this.spriteSheet.tileSizeX, 
                            this.spriteSheet.tileSizeY)
                            .intersects(tile.mask)) {
                        collision = true;
                    }
                }
            };
            
            if ( ! collision ) {
                this.x -= Math.cos(angle) * recoil;
                this.y -= Math.sin(angle) * recoil;
            }
        }
        
        Camera.shakeEffect = InputManager.lbPressed;
        
        ArrayList<Component> buffer = new ArrayList<>();
        components.stream().forEach((c) -> { 
            c.Tick(scene); 
            
            Projectile pro = null;
            try { pro = (Projectile) c; }
            catch (Exception e) {}
            
            if (pro != null && pro.shouldRemove)
                buffer.add(c);
            
        });
        
        buffer.stream().forEach((c) -> { components.remove(c); });
        
        // Abilities
        if (InputManager.spacePressed)
            Dash.Invoke();
        
        Dash.Tick(scene, frame);
        
        // Counters
        shootCounter++;
        
    }
    
    // Render
    @Override
    public void Render(CustomImage screen) {
        components.stream().forEach((c) -> { c.Render(screen); });
        Dash.Render(screen);
        super.Render(screen);
    }
    
}
