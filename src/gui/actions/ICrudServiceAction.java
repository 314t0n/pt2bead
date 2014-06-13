
package gui.actions;

import javax.swing.Action;
/*
Alapvető 
*/
public interface ICrudServiceAction {
    
    Action getCreateAction();
    Action getReadAction();
    Action getUpdateAction();
    Action getDeleteAction();       
    
}
