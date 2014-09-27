package inforet.module;

import inforet.model.Document;
import inforet.model.Sentence;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel on 21/09/2014.
 */
public class DocSummary {

    private int summaryLen = 1; //Sentence
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


    public String getNonQueryBiasedSummary ( Document doc ){
        //Find out what the document is about
        DocumentAnalysis docMeta = DocumentAnalysis.doAnalysis(doc);
        return luhnSummary(docMeta);
    }

    public String getQueryBiasedSummary ( Document doc, List<String> queryTerms ){
        //Find out what the document is about
        DocumentAnalysis docMeta = DocumentAnalysis.doAnalysis(doc);

        return null;
    }

    private String luhnSummary(DocumentAnalysis docMeta){
        String summary = "";
        // Identify top 3 terms used in the document
        // find the sentence with the highest weighted sum of the keywords found.
        // retrieve the sentence and return.

        docMeta.getWordRank().get(x);

        return summary;
    }

    private double sentenceValue(Sentence sentence, String[] keywords ){
        int keyValue = 0;
        double value = 0;

        //Weighted Sum of Keywords
        for ( int i = 0; i < keywords.length; i++ ){
            keyValue += ((sentence.getWordFreqRanking().indexOf(keywords[i]) ^ 2 ) / sentence.getWordCount());
        }

        return keyValue;
    }


}
