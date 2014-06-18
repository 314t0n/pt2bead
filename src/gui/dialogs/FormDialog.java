package gui.dialogs;

import gui.MainFrame;
import gui.panels.DialogPanel;
import gui.tablemodels.GenericTableModel;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class FormDialog extends JDialog implements ActionListener {

    private JPanel submitPanel;
    private DialogPanel addPanel;
    protected JPanel formPanel;
    private JButton yesButton;
    private JButton noButton;
    private boolean answer;

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

    private void setButtons() {
        yesButton = new JButton("Mentes");
        yesButton.addActionListener(this);
        submitPanel.add(yesButton);
        noButton = new JButton("Megse");
        noButton.addActionListener(this);
        submitPanel.add(noButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (yesButton == e.getSource()) {

            try {

                addPanel.setAttributes();
                answer = true;
                setVisible(false);

            } catch (NumberFormatException ex) {
                MainFrame.showError("Hibás szám az ár mezőnél!");
            }

        } else if (noButton == e.getSource()) {
            setVisible(false);
        }
    }

    public boolean isSaveRequired() {
        return answer;
    }

}
