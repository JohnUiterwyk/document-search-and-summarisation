package inforet.module;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by johnuiterwyk on 12/08/2014.
 */
public class TermInfo {
    private int invlistLineNum = -1;
    public int docFrequency = 0;
    private List<Posting> postings = new ArrayList<Posting>();
    public TermInfo(int docId)
    {
        this.addOccurance(docId);
    }
    public void addOccurance(int docId)
    {
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
            docFrequency++;
        }

    }
}
