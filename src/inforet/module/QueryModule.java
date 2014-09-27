package inforet.module;

import inforet.model.*;
import inforet.util.Heapify;
import inforet.util.Similarity;

import java.util.*;

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

    public QueryModule()
    {

    }

    /***
     * Perform a query on th
     * @param queryTerms
     * @param model
     */
    public void doQuery(String[] queryTerms, Model model, Boolean useBm25)
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
                    // try to fetch the doc result from the results list first using the doc index
                    QueryResult result = results.get(posting.docIndex);

                    // if the result is not found, create a new result
                    if(result == null)
                    {
                        result = new QueryResult();
                    }
                    // get the doc from the collection (without text content) and store reference in result
                    Document doc = model.getDocumentCollection().getDocumentByIndex(posting.docIndex, false);
                    result.setDoc(doc);

                    // calc BM25 for term/doc pair, and add to the accumulated score so far
                    if(useBm25)
                    {
                        float score = Similarity.GetBm25Score(term,docCount,posting.withinDocFrequency,termInfo.getDocumentFrequency(),result.getDoc().getLengthWeight(),1.2f,.75f);
                        result.setSimilarityScore(result.getSimilarityScore()+score);
                    }

                    // put the result (back) into the hash map
                    results.put(posting.docIndex,result);
                }
            }
        }
    }


    public List<QueryResult> getSortedResultsList()
    {
        //todo: replace insertion sort with min heap
        ArrayList<QueryResult> sortedResults = new ArrayList<QueryResult>(results.values());
        Collections.sort(sortedResults);
        Collections.reverse(sortedResults);
        return sortedResults;
    }

    public List<QueryResult> getTopResult(int resultCount)
    {
        Heapify<QueryResult> heapify = new Heapify<QueryResult>();
        return heapify.getTop(results.values(),resultCount);
    }

    public HashMap<Integer, QueryResult> getResultsMap() {
        return results;
    }

}


