package dust.gfx;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import javax.swing.JComponent;

/**
 *
 * @author askek
 */
public class CustomImage extends JComponent {
    
    // Fields
    public BufferedImage image;
    public int[] pixels;
    public final int xOffset, yOffset;
    private final int transparent = 0xff00ff;
    
    // Ctor
    public CustomImage(int width, int height, int xOffset, int yOffset) {
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        pixels = ( (DataBufferInt) image.getRaster().getDataBuffer() ).getData();
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }
    
    // Ctor
    public CustomImage(int width, int height, int xOffset, int yOffset, boolean ARGB) {
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        pixels = ( (DataBufferInt) image.getRaster().getDataBuffer() ).getData();
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }
    
    // Paint component
    @Override
    public void paintComponent(Graphics gfx) {
        gfx.drawImage(image, xOffset, yOffset, null);
    }
    
    // Apply polydimensional
    public void ApplyPolyDimensional(int[][] polyDimensional, 
            int xOffset, int yOffset) {
        
        for (int y = 0; y < polyDimensional.length; y++) {
            for (int x = 0; x < polyDimensional[0].length; x++) {
                
                // Security checks
                if (x + xOffset >= 0 && x + xOffset < image.getWidth()
                    && y + yOffset >= 0 && y + yOffset < image.getHeight()) {
                    if ((polyDimensional[y][x] &0xffffff) != transparent) {
                        
                        pixels[(yOffset + y) * image.getWidth() + (xOffset + x)]
                                = polyDimensional[y][x];
                    
                    }
                }
            
            }
        }
        
    }
    
}
