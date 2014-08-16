package inforet.module;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by johnuiterwyk on 12/08/2014.
 */
public class TermInfo {


    private int invListLineNum = -1;


    private int documentFrequency = 0;
    private List<Posting> postings = null;
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
    public void addOccurance(int docId)
    {
        if(postings == null)
        {
            postings  = new ArrayList<Posting>();
        }
        Boolean found = false;

        for(Posting posting: postings)
        {
            if(posting.docId == docId)
            {
                found = true;
                posting.withinDocFrequency++;
                break;
            }
        }
        if(!found)
        {
            postings.add(new Posting(docId));
            this.documentFrequency++;
        }
    }

    /**
     * This sets the list of postings.
     * @param postings
     */
    public void setPostings(List<Posting> postings)
    {
        this.postings = postings;
    }

    public List<Posting> GetPostings()
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


    public int getInvListLineNum() {
        return invListLineNum;
    }

    public void setInvListLineNum(int invListLineNum) {
        this.invListLineNum = invListLineNum;
    }
}
