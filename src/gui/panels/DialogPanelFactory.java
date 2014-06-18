package gui.panels;


public class DialogPanelFactory {
    
     public static <T extends DialogPanel> T getDialogPanel(Class<T> panel) throws InstantiationException, IllegalAccessException {
        return panel.newInstance();
    }
    
}
