package inforet.test;

import inforet.controller.IndexAppController;
import inforet.model.Document;
import inforet.model.DocumentCollection;

import java.util.HashMap;

/**
 * Created by johnuiterwyk on 23/09/2014.
 */
public class TestController {
    public TestController()
    {
        //test index
        String[] indexArgsArray = {"-s","data/stoplist", "data/latimes_med.txt"};
        IndexAppController indexApp = new IndexAppController(indexArgsArray);

        //test document collection
        DocumentCollection docCollection = new DocumentCollection();
        docCollection.setPathToCollection("data/latimes_med.txt");
        docCollection.loadFromMapFile("map");
        Document doc = docCollection.getDocumentByIndex(7,true);
        System.out.print(doc.getBodyText());
    }
}
