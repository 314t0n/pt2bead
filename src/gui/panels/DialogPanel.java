package gui.panels;

import javax.swing.JPanel;
import logic.IEntity;

public abstract class DialogPanel extends JPanel {

    public abstract void setAttributes();
    
    public abstract IEntity getModel();
    
}
