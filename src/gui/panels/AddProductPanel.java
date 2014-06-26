package gui.panels;

import java.awt.GridLayout;
import java.util.List;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import logic.IEntity;
import logic.entites.Category;
import logic.entites.Product;
import logic.exceptions.NegativNumberException;

public class AddProductPanel extends DialogPanel {

    private JTextField manufacturer;
    private JTextField type;
    private JTextField description;
    private JTextField price;
    private JCheckBox active;
    private Product product;
    private JComboBox categoryList;
    private List<Category> categories;

    /**
     * Új termék felvétele panel
     *
     * @param categories categoryController
     */
    public AddProductPanel(Product product, List<Category> categories) {

        //gyártó, típus, leírás, ár, raktárkészlet, aktív-e
        setName("Új termék");
        GridLayout layout = new GridLayout(7, 0);
        setLayout(layout);

        this.categories = categories;
        initPanel();

        this.product = product;

    }

    private void initPanel() {

        add(new JLabel("Felvesz"));
        manufacturer = new JTextField("gyártó", 25);
        type = new JTextField("típus", 25);
        description = new JTextField("leírás", 25);
        active = new JCheckBox("aktív", true);
        price = new JTextField("ár", 5);
        price.setText("2");
        categoryList = new JComboBox(categories.toArray());

        add(manufacturer);
        add(type);
        add(description);
        add(price);
        add(categoryList);
        add(active);

    }

    @Override
    public void setAttributes() {
        
        checkNumber();

        product.setActive(active.isSelected());
        product.setType(type.getText());
        product.setDescription(description.getText());
        product.setManufacturer(manufacturer.getText());
        product.setPrice(Integer.parseInt(price.getText()));
        product.setCategory(categories.get(categoryList.getSelectedIndex()));

    }

    private void checkNumber() {
        if (Integer.parseInt(price.getText()) < 0) {
            throw new NegativNumberException();
        }
    }

    @Override
    public IEntity getModel() {
        setAttributes();
        return product;
    }

}
