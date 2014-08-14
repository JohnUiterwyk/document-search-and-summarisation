package inforet.controller;

import inforet.module.IndexingModule;
import inforet.util.IndexArgs;
import inforet.module.ParsingModule;
import inforet.util.MapFileManager;

/**
 * Created by johnuiterwyk on 11/08/2014.
 *
 * References:
 * http://stackoverflow.com/questions/180158/how-do-i-time-a-methods-execution-in-java
 */
public class IndexAppController {

    private ParsingModule parsingModule = new ParsingModule();
    private IndexingModule indexingModule = new IndexingModule();
    private IndexArgs indexArgs = new IndexArgs();

    public IndexAppController(String[] args)
    {

        long startTime = System.currentTimeMillis();

        indexArgs.parseArgs(args);
        parsingModule.loadFile(indexArgs.pathToDocsFile);
        String term = parsingModule.getNextWord();
        while(term != null)
        {
            if(indexArgs.printIndexTerms)
            {
                System.out.println(term);
            }
            indexingModule.addTerm(term,parsingModule.getCurrentDocId());
            term = parsingModule.getNextWord();
        }

        MapFileManager mapFileManager = new MapFileManager();
        mapFileManager.saveDocIdMap(parsingModule.getDocIdMap());


        long endTime = System.currentTimeMillis();
        System.out.println("Term count: "+indexingModule.getTermCount());
        System.out.println("Running time:" + (endTime - startTime) + " milliseconds");

    }
}
