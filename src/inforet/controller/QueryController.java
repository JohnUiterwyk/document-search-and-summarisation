package inforet.controller;

import inforet.module.Posting;
import inforet.module.TermInfo;
import inforet.util.IndexFileManager;
import inforet.util.MapFileManager;
import inforet.util.QueryArgs;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

/**
 * Created by Daniel on 16/08/2014.
 */
public class QueryController {

    // Initialize all the necessary modules

    // This is yo big daddy controller ! I do everything !
    // So, what are my arguments.

    /***
     * Perform the query tasks upon instantiation of the controller.
     * @param args
     */
    public QueryController(String[] args){
        // Parse Args
        QueryArgs qargs = new QueryArgs();
        try {
            qargs.parseArgs(args);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.err.printf("Invalid Arguments received. Please check your arguments passed to ./query");
        }

        //Load the doc id map
        MapFileManager mapFileManager = new MapFileManager();
        List<String> docIds = mapFileManager.loadDocIdMap(qargs.mapPath);

        IndexFileManager indexFileManager = new IndexFileManager();
        Map<String,TermInfo> lexicon = indexFileManager.loadLexicon(qargs.lexiconPath);

        //Do the query
        for ( String term : qargs.query ){
            System.out.println("Term: "+term);
            TermInfo termInfo = lexicon.get(term);
            if (termInfo != null) {
                List<Posting> postings = indexFileManager.getPostings(termInfo,qargs.invlistPath);
                //output info
                System.out.println("Doc Freq: "+termInfo.getDocumentFrequency());
                int totalOccurances = 0;
                for(Posting posting:postings)
                {
                    totalOccurances+=posting.withinDocFrequency;
                    System.out.println("Doc: "+docIds.get(posting.docId)+" "+posting.withinDocFrequency);
                }
                System.out.println("Total occurances: "+totalOccurances);
            }else
            {

                System.out.println("Doc Freq: 0");
            }
        }

    }
}
