package inforet.controller;

import inforet.module.query.LoadLexicon;
import inforet.module.query.LoadMap;
import inforet.util.QueryArgs;

import java.io.FileNotFoundException;

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

        //Load the files
        LoadLexicon lex = new LoadLexicon();
        LoadMap     map = new LoadMap();

        //Do the query
        for ( String str : qargs.query ){
            //TODO : Perform query over each 'str' term
        }

    }
}
