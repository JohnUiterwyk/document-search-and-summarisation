package inforet.module;

import inforet.model.Document;
import inforet.model.Sentence;
import javafx.collections.transformation.SortedList;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.*;

/**
 * Created by Daniel on 24/09/2014.
 */
public class DocumentAnalysis {

    //Ascending Ranked List of most used words
    private List<String> wordRank       = null;
    //MetaData Annotated Sentences
    private List<Sentence> sentences    = null;

    public DocumentAnalysis() {
        this.wordRank = new ArrayList<String>();
        this.sentences = new ArrayList<Sentence>();
    }


    public static DocumentAnalysis doAnalysis (Document doc){
        DocumentAnalysis docAnalysis = new DocumentAnalysis();

        //Lets begin by ranking all the words used in descending order of use count.
        docAnalysis.wordRank = docAnalysis.doWordRank(doc);
        //Extract the sentences and perform sentence level analysis
        docAnalysis.sentences = doSentenceAnalysis(doc.getBodyText());
        return docAnalysis;
    }
///////////// Document Word Ranking /////////////////////////
    public List<String> doWordRank ( Document doc ){
        List<String> wordRank = new ArrayList<String>();
        for ( String key : doc.getTermFrequency().keySet() ){
            insertSortWordRank(key, doc); // Ascending List
        }
        Collections.reverse(this.wordRank); // Descending List
        return wordRank;
    }

    private void insertSortWordRank (String key, Document doc){
        if( doc.getTermFrequency().get(key) >= doc.getTermFrequency().get(wordRank.get(wordRank.size() - 1 ) ) ){
            this.wordRank.add(key);
        }
        else {
            String temp = this.wordRank.get(wordRank.size() - 1);
            this.wordRank.set((wordRank.size() - 1), key);
            this.wordRank.add(temp);
        }
    }
/////////// Sentence Analysis ///////////////

    private List<Sentence> doSentenceAnalysis ( String text ){
        List<Sentence> sentenceList = new ArrayList<Sentence>();
        // Read one character at a time until the end of text,
        // spliting the sentences and adding metadata as we go.

        StringCharacterIterator sci = new StringCharacterIterator(text);
        StringBuilder str = new StringBuilder();
        int periodCounter = 0;
        int newlineCounter = 0;

        //Iterate through the text until the end is reached
        for (char ch = sci.first(); ch != CharacterIterator.DONE; ch = sci.next()){

            //TODO Filter


        }

        return sentenceList;
    }

}
