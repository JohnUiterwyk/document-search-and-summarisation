package inforet.module;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by johnuiterwyk on 14/08/2014.
 * http://stackoverflow.com/questions/46898/how-do-i-iterate-over-each-entry-in-a-map
 *
 */
public class IndexingModule {

    private Map<String, TermInfo> terms = new HashMap<String, TermInfo>();
    private int termCount = 0;

    public IndexingModule() {
    }

    public void addTerm(String term, int docId)
    {
        //check for word in map
        if(term.length() > 1)
        {
            if(terms.containsKey(term) == false) {

                termCount++;
                terms.put(term, new TermInfo(docId));
            }else
            {
                TermInfo termInfo = terms.get(term);
                termInfo.addOccurance(docId);
                terms.put(term, termInfo);
            }
        }
    }


    public int getTermCount()
    {
        return termCount;
    }
}
