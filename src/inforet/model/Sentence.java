package inforet.model;


import inforet.module.TermNormalizer;
import inforet.util.Heapify;

import java.util.*;

/**
 * Created by Daniel on 24/09/2014.
 */
public class Sentence extends TextContent implements Comparable<Sentence>{

    public static int MIN_LENGTH = 3;

    private int paragraph;
    public float score = 0;

    public Sentence() {
        this("");
    }
    public Sentence(String sentence) {
        this(sentence,-1);
    }
    public Sentence(String sentence, int paragraphNumber) {
        this.append(sentence);
        this.paragraph = paragraphNumber;
    }

    public int getParagraph() {
        return paragraph;
    }

    public void setParagraph(int paragraph) {
        this.paragraph = paragraph;
    }




    public static boolean isSentenceTerminator(char c){

        // Note : This isn't a proper way to determine sentence boundaries, but will do good enough.
        // See the OpenNLP (natural language processing) project for the proper way.
        // http://opennlp.apache.org/
        switch(c){
            case '.' : return true;
            case '!' : return true;
            case '?' : return true;
            default  : return false;
        }

    }

    @Override
    public int compareTo(Sentence compareSentence)
    {
        if(this.score < compareSentence.score)
        {
            return -1;
        }else if(this.score > compareSentence.score)
        {
            return 1;
        }
        return 0;
    }
}
