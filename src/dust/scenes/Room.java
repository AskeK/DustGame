package dust.scenes;

import dust.components.Component;
import dust.components.Player;
import dust.tilemapeditor.ComponentInfo;
import dust.tilemapeditor.Porting;
import java.util.ArrayList;
import javax.swing.JFrame;

/**
 *
 * @author askek
 */
public class Room {
    
    // Fields
    public ArrayList<Component> components;
    public int x, y;
    public Player player = null;
    
    // Ctor
    public Room(String URL, JFrame frame, int x, int y) {
        
        components = new ArrayList<>();
        
        ArrayList<ComponentInfo> tempInfo = Porting.Import(URL);
        ArrayList<Component> tempComps = new ArrayList<>();
        
        tempInfo.stream().forEach((c) -> {
            if ( ! c.GetComponent(frame).getClass().equals(Player.class) ) {
                components.add(c.GetComponent(frame));
            } else {
                player = (Player) c.GetComponent(frame);
            }
        });
        
        this.x = x;
        this.y = y;
        
        for (Component c : components) {
            c.x += x;
            c.y += y;
        }
        
    }
    
}
