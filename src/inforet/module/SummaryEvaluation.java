package inforet.module;

import inforet.model.QueryResult;
import inforet.util.ConsoleInput;

import java.util.List;

/**
 * Created by johnuiterwyk on 28/09/2014.
 */
public class SummaryEvaluation
{
    private int nonQueryBiasedSummaryScore = 0;
    private int queryBiasedSummaryScore = 0;
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
            System.out.println("Headline: ");
            System.out.println(result.getDoc().getHeadline());
            System.out.println("Text:");
            System.out.println(result.getDoc().getText());
            System.out.println("### END DOCUMENT ###");
            System.out.println();
            input.pause("Press enter to view summaries.");
            System.out.println("### Summaries ###");
            System.out.println();
            System.out.println("Summary 1:");
            printWrappedLine(result.getSummaryNQB(),70);
            System.out.println();
            System.out.println("Summary 2:");
            printWrappedLine("I love lamp.",70);
            System.out.println();
            int choice = input.getIntRange(1,2,"Please enter the number 1 or 2 to indicate which summary you prefer:");
            if(choice == 1)
            {
                nonQueryBiasedSummaryScore++;
            }else
            {
                queryBiasedSummaryScore++;
            }
            System.out.println("Your choice has been recorded for document "+count+" of "+topResults.size()+".");
            input.pause("Press enter evaluate the next document.");
        }
    }

    /***
     * prints a string to the console, with automatic line wrapping at a certain number of characters
     * This is based on the following stack overflow post:
     * http://stackoverflow.com/questions/4212675/
     *
     * @param line
     */
    public void printWrappedLine(String line, int maxChar)
    {
        StringBuilder sb = new StringBuilder(line);

        int i = 0;
        while (i + maxChar < sb.length() && (i = sb.lastIndexOf(" ", i + maxChar)) != -1) {
            sb.replace(i, i + 1, "\n");
        }

        System.out.println(sb.toString());
    }

    public void printEvaluationResults()
    {
        float totalVotes = nonQueryBiasedSummaryScore + queryBiasedSummaryScore;

        System.out.println("### Evaluation Report ###");
        System.out.printf("Non-query biased summary score: %d/%.0f (%.1f%%)\n",nonQueryBiasedSummaryScore,totalVotes,(100*nonQueryBiasedSummaryScore/totalVotes));
        System.out.printf("Query biased summary score: %d/%.0f (%.1f%%)\n",queryBiasedSummaryScore,totalVotes,(100*queryBiasedSummaryScore/totalVotes));



    }
}
