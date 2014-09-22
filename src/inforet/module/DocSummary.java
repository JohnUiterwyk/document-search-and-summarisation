package inforet.module;

import inforet.model.Document;

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
        String summary;
        return summary;
    }

    public String getQueryBiasedSummary ( Document doc, List<String> queryTerms ){
        return null;
    }

}
