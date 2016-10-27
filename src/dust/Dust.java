package dust;

import dust.gfx.CustomImage;
import dust.gfx.SpriteSheet;
import dust.managers.Camera;
import dust.managers.InputManager;
import dust.managers.SceneManager;
import dust.tilemapeditor.ComponentInfo;
import dust.tilemapeditor.TileMapInstance;
import dust.ui.Crosshair;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JFrame;

/**
 *
 * @author askek
 */
public class Dust extends Canvas implements Runnable {
    
    // Fields
    public static final int SCREENWIDTH = 800;
    public static final int SCREENHEIGHT = 600;
    private static final String TITLE = "BitNap Studios - Dust V0.0.2";
    
    private final JFrame frame;
    private final Thread thread;
    private final CustomImage screen;
    private boolean running;
    
    private final InputManager inputManager;
    private final SceneManager sceneManager;
    
    // Ctor
    private Dust() {
        
        // TileMapEditor
        TileMapInstance.AddSpriteSheet("Outdoor", new SpriteSheet("res/spriteSheets/outdoor.png", 32, 32));
        TileMapInstance.AddSpriteSheet("Player", new SpriteSheet("res/spriteSheets/player.png", 32, 32));
        TileMapInstance.AddComponent("Player", new ComponentInfo("Player", "Player", 0, 0, 0, 0, false));
        
        TileMapInstance.AddComponent("StoneWall", 
                new ComponentInfo("Tile", "Outdoor", 0, 0, 1, 0, true));
        
        TileMapInstance.AddComponent("Grass", 
                new ComponentInfo("Tile", "Outdoor", 0, 0, 0, 1, false));
        
        TileMapInstance.AddComponent("Grass2", 
                new ComponentInfo("Tile", "Outdoor", 0, 0, 3, 0, false));
        
        TileMapInstance.AddComponent("Grass3", 
                new ComponentInfo("Tile", "Outdoor", 0, 0, 3, 1, false));
        
        TileMapInstance.AddComponent("Grass4", 
                new ComponentInfo("Tile", "Outdoor", 0, 0, 3, 2, false));
        
        TileMapInstance.AddComponent("Grass5", 
                new ComponentInfo("Tile", "Outdoor", 0, 0, 4, 0, false));
        
        TileMapInstance.AddComponent("Grass6", 
                new ComponentInfo("Tile", "Outdoor", 0, 0, 4, 1, false));
        
        TileMapInstance.AddComponent("Grass7", 
                new ComponentInfo("Tile", "Outdoor", 0, 0, 4, 2, false));
        
        TileMapInstance.AddComponent("Grass8", 
                new ComponentInfo("Tile", "Outdoor", 0, 0, 5, 0, false));
        
        TileMapInstance.AddComponent("Grass9", 
                new ComponentInfo("Tile", "Outdoor", 0, 0, 5, 1, false));
        
        TileMapInstance.AddComponent("Grass10", 
                new ComponentInfo("Tile", "Outdoor", 0, 0, 5, 2, false));
        
        // Init
        frame = new JFrame(TITLE);
        thread = new Thread(this);
        thread.setName("Dust main thread");
        
        screen = new CustomImage(SCREENWIDTH, SCREENHEIGHT, 0, 0);
        frame.getContentPane().add(screen);
        
        frame.setSize(new Dimension(SCREENWIDTH, SCREENHEIGHT));
        frame.setMinimumSize(new Dimension(SCREENWIDTH, SCREENHEIGHT));
        frame.setMaximumSize(new Dimension(SCREENWIDTH, SCREENHEIGHT));
        frame.setResizable(false);
       
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.add(this);
        frame.pack();
        
        sceneManager = new SceneManager(frame);
        
        inputManager = new InputManager();
        frame.addKeyListener(inputManager);
        frame.addMouseListener(inputManager);
        
        Start();
        
    }
    
    // Run
    @Override
    public void run() { 
        long counter = 0;
        long lastTime = System.nanoTime();
        
        while (running) {
            counter += System.nanoTime() - lastTime;
            lastTime = System.nanoTime();
            
            if (counter >= 1000000000.0 / 60.0) {
                counter = 0;
                Tick();
                Render();
            }
        }
        
        Stop();
    
    }
    
    // Tick
    boolean tileMap = false;
    int counter = 60;
    public void Tick() {
        // Starts new tilemapeditor instance
        if (InputManager.ctrlPressed &&
            InputManager.altPressed &&
                ! tileMap ) {
            
            InputManager.ctrlPressed = false;
            InputManager.altPressed = false;
            tileMap = true;
            new TileMapInstance();
        
        }
        
        counter++;
        if (counter >= 60) tileMap = false;
        
        sceneManager.Tick();
        Camera.Tick(frame);
        Crosshair.Tick(frame);
    }
    
    // Render
    public void Render() {
        for (int i = 0; i < screen.pixels.length; i++) {
            screen.pixels[i] = 0xff00ff;
        }
        
        sceneManager.Render(screen);
        Crosshair.Render(screen);
        
        Graphics gfx = frame.getContentPane().getGraphics();
        screen.paintComponent(gfx);
        gfx.dispose();
    }
    
    // Start
    private synchronized void Start() {
        running = true;
        thread.start();
    }
    
    // Stop
    private synchronized void Stop() {
        running = false;
        
        try {
            thread.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    //  Entry point
    public static void main(String[] args) {
        new Dust();
    }
    
}
