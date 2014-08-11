package inforet.module;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.io.BufferedReader;

/**
 * Created by johnuiterwyk on 11/08/2014.
 *
 * References:
 * http://stackoverflow.com/questions/2788080/reading-a-text-file-in-java
 * http://stackoverflow.com/questions/716597/array-or-list-in-java-which-is-faster
 * http://stackoverflow.com/questions/7488643/java-how-to-convert-comma-separated-string-to-arraylist
 * http://stackoverflow.com/questions/180158/how-do-i-time-a-methods-execution-in-java
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

        System.out.println("Term count: " +terms.size());

    }

    public void printTerms()
    {
        Iterator<String> keySetIterator = terms.keySet().iterator();
        while(keySetIterator.hasNext()){
            String key = keySetIterator.next();
            System.out.println(key + " : " + terms.get(key));
        }
    }

    public void parseLine()
    {
        lineWords.clear();
        lineWords.addAll(Arrays.asList(line.split(" ")));

        Integer count = null;
        for(String word: lineWords)
        {
            //System.out.println(word+" ");
            if(terms.containsKey(word) == false) {
                terms.put(word, new Integer(1));
            }else
            {
                count = (Integer)terms.get(word);
                terms.put(word, count + 1);
            }
        }

    }

    public Boolean readNextLine()
    {
            try
            {
                line = reader.readLine();
            }catch (IOException ex)
            {
                System.err.print(ex.getMessage());
                line = null;
            }
        if(line != null)
        {
            return true;
        }else
        {
            return  false;
        }

    }

}
