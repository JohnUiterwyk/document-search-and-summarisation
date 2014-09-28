package inforet;

import inforet.controller.QueryController;
import inforet.controller.SummaryEvaluationController;

/**
 * Created by johnuiterwyk on 28/09/2014.
 */
public class summaryeval {
    public static void main(String[] args)
    {
        long startTime = System.currentTimeMillis();
        new SummaryEvaluationController(args);
        long endTime = System.currentTimeMillis();
        System.out.println("Running time: " + (endTime - startTime) + " ms");

    }
}
