package inforet.util;

import inforet.module.Posting;
import inforet.module.TermInfo;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by johnuiterwyk on 16/08/2014.
 */
public class IndexFileManager {
    private static String LexiconFileName = "lexicon";
    private static String InvListFileName = "invlists";


    /**
     * save the term/termInfo map to a lexicon file and a inverted list file
     * @param terms the hash map of terms from the indexing module
     */
    public void saveToDisk(Map<String, TermInfo> terms)
    {
        this.saveInvList(terms);
        this.saveLexicon(terms);


        //store the line number in each terminfo
        //then save the lexicon

    }

    /**
     * write the postings to a file, saving the line number to the term info
     * for later use in writing out the lexicon
     * @param terms
     */
    private void saveInvList(Map<String, TermInfo> terms)
    {
        String lineSeparator = System.getProperty("line.separator");
        StringBuilder stringBuilder = new StringBuilder();

        //first save the inverted list
        //convert the doc map to a string using the format  docIndex,docNo\n
        BufferedWriter writer = null;
        try
        {
            writer = new BufferedWriter( new FileWriter(InvListFileName));
            int lineCounter = 0;
            for (Map.Entry<String, TermInfo> entry : terms.entrySet())
            {
                TermInfo termInfo = entry.getValue();
                List<Posting> postings = termInfo.GetPostings();
                for (Posting posting: postings) {

                    stringBuilder.append(posting.docId);
                    stringBuilder.append(",");
                    stringBuilder.append(posting.withinDocFrequency);
                    if(postings.indexOf(posting) != postings.size() -1)
                    {
                        stringBuilder.append(",");
                    }
                }

                stringBuilder.append(lineSeparator);
                writer.write(stringBuilder.toString());

                //save the current file line number for use with lexicon file
                termInfo.setInvListLineNum(lineCounter);
                entry.setValue(termInfo);
                lineCounter++;

                //reset string builder
                stringBuilder.setLength(0);

            }

        }
        catch (IOException ex)
        {

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

            }
        }
    }

    private void saveLexicon(Map<String, TermInfo> terms)
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
                stringBuilder.append(termInfo.getInvListLineNum());
                stringBuilder.append(lineSeparator);
                writer.write(stringBuilder.toString());

                //reset string builder
                stringBuilder.setLength(0);

            }

        }
        catch (IOException ex)
        {

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

            }
        }
    }

    /**
     * load a lexicon file
     * @param pathToLexicon the path to the lexicon file to load
     * @return a hash map of term, TermInfo
     */
    public Map<String,TermInfo> loadLexicon(String pathToLexicon)
    {
        return null;
    }


    /**
     * load the inverted index file, but dont read it
     * @return a boolean indicating if the load was successful
     */
    public Boolean loadInvIndexFile()
    {
        //get the file reader ready
        return null;
    }

    /**
     * get a list of the postings at a particular line number in the inv lists file
     * @param lineNum
     * @return
     */
    public List<Posting> getPostingAtLine(int lineNum)
    {
        List<Posting> postings = new ArrayList<Posting>();
        return null;

    }


}
