package progtech2;

import gui.MainFrame;
import java.awt.EventQueue;
import javax.persistence.PersistenceException;

public class Main {

    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                try {
                    new MainFrame().setVisible(true);
                } catch (PersistenceException ex) {
                    MainFrame.showError("Adatbázis kapcsolat hiba. (Szerver fut?)");
                }
            }
        });
    }

}
