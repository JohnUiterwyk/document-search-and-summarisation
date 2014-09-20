package inforet.model;

/**
 * Created by johnuiterwyk on 20/09/2014.
 */
public class DocumentMapEntry {
    public Integer index;
    public String identifier;
    public long fileOffset;
    public Float weight;
    public DocumentMapEntry(Integer index, String identifier, Float weight, long fileOffset)
    {
        this.index = index;
        this.identifier = identifier;
        this.weight = weight;
        this.fileOffset = fileOffset;
    }
}
