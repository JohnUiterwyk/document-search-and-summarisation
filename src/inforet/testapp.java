package inforet;

import inforet.test.TestController;

/**
 * Created by johnuiterwyk on 23/09/2014.
 */
public class testapp {
    public static void main(String[] args)
    {
        long startTime = System.currentTimeMillis();
        new TestController();
        long endTime = System.currentTimeMillis();
        System.out.println("Running time: " + (endTime - startTime) + " ms");

    }
}
