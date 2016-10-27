package dust.managers;

import dust.components.Component;
import dust.gfx.CustomImage;
import dust.scenes.Dungeon;
import dust.scenes.Scene;
import dust.scenes.TileMap;
import javax.swing.JFrame;

/**
 *
 * @author askek
 */
public class SceneManager {
    
    // Fields
    private int currentScene;
    private final Scene[] scenes;
    
    // Ctor
    public SceneManager(JFrame frame) {
        currentScene = 0;
        scenes = new Scene[] {
            new Dungeon(frame, 4, 1),
        };
    }
    
    // Tick
    public void Tick() {
        scenes[currentScene].Tick();
    }
    
    // Render
    public void Render(CustomImage screen) {
        scenes[currentScene].components.stream().forEach((Component c) -> { c.Render(screen); });
        
        if (scenes[currentScene].player != null) {
            scenes[currentScene].player.Render(screen);
        }
    }
    
}
