package inforet.util;


import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by Daniel on 16/08/2014.
 */
public class QueryArgs {

    public Boolean bm25Enabled = false;
    public String queryLabel = "";
    public Integer maxResults = 10;

    public String lexiconPath = "";
    public String invlistPath = "";
    public String mapPath     = "";
    public String collectionPath = "";

    public Boolean stopListEnabled = false;
    public String stopListPath = "";

    public String queryString = null;

    public Boolean printSummary = false;


    //Configurations
    private static int minArgs = 3;

    public QueryArgs() {
        //Question:
        // Another Nice empty constructor :) Why aren't we just using statics. It's faster...
        //
        // Answer:
        // Static variables represent global state.
        // They are hard to reason about, hard to test, and result in tight coupling
        //
        // If you use them freely, one day they will drive you insane
        // as you waste days trying to solve an error that is being caused
        // by code, that you didn't write, using your pretty little static classes
        // and secretly changing their state.
        //
        // Its pretty much the opposite of OOP, see here my young padawan:
        //
        // http://stackoverflow.com/questions/7026507/why-are-static-variables-considered-evils
    }

    /***
     * Given the main(args) this parses the ./query program input,
     * setting the variables in this corresponding object.
     *
     * Usage :
     * qa = new QueryArgs();
     * qa.parseArgs(args);
     * qa.<lexiconPath|invlistPath...>;
     *
     * @param args commandline arguments
     * @throws FileNotFoundException
     */
    public void parseArgs(String[] args) throws FileNotFoundException
    {
        for ( int i = 0; i < args.length; i++)
        {
            String arg = args[i];
            if(arg.equals("-BM25"))
            {
                bm25Enabled = true;

            }else if(arg.equals("-q"))
            {
               i++;
               queryLabel = args[i];
            }else if(arg.equals("-n"))
            {
                i++;
                maxResults = Integer.parseInt(args[i]);
            }else if(arg.equals("-l"))
            {
                i++;
                lexiconPath = args[i];
                checkFile(lexiconPath);
            }else if(arg.equals("-i"))
            {
                i++;
                invlistPath = args[i];
                checkFile(invlistPath);
            }else if(arg.equals("-m"))
            {
                i++;
                mapPath = args[i];
                checkFile(mapPath);
            }else if(arg.equals("-s"))
            {
                i++;
                stopListEnabled = true;
                stopListPath = args[i];
                checkFile(stopListPath);
            }else if(arg.equals("-d"))
            {
                i++;
                collectionPath = args[i];
                checkFile(collectionPath);
            }else if(arg.equals("-p"))
            {
                printSummary = true;
            }else
            {
                StringBuilder sb = new StringBuilder();
                for ( ; i < args.length; i++){
                    sb.append(args[i]);
                    sb.append(" ");
                }
                queryString = sb.toString();
            }

        }


        //TODO: run a check to see if all required args are included


    }

    public Boolean checkFile(String filePath) throws FileNotFoundException
    {
        if ( ! new File(filePath).exists() ){ //Map file exists?
            usage();
            throw new FileNotFoundException(filePath + "file not found.");
        }else
        {
            return true;
        }
    }

    public String parseQueryString(int firstTerm,String args[])
    {
        //Set the query terms
        StringBuilder sb = new StringBuilder();
        for ( int i = firstTerm; i < args.length; i++){
            sb.append(args[i]);
            sb.append(" ");
        }
        return sb.toString();
    }

    /***
     * Prints the usage instructions to standard out.
     */
    private void usage(){
        StringBuilder sb = new StringBuilder();
        sb.append("Usage : \n");
        sb.append("java search -BM25 -q <query-label> -n <num-results> -l <lexicon> -i <invlists> " +
                "-m <map> [-s <stoplist>] <queryterm-1> [<queryterm-2> ...  <queryterm-N>]");
        System.out.println(sb.toString());
    }
}
