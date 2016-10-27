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
        "res/tileMaps/startRoom01.txt",
        "res/tileMaps/adsRoom01.txt",
        "res/tileMaps/bossRoom01.txt",
    };
    
    private final ArrayList<Room> rooms;
    private final Random rng;
    
    // Ctor
    public Dungeon(JFrame frame, int numRooms) {
        
        // TEMP:
        rooms = new ArrayList<>();
        rng = new Random();
        for (int i = 0; i < numRooms; i++) {
            if (i == 0) {
                // Start rooom
                rooms.add(new Room(originalrooms[rng.nextInt(1)],
                    frame, 800 * i, 0));
            }
            
            if (i > 0 && i < numRooms - 1) {
                // Ads room
                rooms.add(new Room(originalrooms[rng.nextInt(1) + 1],
                    frame, 800 * i, 0));
            }
            
            if (i == numRooms - 1) {
                // Boss room
                rooms.add(new Room(originalrooms[rng.nextInt(1) + 2],
                    frame, 800 * i, 0));
            }
        }
        
        rooms.stream().forEach((rm) -> {
            rm.components.stream().forEach(this.components::add);
        });
        
        if (rooms.get(0).player != null) {
            this.player = rooms.get(0).player;
            Camera.transform = this.player;
        }
        
    }
    
}
