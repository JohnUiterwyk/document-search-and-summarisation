package inforet.module;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by johnuiterwyk on 14/08/2014.
 * http://stackoverflow.com/questions/46898/how-do-i-iterate-over-each-entry-in-a-map
 *
 */
public class IndexingModule {

    private Map<String, TermInfo> terms = new HashMap<String, TermInfo>(300000);
    private int termCount = 0;

    public IndexingModule() {
    }

    /***
     * Adds a term into the Lexicon.
     * This method also increments the corresponding term occurrence count in the
     * inverted index.
     *
     * @param term
     * @param docId
     */
    public void addTerm(String term, Integer docId)
    {
        //check for word in map
        if(term.length() > 1)
            if (terms.containsKey(term) == false) {

                termCount++;
                TermInfo termInfo = new TermInfo();
                termInfo.addOccurance(docId);
                terms.put(term, termInfo);
            } else {
                TermInfo termInfo = terms.get(term);
                termInfo.addOccurance(docId);
                terms.put(term, termInfo);
            }
    }


    public  Map<String, TermInfo> getTerms()
    {
        return terms;
    }
    public int getTermCount()
    {
        return termCount;
    }
}
