package inforet.model;

/**
 * Created by johnuiterwyk on 26/09/2014.
 */
public class Model {
    private DocumentCollection documents = new DocumentCollection();
    private Lexicon lexicon = new Lexicon();
    private InvertedLists invertedList = new InvertedLists();
    private StopList stopListModule = null;

    public Model()
    {

    }

    public void loadCollectionFromMap(String pathToMap)
    {
        documents.loadFromMapFile(pathToMap);
    }

    public DocumentCollection getDocumentCollection() {
        return documents;
    }

    public void setPathToCollection(String pathToCollection)
    {
        documents.setPathToCollection(pathToCollection);
    }

    public void loadStopList(String pathToStopList)
    {
        stopListModule = new StopList();
        stopListModule.initStopList(pathToStopList);
    }

    public StopList getStopListModule() {
        return stopListModule;
    }

    public void loadLexicon(String pathToLexicon)
    {
        lexicon.loadLexicon(pathToLexicon);
    }

    public Lexicon getLexicon() {
        return lexicon;
    }

    public void loadInvertedList(String pathToInvList)
    {
        invertedList.loadInvertedList(pathToInvList);
    }

    public InvertedLists getInvertedList() {
        return invertedList;
    }




}
