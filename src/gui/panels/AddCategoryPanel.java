package gui.panels;

import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import logic.IEntity;
import logic.Strings;
import logic.entites.Category;
/**
 * Új kategória panel
 * 
 * @author  
 */
public class AddCategoryPanel extends DialogPanel {

    private JTextField name;
    private Category category;
    /**
     * 
     * @param category 
     */
    public AddCategoryPanel(Category category) {

        setName(Strings.NEW_CATEGORY);
        GridLayout layout = new GridLayout(1, 0);
        setLayout(layout);

        initPanel();

        this.category = category;

    }
 
    private void initPanel() {

        add(new JLabel("Felvesz"));
        name = new JTextField("név", 25);
        add(name);

    }

    @Override
    public void setAttributes() {
        category.setName(name.getText());
    }

    @Override
    public IEntity getModel() {
        return category;
    }

}
