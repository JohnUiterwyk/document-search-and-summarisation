package inforet.module;

/**
 * Created by johnuiterwyk on 12/08/2014.
 */
public class Posting {
    public int docId = -1;
    public int withinDocFrequency = 0;
    public Posting(int docId)
    {

        this.docId = docId;
        this.withinDocFrequency++;
    }
}
