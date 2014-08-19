package inforet;

import inforet.controller.IndexAppController;

import java.util.concurrent.TimeUnit;

/**
 * Created by johnuiterwyk on 6/08/2014.
 *
 * This file is the main entry point for the search component
 */


public class index {

    public static void main(String[] args)
    {
        long startTime = System.currentTimeMillis();

        new IndexAppController(args); //Let it begin

        //Print out some timing information
        /** Uncomment for Timing
        long endTime = System.currentTimeMillis();
        System.out.println("Running time:" + (endTime - startTime) + " milliseconds");
        long millis = (endTime - startTime);
        System.out.printf("\n\n" + String.format("%d min, %d sec \n",
                                                  TimeUnit.MILLISECONDS.toMinutes(millis),
                                                  TimeUnit.MILLISECONDS.toSeconds(millis) -
                                                  TimeUnit.MINUTES.toSeconds
                                                  (TimeUnit.MILLISECONDS.toMinutes(millis))
        ));

         */
    }
}
