package inforet.model;

import inforet.module.Posting;
import inforet.module.TermInfo;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by johnuiterwyk on 20/09/2014.
 */
public class InvertedList
{
    public InvertedList()
    {

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
                Map<Integer, Integer> postingsMap = termInfo.GetPostings();
                //save the current length of the file as the read position
                termInfo.setInvListFilePosition(currentFilePosition);
                entry.setValue(termInfo);

                for (Map.Entry<Integer, Integer> posting : postingsMap.entrySet())
                {

                    output.writeInt(posting.getKey().intValue());
                    output.writeInt(posting.getValue().intValue());
                    currentFilePosition+=(Integer.SIZE*2)/ Byte.SIZE;
                }

                //save the current file line number for use with lexicon file

                //reset string builder

            }

        }
        catch (IOException ex)
        {
            System.err.println("IO Error saving "+ InvListFileName);

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
                System.err.println("IO Error saving "+ InvListFileName);

            }
        }
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
                Posting posting = new Posting();
                posting.docId = inputStream.readInt();
                posting.withinDocFrequency = inputStream.readInt();
                postings.add(posting);
            }
            inputStream.close();
        }catch(FileNotFoundException ex)
        {
            System.err.println(invListFileName+" is not found");
        } catch (EOFException ex)
        {
        } catch (IOException ex)
        {
            System.err.println("IO Error reading "+invListFileName);
        }catch (Exception ex)
        {
        }
        return postings;

    }
}
