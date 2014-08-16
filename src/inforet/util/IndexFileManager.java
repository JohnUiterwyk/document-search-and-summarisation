package inforet.util;

import inforet.module.Posting;
import inforet.module.TermInfo;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
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
     * write the postings to a file, saving the position of each postings list
     * this now writes just integers for space saving
     * also makes it possible to jump to a specific position
     * for later use in writing out the lexicon
     * @param terms
     */
    private void saveInvList(Map<String, TermInfo> terms)
    {
        String lineSeparator = System.getProperty("line.separator");

        //first save the inverted list
        //convert the doc map to a string using the format  docIndex,docNo\n
        DataOutputStream output = null;
        long currentFilePosition =0;
        try
        {
            output = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(InvListFileName)));
            for (Map.Entry<String, TermInfo> entry : terms.entrySet())
            {
                TermInfo termInfo = entry.getValue();
                List<Posting> postings = termInfo.GetPostings();
                //save the current length of the file as the read position
                termInfo.setInvListFilePosition(currentFilePosition);
                entry.setValue(termInfo);

                for (Posting posting: postings)
                {

                    output.writeInt(posting.docId);
                    output.writeInt(posting.withinDocFrequency);
                    currentFilePosition+=(Integer.SIZE*2)/ Byte.SIZE;
                }

                //save the current file line number for use with lexicon file

                //reset string builder

            }

        }
        catch (IOException ex)
        {

        }
        finally
        {
            try
            {
                if (output != null)
                    output.close( );
            }
            catch (IOException ex)
            {

            }
        }
    }

    /**
     * write the terms to a lexicon file
     * @param terms
     */
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
                stringBuilder.append(termInfo.getInvListFilePosition());
                stringBuilder.append(lineSeparator);
                writer.write(stringBuilder.toString()) ;

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
        Map<String,TermInfo> lexicon = new HashMap<String, TermInfo>();

        //load the file
        FileReader fileReader = null;
        try
        {
            fileReader  = new FileReader(pathToLexicon);
        }catch (FileNotFoundException ex)
        {
            System.err.print(ex.getMessage());
        }
        BufferedReader reader = new BufferedReader(fileReader);
        String line = null;

        //read the first line
        try
        {
            line = reader.readLine();
        }catch (IOException ex)
        {
            System.err.print(ex.getMessage());
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
                System.err.print(ex.getMessage());
                line= null;
            }
        }

        return lexicon;
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
     * get s list of postings using the given term info and inv list file
     * @param termInfo
     * @param invListFileName
     * @return
     */
    public List<Posting> getPostings(TermInfo termInfo,String invListFileName)
    {

        List<Posting> postings = new ArrayList<Posting>();

        DataInputStream inputStream = null;
        try
        {
            inputStream  = new DataInputStream(new FileInputStream(invListFileName));
            inputStream.skip(termInfo.getInvListFilePosition());
            for (int i = 0; i < termInfo.getDocumentFrequency(); i++)
            {
                int docId = inputStream.readInt();
                Posting posting = new Posting(docId);
                posting.withinDocFrequency = inputStream.readInt();
                postings.add(posting);
            }
            inputStream.close();
        }catch(FileNotFoundException ex)
        {
            System.err.println(ex.getMessage());
        } catch (EOFException ex)
        {
            System.err.println(ex.getMessage());
        } catch (IOException ex)
        {
            System.err.println(ex.getMessage());
        }catch (Exception ex)
        {
            System.err.println(ex.getMessage());
        }
        return postings;

    }


}
