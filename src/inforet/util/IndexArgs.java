package inforet.util;

import inforet.controller.StopListController;

/**
 * Created by johnuiterwyk on 11/08/2014.
 * AppConfig parses the command line arguments into an easy to use object
 *
 *
 */
public class IndexArgs {

    public Boolean printIndexTerms = false;
    public Boolean useStopWords = false;

    public String pathToDocsFile = "";
    public String pathToStopWordsFile = "";
    public IndexArgs()
    {

    }

    public void parseArgs(String[] args)
    {
        for (int i = 0; i < args.length; i++)
        {
            String arg = args[i];

            if(i == args.length -1)
            {
                this.pathToDocsFile = arg;
            }else if(arg.equals("-p"))
            {
                this.printIndexTerms = true;
            }else if(arg.equals("-s"))
            {
                this.useStopWords = true;
                i++;
                if(i < args.length - 1 )
                {
                    this.pathToStopWordsFile = args[i];

                    //Set the Path to the StopList for the StopListController
                    StopListController.setStopListPath(pathToStopWordsFile);
                    StopListController.setEnabled();
                }else
                {
                    //TODO:throw invalid arguments error
                }
            }

        }
    }
}
