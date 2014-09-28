package inforet.model;

import inforet.module.TermNormalizer;
import inforet.util.Heapify;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.*;

/**
 * Created by johnuiterwyk on 20/09/2014.
 * The DocInfo class contains information that will be stored in the map
 * This includes the index, a the identifier string, and the wieght
 *
 * Occurance of a substring in a string:
 * http://stackoverflow.com/questions/767759/occurrences-of-substring-in-a-string
 */
public class Document extends TextContent
{

    private int index = -1;
    private String identifier;
    private long fileOffset;
    private long rawFullLength;
    private String headline = "";
    private float lengthWeight = 0f;
    public Document() {
    }

    public String getIdentifier() {
        return identifier;
    }
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public long getRawLength() {
        return rawFullLength;
    }

    public void setRawLength(long length) {
        this.rawFullLength = length;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public float getLengthWeight() {
        return lengthWeight;
    }

    public void setLengthWeight(float lengthWeight) {
        this.lengthWeight = lengthWeight;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getHeadline() {
        return headline;
    }


    public long getFileOffset() {
        return fileOffset;
    }

    public void setFileOffset(long fileOffset) {
        this.fileOffset = fileOffset;
    }


    /***
     * get sentence list splits the doc into sentences. NOTE! this parsing is done each time you call this,
     * so only call it once and save the output. This doesnt store the sentence in the document.
     * @return
     */
    public List<Sentence> getSentenceList ()
    {
        List<Sentence> sentenceList = new ArrayList<Sentence>();
        // Read one character at a time until the end of text,
        // spliting the sentences and adding metadata as we go.

        StringCharacterIterator sci = new StringCharacterIterator(this.getText());
        StringBuilder strBld = null;
        int paragraph           = 0;
        int sinceLastDotCounter = 0;
        int newlineCounter      = 0;

        //Iterate through the text until the end is reached
        for (char ch = sci.first(); ch != CharacterIterator.DONE; ch = sci.next()){
            ch = Character.toLowerCase(ch);
            //Determine if is new paragraph
            //Check if we get a newline
            if ( ch == '\n' ){
                newlineCounter++;
                if ( newlineCounter > 1 ){
                    newlineCounter = 0;
                    paragraph++;        // Conditions for a new paragraph is met.
                    continue; // Do not add new line to the collection of sentences.
                }else
                {
                    //single new lines should be added as a space
                    ch = ' ';
                }

            }else
            {
                newlineCounter = 0;
            }

            // Extract the sentences
            if(strBld == null) strBld = new StringBuilder();

            strBld.append(ch);
            sinceLastDotCounter++;

            if ( strBld.length() < Sentence.MIN_LENGTH ){ // Is shorter than min length, lets continue.
                continue;
            }
            else if ( Sentence.isSentenceTerminator(ch) ){   //A dot character has been found.
                //Filter out acronyms such as A.B.C. or just .
                if ( sinceLastDotCounter > 2 ){  // Previous to last character was a not  "."
                    // We've got a legitimate sentence terminator
                    Sentence sentence = new Sentence(strBld.toString(), paragraph);
                    sentenceList.add(sentence);
                    strBld = null;
                }
                sinceLastDotCounter = 0; // Reset the counter.
            }
        }

        return sentenceList;
    }
}
