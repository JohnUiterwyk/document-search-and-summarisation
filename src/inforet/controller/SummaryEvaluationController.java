package inforet.controller;

import inforet.model.Model;
import inforet.model.QueryResult;
import inforet.module.DocSummary;
import inforet.module.QueryModule;
import inforet.module.SummaryEvaluation;
import inforet.module.TermNormalizer;
import inforet.util.ConsoleInput;
import inforet.util.QueryArgs;

import java.io.FileNotFoundException;
import java.util.List;

/**
 * Created by johnuiterwyk on 28/09/2014.
 */
public class SummaryEvaluationController {
    public SummaryEvaluationController(String[] args) {
        // Parse Args
        QueryArgs queryArgs = new QueryArgs();
        try {
            queryArgs.parseArgs(args);
        } catch (FileNotFoundException e) {
            //e.printStackTrace();
            System.err.println("Invalid Arguments received. Please check your arguments passed to ./search");
            return;
        }

        System.out.println("Please enter a search query:");
        queryArgs.queryString = new ConsoleInput().getString();

        // Run the Term Normaliser
        TermNormalizer normalizer = new TermNormalizer();
        String[] queryTerms = normalizer.transformedStringToTerms(queryArgs.queryString);

        // create the model
        Model model = new Model();
        model.loadCollectionFromMap(queryArgs.mapPath);
        model.loadLexicon(queryArgs.lexiconPath);
        model.loadInvertedList(queryArgs.invlistPath);
        if (queryArgs.stopListEnabled) {
            model.loadStopList(queryArgs.stopListPath);
        }
        model.setPathToCollection(queryArgs.collectionPath);

        //fetch the top results from the query

        QueryModule queryModule = new QueryModule();
        queryModule.doQuery(queryTerms, model, queryArgs.bm25Enabled);
        List<QueryResult> topResults = queryModule.getTopResult(queryArgs.maxResults);

        // retrieve the summaries
        DocSummary docSummary = new DocSummary();
        for (QueryResult result : topResults) {
            result.setDoc(model.getDocumentCollection().getDocumentByIndex(result.getDoc().getIndex(), true));
            result.setSummaryNQB(docSummary.getNonQueryBiasedSummary(result.getDoc(), model.getStopListModule()));
        }

        // perform evaluation
        SummaryEvaluation evaluation = new SummaryEvaluation(topResults);
        evaluation.printEvaluationResults();

    }
}
