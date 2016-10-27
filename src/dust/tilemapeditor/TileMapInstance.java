package dust.tilemapeditor;

import dust.gfx.CustomImage;
import dust.gfx.SpriteSheet;
import dust.managers.InputManager;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author askek
 */
public class TileMapInstance extends Canvas implements Runnable {
    
    // Fields
    private final int SCREENWIDTH = 1360, SCREENHEIGHT = 720;
    
    private final JFrame frame;
    private final Thread thread;
    private boolean running;
    
    private final CustomImage drawArea, selectArea;
    private final JTextField importURL, exportURL;
    private final JButton importAccept, exportAccept;
    
    private static HashMap<String, SpriteSheet> spriteSheets = null;
    private static HashMap<String, ComponentInfo> components = null;
    
    // Ctor
    public TileMapInstance() {
        
        frame = new JFrame("BitNap studio - Tile Map Editor V. 0.0.1");
        frame.addWindowListener(new WindowListener () {
            @Override
            public void windowOpened(WindowEvent we) {}
            @Override
            public void windowClosing(WindowEvent we) {}
            @Override
            public void windowClosed(WindowEvent we) {
                try { thread.join(); }
                catch (Exception e) {}
            }
            @Override
            public void windowIconified(WindowEvent we) {}
            @Override
            public void windowDeiconified(WindowEvent we) {}
            @Override
            public void windowActivated(WindowEvent we) {}
            @Override
            public void windowDeactivated(WindowEvent we) {}
            
        });
        
        drawArea = new CustomImage(800, 600, 32, 60, true);
        selectArea = new CustomImage(SCREENWIDTH - 896, 600, 864, 60);
        
        frame.setSize(new Dimension(SCREENWIDTH, SCREENHEIGHT));
        frame.setMinimumSize(new Dimension(SCREENWIDTH, SCREENHEIGHT));
        frame.setMaximumSize(new Dimension(SCREENWIDTH, SCREENHEIGHT));
        frame.setResizable(false);
        
        JPanel panel = new JPanel();
        panel.setBackground(Color.white);
        
        importURL = new JTextField("res/tileMaps/", 20);
        importAccept = new JButton("Import");
        importAccept.addActionListener((ActionEvent ae) -> {
            DrawArea.components = Porting.Import(importURL.getText());
        });
        
        exportURL = new JTextField("res/tileMaps/", 20);
        exportAccept = new JButton("Export");
        exportAccept.addActionListener((ActionEvent ae) -> {
            Porting.Export(exportURL.getText(), DrawArea.components);
        });
        
        panel.add(importURL, BorderLayout.NORTH);
        panel.add(importAccept, BorderLayout.NORTH);
        panel.add(exportURL, BorderLayout.NORTH);
        panel.add(exportAccept, BorderLayout.NORTH);
        
        frame.getContentPane().add(panel, BorderLayout.NORTH);
        
        JPanel customPanel = new JPanel();
        customPanel.add(drawArea);
        customPanel.add(selectArea);
        customPanel.setBackground(Color.white);
        
        frame.getContentPane().add(customPanel);
        
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
        frame.add(this);
        frame.pack();
        
        InputManager input = new InputManager(customPanel);
        customPanel.addKeyListener(input);
        customPanel.addMouseListener(input);
        customPanel.requestFocus();
        
        new SelectArea(selectArea.image.getWidth());
        new DrawArea("StoneWall");
        
        thread = new Thread(this);
        Start();
        
    }
    
    // Run
    @Override
    public void run() { 
        
        long lastTime = System.nanoTime();
        long counter = 0;
        
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
    private void Tick() { 
        for (int i = 0; i < drawArea.pixels.length; i++)
            drawArea.pixels[i] = 0xffaaaaaa;
        
        for (int i = 0; i < selectArea.pixels.length; i++)
            selectArea.pixels[i] = 0xaaaaaa;
        
        SelectArea.Tick(frame, selectArea);
        DrawArea.Tick(frame, drawArea);
        
    }
    
    // Render
    private void Render() {
        SelectArea.Render(selectArea);
        DrawArea.Render(drawArea);
        
        Graphics gfx = frame.getContentPane().getGraphics();
        drawArea.paintComponent(gfx);
        selectArea.paintComponent(gfx);
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
        try { thread.join(); }
        catch (Exception e) {}
    }
    
    // Add (SpriteSheet)
    public static void AddSpriteSheet(String key, SpriteSheet value) {
        if (spriteSheets == null)
            spriteSheets = new HashMap<>();
        
        spriteSheets.put(key, value);
    }
    
    // Remove
    public static void RemoveSpriteSheet(String key) {
        if (spriteSheets == null)
            return;
        
        spriteSheets.remove(key);
    }
    
    // Get
    public static SpriteSheet GetSpriteSheet(String key) {
        if (spriteSheets == null)
            return null;
        
        return spriteSheets.get(key);
    }
    
    // Add (ComponentInfo)
    public static void AddComponent(String key, ComponentInfo value) {
        if (components == null)
            components = new HashMap<>();
        
        components.put(key, value);
    }
    
    // Remove
    public static void RemoveComponent(String key) {
        if (components == null)
            return;
        
        components.remove(key);
    }
    
    // Get
    public static ComponentInfo GetComponent(String key) {
        if (components == null)
            return null;
        
        return components.get(key);
    }
    
    // Get all
    public static HashMap<String, ComponentInfo> GetAllComponents() {
        return components;
    }
    
}
