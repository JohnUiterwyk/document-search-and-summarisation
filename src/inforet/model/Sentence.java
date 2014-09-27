package inforet.model;


import java.util.*;

/**
 * Created by Daniel on 24/09/2014.
 */
public class Sentence {
    private String sentence;
    private Map<String, Integer> wordFrequency;
    private List<String> wordFreqRanking;
    private int wordCount;
    private int paragraph;

    public Sentence() {
        this("");
    }
    public Sentence(String sentence) {
        this(sentence,-1);
    }
    public Sentence(String sentence, int paragraphNumber) {
        this.sentence = sentence;
        this.paragraph = paragraphNumber;
        this.wordFrequency = new HashMap<String, Integer>();
        if (!sentence.isEmpty()){
            this.identifyWordFrequency();
            this.doWordFreqRanking();
        }
    }

    public String getSentence() {
        return sentence;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }

    public Map<String, Integer> getWordFrequency() {
        return wordFrequency;
    }

    public void setWordFrequency(Map<String, Integer> wordFrequency) {
        this.wordFrequency = wordFrequency;
    }

    public int getParagraph() {
        return paragraph;
    }

    public void setParagraph(int paragraph) {
        this.paragraph = paragraph;
    }

    public List<String> getWordFreqRanking() {
        return wordFreqRanking;
    }

    public int getWordCount() {
        return wordCount;
    }

////// Functions //////////////////////////////////////////////////////////////////////////////////////////////////


    private void identifyWordFrequency(){
        String[] words = sentence.split("[\\s\\.!\\?]+"); // Split on whitespace & .!?
        //TODO : Strip out stop words
        this.wordCount = words.length;
        for( String word : words ){
            if ( wordFrequency.containsKey(word) ){
                int value = wordFrequency.get(word);
                wordFrequency.replace(word, value++);
            }
            else {
                wordFrequency.put(word, 1); // New entry.
            }
        }
    }

    private void doWordFreqRanking(){
        this.wordFreqRanking = new ArrayList<String>();

        for ( String key : wordFrequency.keySet() ){
            insertSortWordRank(key); // Ascending List
        }
        Collections.reverse(this.wordFreqRanking); // Descending List
    }
    //TODO SORTING IS BROKEN !!! FIXME !!!

    private void insertSortWordRank (String key){
        if( wordFrequency.get(key) >= wordFrequency.get(wordFreqRanking.get(wordFreqRanking.size() - 1)) ){
            this.wordFreqRanking.add(key);
        }
        else {
            String temp = this.wordFreqRanking.get(wordFreqRanking.size() - 1);
            this.wordFreqRanking.set((wordFreqRanking.size() - 1), key);
            this.wordFreqRanking.add(temp);
        }
    }
}
