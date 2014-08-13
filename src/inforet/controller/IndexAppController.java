package inforet.controller;

import inforet.util.IndexArgs;
import inforet.module.ParsingModule;

/**
 * Created by johnuiterwyk on 11/08/2014.
 *
 * References:
 * http://stackoverflow.com/questions/180158/how-do-i-time-a-methods-execution-in-java
 */
public class IndexAppController {

    private ParsingModule parser = new ParsingModule();
    private IndexArgs indexArgs = new IndexArgs();
    public IndexAppController(String[] args)
    {

        long startTime = System.currentTimeMillis();


        indexArgs.parseArgs(args);

        parser.loadFile(indexArgs.pathToDocsFile);

        parser.parse(indexArgs.printIndexTerms);


        long endTime = System.currentTimeMillis();
        System.out.println("Running time:" + (endTime - startTime) + " milliseconds");

    }
}
