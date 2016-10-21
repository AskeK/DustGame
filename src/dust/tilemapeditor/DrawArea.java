package dust.tilemapeditor;

import dust.gfx.CustomImage;
import dust.gfx.SpriteSheet;
import dust.managers.InputManager;
import java.awt.Color;
import java.awt.MouseInfo;
import java.awt.Rectangle;
import java.util.ArrayList;
import javax.swing.JFrame;

/**
 *
 * @author askek
 */
public class DrawArea {
    
    // Fields
    private static ArrayList<ComponentInfo> components;
    public static ComponentInfo currentComponent;
    private static int[][] cursorPixels = null;
    
    // Ctor
    public DrawArea(String cursor) {
        components = new ArrayList<>();
        currentComponent = TileMapInstance.GetComponent(cursor);
        if (currentComponent == null)
            System.out.println("Cursor Component not found");
    }
    
    // Tick
    public static void Tick(JFrame frame, CustomImage parent) {
        
        // Cursor
        cursorPixels = TileMapInstance.GetSpriteSheet(currentComponent.spriteSheet)
                .GetPixels(currentComponent.spriteSheetX, currentComponent.spriteSheetY);
        
        for (int y = 0; y < cursorPixels.length; y++) {
            for (int x = 0; x < cursorPixels[0].length; x++) {
                
                Color color = Color.decode( "#" + Integer.toHexString(cursorPixels[y][x])
                        .substring(2, 8) ).brighter();
                cursorPixels[y][x] = color.getRGB();
                
            }
        }
        
        // Cursor
        int mouseX = MouseInfo.getPointerInfo().getLocation().x 
                - frame.getX() - parent.xOffset - 3;
        int mouseY = MouseInfo.getPointerInfo().getLocation().y 
                - frame.getY() - parent.yOffset - 26;
        
        // Grid snap
        if ( ! InputManager.ctrlPressed ) {
            mouseX = (int) (mouseX / 32) * 32;
            mouseY = (int) (mouseY / 32) * 32;
        }
        
        // Sets cursor pos
        currentComponent.x = mouseX;
        currentComponent.y = mouseY;
        
        // Draw mechanic
        if (InputManager.lbPressed) {
            
           
            
            if ( ! InputManager.altPressed ) {
                ArrayList<ComponentInfo> buffer = new ArrayList<>();
                for (ComponentInfo c : components) {
                    int tsX = TileMapInstance.GetSpriteSheet(c.spriteSheet).tileSizeX;
                    int tsY = TileMapInstance.GetSpriteSheet(c.spriteSheet).tileSizeY;
                    if (new Rectangle(mouseX, mouseY, 32, 32)
                            .intersects(new Rectangle(c.x, c.y, tsX, tsY))) {
                        
                           buffer.add(c);
                    
                    }
                }
                
                buffer.stream().forEach((c) -> { components.remove(c); });
            }
            
            components.add(new ComponentInfo(currentComponent));
            
        }
        
        // Delete mechanic
        if (InputManager.rbPressed) {
            ArrayList<ComponentInfo> buffer = new ArrayList<>();
            for (ComponentInfo c : components) {
                SpriteSheet ss = TileMapInstance.GetSpriteSheet(c.spriteSheet);
                if (new Rectangle(mouseX, mouseY, 32, 32)
                        .intersects(new Rectangle(c.x, c.y, 
                                ss.tileSizeX, ss.tileSizeY))) {
                    
                    buffer.add(c);
                    
                }
            }
            
            buffer.stream().forEach((c) -> { components.remove(c); });
        }
        
        
    }
    
    // Render
    public static void Render(CustomImage drawArea) {
        components.forEach((c) -> { 
            drawArea.ApplyPolyDimensional(
                    TileMapInstance.GetSpriteSheet(c.spriteSheet)
                            .GetPixels(c.spriteSheetX, c.spriteSheetY), 
                    c.x, c.y);
        });
        
        if (cursorPixels != null) {
            drawArea.ApplyPolyDimensional(cursorPixels, currentComponent.x, currentComponent.y);
        }
        
    }
    
}
