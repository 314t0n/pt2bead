package gui;

import gui.actions.CreateProductListener;
import gui.actions.ProductCrudAction;
import gui.tablemodels.GenericTableModel;
import gui.tablemodels.TableModelFactory;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableRowSorter;
import logic.CrudService;
import logic.IEntity;
import logic.Strings;

public class BasicEditor<T extends IEntity, S extends CrudService<T>> extends JPanel {

    private final JPanel mainPanel;
    private GenericTableModel<T, S> tableModel;
    private TableRowSorter<GenericTableModel> tableSorter;
    private final JFrame parentFrame;
    private final IEntity entity;

    public BasicEditor(IEntity entity, JFrame parentFrame) {
        super();

        this.parentFrame = parentFrame;
        this.mainPanel = new JPanel();
        this.entity = entity;
        try {
            this.tableModel = TableModelFactory.createTableModel(entity.getClass(), entity.getPropertyNames());
        } catch (InstantiationException ex) {
            Logger.getLogger(BasicEditor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(BasicEditor.class.getName()).log(Level.SEVERE, null, ex);
        }

        setControllPanel(mainPanel);

        setTable();

        this.add(mainPanel);
    }

    protected void setControllPanel(JPanel panel) {

        ProductCrudAction productAction = new ProductCrudAction(parentFrame, tableModel);

        BorderLayout layout = new BorderLayout();

        panel.setLayout(layout);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        JButton btn1 = new JButton(Strings.NEW);
        JButton btn2 = new JButton(Strings.DEL);
        JButton btn3 = new JButton(Strings.MOD);

        buttonPanel.add(btn1);
        buttonPanel.add(btn2);
        buttonPanel.add(btn3);

        btn1.setAction(productAction.getCreateAction());
        //btn2.setAction(deleteAction);
        //btn3.setAction(updateAction);

        panel.add(buttonPanel, BorderLayout.NORTH);

    }

    private void setTable() {

        try {

            JTable table = new JTable();

            tableSorter = new TableRowSorter(tableModel);

            table.setModel(tableModel);
            table.setRowSorter(tableSorter);
            table.setAutoCreateRowSorter(true);
            mainPanel.add(new JScrollPane(table), BorderLayout.CENTER);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    protected Action createAction;

}
