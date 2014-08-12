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
    private Map<String, TermInfo> terms = new HashMap<String, TermInfo>();

    private Pattern notNumAndLetters = Pattern.compile("[^a-z0-9]+");

    private Boolean inDoc = false;
    private Boolean inHeadline = false;
    private Boolean inText = false;

    private Boolean printTermsEnabled = false;
    private int currentDocId = -1;

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
    public void parse(Boolean printTermsEnabled) {
        this.printTermsEnabled = printTermsEnabled;
        while (this.readNextLine() == true) {
            parseLine();
        }

        System.out.println("Term count: " + terms.size());

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
    private void parseLine()
    {
        lineWords.clear();
        lineWords.addAll(Arrays.asList(line.split(" ")));
        if(lineWords.size() > 0) {
            String firstWord = lineWords.get(0);

            if (firstWord.length() > 0 && firstWord.charAt(0) == '<') {
                if (firstWord.equals("<DOC>")) {
                    this.inDoc = true;
                } else if (firstWord.equals("</DOC>")) {
                    this.inDoc = false;
                } else if (firstWord.equals("<DOCNO>")) {
                    this.currentDocId = docIds.size();
                    docIds.add(lineWords.get(1));
                } else if (firstWord.equals("<HEADLINE>")) {
                    this.inHeadline = true;
                } else if (firstWord.equals("</HEADLINE>")) {
                    this.inHeadline = false;
                } else if (firstWord.equals("<TEXT>")) {
                    this.inText = true;
                } else if (firstWord.equals("</TEXT>")) {
                    this.inText = false;
                }
            } else {
                if (this.inDoc && (this.inHeadline || this.inText)) {
                    for (String word : lineWords) {
                        indexTerm(word);
                    }

                }
            }
        }



    }

    private void indexTerm(String word)
    {
        //use precompiled notNumAndLetters for performance
        word = notNumAndLetters.matcher(word.toLowerCase()).replaceAll("");


        //check for word in map
        if(word.length() > 1)
        {
            if(terms.containsKey(word) == false) {

                terms.put(word, new TermInfo(this.currentDocId));
            }else
            {
                TermInfo termInfo = terms.get(word);
                termInfo.addOccurance(this.currentDocId);
                terms.put(word, termInfo);
            }
            if(this.printTermsEnabled)
            {
                System.out.println(word+" : "+terms.get(word).docFrequency);
            }
        }
    }

//    public void printTerms()
//    {
//
////        this was bad!!!!!!!!!
////        iterating over a hashmap includes empty slots!!!
////        oops!!
//        Iterator<String> keySetIterator = terms.keySet().iterator();
//        while(keySetIterator.hasNext()){
//            String key = keySetIterator.next();
//            System.out.println(key + " : " + terms.get(key));
//        }
//    }

}
