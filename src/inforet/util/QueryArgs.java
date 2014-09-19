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
    public String stopListPath = "";

    public String queryString = null;


    //Configurations
    private static int minArgs = 3;

    public QueryArgs() {
        //Another Nice empty constructor :) Why aren't we just using statics. It's faster...
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
                stopListPath = args[i];
                checkFile(stopListPath);
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


        //Set the Files


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
        sb.append("java ./search <lexicon> <invlist> <map> query_terms...");
        System.out.println(sb.toString());
    }
}
