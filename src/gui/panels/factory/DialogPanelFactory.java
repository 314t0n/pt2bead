package gui.panels.factory;

import gui.panels.DialogPanel;


public class DialogPanelFactory {
    
     public static <T extends DialogPanel> T getDialogPanel(Class<T> panel) throws InstantiationException, IllegalAccessException {
        return panel.newInstance();
    }
    
}
