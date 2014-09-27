package inforet.model;

import inforet.module.TermNormalizer;
import inforet.util.Heapify;

import java.util.*;

/**
 * Created by johnuiterwyk on 28/09/2014.
 */
public class TextContent
{
    private StringBuilder text = new StringBuilder();
    private HashMap<String,WordFrequency> wordFrequencies;

    public void append(String appendText)
    {
        text.append(appendText);
    }
    public String getText()
    {
        return text.toString();
    }
    public int getContentLength()
    {
        return text.length();
    }

    public HashMap<String, WordFrequency> getWordFrequencies() {
        return getWordFrequencies(null);
    }
    public HashMap<String, WordFrequency> getWordFrequencies(StopList stopList) {
        if(wordFrequencies == null)
        {
            wordFrequencies = new HashMap<String,WordFrequency>();
            List<String> words = this.getListOfWords(stopList);
            for(String word:words)
            {
                //look up the docIndex
                WordFrequency wordFrequency = wordFrequencies.get(word);
                //if a value was not found, create a value
                if(wordFrequency == null)
                {
                    wordFrequency = new WordFrequency(word);
                }
                //increment the within doc freq and put it back in the hashmap
                wordFrequency.frequency++;
                wordFrequencies.put(word,wordFrequency);
            }
        }
        return wordFrequencies;
    }

    public List<WordFrequency> getTopWordFrequencies(int count)
    {
        return getTopWordFrequencies(count, null);
    }

    public List<WordFrequency> getTopWordFrequencies(int count,StopList stopList)
    {
        Heapify<WordFrequency> heapify = new Heapify<WordFrequency>();

        return heapify.getTop(this.getWordFrequencies(stopList).values(), count);
    }

    public List<String> getListOfWords() {
        return getListOfWords(null);
    }
    public List<String> getListOfWords(StopList stopList) {
        List<String> words = new ArrayList<String>();
        words.addAll(Arrays.asList(TermNormalizer.stringToTerms(this.getText())));
        if(stopList != null)
        {
            Iterator<String> iterator = words.iterator();
            while (iterator.hasNext()) {
                String word = iterator.next(); // must be called before you can call iterator.remove()
                if(stopList.contains(word))
                {
                    iterator.remove();
                }
            }
        }
        return words;
    }
}
