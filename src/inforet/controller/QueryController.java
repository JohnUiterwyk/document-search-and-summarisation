package inforet.controller;

import inforet.ResultsView;
import inforet.model.DocumentCollection;
import inforet.model.Model;
import inforet.model.StopList;
import inforet.module.*;
import inforet.util.IndexFileManager;
import inforet.util.QueryArgs;

import java.io.FileNotFoundException;
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

        // Run the Term Normaliser
        TermNormalizer normalizer = new TermNormalizer();
        String[] queryTerms = normalizer.transformedStringToTerms(queryArgs.queryString);

        Model model = new Model();
        model.loadCollectionFromMap(queryArgs.mapPath);
        model.loadLexicon(queryArgs.lexiconPath);
        model.loadInvertedList(queryArgs.invlistPath);
        if(queryArgs.stopListEnabled)
        {
            model.loadStopList(queryArgs.stopListPath);
        }

        //fetch the

        QueryModule queryModule = new QueryModule();
        queryModule.doQuery(queryTerms, model);

        //Do the query

        ResultsView resultsView = new ResultsView();
        resultsView.printResults(queryModule.getSortedResultsList(),queryArgs.maxResults,queryArgs.queryLabel);

    }
}
