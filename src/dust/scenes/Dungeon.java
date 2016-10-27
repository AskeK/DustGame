package dust.scenes;

import dust.components.Player;
import dust.managers.Camera;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JFrame;

/**
 *
 * @author askek
 */
public class Dungeon extends Scene {
    
    // Fields
    private final String[] originalrooms = {
        
        // Start rms
        "res/tileMaps/startRoom01.txt",
        
        // Ads rooms
        "res/tileMaps/adsRoom01.txt",
        "res/tileMaps/adsRoom02.txt",
        
        // Boss rooms
        "res/tileMaps/bossRoom01.txt",
    
    };
    
    private final Room[][] rooms;
    private final Random rng;
    
    // Ctor
    public Dungeon(JFrame frame, int numRmX, int numRmY) {
        
        // TEMP:
        rooms = new Room[numRmY][numRmX];
        rng = new Random();
        for (int i = 0; i < numRmX; i++) {
            if (i == 0) {
                // Start rooom
                rooms[0][i] = new Room(originalrooms[rng.nextInt(1)],
                    frame, 800 * i, 0);
            }
            
            if (i > 0 && i < numRmX - 1) {
                // Ads room
                rooms[0][i] = new Room(originalrooms[rng.nextInt(2) + 1],
                    frame, 800 * i, 0);
            }
            
            if (i == numRmX - 1) {
                // Boss room
                rooms[0][i] = new Room(originalrooms[rng.nextInt(1) + 3],
                    frame, 800 * i, 0);
            }
        }
        
        for (Room[] rms : rooms) {
            for (Room rm : rms) {
                rm.components.stream().forEach
                    (this.components::add);
            }
        }
        
        if (rooms[0][0].player != null) {
            this.player = rooms[0][0].player;
            Camera.transform = this.player;
        }
        
    }
    
    // Tick
    @Override
    public void Tick() {
        int rmX = player.x / 800;
        int rmY = player.y / 600;
        
        // this.components = rooms[rmY][rmX].components;
        
        super.Tick();
        
    }
    
}
