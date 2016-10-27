package dust.tilemapeditor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author askek
 */
public class Porting {
    
    // Import
    public static ArrayList<ComponentInfo> Import(String URL) {
        
        ArrayList<ComponentInfo> response = new ArrayList<>();
        File file = new File(URL);
        if ( ! file.exists() ) {
            System.out.println("File not found: " + URL);
            return response;
        }
        
        try {
            
            FileInputStream input = new FileInputStream(file);
            byte[] data = new byte[ (int) file.length() ];
            input.read(data);
            
            String strdata = "";
            for (int i = 0; i < data.length; i++) {
                strdata += (char) data[i];
            }
            
            String[] elements = strdata.split("[|]");
            for (String element : elements) {
                String[] args = element.split("[:]");
            
                if (args.length >= 6) {
                    
                    ComponentInfo info = new ComponentInfo(
                            args[0], args[1],
                            Integer.decode(args[2]),
                            Integer.decode(args[3]),
                            Integer.decode(args[4]),
                            Integer.decode(args[5])
                    );
                    
                    response.add(info);
                    
                } else {
                    System.out.println("Import error: Wrong num of args");
                }
            }
        
        } catch (FileNotFoundException ex) { } catch (IOException ex) { }
            
        System.out.println("Imported: " + URL);
        return response;
        
    }
    
    // Export
    public static void Export(String URL, 
            ArrayList<ComponentInfo> scene) {
        
        try {
            
            File file = new File(URL);
            file.createNewFile();
            
            String data = "";
            for (ComponentInfo c : scene) {
                data += c.type + ":";
                data += c.spriteSheet  + ":";
                data += c.x + ":";
                data += c.y + ":";
                data += c.spriteSheetX + ":";
                data += c.spriteSheetY + "|";
            }
            
            try (FileOutputStream output = new FileOutputStream(file)) {
                output.write( data.getBytes() );
                System.out.println("Exported: " + URL);
            } catch (Exception e) { }
            
        } catch (IOException ex) {Logger.getLogger(Porting.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
