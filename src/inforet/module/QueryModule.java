package inforet.module;

import inforet.model.*;
import inforet.util.Similarity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by johnuiterwyk on 20/09/2014.
 * This class will contain functionality regarding processing a query, and returning an arraylist of results
 *
 * TODO: rewrite this to to be less coupled.
 * TODO: Doc collection should be passed by reference into this class (dependency injection)
 *
 */
public class QueryModule
{
    private HashMap<Integer, QueryResult> results = new HashMap<Integer, QueryResult>();
    private ArrayList<TermInfo> terms = new ArrayList<TermInfo>();

    public QueryModule(String[] queryTerms, Model model )
    {

        int docCount = model.getDocumentCollection().getDocuments().size();

        for ( String term : queryTerms )
        {

            TermInfo termInfo = model.getLexicon().get(term);
            terms.add(termInfo);
            if (termInfo != null) {
                List<Posting> postings = model.getInvertedList().getPostings(termInfo);
                //output info
                for(Posting posting:postings)
                {
                    QueryResult result = results.get(posting.docIndex);
                    if(result == null) result = new QueryResult();
                    result.setDoc(model.getDocumentCollection().getDocumentByIndex(posting.docIndex, false));
                    float score = Similarity.GetBm25Score(term,docCount,posting.withinDocFrequency,termInfo.getDocumentFrequency(),result.getDoc().getWeight(),1.2f,.75f);
                    result.setSimilarityScore(result.getSimilarityScore()+score);
                }
            }
        }
        //now push it into a min heap
        for(QueryResult result:results.values())
        {
            //insert into min-heap
        }
    }

    public HashMap<Integer, QueryResult> getResults() {
        return results;
    }


}


