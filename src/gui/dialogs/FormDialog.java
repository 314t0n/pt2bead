package gui.dialogs;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class FormDialog extends JDialog implements ActionListener {

    private JPanel submitPanel = null;
    protected JPanel formPanel = null;
    private JButton yesButton = null;
    private JButton noButton = null;
    private boolean answer = false;

    public FormDialog(JFrame frame, boolean modal, JPanel mainPanel) {
        super(frame, modal);
        submitPanel = new JPanel();
        setLayout(new BorderLayout());
        getContentPane().add(submitPanel, BorderLayout.SOUTH);
        getContentPane().add(mainPanel, BorderLayout.CENTER);

        yesButton = new JButton("Mentes");
        yesButton.addActionListener(this);
        submitPanel.add(yesButton);
        noButton = new JButton("Megse");
        noButton.addActionListener(this);
        submitPanel.add(noButton);
        pack();
        setLocationRelativeTo(frame);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {   
        if (yesButton == e.getSource()) {
            answer = true;
            setVisible(false);
        } else if (noButton == e.getSource()) {
            setVisible(false);
        }
    }

    public boolean isSave() {
        return answer;
    }
}
