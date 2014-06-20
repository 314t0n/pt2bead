package gui.actions;

import javax.swing.Action;

/**
 * Általános szerkesztési feladatok.
 *
 *
 * Create Read Update Delete
 *
 * @author ag313w
 */
public interface ICrudServiceAction {

    Action getCreateAction();

    Action getReadAction();

    Action getUpdateAction();

    Action getDeleteAction();

}
