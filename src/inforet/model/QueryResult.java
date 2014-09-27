package inforet.model;


/**
 * Created by johnuiterwyk on 20/09/2014.
 */
public class QueryResult implements Comparable<QueryResult>
{
    private String queryLabel = "";
    private int rank = 0;
    private float similarityScore = 0.0f;
    private String summaryQB  = "";
    private String summaryNQB = "";
    private Document doc = null;

    public String getQueryLabel() {
        return queryLabel;
    }

    public void setQueryLabel(String queryLabel) {
        this.queryLabel = queryLabel;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public float getSimilarityScore() {
        return similarityScore;
    }

    public void setSimilarityScore(float similarityScore) {
        this.similarityScore = similarityScore;
    }

    public String getSummaryQB() {
        return summaryQB;
    }

    public void setSummaryQB(String summaryQB) {
        this.summaryQB = summaryQB;
    }

    public String getSummaryNQB() {
        return summaryNQB;
    }

    public void setSummaryNQB(String summaryNQB) {
        this.summaryNQB = summaryNQB;
    }

    public Document getDoc() {
        return doc;
    }

    public void setDoc(Document doc) {
        this.doc = doc;
    }

    /***
     * Compares the query result to another query result based on the similarity score
     * @param compareResult
     * @return
     */
    @Override
    public int compareTo(QueryResult compareResult)
    {
        // lower score gets moved to the bottom of list
        // higher score gets moved to the top of the list
        if(this.getSimilarityScore() < compareResult.getSimilarityScore())
        {
            return 1;
        }else if(this.getSimilarityScore() > compareResult.getSimilarityScore())
        {
            return -1;
        }
        return 0;
    }
}
