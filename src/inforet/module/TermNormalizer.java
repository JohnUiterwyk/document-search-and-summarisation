package inforet.module;

import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * Created by Daniel on 16/08/2014.
 */
public class TermNormalizer {
    private static Pattern notNumAndLetters = Pattern.compile("[^a-z0-9]+");
    public static String transform(String str){
        return notNumAndLetters.matcher(str.toLowerCase().trim()).replaceAll("");
    }

    public static String[] stringToTerms(String str)
    {
        return notNumAndLetters.matcher(str.toLowerCase().trim()).replaceAll(" ").split("[/\\s\\-\\n]");
    }
}
