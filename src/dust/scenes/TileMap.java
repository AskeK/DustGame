package dust.scenes;

import dust.components.Player;
import dust.managers.Camera;
import dust.tilemapeditor.ComponentInfo;
import dust.tilemapeditor.Porting;
import java.util.ArrayList;
import javax.swing.JFrame;

/**
 *
 * @author askek
 */
public class TileMap extends Scene {
   
    // Ctor
    public TileMap(String URL, int xOffset, int yOffset, JFrame frame) {
        super();
        
        ArrayList<ComponentInfo> comps = Porting.Import(URL);
        comps.stream().map((c) -> c.GetComponent(frame)).forEach((cfinal) -> {
            
            Player tempPlayer = null;
            try { tempPlayer = (Player) cfinal; }
            catch (Exception e) {}
            
            if (tempPlayer == null) {
                this.components.add(cfinal);
            } else {
                this.player = tempPlayer;
                Camera.transform = this.player;
            }
            
        });
        
    }
    
    // Tick
    @Override
    public void Tick() {
        super.Tick();
    }
    
}
