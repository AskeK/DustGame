package dust.scenes;

import dust.components.Component;
import dust.components.Player;
import java.util.ArrayList;

/**
 *
 * @author askek
 */
public abstract class Scene {    

    // Fields
    public ArrayList<Component> components;
    public Player player;
    
    // Ctor
    public Scene() {
        components = new ArrayList();
        player = null;
    }
    
    // Tick
    public void Tick() {
        components.stream().forEach((c) -> {
            c.Tick(this.components);
        });
        
        if (player != null) 
            player.Tick(this.components);
        
    }

}
