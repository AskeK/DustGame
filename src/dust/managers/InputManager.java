package dust.managers;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 *
 * @author askek
 */
public class InputManager implements MouseListener, KeyListener {
    
    // Fields
    public static boolean
            lbPressed = false,
            
            wPressed = false,
            sPressed = false,
            aPressed = false,
            dPressed = false,
    
            spacePressed = false;
    
    // Event functions
    @Override
    public void mouseClicked(MouseEvent me) {
    }

    @Override
    public void mousePressed(MouseEvent me) {
        if (me.getButton() == 1) lbPressed = true;
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        if (me.getButton() == 1) lbPressed = false;
    }

    @Override
    public void mouseEntered(MouseEvent me) {
    }

    @Override
    public void mouseExited(MouseEvent me) {
    }

    @Override
    public void keyTyped(KeyEvent ke) {
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        if (ke.getKeyChar() == 'w') wPressed = true;
        if (ke.getKeyChar() == 's') sPressed = true;
        if (ke.getKeyChar() == 'a') aPressed = true;
        if (ke.getKeyChar() == 'd') dPressed = true;
        
        if (ke.getKeyChar() == ' ') spacePressed = true;
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        if (ke.getKeyChar() == 'w') wPressed = false;
        if (ke.getKeyChar() == 's') sPressed = false;
        if (ke.getKeyChar() == 'a') aPressed = false;
        if (ke.getKeyChar() == 'd') dPressed = false;
        
        if (ke.getKeyChar() == ' ') spacePressed = false;
    }
    
}
