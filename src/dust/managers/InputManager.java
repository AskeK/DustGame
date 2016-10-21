package dust.managers;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JComponent;

/**
 *
 * @author askek
 */
public class InputManager implements MouseListener, KeyListener {
    
    // Fields
    private static JComponent parent;
    public static boolean
            lbPressed = false,
            rbPressed = false,
            
            wPressed = false,
            sPressed = false,
            aPressed = false,
            dPressed = false,
            tPressed = false,
    
            spacePressed = false,
            ctrlPressed = false,
            altPressed = false;
    
    public InputManager() {}
    
    // Ctor with parent
    public InputManager(JComponent parent) {
        this.parent = parent;
    }
    
    // Event functions
    @Override
    public void mouseClicked(MouseEvent me) {
    }

    @Override
    public void mousePressed(MouseEvent me) {
        if (me.getButton() == 1) lbPressed = true;
        if (me.getButton() == 3) rbPressed = true;
        
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        if (me.getButton() == 1) lbPressed = false;
        if (me.getButton() == 3) rbPressed = false;
        
    }

    @Override
    public void mouseEntered(MouseEvent me) {
        parent.requestFocus();
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
        if (ke.getKeyCode() == 17) ctrlPressed = true;
        if (ke.getKeyCode() == 18) altPressed = true;
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        if (ke.getKeyChar() == 'w') wPressed = false;
        if (ke.getKeyChar() == 's') sPressed = false;
        if (ke.getKeyChar() == 'a') aPressed = false;
        if (ke.getKeyChar() == 'd') dPressed = false;
        
        if (ke.getKeyChar() == ' ') spacePressed = false;
        if (ke.getKeyCode() == 17) ctrlPressed = false;
        if (ke.getKeyCode() == 18) altPressed = false;
    }
    
}
