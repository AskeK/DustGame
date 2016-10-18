package dust.mechanicrelated;

import dust.components.Component;
import dust.components.Player;
import dust.gfx.SpriteSheet;
import dust.managers.Camera;
import java.awt.MouseInfo;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JFrame;

/**
 *
 * @author askek
 */
public class Dash extends Ability {
    
    // Fields
    private static final int ticks = 8, dist = 256, coolDown = 30;
    private static boolean inProgress = false, onCoolDown = false;
    private static int counter = 0, coolDownCounter = coolDown;
    
    private static Random rng = new Random();
    
    // Ctor
    public Dash(Player player) {
        super(player);
    }
    
    // Invoke
    public static void Invoke() {
        if ( ! onCoolDown 
            && coolDownCounter >= coolDown) {
            
            coolDownCounter = 0;
            inProgress = true;
            counter = 0;
        
        }
    }
    
    // Tick
    public static void Tick(ArrayList<Component> scene, JFrame frame) {
        
        // Particle class
        class DashParticle extends Component {

            // Fields
            private float angle;
            private int speed, removeTime;
            public boolean shouldRemove;

            // Ctor
            public DashParticle(SpriteSheet spriteSheet, Random rng, int x, int y) {
                super (spriteSheet, x, y, rng.nextInt(2), 0);
                angle = (float) rng.nextInt(2 * (int) Math.PI * 1000) / 1000;
                speed = rng.nextInt(2);
                removeTime = rng.nextInt(20) + 20;
            }

            // Tick
            int counter = 0;
            @Override
            public void Tick(ArrayList<Component> scene) {
                this.x += Math.cos(angle) * speed;
                this.y += Math.sin(angle) * speed;
                counter++;

                if (counter >= removeTime)
                    shouldRemove = true;
            }

        }
        
        // Checks
        ArrayList<Component> buffer = new ArrayList<>();
        components.stream().forEach((c) -> { 
            c.Tick(scene);
            
            DashParticle par = null;
            try { par = (DashParticle) c; }
            catch (Exception e) { }
            
            if (par != null && par.shouldRemove) {
                buffer.add(c);
            }
            
        });
        
        buffer.stream().forEach((c) -> {
            components.remove(c);
        });
    
        if (inProgress) {
            
            // Particle part
            int num = rng.nextInt(6) + 3;
            SpriteSheet spriteSheet = new SpriteSheet("res/spriteSheets/dashParticles.png", 16, 16);
            for (int i = 0; i < num; i++) {
                int x = rng.nextInt(32);
                int y = rng.nextInt(32);
                
                Dash.components.add(new DashParticle(spriteSheet, rng, x + player.x, y + player.y));
            }
            
            // Movement part
            int mouseX = MouseInfo.getPointerInfo().getLocation().x
                    - frame.getX() - (player.x + player.spriteSheet.tileSizeX / 2 - Camera.x);
            int mouseY = MouseInfo.getPointerInfo().getLocation().y
                    - frame.getY() - (player.y + player.spriteSheet.tileSizeY / 2 - Camera.y);
            
            float angle = (float) Math.atan2(mouseY, mouseX);
            
            player.x += Math.cos(angle) * (dist / ticks);
            player.y += Math.sin(angle) * (dist / ticks);
            counter += dist / ticks;
            
            if (counter >= dist) {
                counter = 0;
                inProgress = false;
            }
            
        }
        
        coolDownCounter++;
    }
    
}
