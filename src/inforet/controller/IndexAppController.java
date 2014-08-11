package inforet.controller;

import inforet.util.IndexArgs;
import inforet.module.ParsingModule;

/**
 * Created by johnuiterwyk on 11/08/2014.
 */
public class IndexAppController {

    private ParsingModule parser = new ParsingModule();
    private IndexArgs indexArgs = new IndexArgs();
    public IndexAppController(String[] args)
    {
        this.indexArgs.parseArgs(args);
        parser.loadFile(indexArgs.pathToDocsFile);
        parser.parse();

    }
}
