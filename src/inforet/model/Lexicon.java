package inforet.model;

import inforet.module.TermInfo;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by johnuiterwyk on 20/09/2014.
 */
public class Lexicon {

    private static String LexiconFileName = "lexicon";
    private  Map<String,TermInfo> lexicon = new HashMap<String, TermInfo>();

    public TermInfo get(String term)
    {
        return lexicon.get(term);
    }
    /**
     * load a lexicon file
     * @param pathToLexicon the path to the lexicon file to load
     * @return a hash map of term, TermInfo
     */
    public void loadLexicon(String pathToLexicon)
    {

        //load the file
        FileReader fileReader = null;
        try
        {
            fileReader  = new FileReader(pathToLexicon);
        }catch (FileNotFoundException ex)
        {
            System.err.println(pathToLexicon+" not found.");
        }
        BufferedReader reader = new BufferedReader(fileReader);
        String line = null;

        //read the first line
        try
        {
            line = reader.readLine();
        }catch (IOException ex)
        {
            System.err.println("IO Error reading "+pathToLexicon);
        }

        //loop through the lines, building the lexicon list
        while(line != null)
        {
            String[] lineData =  line.split(",");
            TermInfo termInfo = new TermInfo();
            String term = lineData[0];
            termInfo.setDocumentFrequency(Integer.valueOf(lineData[1]));
            termInfo.setInvListFilePosition(Long.valueOf(lineData[2]));
            lexicon.put(term, termInfo);
            try
            {
                line = reader.readLine();
            }catch (IOException ex)
            {
                System.err.println("IO Error reading "+pathToLexicon);
                line= null;
            }
        }
    }


    /**
     * write the terms to a lexicon file
     * @param terms
     */

    public void saveLexicon(Map<String, TermInfo> terms)
    {
        String lineSeparator = System.getProperty("line.separator");
        StringBuilder stringBuilder = new StringBuilder();

        //first save the inverted list
        //convert the doc map to a string using the format  docIndex,docNo\n
        BufferedWriter writer = null;
        try
        {
            writer = new BufferedWriter( new FileWriter(LexiconFileName));
            for (Map.Entry<String, TermInfo> entry : terms.entrySet())
            {
                String term = entry.getKey();
                TermInfo termInfo = entry.getValue();

                stringBuilder.append(term);
                stringBuilder.append(",");
                stringBuilder.append(termInfo.getDocumentFrequency());
                stringBuilder.append(",");
                stringBuilder.append(termInfo.getInvListFilePosition());
                stringBuilder.append(lineSeparator);
                writer.write(stringBuilder.toString()) ;

                //reset string builder
                stringBuilder.setLength(0);

            }

        }
        catch (IOException ex)
        {
            System.err.println("IO Error saving "+ LexiconFileName);

        }
        finally
        {
            try
            {
                if (writer != null)
                    writer.close( );
            }
            catch (IOException ex)
            {
                System.err.println("IO Error saving "+ LexiconFileName);

            }
        }
    }


}
