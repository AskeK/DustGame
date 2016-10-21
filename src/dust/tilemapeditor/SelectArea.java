package dust.tilemapeditor;

import dust.gfx.CustomImage;
import dust.managers.InputManager;
import java.awt.MouseInfo;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JFrame;

/**
 *
 * @author askek
 */
public class SelectArea {
    
    // Fields
    private static ArrayList<ComponentInfo> components;
    
    // Ctor
    public SelectArea(int scanWidth) {
        
        // Init
        components = new ArrayList<>();
        
        // Lists all components
        int counter = 0;
        for (HashMap.Entry<String, ComponentInfo> entry : TileMapInstance.GetAllComponents().entrySet()) {
            components.add(new ComponentInfo(
                    entry.getValue(),
                    (counter % 8) * (scanWidth / 8) + 32,
                    (counter / 8) * 64 + 32));
            counter++;
        }
        
    }
    
    // Tick
    public static void Tick(JFrame frame, CustomImage parent) {
        
        // Check for select
        if (InputManager.lbPressed) {
            for (ComponentInfo c : components) {
                
                int mouseX = MouseInfo.getPointerInfo().getLocation().x
                        - frame.getX() - parent.xOffset - 3;
                int mouseY = MouseInfo.getPointerInfo().getLocation().y
                        - frame.getY() - parent.yOffset - 26;
                
                if (new Rectangle(mouseX, mouseY, 1, 1)
                        .intersects(new Rectangle(c.x, c.y,
                        TileMapInstance.GetSpriteSheet(c.spriteSheet).tileSizeX,
                        TileMapInstance.GetSpriteSheet(c.spriteSheet).tileSizeY))) {
                    
                    DrawArea.currentComponent = new ComponentInfo(c, 0, 0);
                    
                }
                
            }
        }
        
    }
    
    // Render
    public static void Render(CustomImage selectArea) {
        components.stream().forEach((c) -> {
            selectArea.ApplyPolyDimensional(
                    TileMapInstance.GetSpriteSheet(c.spriteSheet)
                            .GetPixels(c.spriteSheetX, c.spriteSheetY), 
                    c.x, c.y);
        });
        
    }
    
}
