package inforet.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Daniel on 24/09/2014.
 */
public class Sentence {
    private String sentence;
    private Map<String, Integer> wordFrequency;
    private int paragraph;

    public Sentence() {
        this.sentence = "";
        this.wordFrequency = new HashMap<String, Integer>();
        this.paragraph = -1;
    }
    public Sentence(String sentence) {
        this.sentence = sentence;
        this.wordFrequency = new HashMap<String, Integer>();
        this.paragraph = -1;
    }
    public Sentence(String sentence, int para) {
        this.sentence = sentence;
        this.wordFrequency = new HashMap<String, Integer>();
        this.paragraph = para;
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
}
