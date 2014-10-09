package inforet.module;

import inforet.model.Document;
import inforet.model.Sentence;
import inforet.model.StopList;
import inforet.model.WordFrequency;
import inforet.util.Heapify;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Daniel on 21/09/2014.
 */
public class DocSummary {

    private int summaryLen = 3; //Sentence
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
     *  b) Query Biased = Adapted Luhn Extraction sentence score algorithm
     */


    public String getNonQueryBiasedSummary ( Document doc, StopList stopList){
        //Find out what the document is about
        return luhnSummary(doc, stopList);
    }

    public String getQueryBiasedSummary ( Document doc, StopList stopList, List<String> queryTerms ){
        //Find out what the document is about

        return queryBiasedLuhnSummary(doc, stopList, queryTerms);
    }
    //Non-Query Biased Summary
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
                if(sentenceWordFreq != null && sentence.getText().length() > 5)
                {
                    //add a the weighted frequency to the
                    // The sum of keywords found in the sentence weighted against the length of the sentece.
                    // Inspired by Luhn's Keyword based Extraction Summary ( Luhn '58)
                    sentence.score += (float)((sentenceWordFreq.frequency)^2) / sentence.getWordCount();
                }
            }
        }

        // build summary using the top X sentences
        StringBuilder summary = new StringBuilder();
        Heapify<Sentence> heapify = new Heapify<Sentence>();
        for(Sentence sentence : heapify.getTop(docSentences,summaryLen)) {
            summary.append(sentence.getText() + '\n');
        }

        return summary.toString();
    }


    private String queryBiasedLuhnSummary(Document doc, StopList stopList, List<String> queryTerms){
        //get the list of sentences for the doc
        List<Sentence> docSentences = doc.getSentenceList();

        //Term Normaliser & Stoplist
        List<String> cleanQuery = new ArrayList<String>();
        for ( String s : queryTerms ){
            if ( stopList.contains(s) ){
                continue;
            }

            cleanQuery.add(TermNormalizer.transform(s));
        }
        queryTerms = cleanQuery;

        // loop through the sentences
        for(Sentence sentence : docSentences)
        {
            //get the fill hashmap of words/freq for a sentence
            HashMap<String,WordFrequency> wordFreqMap = sentence.getWordFrequencies(stopList);

            // now for each query term
            for(String term : queryTerms)
            {
                // check if its in the sentence hash map
                WordFrequency sentenceWordFreq = wordFreqMap.get(term);

                //if we find the word in the sentence
                if(sentenceWordFreq != null)
                {
                    //add a the weighted frequency to the
                    // The sum of keywords found in the sentence weighted against the length of the sentece.
                    // Inspired by Luhn's Keyword based Extraction Summary ( Luhn '58)
                    sentence.score += (float)((sentenceWordFreq.frequency)^2) / sentence.getWordCount();
                }
            }
        }

        // build summary using the top X sentences
        StringBuilder summary = new StringBuilder();
        Heapify<Sentence> heapify = new Heapify<Sentence>();
        for(Sentence sentence : heapify.getTop(docSentences,summaryLen)) {
            summary.append(sentence.getText() + '\n');
        }

        return summary.toString();
    }
}
