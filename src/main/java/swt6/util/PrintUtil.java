package swt6.util;

public class PrintUtil {
    public static void printLargeTitle(String title) {
        System.out.println("!-----------------------------------------------------!");
        printTitle(title);
        System.out.println("!-----------------------------------------------------!");
        System.out.println();
    }

    public static void printColumn(String s, int width) {
        System.out.print(s);
        for(int i = 0; i < width - s.length() - 1; ++i) {
            System.out.print(" ");
        }
        System.out.print(" ");
    }

    public static void printTitle(String title) {

        StringBuilder leftIndent = new StringBuilder();
        StringBuilder rightIndent = new StringBuilder();
        for (int i = 0; i < (27 - title.length()) / 2; ++i) {
            leftIndent.append(" ");
        }
        for (int i = 0; i < (28 - title.length()) / 2; ++i) {
            rightIndent.append(" ");
        }

        System.out.println("!------------ " + leftIndent + title + rightIndent + " ------------!");
    }

    public static void printSubtitle(String title) {
        System.out.println("  !-- " + title);
    }
}
