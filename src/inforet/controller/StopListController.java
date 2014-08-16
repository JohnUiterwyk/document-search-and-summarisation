package inforet.controller;

import inforet.module.StopList;

/**
 * Created by Daniel on 12/08/2014.
 */
public class StopListController {

    private static String stopListPath = "";

    public static String getStopListPath() {
        return stopListPath;
    }

    public static void setStopListPath(String stopListPath) {
        StopListController.stopListPath = stopListPath;
    }

    private static Boolean enabled = false;
    public static void setEnabled() {
        StopListController.enabled = true;
    }
    public static Boolean isEnabled() {
        return enabled;
    }

    //Expose only one method

    /***
     * This method queries the StopList for the queried string.
     * It attempts to self initialize & load the stopList from file the first time
     * this method is called.
     *
     * The stoplist file must be set via StopListController.setStopListPath(String)
     * prior to calling this method. The input file must be a newline deliminated for each stop-word.
     *
     * @param query
     * @return True if the query string matches a StopWord, False for otherwise.
     */
    public static boolean isStopWord(String query){
        try {
            return StopList.getInstance().getStopList().containsKey( StopList.hashKey(query) );
        } catch (Exception e) {
            System.out.printf("No Existing StopList Found. Will attempt to initialize.");
            return StopList.getInstance(getStopListPath()).getStopList().containsKey( StopList.hashKey(query) );
        }
    }
}
