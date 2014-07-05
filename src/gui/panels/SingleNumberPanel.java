package gui.panels;

import java.awt.GridLayout;
import javax.swing.JTextField;
import logic.IEntity;
import logic.Strings;
import logic.exceptions.NegativeNumberException;
import logic.exceptions.ZeroNumberException;
/**
 * Egyszerű, szám érték panel
 * @author ag313w
 */
public class SingleNumberPanel extends DialogPanel {

    private JTextField number;

    public SingleNumberPanel() {

        setName(Strings.AMOUNT);
        GridLayout layout = new GridLayout(1, 0);
        setLayout(layout);
        initPanel();

    }

    private void initPanel() {

        number = new JTextField(Strings.AMOUNT, 5);
        number.setText("1");

        add(number);

    }
  
    private void checkNumber() {
        if (Integer.parseInt(number.getText()) < 0) {
            throw new NegativeNumberException();
        } else if (Integer.parseInt(number.getText()) < 1) {
            throw new ZeroNumberException();
        }
    }

    public Integer getValue() {        
        return Integer.parseInt(number.getText());
    }

    @Override
    public void setAttributes() {
        checkNumber();
    }

    @Override
    public IEntity getModel() {
        return null;
    }

}
