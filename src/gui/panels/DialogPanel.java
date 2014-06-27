package gui.panels;

import javax.swing.JPanel;
import logic.IEntity;
/**
 * A formdialoghoz panel
 * 
 * 
 * @author ag313w
 */
public abstract class DialogPanel extends JPanel {
    /**
     * Be lehessen állítani az űrlapon a kapott objektum attribútumait
     */
    public abstract void setAttributes();
    /**
     * Le lehessen kérni az adott objektumot
     * @return 
     */
    public abstract IEntity getModel();
    
}
