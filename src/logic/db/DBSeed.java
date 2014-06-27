package logic.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import logic.Logger;

public class DBSeed {

    private static final String DB_URL = "jdbc:derby://localhost:1527/progtech2PU";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "test";
    private static Connection connection;

    public static void doSeed() throws SQLException {

        execute("INSERT INTO ROOT.CATEGORY (ID, NAME) VALUES (1, 'smartphone')");
        execute("INSERT INTO ROOT.CATEGORY (ID, NAME) VALUES (2, 'walkman')");
        execute("INSERT INTO ROOT.CATEGORY (ID, NAME) VALUES (3, 'tablet')");
        execute("INSERT INTO ROOT.CATEGORY (ID, NAME) VALUES (4, 'ssd')");

        execute("INSERT INTO ROOT.PRODUCT (ID, ACTIVE, DESCRIPTION, MANUFACTURER, PRICE, STOCK, TYPE, CATEGORY_ID) VALUES (5, 1, '14inch', 'Samsung', 55000, 0, 'Galaxy', 3)");
        execute("INSERT INTO ROOT.PRODUCT (ID, ACTIVE, DESCRIPTION, MANUFACTURER, PRICE, STOCK, TYPE, CATEGORY_ID) VALUES (51, 1, 'kazetta lejátszó', 'Sony', 10000, 10, 'walkman', 2)");
        execute("INSERT INTO ROOT.PRODUCT (ID, ACTIVE, DESCRIPTION, MANUFACTURER, PRICE, STOCK, TYPE, CATEGORY_ID) VALUES (151, 1, '120Gb', 'Western Digital', 49000, 100, 'Green ', 4)");
        execute("INSERT INTO ROOT.PRODUCT (ID, ACTIVE, DESCRIPTION, MANUFACTURER, PRICE, STOCK, TYPE, CATEGORY_ID) VALUES (152, 1, '4', 'Apple', 200000, 0, 'iPad', 3)");
        execute("INSERT INTO ROOT.PRODUCT (ID, ACTIVE, DESCRIPTION, MANUFACTURER, PRICE, STOCK, TYPE, CATEGORY_ID) VALUES (302, 1, '5.0MP, 1440p, 4G', 'Sony', 99889, 48, 'Xperia Z', 1)");
        execute("INSERT INTO ROOT.PRODUCT (ID, ACTIVE, DESCRIPTION, MANUFACTURER, PRICE, STOCK, TYPE, CATEGORY_ID) VALUES (303, 1, 'aranyozott', 'Apple', 345000, 3, 'iPhone 5', 1)");

        execute("INSERT INTO ROOT.ORDER_ (ID, ADDRESS, DATE_FIELD, EMAIL, FULLFILLED, NAME, NUMBER) VALUES (251, '16 Ashmark Avenue, New Cumnock, Cumnock, East Ayrshire KA18 4ET, UK', '2014-06-27 14:14:14.576', 'asd@asd.asd', 0, 'Akciós Áron', '5555')");
        execute("INSERT INTO ROOT.ORDER_ (ID, ADDRESS, DATE_FIELD, EMAIL, FULLFILLED, NAME, NUMBER) VALUES (252, '17 Kingfisher Way, Cambridge, Cambridgeshire CB2 8EW, UK', '2014-06-27 14:15:48.328', 'tech@kolos.no', 0, 'Techno Kolos ', '55334433')");
        execute("INSERT INTO ROOT.ORDER_ (ID, ADDRESS, DATE_FIELD, EMAIL, FULLFILLED, NAME, NUMBER) VALUES (253, '78A Norreys Road, Cumnor, Oxford, Oxfordshire OX2 9PU', '2014-06-27 14:17:00.871', 'sd@asd.asd', 0, 'Kala Pál ', '67 5677 565634')");
        execute("INSERT INTO ROOT.ORDER_ (ID, ADDRESS, DATE_FIELD, EMAIL, FULLFILLED, NAME, NUMBER) VALUES (301, '16 Ashmark Avenue, New Cumnock, Cumnock, East Ayrshire KA18 4ET', '2014-06-27 14:29:45.08', 'minden@ads.asd', 0, 'Minden Áron', '77 889 6766')");
        execute("INSERT INTO ROOT.ORDER_ (ID, ADDRESS, DATE_FIELD, EMAIL, FULLFILLED, NAME, NUMBER) VALUES (304, 'Blackmore Park Road, Malvern, Worcestershire WR13', '2014-06-27 14:33:02.935', 'wincse@matav.hu', 0, 'Wincs Eszter ', '64 33 223 3444')");
        execute("INSERT INTO ROOT.ORDER_ (ID, ADDRESS, DATE_FIELD, EMAIL, FULLFILLED, NAME, NUMBER) VALUES (305, '57 William Street, Huddersfield, West Yorkshire HD4 ', '2014-06-27 14:34:34.484', 'asd@asda.asd', 1, 'Har Mónika ', '2342442423')");

        execute("INSERT INTO ROOT.ORDER_PRODUCTS (ORDER_ID, AMOUNT, PRODUCTS_KEY) VALUES (251, 1, 5)");
        execute("INSERT INTO ROOT.ORDER_PRODUCTS (ORDER_ID, AMOUNT, PRODUCTS_KEY) VALUES (252, 5, 51)");
        execute("INSERT INTO ROOT.ORDER_PRODUCTS (ORDER_ID, AMOUNT, PRODUCTS_KEY) VALUES (253, 3, 151)");
        execute("INSERT INTO ROOT.ORDER_PRODUCTS (ORDER_ID, AMOUNT, PRODUCTS_KEY) VALUES (301, 1, 152)");
        execute("INSERT INTO ROOT.ORDER_PRODUCTS (ORDER_ID, AMOUNT, PRODUCTS_KEY) VALUES (304, 4, 302)");
        execute("INSERT INTO ROOT.ORDER_PRODUCTS (ORDER_ID, AMOUNT, PRODUCTS_KEY) VALUES (305, 2, 302)");

    }

    private static void execute(String sql) throws SQLException {

        connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

        Statement statement = null;
        try {

            statement = connection.createStatement();
            statement.executeUpdate(sql);
            Logger.log("Database seed: [ok].", "INFO");

        } catch (SQLException e) {

            Logger.log("Database seed: [fail].", "INFO");
            Logger.log(e.getMessage(), "INFO");

        } finally {

            if (statement != null) {
                statement.close();
            }

            if (connection != null) {
                connection.close();
            }

        }

    }

}
