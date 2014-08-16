package inforet.module;
import inforet.controller.StopListController;

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
        //Ignore entry if it is a stop word.
        if(StopListController.isEnabled() && StopListController.isStopWord(term)){
            return;
        }
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
