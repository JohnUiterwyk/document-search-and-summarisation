package inforet.controller;

import inforet.module.IndexingModule;
import inforet.util.IndexArgs;
import inforet.module.ParsingModule;
import inforet.util.IndexFileManager;
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



        //parse the args
        indexArgs.parseArgs(args);

        //load the doc collection and get the first word
        parsingModule.loadFile(indexArgs.pathToDocsFile);
        String term = parsingModule.getNextWord();

        //loop through the collection extract term/doc pairs
        while(term != null)
        {
            if(indexArgs.printIndexTerms)
            {
                System.out.println(term);
            }
            indexingModule.addTerm(term,parsingModule.getCurrentDocId());
            term = parsingModule.getNextWord();
        }

        //save the map file
        MapFileManager mapFileManager = new MapFileManager();
        mapFileManager.saveDocIdMap(parsingModule.getDocIdMap());

        //save the lexicon and inverted list
        IndexFileManager indexFileManager = new IndexFileManager();
        indexFileManager.saveToDisk(indexingModule.getTerms());


        System.out.println("Term count: "+indexingModule.getTermCount());

    }
}
