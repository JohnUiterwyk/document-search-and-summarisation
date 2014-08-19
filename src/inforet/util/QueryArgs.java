package inforet.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel on 16/08/2014.
 */
public class QueryArgs {

    public String lexiconPath = "";
    public String invlistPath = "";
    public String mapPath     = "";
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
    public void parseArgs(String[] args) throws FileNotFoundException {

        //Error Checking - Checking existance only for now
        if ( args.length < minArgs ){
            usage();
        }
        else if ( ! new File(args[0]).exists() ){ //Lexicon file exists?
            usage();
            throw new FileNotFoundException("arg[0] file not found :" + args[0]);
        }
        else if ( ! new File(args[1]).exists() ){ //InvList file exists?
            usage();
            throw new FileNotFoundException("arg[1] file not found :" + args[1]);
        }
        else if ( ! new File(args[2]).exists() ){ //Map file exists?
            usage();
            throw new FileNotFoundException("arg[2] file not found :" + args[2]);
        }


        //Set the Files
        lexiconPath = args[0];
        invlistPath = args[1];
        mapPath     = args[2];

        //Set the query terms
        queryString = new String();
        StringBuilder sb = new StringBuilder();
        for ( int i = 3; i < args.length; i++){
            sb.append(args[i]);
            sb.append(" ");
        }
        queryString = sb.toString();
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
