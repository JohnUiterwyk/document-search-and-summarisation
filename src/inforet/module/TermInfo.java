package inforet.module;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by johnuiterwyk on 12/08/2014.
 */
public class TermInfo {


    private long invListFilePosition = -1;
    private int postingHashMapSize = 8;

    private int documentFrequency = 0;
    private Map<Integer, Integer>  postings = null;
    public TermInfo()
    {
    }

    /**
     * add a term occurance for the given doc id.
     * This searches the existing posting,
     * if a posting is found, the within doc freq is incremented
     * if a posting is not found, a new posting is added
     *
     * @param docId
     */
    public void addOccurance(Integer docId)
    {
        //if the posting table doesnt exist, create a new one
        if(postings == null)
        {
            postings  = new HashMap<Integer, Integer>(postingHashMapSize);
        }

        //look up the docIndex
        Integer withinDocFreq = postings.get(docId);
        //if a value was not found, create a value
        if(withinDocFreq == null)
        {
            withinDocFreq = 0;
            this.documentFrequency++;
        }
        //increment the within doc freq and put it back in the hashmap
        withinDocFreq++;
        postings.put(docId,withinDocFreq);
    }


    public Map<Integer, Integer> GetPostings()
    {
        return postings;
    }

    public int getDocumentFrequency()
    {
        return this.documentFrequency;
    }

    public void setDocumentFrequency(int documentFrequency)
    {
        this.documentFrequency = documentFrequency;
    }


    public long getInvListFilePosition() {
        return invListFilePosition;
    }

    public void setInvListFilePosition(long invListFilePosition) {
        this.invListFilePosition = invListFilePosition;
    }
}
