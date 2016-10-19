package dust.managers;

import dust.components.Component;
import java.awt.MouseInfo;
import java.util.Random;
import javax.swing.JFrame;

/**
 *
 * @author askek
 */
public class Camera {
    
    // Fields
    private static final int mouseEffectVolume = 6, shakeEffectVolume = 4;
    public static boolean mouseEffect = true, shakeEffect = false;
    private static Random rng = new Random();
    
    public static Component transform = null;
    public static int x = 0, y = 0;
    
    // Tick
    public static void Tick(JFrame frame) {
        if (transform != null) {
            
            x = transform.x
                    - frame.getWidth() / 2;
            
            y = transform.y
                    - frame.getHeight() / 2;
            
            // Mouse effect
            if (mouseEffect) {
                int mouseX = MouseInfo.getPointerInfo().getLocation().x
                        - frame.getX() - frame.getWidth() / 2;

                int mouseY = MouseInfo.getPointerInfo().getLocation().y
                        - frame.getY() - frame.getHeight() / 2;
                
                float angle = (float) Math.atan2(mouseY, mouseX);
                int dist = (int) Math.sqrt(mouseX * mouseX + mouseY * mouseY);
                
                x += Math.cos(angle) * (dist / mouseEffectVolume);
                y += Math.sin(angle) * (dist / mouseEffectVolume);
                
            }
            
            // Shake Effect
            if (shakeEffect) {
                float angle = (float) rng.nextInt(2 * (int) Math.PI * 1000) / 1000;
                int dist = rng.nextInt(shakeEffectVolume - 1) + 1;
                x += Math.cos(angle) * dist;
                y += Math.sin(angle) * dist;
            }
            
        }
    }
    
}
