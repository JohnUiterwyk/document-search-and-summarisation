package inforet.util;

/**
 * Created by johnuiterwyk on 9/10/2014.
 */
public class LineWrapper {
    /***
     * prints a string to the console, with automatic line wrapping at a certain number of characters
     * This is based on the following stack overflow post:
     * http://stackoverflow.com/questions/4212675/
     *
     * @param line
     */
    public static void printWrappedLine(String line, int maxChar)
    {
        line = line.replace("\n"," ");
        StringBuilder sb = new StringBuilder(line);

        int i = 0;
        while (i + maxChar < sb.length() && (i = sb.lastIndexOf(" ", i + maxChar)) != -1) {
            sb.replace(i, i + 1, "\n");
        }

        System.out.println(sb.toString());
    }
}
