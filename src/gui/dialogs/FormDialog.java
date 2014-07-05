package gui.dialogs;

import gui.MainFrame;
import gui.panels.DialogPanel;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import logic.Strings;
import logic.exceptions.NegativeNumberException;
/**
 * Kitölthető űrlap dialog
 * 
 * Mentés, Mégse gombokkal
 * 
 * Az űrlap panel injektálható
 * 
 * @author  
 */
public class FormDialog extends JDialog implements ActionListener {

    private JPanel submitPanel;
    private DialogPanel addPanel;
    protected JPanel formPanel;
    private JButton yesButton;
    private JButton noButton;
    private boolean answer;
    /**
     * 
     * @param frame szülő keret
     * @param modal 
     * @param addPanel beszúrt panel elem
     */
    public FormDialog(JFrame frame, boolean modal, DialogPanel addPanel) {
        super(frame, modal);

        this.submitPanel = new JPanel();
        this.addPanel = addPanel;

        setLayout(new BorderLayout());
        getContentPane().add(submitPanel, BorderLayout.SOUTH);
        getContentPane().add(addPanel, BorderLayout.CENTER);

        this.answer = false;
        setButtons();
        pack();
        setLocationRelativeTo(frame);
        setVisible(true);
    }
    /**
     * Gombok beállítása
     */
    private void setButtons() {
        yesButton = new JButton("Mentes");
        yesButton.addActionListener(this);
        submitPanel.add(yesButton);
        noButton = new JButton("Megse");
        noButton.addActionListener(this);
        submitPanel.add(noButton);
    }
    /**
     * Mentés/Mégse gombok
     * esetén beállítja az állapot (kell-e menteni).
     * @param e 
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (yesButton == e.getSource()) {

            try {

                addPanel.setAttributes();
                answer = true;
                setVisible(false);

            } catch (NegativeNumberException ex) {
                MainFrame.showError(ex.getMessage());
            } catch (NumberFormatException ex) {
                MainFrame.showError(Strings.ERROR_NUMBER_FORMAT);
            }

        } else if (noButton == e.getSource()) {
            setVisible(false);
        }
    }
    /**
     * Kell-e menteni
     * @return 
     */
    public boolean isSaveRequired() {
        return answer;
    }

}
