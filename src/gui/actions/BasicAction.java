package gui.actions;

import gui.BasicEditor;

/**
 *
 * @author ag313w
 */
abstract public class BasicAction {
    
    protected BasicEditor editor;

    public BasicAction(BasicEditor editor) {
        this.editor = editor;
    }
    
}
