package inforet.controller;

import inforet.module.Posting;
import inforet.module.QueryModule;
import inforet.module.TermInfo;
import inforet.module.TermNormalizer;
import inforet.util.IndexFileManager;
import inforet.util.MapFileManager;
import inforet.util.QueryArgs;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by Daniel on 16/08/2014.
 */
public class QueryController {

    // Initialize all the necessary modules

    // This is yo big daddy controller ! I do everything !
    // So, what are my arguments.

    /***
     * Perform the query tasks upon instantiation of the controller.
     * This parses the query into a usable bag of words upon which a query of each query term is done
     * against the inverted index.
     *
     * @param args
     */
    public QueryController(String[] args){
        // Parse Args
        QueryArgs queryArgs = new QueryArgs();
        try {
            queryArgs.parseArgs(args);
        } catch (FileNotFoundException e) {
            //e.printStackTrace();
            System.err.println("Invalid Arguments received. Please check your arguments passed to ./search");
            return;
        }



        QueryModule queryModule = new QueryModule(queryArgs);
        //Do the query


    }
}
