package gui.panels;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextField;
import logic.IEntity;
import logic.Logger;
import logic.Strings;
import logic.entites.Order;
import logic.entites.Product;
/**
 * 
 * @author ag313w
 */
public class AddOrderPanel extends DialogPanel {

    private Order order;
    private JList list;
    private List<Product> productsHelper;
    private final JTextField nameField;
    private final JTextField addressField;
    private final JTextField phoneField;
    private final JTextField emailField;

    public AddOrderPanel(Order order) {

        setName("Új rendelés");
        GridLayout layout = new GridLayout(7, 0);
        setLayout(layout);

        this.productsHelper = new ArrayList();
        this.order = order;
        this.list = new JList();

        this.nameField = new JTextField("Név");
        this.addressField = new JTextField("Cím");
        this.phoneField = new JTextField("Tel");
        this.emailField = new JTextField("Email");

        initPanel();

    }

    private void initPanel() {

        add(new JLabel("Rendelés"));

        add(nameField);
        add(addressField);
        add(phoneField);
        add(emailField);

        add(list);

        list.setListData(createList());

        JButton removeProduct = new JButton(Strings.REMOVE_PRODUCT);

        add(removeProduct);

        removeProduct.setAction(removeProductAction);
    }

    private String[] createList() {

        String[] productsAsString = new String[order.getProducts().size()];
        productsHelper.clear();

        int i = 0;
        for (Map.Entry<Product, Integer> entry : order.getProducts().entrySet()) {
            productsAsString[i] = entry.getKey().toString() + " (" + entry.getValue() + ")";
            productsHelper.add(entry.getKey());
            i++;
        }

        return productsAsString;

    }

    private final Action removeProductAction = new AbstractAction(Strings.REMOVE_PRODUCT) {
        @Override
        public void actionPerformed(ActionEvent e) {
            order.getProducts().remove(productsHelper.get(list.getSelectedIndex()));
            Logger.log("Removed: " + list.getSelectedIndex(), "DEBUG");
            list.setListData(createList());
        }
    };

    @Override
    public void setAttributes() {

        order.setAddress(addressField.getText());
        order.setEmail(emailField.getText());
        order.setName(nameField.getText());
        order.setNumber(phoneField.getText());     

    }

    @Override
    public IEntity getModel() {
        return order;
    }

}
