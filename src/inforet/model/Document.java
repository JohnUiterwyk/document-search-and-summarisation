package inforet.model;

import inforet.module.TermNormalizer;

import java.util.*;

/**
 * Created by johnuiterwyk on 20/09/2014.
 * The DocInfo class contains information that will be stored in the map
 * This includes the index, a the identifier string, and the wieght
 *
 * Occurance of a substring in a string:
 * http://stackoverflow.com/questions/767759/occurrences-of-substring-in-a-string
 */
public class Document {


    private int index = -1;
    private String identifier;
    private long fileOffset;
    private long rawFullLength;
    private String headline = "";
    private StringBuilder bodyTextBuilder = new StringBuilder();
    private float weight = 0f;
    private Map<String,Integer> termFrequencies;

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

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
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

    public String getBodyText() {
        return bodyTextBuilder.toString();
    }
    public int getBodyTextLength() {
        return bodyTextBuilder.length();
    }

    public void appendLineToBody(String text) {
        this.bodyTextBuilder.append(text);
        this.bodyTextBuilder.append("\n");
    }

    public Map<String, Integer> getTermFrequency()
    {
        //if the term frequency map doesn't exist, create it
        if(termFrequencies == null)
        {
            termFrequencies = new HashMap<String, Integer>();
            List<String> words = this.getListOfWords();
            for(String word:words)
            {
                //look up the docIndex
                Integer termFrequency = termFrequencies.get(word);
                //if a value was not found, create a value
                if(termFrequency == null)
                {
                    termFrequency = 0;
                }
                //increment the within doc freq and put it back in the hashmap
                termFrequency++;
                termFrequencies.put(word,termFrequency);
            }
        }
        return termFrequencies;
    }

    public int getFrequencyOfTerm(String term)
    {
        int count = 0;
        int currentIndex = 0;
        String doc = this.getBodyText();
        while(currentIndex != -1)
        {
            currentIndex = doc.indexOf(term,currentIndex);
            if( currentIndex != -1 )
            {
                count++;
                currentIndex += term.length();
            }

        }
        return count;
    }

    public List<String> getListOfWords() {
        List<String> words = new ArrayList<String>();
        words.addAll(Arrays.asList(TermNormalizer.stringToTerms(getHeadline())));
        words.addAll(Arrays.asList(TermNormalizer.stringToTerms(getBodyText())));
        return words;
    }
}
