package inforet.module;

import inforet.model.QueryResult;
import inforet.util.ConsoleInput;
import inforet.util.LineWrapper;

import java.util.List;

/**
 * Created by johnuiterwyk on 28/09/2014.
 */
public class SummaryEvaluation
{
    private int nonQueryBiasedSummaryScore = 0;
    private int queryBiasedSummaryScore = 0;
    private int identicalSummaries = 0;
    public SummaryEvaluation(List<QueryResult> topResults)
    {
        ConsoleInput input = new ConsoleInput();
        System.out.println();
        System.out.println("### Document Summary Evaluation ###");
        System.out.println();
        System.out.println("Please evaluate the following summaries.");
        System.out.println("You will be presented with a document, followed by two summaries.");
        System.out.println("Please enter the number 1 or 2 to identify which summary you prefer.");
        input.pause("Press enter to begin the evaluation.");
        int count = 0;
        for(QueryResult result:topResults)
        {
            count++;
            System.out.println("### Document "+count+" ###");
//            System.out.println("Headline: ");
//            System.out.println(result.getDoc().getHeadline());
//            System.out.println("Text:");
//            System.out.println(result.getDoc().getText());
//            System.out.println("### END DOCUMENT ###");
//            System.out.println();
//            input.pause("Press enter to view summaries.");
            System.out.println("### Summaries ###");
            System.out.println();
            System.out.println("Summary 1:");
            LineWrapper.printWrappedLine(result.getSummaryNQB(), 70);
            System.out.println();
            System.out.println("Summary 2:");
            LineWrapper.printWrappedLine(result.getSummaryQB(), 70);
            System.out.println();
            if(result.getSummaryNQB().compareTo(result.getSummaryQB())!=0)
            {
                int choice = input.getIntRange(1,2,"Please enter the number 1 or 2 to indicate which summary you prefer:");
                if(choice == 1)
                {
                    nonQueryBiasedSummaryScore++;
                }else
                {
                    queryBiasedSummaryScore++;
                }
                System.out.println("Your choice has been recorded for document "+count+" of "+topResults.size()+".");
            }else
            {
                identicalSummaries++;
                System.out.println("Summaries are identical. Skipping evaluation.");

            }

            input.pause("Press enter evaluate the next document.");
        }
    }



    public void printEvaluationResults()
    {
        float totalVotes = nonQueryBiasedSummaryScore + queryBiasedSummaryScore;

        System.out.println("### Evaluation Report ###");
        System.out.printf("Non-query biased summary score: %d/%.0f (%.1f%%)\n",nonQueryBiasedSummaryScore,totalVotes,(100*nonQueryBiasedSummaryScore/totalVotes));
        System.out.printf("Query biased summary score: %d/%.0f (%.1f%%)\n",queryBiasedSummaryScore,totalVotes,(100*queryBiasedSummaryScore/totalVotes));
        System.out.printf("Identical summaries: %d/%.0f (%.1f%%)\n",identicalSummaries,totalVotes,(100*identicalSummaries/totalVotes));



    }
}
