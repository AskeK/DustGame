package dust.mechanicrelated;

import dust.components.Component;
import dust.components.Player;
import dust.gfx.CustomImage;
import java.util.ArrayList;
import javax.swing.JFrame;

/**
 *
 * @author askek
 */
public abstract class Ability {
    
    // Fields
    protected static ArrayList<Component> components;
    protected static Player player;
    
    // Ctor
    public Ability(Player player) {
        components = new ArrayList<>();
        this.player = player;
    }
    
    // Invoke
    public static void Invoke() { }
    
    // Tick
    protected static void Tick(ArrayList<Component> scene) {
        components.stream().forEach((c) -> { c.Tick(scene); });
    }
    
    // Render
    public static void Render(CustomImage screen) {
        components.stream().forEach((c) -> { c.Render(screen); });
    }
    
}
