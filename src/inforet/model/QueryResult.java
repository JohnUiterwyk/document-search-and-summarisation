package inforet.model;

import inforet.model.Document;

/**
 * Created by johnuiterwyk on 20/09/2014.
 */
public class QueryResult
{
    private String queryLabel = "";
    private Integer docIndex = -1;
    private Integer rank = 0;
    private Float similarityScore = 0.0f;
    private String summaryQB  = "";
    private String summaryNQB = "";

    private Document doc = null;
}
