package inforet.module;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.io.BufferedReader;
import java.util.regex.Pattern;

/**
 * Created by johnuiterwyk on 11/08/2014.
 *
 * References:
 * http://stackoverflow.com/questions/2788080/reading-a-text-file-in-java
 * http://stackoverflow.com/questions/716597/array-or-list-in-java-which-is-faster
 * http://stackoverflow.com/questions/7488643/java-how-to-convert-comma-separated-string-to-arraylist
 * http://stackoverflow.com/questions/180158/how-do-i-time-a-methods-execution-in-java
 * http://stackoverflow.com/questions/6239061/regular-expression-to-remove-everything-but-characters-and-numbers
 * http://stackoverflow.com/questions/1757363/java-hashmap-performance-optimization-alternative
 *
 *
 *
 */
public class ParsingModule
{
    private List<String> docIds = new ArrayList<String>();
    private FileReader fileReader = null;
    private BufferedReader reader = null;
    private String line = null;
    private List<String> lineWords = new ArrayList<String>();
    private Map<String, Integer> terms = new HashMap<String, Integer>();

    private Pattern notNumAndLetters = Pattern.compile("[^a-z0-9]+");

    private Boolean inDoc = false;
    private Boolean inHead = false;
    private Boolean inText = false;

    public ParsingModule()
    {

    }

    public void loadFile(String pathToFile)
    {
        try
        {
            fileReader = new FileReader(pathToFile);
        }catch (FileNotFoundException ex)
        {
            System.err.print(ex.getMessage());
            return;
        }
        reader = new BufferedReader(fileReader);


    }
    public void parse() {
        while (this.readNextLine() == true) {
            parseLine();
        }

        System.out.println("Term count: " + terms.size());

    }

    public void printTerms()
    {
        Iterator<String> keySetIterator = terms.keySet().iterator();
        while(keySetIterator.hasNext()){
            String key = keySetIterator.next();
            System.out.println(key + " : " + terms.get(key));
        }
    }

    private void parseLine()
    {
        lineWords.clear();
        lineWords.addAll(Arrays.asList(line.split(" ")));

        for(String word: lineWords)
        {
            indexTerm(word);
        }

    }

    private void indexTerm(String word)
    {
        //use precompiled notNumAndLetters for performance
        word = notNumAndLetters.matcher(word.toLowerCase()).replaceAll("");

        //check for word in map
        if(terms.containsKey(word) == false) {
            terms.put(word, new Integer(1));
        }else
        {
            Integer count = (Integer)terms.get(word);
            terms.put(word, count + 1);
        }
    }

    private Boolean readNextLine()
    {
        try
        {
            line = reader.readLine();
        }catch (IOException ex)
        {
            System.err.print(ex.getMessage());
            line = null;
        }
        return (line != null);

    }

}
