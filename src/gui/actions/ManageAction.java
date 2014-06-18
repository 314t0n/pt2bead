package gui.actions;

import gui.tablemodels.GenericTableModel;
import javax.swing.AbstractAction;

abstract public class ManageAction extends AbstractAction {

    protected GenericTableModel model;

    public ManageAction(String name, GenericTableModel model) {
        super(name);
        this.model = model;

    }

}
