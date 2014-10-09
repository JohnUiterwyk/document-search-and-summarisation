package inforet.view;

import inforet.model.QueryResult;
import inforet.module.DocSummary;

import java.util.List;

/**
 * Created by johnuiterwyk on 27/09/2014.
 */
public class ResultsView
{

    public void printResults(List<QueryResult> results, String label)
    {
        for(int i=0;i< results.size(); i++)
        {
            QueryResult result = results.get(i);
            this.printResultDetails(result,label,i+1);
        }
    }
    public void printResultsWithSummary(List<QueryResult> results, String label)
    {
        for(int i=0;i< results.size(); i++)
        {
            QueryResult result = results.get(i);
            this.printResultDetails(result,label,i+1);
            System.out.println("Non Query Biased Summary:");
            System.out.println(result.getSummaryNQB());
            System.out.println("Query Biased Summary:");
            System.out.println(result.getSummaryQB());
        }
    }

    private void printResultDetails(QueryResult result, String label, int rank)
    {
        System.out.printf("%s %s %d %3.3f\n",label,result.getDoc().getIdentifier(),rank,result.getSimilarityScore());
    }
}
