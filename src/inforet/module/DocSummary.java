package inforet.module;

import inforet.model.Document;
import inforet.model.Sentence;
import inforet.model.StopList;
import inforet.model.WordFrequency;
import inforet.util.Heapify;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Daniel on 21/09/2014.
 */
public class DocSummary {

    private int summaryLen =1; //Sentence
    public DocSummary() {
    }

    /**
     * 3) "Advance Information Retrieval Feature"
     *
     *
     *  Extend your search system to produce short document
        summaries with each answer item returned in a search results list.
        You must implement at least two summary creation approaches: one of these should
        be based on query-biased information, taking the user's query into account. The
         second should include evidence other than the user's current query.
     *  ************************************************************************************
     *  This will implement Extraction based summary as follows :
     *  a) Non-Query Biased = Luhn 1958, Summary sentence extraction based on the number of keywords occuring in a sentence.
     *  b) Query Biased =
     */


    public String getNonQueryBiasedSummary ( Document doc, StopList stopList){
        //Find out what the document is about
        return luhnSummary(doc, stopList);
    }

    public String getQueryBiasedSummary ( Document doc, List<String> queryTerms ){
        //Find out what the document is about

        return null;
    }

    private String luhnSummary(Document doc, StopList stopList){
        // get the top X used words in the document
        List<WordFrequency> docTopWords = doc.getTopWordFrequencies(3, stopList);

        //get the list of sentences for the doc
        List<Sentence> docSentences = doc.getSentenceList();

        // loop through the sentences
        for(Sentence sentence : docSentences)
        {
            //get the fill hashmap of words/freq for a sentence
            HashMap<String,WordFrequency> wordFreqMap = sentence.getWordFrequencies(stopList);

            // now for each of the top X words in the doc
            for(WordFrequency docTopWord :docTopWords)
            {
                // check if its in the sentence hash map
                WordFrequency sentenceWordFreq = wordFreqMap.get(docTopWord.word);

                //if we find the word in the sentence
                if(sentenceWordFreq != null)
                {
                    //add a the weighted frequency to the score
                    sentence.score += (float)sentenceWordFreq.frequency / docTopWord.frequency;
                }
            }
        }

        // build summary using the top X sentences
        StringBuilder summary = new StringBuilder();
        Heapify<Sentence> heapify = new Heapify<Sentence>();
        for(Sentence sentence : heapify.getTop(docSentences,summaryLen))
        {
            summary.append(sentence.getText()+'\n');
        }
        // Identify top 3 terms used in the document
        // find the sentence with the highest weighted sum of the keywords found.
        // retrieve the sentence and return.
//        for ( ) //TOp X terms
//
//        docMeta.getWordRank().get(x);

        return summary.toString();
    }

    private double sentenceValue(Sentence sentence, String[] keywords ){
        int keyValue = 0;
        double value = 0;

        //Weighted Sum of Keywords
        for ( int i = 0; i < keywords.length; i++ ){
            //keyValue += ((sentence.getWordFreqRanking().indexOf(keywords[i]) ^ 2 ) / sentence.getWordCount());
        }

        return keyValue;
    }


}
