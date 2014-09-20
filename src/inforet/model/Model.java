package inforet.model;

import java.util.*;

/**
 * Created by johnuiterwyk on 20/09/2014.
 */
public class Model
{
    private DocumentCollection documentCollection = new DocumentCollection();

    public void Model()
    {

    }

    public Document getDocumentByIndex(Integer index)
    {
        return getDocumentByIndex(index, false);
    }
    public Document getDocumentByIndex(Integer index, Boolean textRequired)
    {
        return documentCollection.getDocumentByIndex(index,textRequired);
    }
    public DocumentCollection getDocumentCollection()
    {
        return documentCollection;
    }

}
