package slack;

public class Formatter {
    public static String format(String text) {
        return formatBold(text);
    }

    private static String formatBold(String text) {
        text = text.replace("<b>", "*");

        return text.replace("</b>", "*");
    }
}
