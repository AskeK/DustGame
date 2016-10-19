package dust;

import dust.components.Player;
import dust.gfx.CustomImage;
import dust.gfx.SpriteSheet;
import dust.managers.Camera;
import dust.managers.InputManager;
import dust.managers.SceneManager;
import dust.ui.Crosshair;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.HashMap;
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
    public void Tick() {
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
        Dust instance = new Dust();
    }
    
}
