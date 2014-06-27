package progtech2;

import gui.MainFrame;
import java.awt.EventQueue;
import java.sql.SQLException;
import javax.persistence.PersistenceException;
import logic.db.DBSeed;
import logic.db.DataSource;
import logic.Logger;

public class Main {

    public static void main(String[] args) {

        try {
            if (DataSource.getInstance().getController("Category").rowCount() < 1) {
                DBSeed.doSeed();
            }
        } catch (SQLException ex) {
            Logger.log(ex.getMessage(), "INFO");
        }

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
