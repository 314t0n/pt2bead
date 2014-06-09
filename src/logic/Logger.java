package logic;

public class Logger {

    public static void log(String msg) {
        System.out.println(msg);
    }

    public static void log(String msg, String lvl) {
        System.out.print("[" + lvl + "]" + ": ");
        System.out.println(msg);
    }

}
