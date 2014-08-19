package inforet.controller;

import inforet.module.IndexingModule;
import inforet.module.StopListModule;
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

    private ParsingModule parsingModule     = new ParsingModule();
    private StopListModule stopListModule = new StopListModule();
    private IndexingModule indexingModule   = new IndexingModule();
    private IndexArgs indexArgs             = new IndexArgs();

    /***
     * This controller constructor governs the whole Indexing Process.
     * It initialises a stopList if given, calls on a parser to retrieve the nextWord
     * to be indexed from the document collection and hands it over to the Lexicon & Inverted List
     * creation processes. Once there is nothing more to read from the document collection, the
     * Lexicon, Inverted List and Document Map is written out to "lexicon", "invlists", and "map" accordingly.
     *
     * This controller is coupled with :
     * - IndexingModule
     * - ParsingModule
     * - IndexFileManager
     * - MapFileManager
     * - IndexArgs
     * - StopListModule
     *
     * @param args
     */
    public IndexAppController(String[] args)
    {

        //parse the args
        indexArgs.parseArgs(args);

        //load the doc collection and get the first word
        parsingModule.loadFile(indexArgs.pathToDocsFile);
        if(indexArgs.useStopWords)
        {
            stopListModule.initStopList(indexArgs.pathToStopWordsFile);
        }
        //Get the next word from the document collection
        //The first word in this case.
        String term = parsingModule.getNextWord();

        //loop through the collection extract term/doc pairs
        while(term != null)
        {
            if(indexArgs.printIndexTerms)
            {
                System.out.println(term);
            }

            //Ignore entry if it is a stop word.
            if(!stopListModule.isEnabled() || !stopListModule.contains(term)){
                indexingModule.addTerm(term,parsingModule.getCurrentDocId());
            }
            term = parsingModule.getNextWord();
        }

        //save the map file
        MapFileManager mapFileManager = new MapFileManager();
        mapFileManager.saveDocIdMap(parsingModule.getDocIdMap());

        //save the lexicon and inverted list
        IndexFileManager indexFileManager = new IndexFileManager();
        indexFileManager.saveToDisk(indexingModule.getTerms());

        /**
        System.out.println("Term count: "+indexingModule.getTermCount());
        */
    }
}
