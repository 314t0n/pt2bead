package gui.panels;

import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import logic.entites.Category;

public class AddCategoryPanel extends AddPanel {

    private JTextField name;
    private Category category;

    public AddCategoryPanel(Category category) {

        setName("Új kategória");
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

}
