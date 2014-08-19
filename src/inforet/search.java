package inforet;

import inforet.controller.QueryController;

/**
 * Created by johnuiterwyk on 6/08/2014.
 *
 * This file is the main entry point for the search component
 */

public class search {

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();

        new QueryController(args);
        /** Uncomment for Timing
        long endTime = System.currentTimeMillis();
        System.out.println("Running time:" + (endTime - startTime) + " milliseconds");
        */
    }
}
