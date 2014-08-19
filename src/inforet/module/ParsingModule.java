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
 * http://blog.manishchhabra.com/2012/08/the-5-main-differences-betwen-hashmap-and-hashtable/
 *
 *
 *
 */
public class ParsingModule
{
    private List<String> docIdMap = new ArrayList<String>();
    private FileReader fileReader = null;
    private BufferedReader reader = null;
    private List<String> lineWords = new ArrayList<String>();
    private int wordIndex = 0;
    private String line = null;

    private Pattern notNumAndLetters = Pattern.compile("[^a-z0-9]+");


    private Boolean inDoc = false;
    private Boolean inHeadline = false;
    private Boolean inText = false;


    private Integer currentDocId = -1;

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

    public Integer getCurrentDocId() {
        return currentDocId;
    }
    public List<String> getDocIdMap() {
        return docIdMap;
    }

    /***
     * getNextWord from the Document Collection.
     * This method reads the predefined Document Library, reading the Document Contents
     * line by line, ignorning numbers, whitespace & individual characters
     * @return nextWord
     */
    public String getNextWord()
    {
        String result = null;
        do{
            //check if we have a line to work with
            if(line == null)
            {
                //try to get the next line
                try
                {
                    line = reader.readLine();
                }catch (IOException ex)
                {
                    System.err.print(ex.getMessage());
                }
                //if the line is null, we are done with the file
                if(line == null)
                {
                    return null;
                }

                //if parsing produces no words, skip to the next line
                if(parseLine(line) == false)
                {
                    line = null;
                    continue;
                }
            }

            //if we make it here, we have a parsed line
            //if the current word index < the size of the line words
            //return the current word
            if(wordIndex < lineWords.size())
            {
                //fetch the next word  in the line and then normalize the word.
                result = lineWords.get(wordIndex);
                result = TermNormalizer.transform(result);
                wordIndex++;
            }else
            {
                wordIndex = 0;
                lineWords.clear();
                line = null;
            }
        }while(result == null || result.length() == 0);
        return result;
    }

    /***
     * Parses a line of text from an SGML document.
     * It splits text within the <HEADLINE> </HEADLINE> and <TEXT></TEXT> tags
     * into an array list of terms. These words are added to the "linewords" List
     * which is retrieved by getNextWord(); method to return the next word to the caller.
     *
     * @param line
     * @return
     */
    private Boolean parseLine(String line)
    {

        if(line.length() > 0) {

            //and check if the word is tag
            if (line.charAt(0) == '<') {
                //fist split the line on spaces and dashes
                lineWords.addAll(Arrays.asList(line.split("[\\s\\-]")));
                String firstWord = lineWords.get(0);

                if (firstWord.equals("<DOC>")) {
                    this.inDoc = true;
                } else if (firstWord.equals("</DOC>")) {
                    this.inDoc = false;
                } else if (firstWord.equals("<DOCNO>")) {
                    this.currentDocId = docIdMap.size();
                    lineWords.clear();
                    lineWords.addAll(Arrays.asList(line.split("[\\s]")));
                    docIdMap.add(lineWords.get(1));
                } else if (firstWord.equals("<HEADLINE>")) {
                    this.inHeadline = true;
                } else if (firstWord.equals("</HEADLINE>")) {
                    this.inHeadline = false;
                } else if (firstWord.equals("<TEXT>")) {
                    this.inText = true;
                } else if (firstWord.equals("</TEXT>")) {
                    this.inText = false;
                }
            } else if (this.inDoc && (this.inHeadline || this.inText)) {
                //fist split the line on spaces and dashes and slashes
                lineWords.addAll(Arrays.asList(line.split("[/\\s\\-]")));
                return true;
            }
        }

        lineWords.clear();
        return false;

    }



}
