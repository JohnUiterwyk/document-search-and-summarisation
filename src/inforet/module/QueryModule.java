package inforet.module;

import inforet.model.Document;
import inforet.model.DocumentCollection;
import inforet.util.IndexFileManager;
import inforet.util.MapFileManager;
import inforet.util.QueryArgs;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    private ArrayList<QueryResult> results = new ArrayList<QueryResult>();

    public QueryModule(QueryArgs queryArgs)
    {
        //Load the doc id map

        DocumentCollection documents = new DocumentCollection();
        documents.loadFromMapFile(queryArgs.mapPath);

        IndexFileManager indexFileManager = new IndexFileManager();
        Map<String,TermInfo> lexicon = indexFileManager.loadLexicon(queryArgs.lexiconPath);

        // Run the Term Normaliser
        TermNormalizer normalizer = new TermNormalizer();
        String[] queryTerms = normalizer.stringToTerms(queryArgs.queryString);

        for ( String term : queryTerms ){
            term = normalizer.transform(term);

            System.out.println("Term: "+term);
            TermInfo termInfo = lexicon.get(term);
            if (termInfo != null) {
                List<Posting> postings = indexFileManager.getPostings(termInfo,queryArgs.invlistPath);
                //output info
                System.out.println("Doc Freq: "+termInfo.getDocumentFrequency());
                int totalOccurances = 0;
                for(Posting posting:postings)
                {
                    totalOccurances+=posting.withinDocFrequency;
                    Document doc = documents.getDocumentByIndex(posting.docId, false);
                    System.out.println("Doc: "+doc.getIdentifier()+" "+posting.withinDocFrequency);
                }
                System.out.println("Total occurances: "+totalOccurances);
            }else
            {

                System.out.println("Doc Freq: 0");
            }
        }
    }


}


